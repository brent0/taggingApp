/*
 * TAGGING APPLICATION
 *
 *  This is an application that allows the creation of maps and letters from data stored in a database. It also
 *  allows data to be entered into these databases.
 *  It uses the following open source libraries to help with these tasks:
 *      GEOTOOLS -  Open Source Java GIS Toolkit. The GeoTools library is made available under the LGPL license found at
 *       http://www.gnu.org/licenses/lgpl-2.1.html. Geotools is used in this application to render georeferenced images
 *      ITEXT -     Open Source library available under the terms of the GNU Affero General Public License version 3
 *                  available for viewing at http://www.gnu.org/licenses/agpl.html. Aids in the creation of PDF files.
 *                  This is what is used to create the final document to be sent to those who return tags.
 *
 *
 *  Created on Dec 2, 2010, 11:00:06 AM
 */


/*
 * mapFrame.java is the component of the tagging application that handels the bulk of the logical opperations associated
 * with generating the rewarded maps and text. Various class objects are declared to help organize and store the data that
 * is to be computed.
 */
/**
 * Created By:
 *     Brent Cameron
 *     107 Gibbons Rd
 *     Huntington NS
 *     B1K 1V1
 *     (902) 727- 2157
 * For:
 *     Department of Fisheries and Oceans Canada
 *     Snow Crab Science
 */
//import statements that load the nessesary libraries into memory
import java.awt.RenderingHints;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.*;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;



import javax.imageio.ImageIO;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.*;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.map.DefaultMapContext;
import org.geotools.map.MapContext;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Fill;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.data.shapefile.ShapefileDataStore;

import org.opengis.filter.FilterFactory;

import com.vividsolutions.jts.geom.*;

// capdata object used to store each individual capture. This object is usually
// linked in a list with other capdata objects. Each person object has a list of these
// capture objects within its data.
class capdata {

    String who;
    String caplat;
    String caplong;
    String capdat;
    String captag;
    String capcode;
    String releasecode;
    LineString cappath;
    }

// pix object with a constructor. This object is used when locating a good lable placement. These object
// are linked and looked at in sequence untill a good fit is found
class pix {

    int up;
    int over;

    pix(int a, int b) {
        up = a;
        over = b;
    }
}

// Person object used to keep track of a person and what conditions are associated with them when creating
// the letter
class person {

    String name;
    int before = 0;
    int after = 0;
    LinkedList<capdata> caps;
    LinkedList<String> years;
    boolean mult = false;
    boolean paraB;
    boolean paraC;
    boolean paraD;
    boolean paraE;
    boolean paraF;
    boolean MTC_IDNR = false;
}

class person2 {

    String name;
    String address;
    LinkedList<samples> samps;
}

class samples {

    String samlat;
    String samlong;
    String samdat;
    String tag;
    LinkedList<captures> caps;
}

class captures {

    String caplat;
    String caplong;
    String capdat;
    String captag;
    String capcode;
    }

// This printform object keep track of the various data associated with each map
class printForm {

    String mapname;
    Envelope zoomToo;
    LinkedList<LineString> linelis;
    LinkedList<LineString> fields;
    LinkedList<String> labelnames;
    LinkedList<LineString> cappath;
}

//The primary class of mapFrame.java. The functions within this class are called to generated the maps and text
public class mapFrame extends tagFrame {
public static String forarea;
    static LinkedList<person> peop = new LinkedList<person>(); // Creates a linkedlist of person objects. A loadPersonData call
    // from tagFrame populates this list with the desired person objects
    // The loadPersonData method is declared below
    private static int wid = 800; //The width of the main map, A change to this will most likely require a change to
    //other positions such as the inset map and thet axis plotting
    private static int hei = 800; //The height of the main map. Again, changing this may affect the positioning of the other map objects
    public static MapContext mainmap = new DefaultMapContext(); // Declareing the main map
    //   public static GraphicEnhancedMapContext mainmap = new GraphicEnhancedMapContext();
    public static MapContext insetmap = new DefaultMapContext(); // Declaring the inset map
    public static int numofmaps = 0;
        public static int mapssofar = 1;
    public static String prevtitle = "";
    static StyleFactory styleFactory = CommonFactoryFinder.getStyleFactory(null);
    static FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory(null);

    // This method is called to generate the letters associated with each person. A call to loadPersonData must be called first
    // in order to load person data.
    public static void loadtext() throws SQLException{


        String base = layout.getText(); // Stores the main text form shown in the text frame on the rewards tab
        // The fllowing are variables used to keep track of the different circumstances for each person such as
        // singular or plural or things that are unique to the letter such as the persons name
        String name;
        String tagtags;
        String waswerewereall;
        String yeartaggedyearstaggedseasonseasons;
        String oneaftersomeafter;
        String wasawerea;
        String thisathesea;
        String onebeforesomebefore;
        String wasbwereb;
        String filenam;

        //Sets up what paragraphs are to be used for release type. Initialyl all set to false as none have passed the conditions to be written
        boolean paraRelease = false;
        boolean paraRetained = false;
        boolean paraMix = false;
        boolean paraUnknown = false;

        // Loop through each person and generate a letter based on the various criteria
        for (int i = 0; i < peop.size(); i++) {

            person temp = peop.get(i);

            tagFrame.jProgress.setString("Creating letter: " + temp.name);
            tagFrame.jProgress.paintImmediately(0, 0, tagFrame.jProgress.getBounds().width, tagFrame.jProgress.getBounds().height);

            String address = "";
            String na = temp.name;
      
            //Attempt to query the database to get this particular persons address information
            
                /*
                Class.forName(conname);
                String url = dataurl;

                Connection conn;
                if (localdata) {
                    conn = DriverManager.getConnection(url);
                } else {
                    conn = DriverManager.getConnection(url, user, pass);
                }

                Statement st = conn.createStatement();
                */
                String add = "";
                String postal = "";
                String townee = "";

                String toda = "SELECT * FROM people WHERE name = '" + na + "';";
                
                 Connection conn = null;
       
        try{
          Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            Statement st = conn.createStatement();
                ResultSet back = st.executeQuery(toda);
                while (back.next()) {
                    //These variables store the address
                    add = back.getString("civic");
                    townee = back.getString("town");
                    String prov = back.getString("prov");
                    postal = back.getString("post");
                    if (!(prov.equals("") || prov.equals("none"))) {
                        townee = townee.concat(" ").concat(prov);
                    }
                }
              //  conn.close(); //close connection to database
                back.close(); //close the resultset returned by the database
st.close();
                //format the address
                address = address.concat(na);
                address = address.concat("\n");
                if (add != null) {

                    // Split the address on any commas and put the rest on a new line,
                    // This will solve the problem of all address info in one line seperated by commas
                    String[] a = add.split(",");
                    for (int r = 0; r < a.length; r++) {
                        address = address.concat(a[r].trim());
                        address = address.concat("\n");
                    }

                }
                // Want to trim leading and trailing white spaces so that everything lines up nicely
                if (townee != null) {
                    address = address.concat(townee.trim());
                    address = address.concat("\n");
                }
                if (postal != null) {
                    address = address.concat(postal.trim());
                    address = address.concat("\n");
                }

            } catch (Exception e) {

                System.err.println("Got an exception while finding persons address!");
                System.err.println(e.getMessage());
            }finally{
            if(conn != null && !conn.isClosed()){
                
                conn.close();
            }
            
        }

            //Format the address so that it is initiall over so many spaces, if more spaces needed, add the same amount to the next two lines of code
            address = "                                                                                      ".concat(address);
            address = address.replaceAll("\\n", "\n                                                                                      ");
            address = address.concat("\n\f");

            //Reinit to false since the previous person would have changed some of thes to true
            paraRelease = false;
            paraRetained = false;
            paraMix = false;
            paraUnknown = false;

            //Set up the location to print the letters
            filenam = datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("Letters/").concat(forarea+yearfld.getText());
            if (rewardrun.isSelected()) {
                filenam = filenam.concat("unrewarded");
            }

            File destination = new File(filenam);
            // if dir location does not exist create one
            
            if (!destination.exists()) {
                destination.mkdir();
            }

            // Name the file to be written to the location
            filenam = filenam + "//" + temp.name + ".txt";
            name = temp.name;


            //Check the conditions associated with the current person. These conditions were give in the loadPersonData method
            if (temp.mult) {
                tagtags = "tags";
            } else {
                tagtags = "tag";
            }

            if (!temp.mult) {
                waswerewereall = "was";
            } else if (temp.years.size() > 1) {
                waswerewereall = "were";
            } else {
                waswerewereall = "were all";
            }

            // Set up a variable to append the years
            String stemp = "";
            // Append the years
            for (int j = 0; j < temp.years.size(); j++) {
                //if else statements to keep track of where in the list the year is added so that the english is proper
                if (j == temp.years.size() - 1 && j != 0) {
                    stemp = stemp + " and " + temp.years.get(j);
                } else if (j == 0) {
                    stemp = stemp + temp.years.get(j);
                } else {
                    stemp = stemp + ", " + temp.years.get(j);
                }
            }

            // Use season or seasons check. For proper english
            if (temp.years.size() > 1) {
                yeartaggedyearstaggedseasonseasons = "seasons " + stemp;
            } else {
                yeartaggedyearstaggedseasonseasons = stemp + " season";
            }
            if(axxxx.isSelected()) yeartaggedyearstaggedseasonseasons = stemp;
            // proper English
            if (temp.after > 1) {
                oneaftersomeafter = "Some";
                wasawerea = "were";
                thisathesea = "these";
            } else {
                oneaftersomeafter = "One";
                wasawerea = "was";
                thisathesea = "this";
            }
            if (temp.before > 1) {
                onebeforesomebefore = "Some";
                wasbwereb = "were";
            } else {
                onebeforesomebefore = "One";
                wasbwereb = "was";
            }


            // Loop that goes through each persons capture data setting what release paragraphs should be 
            // used based on what was reported for each release. A latter check will be needed if more
            // than one release paragraph type selected than use the mixed paragraph
            for (int j = 0; j < temp.caps.size(); j++) {

                capdata d = temp.caps.get(j);
                if (d.releasecode != null && d.capcode.equals("You Captured")) {
                    if (d.releasecode.matches("1.0")) {
                        paraRelease = true;
                    }
                    if (d.releasecode.matches("2.0")) {
                        paraRetained = true;
                    }
                    if (d.releasecode.matches("3.0")) {
                        paraUnknown = true;
                    }
                    if (d.releasecode.matches("1")) {
                        paraRelease = true;
                    }
                    if (d.releasecode.matches("2")) {
                        paraRetained = true;
                    }
                    if (d.releasecode.matches("3")) {
                        paraUnknown = true;
                    }

                }

            }

            //Additional check so no two contrdictory paragraphs are written
            if (paraRelease && paraRetained) {
                paraMix = true;
                paraRelease = false;
                paraRetained = false;


            }
            // Paragraph that deals with unknown release types is used if any of the persons capture data
            // was unknown. This condition is somewhat apparent in the unknown paragraph generated
            if (paraUnknown) {
                paraMix = false;
                paraRelease = false;
                paraRetained = false;

            }

            //Variables used to break down, store and modify the sections of patragraphs used in the template(base)
            //getBetween method simply grabs all word in base that are between the specified tags
            String myinfo = getBetween(base, "PARAGRAPH A"); // between <PARAGRAPH A> and </PARAGRAPH A>
            String intro = getBetween(base, "PARAGRAPH B");
            intro = intro.replaceAll("<name>", "Dear ".concat(name.split(" ")[0])); // add persons name
            intro = intro.replaceAll("<tag/tags>", tagtags); // add formatted english
            String info = getBetween(base, "PARAGRAPH info");
            if(axxxx.isSelected()) info = "The tagged crab you caught <was/were/wereall> tagged in <yeartagged/yearstagged/season/seasons>.";
            info = info.replaceAll("<was/were/wereall>", waswerewereall);
            info = info.replaceAll("<yeartagged/yearstagged/season/seasons>", yeartaggedyearstaggedseasonseasons);

            //Variable and condition to tell whether this person caught a tag that was captured before
            //The temp.before was stored in the persons data in the laodPersonData method
            String capbfor = "";
            if (temp.before > 0) {
                capbfor = getBetween(base, "PARAGRAPH capturedbefore");
                capbfor = capbfor.replaceAll("<onebefore/somebefore>", onebeforesomebefore);
                capbfor = capbfor.replaceAll("<wasb/wereb>", wasbwereb);
            }
            //Variable and condition to tell whether a tag this person released was again recaptured
            //The temp.after was stored in the persons data in the laodPersonData method
            String capaftr = "";
            if (temp.after > 0) {
                capaftr = getBetween(base, "PARAGRAPH capturedafter");
                capaftr = capaftr.replaceAll("<oneafter/someafter>", oneaftersomeafter);
                capaftr = capaftr.replaceAll("<wasa/werea>", wasawerea);
                capaftr = capaftr.replaceAll("<this/these>", thisathesea);

            }

            //Variable and condition to tell whether both of the above conditions were true
            String capbforaftr = "";
            if (temp.after > 0 && temp.before > 0) {
                capbforaftr = getBetween(base, "PARAGRAPH capturedbeforeandafter");
                capbforaftr = capbforaftr.replaceAll("<oneafter/someafter>", oneaftersomeafter.toLowerCase());
                capbforaftr = capbforaftr.replaceAll("<wasa/werea>", wasawerea);
                capbforaftr = capbforaftr.replaceAll("<onebefore/somebefore>", onebeforesomebefore);
                capbforaftr = capbforaftr.replaceAll("<wasb/wereb>", wasbwereb);

            }

            //Variable and conditions to tell what release paragraph should be printed the logics
            //for this is shown above
            String rel = "";
            if (paraRelease) {
                rel = getBetween(base, "PARAGRAPH released");
            }
            if (paraRetained) {
                rel = getBetween(base, "PARAGRAPH notreleased");
            }
            if (paraMix) {
                rel = getBetween(base, "PARAGRAPH mixedrelret");
            }
            if (paraUnknown) {
                rel = getBetween(base, "PARAGRAPH unknownrel");
            }

            // Variables to store the closing paragraphs that are added to each letter
            String fin = getBetween(base, "PARAGRAPH final");
            String end = getBetween(base, "PARAGRAPH end");

            // Variable set up in the case that somones released a taged crab that was captured in a later year
            // or assesment but had no returns themselves. They still get a letter.
            String mtcidnr = getBetween(base, "PARAGRAPH mytagcapturedbutihavenoreturns");
            mtcidnr = mtcidnr.replaceAll("<name>", name);


            String outstring = "";

            //set up the my address with the proper spacing remember to add or subtract the same number of spaces
            // in each of the following lines of code when reformatting the output
            myinfo = myinfo.replaceAll("\\n", "\r                                                                                    ");
            myinfo = "                                                                                    ".concat(myinfo);


            //Format this paragraph
            mtcidnr = mtcidnr.replaceAll("\\n", " ");//replace all newline characters with a space
            mtcidnr = mtcidnr.replaceAll("  ", " "); // replace all double spaces with a single space
            mtcidnr = mtcidnr.replaceFirst(", ", ",\n\n"); //replace the first comma after the name with a comma and two spaces
            //for proper spacing
            mtcidnr = mtcidnr.trim();//trim any leading and trailing white space for proper allignment

            //format this paragraph in the same maner as the above paragraph
            intro = intro.replaceAll("\\n", " ");
            intro = intro.replaceAll("  ", " ");
            intro = intro.replaceFirst(", ", ",\n\n");
            intro = intro.trim();

            //More formatting
            info = info.replaceAll("\\n", " ");
            info = info.replaceAll("  ", " ");
            info = info.trim();

            capbfor = capbfor.replaceAll("\\n", " ");
            capbfor = capbfor.replaceAll("  ", " ");
            capbfor = capbfor.trim();

            capaftr = capaftr.replaceAll("\\n", " ");
            capaftr = capaftr.replaceAll("  ", " ");
            capaftr = capaftr.trim();

            capbforaftr = capbforaftr.replaceAll("\\n", " ");
            capbforaftr = capbforaftr.replaceAll("  ", " ");
            capbforaftr = capbforaftr.trim();

            rel = rel.replaceAll("\\n", " ");
            rel = rel.replaceAll("  ", " ");
            rel = rel.trim();

            fin = fin.replaceAll("\\n", " ");
            fin = fin.replaceAll("  ", " ");
            fin = fin.trim();



            // Put all the paragraphs back together in the outstring variable on the conditions
            if (temp.MTC_IDNR) { //if somone caught a tag that this person released and this person has no captures this time arround
                outstring = myinfo + "\n\n" + mtcidnr + "\n\n" + fin + "\n\n" + end;
            } else if (temp.after > 0 && temp.before > 0) { //This person both caught a crab that someone previously released and released a crab that somone again caught
                outstring = myinfo + "\n\n" + intro + "\n\n" + info + "\n\n" + capbforaftr + "\n\n" + rel + "\n\n" + fin + "\n\n" + end;
            } else if (temp.before > 0) { //Someone previously released
                outstring = myinfo + "\n\n" + intro + "\n\n" + info + "\n\n" + capbfor + "\n\n" + rel + "\n\n" + fin + "\n\n" + end;
            } else if (temp.after > 0) { // Somone caught later
                outstring = myinfo + "\n\n" + intro + "\n\n" + info + "\n\n" + capaftr + "\n\n" + rel + "\n\n" + fin + "\n\n" + end;
            } else { //neither caught before or caught later, the usuall case
                outstring = myinfo + "\n\n" + intro + "\n\n" + info + "\n\n" + rel + "\n\n" + fin + "\n\n" + end;
            }

            //Set up the return address paragraph
            String retinfo = getBetween(base, "PARAGRAPH addresslabel");

            //concat the return address with the persons address and include a formfeed and then the outstring. The form feed is
            // caught by the pdf generation code so that the address are on a seperate page
            outstring = retinfo.concat(address).concat("\n\f").concat(outstring);


            File over = new File(filenam);
            //Remove previousy named files
            if (over.exists()) {
                over.delete();
            }
            over.getParentFile().mkdirs();
            
            // Attempt to write the string to file
            try {
                PrintWriter out = new PrintWriter(new FileWriter(filenam));

                out.print(outstring);
                out.close();
                
                System.out.println("Letter saved to:  "+ filenam);
            } catch (IOException e) {
           

            }
        }//end the loop for this person, if more people will loop back

    }//end loadText method

    // Method getBetween takes a base string b and returns the words between the tags given to bet.
    // The base is passed to loadText for the GUIs rewards tab, and then loadText passes it here for
    // extraction
    public static String getBetween(String b, String bet) {
        String start = "<" + bet + ">";
        String end = "</" + bet + ">";
        int st = b.indexOf(start) + start.length();
        int en = b.indexOf(end);
        String val = b.substring(st, en);
 
        return val;
    }

    // This method is called from tagFrame when the generate map button gets pushed. This method simply adds the layers
    // to the mainmap and the inset map. Layers should be added or removed here. If you notice that some of the layers are not
    // being added to resulting maps, you may have to use a computer with more memory. Layers get dropped in memory resourses become
    // low
    public static void loadMap() throws Exception {

        tagFrame.jProgress.setString("Loading Layers");
        tagFrame.jProgress.paintImmediately(0, 0, tagFrame.jProgress.getBounds().width, tagFrame.jProgress.getBounds().height);

        File tiffFile2 = new File(datadir.concat("/").concat("bio.snowcrab/maps/rasters/").concat("/TrueLand.tif"));//Windows           
        AbstractGridCoverage2DReader rdr2 = new GeoTiffReader(tiffFile2);
 jProgress.setString("Adding Land");
        mainmap.addLayer(rdr2, statsFrame.createGreyscaleStyle(1));
        insetmap.addLayer(rdr2, getRasterStyle());

        File tiffFile = new File(datadir.concat("/").concat("bio.snowcrab/maps/rasters/").concat("bathycoulourWGS.tif"));//Windows
        AbstractGridCoverage2DReader rdr = new GeoTiffReader(tiffFile);
jProgress.setString("Adding bathemetry color");
        //mainmap.addLayer(rdr, getRasterStyle());
        insetmap.addLayer(rdr, getRasterStyle());

        File tiffFile3 = new File(datadir.concat("/").concat("bio.snowcrab/maps/rasters/").concat("801_LL_WGS84_PCT_clip.tif"));//Windows
        AbstractGridCoverage2DReader rdr3 = new GeoTiffReader(tiffFile3);
        jProgress.setString("Adding 801 raster");

        mainmap.addLayer(rdr3, getRasterStyle());
        
        jProgress.setString("Adding 801 shape");
        File shpFile = new File(datadir.concat("/").concat("bio.snowcrab/maps/shapefiles/Chart 801 Shape File/").concat("801-bord_r.shp"));//Windows

        ShapefileDataStore store = (ShapefileDataStore) FileDataStoreFinder.getDataStore(shpFile);
        store.forceSchemaCRS(DefaultGeographicCRS.WGS84);
        FeatureSource featureSource = store.getFeatureSource();

        //mainmap.addLayer(featureSource, createLineStyle(Color.BLACK, 1));

        File shpFile2 = new File(datadir.concat("/").concat("bio.snowcrab/maps/shapefiles/Chart 801 Shape File/").concat("801-bord_p.shp"));//Windows
        ShapefileDataStore store2 = (ShapefileDataStore) FileDataStoreFinder.getDataStore(shpFile2);
        store2.forceSchemaCRS(DefaultGeographicCRS.WGS84);
        FeatureSource featureSource2 = store2.getFeatureSource();

        //mainmap.addLayer(featureSource2, createLineStyle(Color.BLACK, 1));

        File shpFile3 = new File(datadir.concat("/").concat("bio.snowcrab/maps/shapefiles/Chart 801 Shape File/").concat("801-bord_n.shp"));//Windows
        ShapefileDataStore store3 = (ShapefileDataStore) FileDataStoreFinder.getDataStore(shpFile3);
        store3.forceSchemaCRS(DefaultGeographicCRS.WGS84);
        FeatureSource featureSource3 = store3.getFeatureSource();

        //mainmap.addLayer(featureSource3, createLineStyle(Color.BLACK, 1));

        File shpFile4 = new File(datadir.concat("/").concat("bio.snowcrab/maps/shapefiles/Chart 801 Shape File/").concat("801-bord_l.shp"));//Windows

        ShapefileDataStore store4 = (ShapefileDataStore) FileDataStoreFinder.getDataStore(shpFile4);
        store4.forceSchemaCRS(DefaultGeographicCRS.WGS84);
        FeatureSource featureSource4 = store4.getFeatureSource();
        //mainmap.addLayer(featureSource4, createLineStyle(Color.BLACK, 1));
jProgress.setString("Finnished loading layers");

    }

    //This method contains a fairly complex series of loops which attempt to arrange the data into the nessesary objects 
    //as to generate the data properly. Then calls methods to combine data where possible and a method to save the data
    public static void loadPics() {
 System.out.println("Begin Loading Data Sets");

 jProgress.setString("Loading");
        //Initiall set up of objects used to store data
        LinkedList<capdata> tempCap = new LinkedList<capdata>();
        LinkedList<capdata> curCap = new LinkedList<capdata>();
        capdata tempEnt;
        capdata tempEnt2;
        LinkedList<LinkedList<capdata>> info = new LinkedList<LinkedList<capdata>>();


        //The main loop that goes through each person and add the nessesary data associated with this person
        for (int x = 0; x < peop.size(); x++) {
      
            //Update status bar
            tagFrame.jProgress.setString("Organizing Data");
            tagFrame.jProgress.paintImmediately(0, 0, tagFrame.jProgress.getBounds().width, tagFrame.jProgress.getBounds().height);
            //Temporary object to store capture data
            tempCap = new LinkedList<capdata>(peop.get(x).caps);
jProgress.setString("Loading "+peop.get(x).name);
            //The next while loop and for loops remove any repeated data
            while (tempCap.size() != 0) {
                tempEnt = tempCap.getFirst();
                tempEnt.who = peop.get(x).name;
                tempCap.removeFirst();
                curCap.add(tempEnt);
            
                for (int y = 0; y < tempCap.size(); y++) {
jProgress.setString("Loading "+tempEnt.captag);

                    if (tempCap.get(y).captag.equals(tempEnt.captag)) {
                        tempEnt2 = tempCap.get(y);
                        tempEnt2.who = peop.get(x).name;
                        if (!curCap.contains(tempEnt2)) {
                            curCap.add(tempEnt2);
                        }

                        tempCap.remove(y);
                        y--;
                    }
                }
                boolean fou = false;


                for (int f = 0; f < curCap.size(); f++) {
                    capdata fir = curCap.get(f);
                    for (int g = f + 1; g < curCap.size(); g++) {
                        capdata lar = curCap.get(g);
                        //Check on date and lat equality, may wish to extend this to longitue as well
                        if (fir.capdat.equals(lar.capdat) && fir.caplat.equals(lar.caplat)) {

                            curCap.remove(g);
                            g--;

                        }

                    }
                }
jProgress.setString("Arranging data...");

                //Arrange chronologically, needed to create arrows on maps in the proper direction
                for (int f = 0; f < curCap.size(); f++) {
                    capdata fir = curCap.get(f);
                    for (int g = f + 1; g < curCap.size(); g++) {
                        capdata lar = curCap.get(g);
             
                        if (dateToInt(lar.capdat) < dateToInt(fir.capdat)) {

                            curCap.remove(g);
                            curCap.addFirst(lar);
                            fou = true;

                        }

                    }
                    if (fou) {
                        f--;
                    }
                    fou = false;
                }

                info.add(curCap);
                curCap = new LinkedList<capdata>();
            }


        } //END MAIN FOR LOOP

        LinkedList<printForm> newList = new LinkedList<printForm>();
        printForm temp = new printForm();

        //Loop through each entry calling getprintdata method
        for (int i = 0; i < info.size(); i++) {
            tagFrame.jProgress.setString("Getting map parameters");
            tagFrame.jProgress.paintImmediately(0, 0, tagFrame.jProgress.getBounds().width, tagFrame.jProgress.getBounds().height);
         
            java.util.LinkedList<capdata> iftp = info.get(i);
            
            temp = getPrintData(iftp);
      
        
            newList.add(temp);
        }
        //Combine maps where possible base on rules in function combineMaps
        newList = combineMaps(newList);

        //Loop that draws each map by calling savedata

        for (int i = 0; i < newList.size(); i++) {
            tagFrame.jProgress.setString("Drawing and saving maps");
            tagFrame.jProgress.paintImmediately(0, 0, tagFrame.jProgress.getBounds().width, tagFrame.jProgress.getBounds().height);
      
            saveData(newList.get(i));
        }




    }

    private static LinkedList<printForm> combineMaps(LinkedList<printForm> inlist) {
        LinkedList<printForm> outlist = new LinkedList<printForm>();

        printForm p = new printForm();
        printForm inner = new printForm();
        //while inlist is not empty pop top
        while (!inlist.isEmpty()) {
            tagFrame.jProgress.setString("Attempting to combine maps");
            tagFrame.jProgress.paintImmediately(0, 0, tagFrame.jProgress.getBounds().width, tagFrame.jProgress.getBounds().height);



            p = inlist.pop();

            LinkedList<LineString> tfields = new LinkedList<LineString>();

            boolean passe = true;

            //for each in  inlist
            for (int i = 0; i < inlist.size(); i++) {
                inner = inlist.get(i);

                //belong to same person
                String pwho = p.mapname.replaceAll("\\d", "");
                String inwho = inner.mapname.replaceAll("\\d", "");
                double dis = p.linelis.get(0).getLength();

                if (!inwho.equals(pwho)) {
                    passe = false;
                }
                //within a distance

                if (inner.linelis.get(0).getLength() < dis / 4) {
                    passe = false;
                }
                if (inner.linelis.get(0).getLength() > dis * 4) {
                    passe = false;
                }

                for (int k = 0; k < p.linelis.size(); k++) {

                    if (!inner.linelis.get(0).isWithinDistance(p.linelis.get(k), p.linelis.get(k).getLength() * 30)) {
                        passe = false;
                    }
                }
                if (p.linelis.size() < 3) {
                    for (int k = 0; k < p.linelis.size(); k++) {
                        if (inner.linelis.get(0).isWithinDistance(p.linelis.get(k), p.linelis.get(k).getLength() / 2)) {
                            passe = false;
                        }
                        if (inner.linelis.get(0).isWithinDistance(p.linelis.get(k), p.linelis.get(0).getLength() / 2)) {
                            passe = false;
                        }

                    }
                } else {
                    for (int k = 0; k < p.linelis.size(); k++) {
                        if (inner.linelis.get(0).isWithinDistance(p.linelis.get(k), p.linelis.get(k).getLength() * 1.5)) {
                            passe = false;
                        }
                        if (inner.linelis.get(0).isWithinDistance(p.linelis.get(k), p.linelis.get(0).getLength() * 1.5)) {
                            passe = false;
                        }

                    }
                }

                if (passe) {

                    p.zoomToo.expandToInclude(inner.zoomToo);
                    Envelope e = p.zoomToo;
                    double squ = 0;
                    if (e.getHeight() > e.getWidth()) {
                        squ = e.getHeight();
                    } else {
                        squ = e.getWidth();
                    }
                    Envelope ne = new Envelope(e.getMinX(), e.getMinX() + squ, e.getMinY(), e.getMinY() + squ);
                    p.zoomToo = ne;
                    for (int x = 0; x < inner.linelis.size(); x++) {
                        p.linelis.add(inner.linelis.get(x));
                    }
                    for (int j = 0; j < inner.fields.size(); j++) {
                        p.fields.add(inner.fields.get(j));
                    }
                    for (int j = 0; j < inner.labelnames.size(); j++) {
                        p.labelnames.add(inner.labelnames.get(j));
                    }
                    for (int j = 0; j < inner.cappath.size(); j++) {
                        p.cappath.add(inner.cappath.get(j));
                    }
                    inlist.remove(i);
                    i = i - 1;
                }



            }



            double minx = 0;
            double maxx = -360;
            double miny = 360;
            double maxy = 0;
            for (int y = 0; y < p.linelis.size(); y++) {
                for (int z = 0; z < p.linelis.get(y).getNumPoints(); z++) {


                    if (p.linelis.get(y).getCoordinateN(z).x < minx) {
                        minx = p.linelis.get(y).getCoordinateN(z).x;
                    }
                    if (p.linelis.get(y).getCoordinateN(z).x > maxx) {
                        maxx = p.linelis.get(y).getCoordinateN(z).x;
                    }
                    if (p.linelis.get(y).getCoordinateN(z).y < miny) {
                        miny = p.linelis.get(y).getCoordinateN(z).y;
                    }
                    if (p.linelis.get(y).getCoordinateN(z).y > maxy) {
                        maxy = p.linelis.get(y).getCoordinateN(z).y;
                    }
                }
            }
            Envelope e = new Envelope(minx, maxx, miny, maxy);

            Coordinate cen = e.centre();
            if (e.getHeight() > e.getWidth()) {
                double sca = e.getHeight() / 2;
                minx = minx - sca;
                miny = miny - sca;
                double sta = cen.x - e.getHeight() / 2 - sca;

                e = new Envelope(sta, sta + e.getHeight() + sca + sca, miny, miny + e.getHeight() + sca + sca);

            } else {
                double sca = e.getWidth() / 2;
                minx = minx - sca;
                miny = miny - sca;
                double sta = cen.y - e.getWidth() / 2 - sca;
                e = new Envelope(minx, minx + e.getWidth() + sca + sca, sta, sta + e.getWidth() + sca + sca);

            }


            p.zoomToo = e;

            if (p.zoomToo.getArea() < .0009) {
                p.zoomToo.expandBy(.02, .02);
            }
            
             p.zoomToo.expandBy(.02, .02);
            
            double pixelswide = 152 * 1.25;
            double pixelshigh = 13 * 1.25;


            double conx = p.zoomToo.getWidth() / wid;  //degrees per pixel
            double cony = p.zoomToo.getHeight() / hei;

            double fieldwid = conx * pixelswide;
            double fieldhei = cony * pixelshigh;

            LinkedList<pix> locs = new LinkedList<pix>();
            pix pi = new pix(6, 3);
            locs.add(pi);
            pi = new pix(6, 0);
            locs.add(pi);

            pi = new pix(-16, -80);
            locs.add(pi);
            pi = new pix(-16, 2);
            locs.add(pi);
            pi = new pix(-16, -40);
            locs.add(pi);

            pi = new pix(-16, -120);
            locs.add(pi);
            pi = new pix(2, -195);
            locs.add(pi);
            pi = new pix(-16, -195);
            locs.add(pi);
            pi = new pix(6, -80);
            locs.add(pi);

            pi = new pix(6, -195);
            locs.add(pi);
            pi = new pix(6, -120);
            locs.add(pi);

            pi = new pix(6, -40);
            locs.add(pi);
            pi = new pix(-10, 2);
            locs.add(pi);
            pi = new pix(6, 2);
            locs.add(pi);
            pi = new pix(-30, 0);
            locs.add(pi);
            pi = new pix(15, 0);
            locs.add(pi);
            pi = new pix(-30, 30);
            locs.add(pi);
            pi = new pix(-30, -30);
            locs.add(pi);

            for (int m = 0; m < p.linelis.size(); m++) {

                for (int n = 0; n < p.linelis.get(m).getNumPoints(); n++) {
                    boolean fits = false;

                    double startx = p.linelis.get(m).getCoordinateN(n).x;
                    double starty = p.linelis.get(m).getCoordinateN(n).y;
                    double b;
                    Coordinate[] labelcoords = new Coordinate[6];

                    LineString label = null;
                    int index = 0;
int searchx = 0;
int searchy = 0;

                    while (!fits && index < locs.size()) {
                 //    while (!fits){
//if(index < locs.size()) index = 0;
                        pix a = locs.get(index);

                        Coordinate c = new Coordinate(startx + (conx * a.over)+searchx, starty + (cony * a.up)+searchy);
                        labelcoords[0] = new Coordinate(c.x, c.y);
                        labelcoords[1] = new Coordinate(c.x, c.y + fieldhei);
                        labelcoords[2] = new Coordinate(c.x + fieldwid, c.y + fieldhei);
                        labelcoords[3] = new Coordinate(c.x + fieldwid, c.y - 5 * conx);
                        labelcoords[4] = new Coordinate(c.x, c.y - 5 * conx);
                        labelcoords[5] = new Coordinate(c.x, c.y);
                        GeometryFactory geometryFactory2 = JTSFactoryFinder.getGeometryFactory(null);
                        label = geometryFactory2.createLineString(labelcoords);

                     
                      //  if (!label.touches(p.linelis.get(m).getGeometryN(n)) && !p.linelis.get(m).getGeometryN(n).crosses(label)) {
                          
                            fits = true;
                            for (int l = 0; l < tfields.size(); l++) {
                                if (tfields.get(l).crosses(label) || !label.disjoint(tfields.get(l))) {
                                    fits = false;
                                }
                            }
                            if (fits) {
                                tfields.add(label);
                            }
                       // } 
                        index++;
                    //    searchx = (searchx+1) * -1;
                     //   searchy = (searchy+1) * -1;
                    }
                    if (fits == false) {
                
                        tfields.add(label);

                    }
                }
            }


            p.fields = tfields;
           
            outlist.add(p);


        }
        return outlist;
    }

    private static printForm getPrintData(LinkedList<capdata> inf) {
       

        String name = inf.get(0).who;

        printForm curr = new printForm();
        LinkedList<String> tlabelnames = new LinkedList<String>();

        name = name.replaceAll(" ", "");
       // String aa = inf.get(0).captag;
        //aa = aa.substring(0, aa.length() - 2);
        String bbb = tagFrame.yearfld.getText();
       // if(bbb.equals(""))bbb = "0000";
        String nam = name.concat("").concat(bbb);

        curr.mapname = nam;
        Coordinate coords[] = new Coordinate[inf.size()];
        double maxh = 0;
        double minh = 100;
        double maxl = 0;
        double maxr = -300;


        double x = 0;
        double y = 0;

        
        System.out.println(coords.length);
        
        for (int i = 0; i < coords.length; i++) {
            
            String a = inf.get(i).capcode;
            String d = inf.get(i).capdat;
            if(d.startsWith("00")) d= "unknown";
            String formda = "";
            try {

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date da = formatter.parse(d);
                formatter = new SimpleDateFormat("MMM dd, yyyy");
                formda = formatter.format(da);

                // curr.labelnames.add(a);
            } catch (java.text.ParseException e) {
                formda = "Date: unknown";
            }
            a = formda.concat(" ").concat(a);
        
            tlabelnames.add(a);

            y = Double.parseDouble(inf.get(i).caplat);
            x = Double.parseDouble(inf.get(i).caplong);
            Coordinate pos = new Coordinate(x, y);

            coords[i] = pos;
            if (y > maxh) {
                maxh = y;
            }
            if (y < minh) {
                minh = y;
            }
            if (x < maxl) {
                maxl = x;
            }
            if (x > maxr) {
                maxr = x;
            }
        }

        curr.labelnames = tlabelnames;
        LinkedList<LineString> t = new LinkedList<LineString>();

        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
        LineString line = geometryFactory.createLineString(coords);

        t.add(line);
        
        curr.linelis = t;

              
       
        LinkedList<LineString> tlll = new LinkedList<LineString>();
        
        
      for (int i = 0; i < inf.size(); i++) {
        
          LineString tll = inf.get(i).cappath;
            
            if(tll == null){
               
            }
            else{
              tlll.add(tll);
          
            }  
          
      }
           curr.cappath = tlll;
            
        
        double ytraveled = maxh - minh;
        double xtraveled = maxl - maxr;
        if (xtraveled < 1) {
            xtraveled = xtraveled * -1;
        }
        double scale;
        if (ytraveled > xtraveled) {
            scale = ytraveled;
        } else {
            scale = xtraveled;
        }

        scale = scale * 2;


        Envelope boundary = new Envelope(maxr + scale, maxl - scale, minh - scale, maxh + scale);
        Coordinate center = boundary.centre();
        boundary = new Envelope(center.x + scale, center.x - scale, center.y - scale, center.y + scale);

        curr.zoomToo = boundary;
        double pixelswide = 150;
        double pixelshigh = 20;
        double conx = boundary.getWidth() / wid;  //degrees per pixel
        double cony = boundary.getHeight() / hei;

        double fieldwid = conx * pixelswide;
        double fieldhei = cony * pixelshigh;

        LinkedList<pix> locs = new LinkedList<pix>();
        pix p = new pix(0, 3);
        locs.add(p);
        p = new pix(3, 0);
        locs.add(p);
        p = new pix(-24, 0);
        locs.add(p);
        p = new pix(0, -154);
        locs.add(p);
        p = new pix(-24, -154);
        locs.add(p);
          p = new pix(3, -154);
         locs.add(p);
        p = new pix(24, -154);
        locs.add(p);
            p = new pix(-24, -74);
        locs.add(p);
      
        LinkedList<LineString> tfields = new LinkedList<LineString>();

        for (int i = 0; i < inf.size(); i++) {
            boolean fits = false;
            double startx = Double.parseDouble(inf.get(i).caplong);
            double starty = Double.parseDouble(inf.get(i).caplat);

            Coordinate[] labelcoords = new Coordinate[5];

            LineString label = null;
        LinkedList<pix> tlocs = (LinkedList<pix>)locs.clone();
         //   while (!fits && !tlocs.isEmpty()) {
        int searchx = 0;
        int searchy = 0;
        while (!fits) {
            if(tlocs.isEmpty()){
                tlocs = (LinkedList<pix>)locs.clone();
            }
                pix a = tlocs.pop();

                Coordinate c = new Coordinate(startx + (conx * a.over) + searchx, starty + (cony * a.up)+ searchy);
                labelcoords[0] = new Coordinate(c.x, c.y);
                labelcoords[1] = new Coordinate(c.x, c.y + fieldhei);
                labelcoords[2] = new Coordinate(c.x + fieldwid, c.y + fieldhei);
                labelcoords[3] = new Coordinate(c.x + fieldwid, c.y);
                labelcoords[4] = new Coordinate(c.x, c.y);

                GeometryFactory geometryFactory2 = JTSFactoryFinder.getGeometryFactory(null);
                label = geometryFactory2.createLineString(labelcoords);

              
                if (!label.touches(line) && !line.crosses(label) && !label.intersects(line)) {
 
                    fits = true;
                    for (int l = 0; l < tfields.size(); l++) {
                        if (tfields.get(l).crosses(line) || tfields.get(l).intersects(line)) {
                            fits = false;
                        }
                    }
                    if (fits) {
                        tfields.add(label);
                    }

                } else {
                 
                }
searchx = (searchx+1)*-1;
searchy = (searchx+1)*-1;
            }
         if (fits == false) {
                System.out.println("Could not find good label placement for " + curr.mapname);

                tfields.add(label);


            }
        }
        curr.fields = tfields;

        return curr;

    }

    private static void saveData(printForm data) {
System.out.println("SAVING MAP");

        mainmap.setAreaOfInterest(data.zoomToo, mainmap.getCoordinateReferenceSystem());

        GTRenderer renderer = new StreamingRenderer();
        renderer.setContext(mainmap);

        Rectangle imageSize = new Rectangle(wid, hei);
        BufferedImage image = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gr = image.createGraphics();
        gr.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        gr.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        gr.setPaint(Color.WHITE);
        gr.fill(imageSize);

        renderer.paint(gr, imageSize, mainmap.getAreaOfInterest());

        Font font = new Font("Arial", Font.BOLD, 17);
        gr.setFont(font);
        gr.setStroke(new BasicStroke(3));
        gr.setColor(Color.BLACK);

        double conx = data.zoomToo.getWidth() / wid;  //degrees per pixel
        double a = 1 / conx; //1 degree is = a pixels
        double aa = .75 / conx; //45 mins is = aa pixels
        double ab = .5 / conx; //30 min is = ab pixels
        double ac = .25 / conx; //15 min is = ac pixels
        double ad = .16666666666666666666667 / conx; // 10min is = ad pixels
        double ae = .08333333333333333333333 / conx; // 5min is = ae pixels
        double af = .016666666666666666666667 / conx; // 1min is = ae pixels

        String outsca = "";
        double leng = 0;
        double degoff = 0;
        if (af < 600) {
            outsca = "01'";
            degoff = 1.00;
            leng = af;
        }
        if (ae < 600 && leng < 100) {
            outsca = "05'";
            degoff = 5.00;
            leng = ae;
        }
        if (ad < 600 && leng < 100) {
            outsca = "10'";
            degoff = 10.00;
            leng = ad;
        }
        if (ac < 600 && leng < 100) {
            outsca = "15'";
            degoff = 15.00;
            leng = ac;
        }
        if (ab < 600 && leng < 100) {
            outsca = "30'";
            leng = ab;
            degoff = 30.00;
        }
        if (aa < 600 && leng < 100) {
            outsca = "45'";
            degoff = 45.00;
            leng = aa;
        }
        if (a < 600 && leng < 100) {
            outsca = "60'";
            degoff = 60.00;
            leng = a;

        }

        int lenin = (int) leng;
        int barover = lenin;
        int nmpos = 10;
        int barstart = 10;
        if (barover < 120) {
            barstart = (120 - lenin) / 2 + 10;
            nmpos = 10;
        } else {
            barstart = 10;
            nmpos = (lenin / 2) + 10 - 60;
        }
        gr.drawLine(barstart, 760, barstart, 770);
        gr.drawLine(barstart, 770, barstart + lenin, 770);
        gr.drawLine(barstart + lenin, 760, barstart + lenin, 770);
        outsca = outsca.replaceAll("'", " nautical miles");
        gr.drawString(outsca, nmpos, 790);




        gr.setColor(Color.red);
        gr.setPaintMode();


        int x = 0;
        int y = 0;
        int xx = 0;
        int yy = 0;
        int prevxx = 0;
        int prevyy = 0;


        try {

            String mapna = data.mapname;
            String year = mapna.replaceAll("\\D", "");
    
            if(year.length()>3)
            year = year.substring(year.length() - 4, year.length());
            else year = "";
            String tofile = datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("maps/"+forarea + year);
            if (rewardrun.isSelected()) {
                tofile = tofile.concat("unrewarded");
            }
            File destination = new File(tofile);
       
            if (!destination.exists()) {
            
                destination.mkdir();
            }


            Coordinate posa;
            Coordinate posb;
            Double posx;
            Double posy;
            Double posax;
            Double posbx;
            Double posay;
            Double posby;
   
            for (int i = 0; i < data.cappath.size(); i++) {
            
for (int j = 0; j < data.cappath.get(i).getNumPoints()-1; j++) {
/*
if(j > 5){
    posx = data.cappath.get(i).getPointN(j-5).getCoordinate().x;
    posy = data.cappath.get(i).getPointN(j-5).getCoordinate().y;
}     
else if(j > 4){
    posx = data.cappath.get(i).getPointN(j-4).getCoordinate().x;
    posy = data.cappath.get(i).getPointN(j-4).getCoordinate().y;
}
else if(j > 3){
    posx = data.cappath.get(i).getPointN(j-3).getCoordinate().x;
    posy = data.cappath.get(i).getPointN(j-3).getCoordinate().y;
}     
else if(j > 2){
    posx = data.cappath.get(i).getPointN(j-2).getCoordinate().x;
    posy = data.cappath.get(i).getPointN(j-2).getCoordinate().y;
}
else if(j > 1){
    posx = data.cappath.get(i).getPointN(j-1).getCoordinate().x;
    posy = data.cappath.get(i).getPointN(j-1).getCoordinate().y;
}   
else{
    posx = data.cappath.get(i).getPointN(j).getCoordinate().x;
    posy = data.cappath.get(i).getPointN(j).getCoordinate().y;
}
  */                  
                    posax = data.cappath.get(i).getPointN(j).getCoordinate().x;
                    posay = data.cappath.get(i).getPointN(j).getCoordinate().y;
                    
                    posbx = data.cappath.get(i).getPointN(j + 1).getCoordinate().x;
                    posby = data.cappath.get(i).getPointN(j + 1).getCoordinate().y;
                    
                    x = transx(data.zoomToo.getMinX(), data.zoomToo.getMaxX(), posax, wid);
                    y = transy(data.zoomToo.getMinY(), data.zoomToo.getMaxY(), posay, hei);
                    xx = transx(data.zoomToo.getMinX(), data.zoomToo.getMaxX(), posbx, wid);
                    yy = transy(data.zoomToo.getMinY(), data.zoomToo.getMaxY(), posby, hei);

                    prevxx = transx(data.zoomToo.getMinX(), data.zoomToo.getMaxX(), prevxx, wid);
                    prevyy = transy(data.zoomToo.getMinY(), data.zoomToo.getMaxY(), prevyy, hei);



                    if (x == xx && y == yy) {
                        gr.drawLine(x - 1, y - 1, x + 1, y + 1);
                        gr.drawLine(x - 1, y + 1, x + 1, y - 1);
                    } else {
                       if(j < data.cappath.get(i).getNumPoints() - 2){
                               gr.drawLine(x, y, xx, yy);  
                       }
                    }
                }
                gr = drawArrow2(gr, x, y, xx, yy);
                   
            }
/*

            for (int i = 0; i < data.linelis.size(); i++) {
                for (int j = 0; j < data.linelis.get(i).getNumPoints() - 1; j++) {


                    posa = data.linelis.get(i).getCoordinateN(j);
                    posb = data.linelis.get(i).getCoordinateN(j + 1);
                    

                    x = transx(data.zoomToo.getMinX(), data.zoomToo.getMaxX(), posa.x, wid);
                    y = transy(data.zoomToo.getMinY(), data.zoomToo.getMaxY(), posa.y, hei);
                    xx = transx(data.zoomToo.getMinX(), data.zoomToo.getMaxX(), posb.x, wid);
                    yy = transy(data.zoomToo.getMinY(), data.zoomToo.getMaxY(), posb.y, hei);




                    if (x == xx && y == yy) {
                        gr.drawLine(x - 1, y - 1, x + 1, y + 1);
                        gr.drawLine(x - 1, y + 1, x + 1, y - 1);
                    } else {
                        gr = drawArrow(gr, x, y, xx, yy);
                    }
                }
            }

*/
            font = new Font("Arial", Font.BOLD, 15);

            gr.setFont(font);
            gr.setColor(Color.BLACK);

            ReferencedEnvelope inset = mainmap.getAreaOfInterest();
            
       
            
            Coordinate c = inset.centre();
            Coordinate toinc = new Coordinate(-62, 45.25);
            Coordinate toinc2 = new Coordinate(-65.5, 43.8);
            Coordinate toinc3 = new Coordinate(-64.5, 44.4);
        
            
            
            double exp = c.distance(toinc);
            double exp2 = c.distance(toinc2);
            double exp3 = c.distance(toinc3);
  
            
            exp = java.lang.Math.min(exp, exp2);
            exp = java.lang.Math.min(exp, exp3);

            
            
            
            double imwid = inset.getWidth();
            
            inset.expandBy(exp);

            while(imwid < inset.getWidth()/10 ){
                inset.expandBy(-.1);
            }
            
            insetmap.setAreaOfInterest(inset, mainmap.getCoordinateReferenceSystem());



            gr.setColor(Color.RED);
            gr.drawRect(3, 3, image.getWidth() - 6, image.getHeight() - 6);


            //Color DARK_YELLOW = new Color(FBFB00);
            gr.setColor(Color.BLACK);

            // draw box arround label to calibrate
            /*
            
            for(int v = 0; v<data.fields.size(); v++ ){
            LineString aaa = data.fields.get(v);
            for(int m = 0; m < aaa.getNumPoints()-1; m++ ){
        
            int xxx = transx(data.zoomToo.getMinX(), data.zoomToo.getMaxX(), aaa.getCoordinateN(m).x, wid);
            int yyy = transy(data.zoomToo.getMinY(), data.zoomToo.getMaxY(),  aaa.getCoordinateN(m).y, hei);
            int xxxx = transx(data.zoomToo.getMinX(), data.zoomToo.getMaxX(), aaa.getCoordinateN(m+1).x, wid);
            int yyyy = transy(data.zoomToo.getMinY(), data.zoomToo.getMaxY(),  aaa.getCoordinateN(m+1).y, hei);
            
            
            gr.drawLine(xxx, yyy, xxxx, yyyy);
            
            }
            }
            
             */

            while (!data.labelnames.isEmpty()) {


                x = transx(data.zoomToo.getMinX(), data.zoomToo.getMaxX(), data.fields.getFirst().getCoordinateN(0).x, wid);
                y = transy(data.zoomToo.getMinY(), data.zoomToo.getMaxY(), data.fields.getFirst().getCoordinateN(0).y, hei);

                gr.setColor(Color.WHITE);
                String labnam = data.labelnames.pop();
                gr.drawString(labnam, x + 2, y);
                gr.drawString(labnam, x - 2, y);
                gr.drawString(labnam, x, y + 2);
                gr.drawString(labnam, x, y - 2);
                gr.drawString(labnam, x + 1, y);
                gr.drawString(labnam, x - 1, y);
                gr.drawString(labnam, x, y + 1);
                gr.drawString(labnam, x, y - 1);
                gr.setColor(Color.BLACK);
                gr.drawString(labnam, x, y);
                data.fields.pop();
            }

            Rectangle imageresized = new Rectangle(900, 900);

            BufferedImage imagere = new BufferedImage(imageresized.width, imageresized.height, BufferedImage.TYPE_INT_RGB);

            Graphics2D grre = imagere.createGraphics();
            grre.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

            grre.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            grre.setPaint(Color.WHITE);
            grre.fill(imageresized);

            grre.drawImage(image, 75, 25, null);
            grre.setColor(Color.black);


            Font fontb = new Font("Arial", Font.BOLD, 12);
            grre.setFont(fontb);
            grre.setStroke(new BasicStroke(3));

            String title = data.mapname;
            title = title.replaceAll("\\d", "");

            if (title.equals(prevtitle)) {
                numofmaps++;
            } else {
                prevtitle = title;
                numofmaps = 1;
            }
            if (numofmaps < 10) {
                title = title.concat("0").concat(Integer.toString(numofmaps));
            } else {
                title = title.concat(Integer.toString(numofmaps));
            }
            
       
            tofile = tofile + "/" + title;

            grre.drawString(title, 350, 15);

            String degree = "\u00B0";
            String miny = formdd(data.zoomToo.getMinY());
            miny = miny.replaceAll(degree, "");

            double min = Double.parseDouble(miny.substring(miny.length() - 5, miny.length()));
            int deg = Integer.parseInt(miny.substring(0, miny.length() - 5));


            double starting = Math.floor(min / degoff) * degoff;
            double pixof = ((min - starting) / degoff) * leng;
            int pixoff = (int) (((min - starting) / degoff) * lenin);


            String maxy = formdd(data.zoomToo.getMaxY());
            maxy = maxy.replaceAll("\\D", "");
            double current = deg * 100 + starting;
            double limit = Double.parseDouble(maxy) / 100;
            int down = pixoff * -1;
            double downd = pixof * -1;

            String out = "";
            while (current < limit - degoff) {
                starting = starting + degoff;
                if (starting >= 60) {
                    deg = deg + 1;
                    starting = starting - 60;
                }
                current = deg * 100 + starting;
               
                String stout;
                if (starting < 10) {
                    stout = "0".concat(Double.toString(starting));
                } else {
                    stout = Double.toString(starting);
                }
                out = Integer.toString(deg).concat(degree).concat(stout).concat("N");
                down = down + lenin;
                downd = downd + leng;
                if (wid + 25 - (int) downd < wid + 25 && wid + 25 - (int) downd > 25) {
                grre.drawLine(75, wid + 25 - (int) downd, 70, wid + 25 - (int) downd);
                grre.drawString(out, 0, wid + 25 - (int) downd);
            }

            }
            String minx = formdd(data.zoomToo.getMinX());
            minx = minx.replaceAll("\\D", "");

         
            String maxx = formdd(data.zoomToo.getMaxX());
            maxx = maxx.replaceAll(degree, "");

            min = Double.parseDouble(maxx.substring(maxx.length() - 5, maxx.length()));
            deg = Integer.parseInt(maxx.substring(0, maxx.length() - 5));

            starting = Math.floor(min / degoff) * degoff;
            pixoff = (int) (((min - starting) / degoff) * lenin);
            pixof = ((min - starting) / degoff) * leng;

            current = deg * 100 + starting;
            limit = Double.parseDouble(minx) / 100;
            down = pixoff * -1;
            downd = pixof * -1;


            while (current < limit - degoff) {
                starting = starting + degoff;
                if (starting >= 60) {
                    deg = deg + 1;
                    starting = starting - 60;
                }
                current = deg * 100 + starting;
                downd = downd + leng;
                 if (wid + 75 - (int) downd > 75 && wid + 75 - (int) downd < wid + 75) {
                grre.drawLine(wid + 75 - (int) downd, wid + 25, wid + 75 - (int) downd, wid + 30);
            }
                String stout;
                if (starting < 10) {
                    stout = "0".concat(Double.toString(starting));
                } else {
                    stout = Double.toString(starting);
                }

                out = Integer.toString(deg).concat(degree).concat(stout).concat("W");

                // Create a rotation transformation for the font.
                AffineTransform fontAT = new AffineTransform();

                // get the current font
                Font theFont = grre.getFont();

                // Derive a new font using a rotatation transform
                fontAT.rotate(-Math.PI / 4.0);
                Font theDerivedFont = theFont.deriveFont(fontAT);

                grre.setFont(theDerivedFont);
if (wid + 75 - (int) downd > 75 && wid + 75 - (int) downd < wid + 75) {
                grre.drawString(out, wid + 33 - (int) downd, wid + 95);
            }
                grre.setFont(theFont);

            }

            GTRenderer renderer2 = new StreamingRenderer();

            renderer2.setContext(insetmap);


            Rectangle imageSize2 = new Rectangle(200, 200);
            BufferedImage image2 = new BufferedImage(imageSize2.width, imageSize2.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D gr2 = image2.createGraphics();
            gr2.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

            gr2.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            gr2.setPaint(Color.WHITE);
            gr2.fill(imageSize2);

            renderer2.paint(gr2, imageSize2, insetmap.getAreaOfInterest());


            renderer2.stopRendering();
            gr2.setColor(Color.black);
            gr2.drawRect(1, 1, image2.getWidth() - 2, image2.getHeight() - 2);
            x = transx(inset.getMinX(), inset.getMaxX(), data.zoomToo.getMinX(), 200);
            y = transy(inset.getMinY(), inset.getMaxY(), data.zoomToo.getMaxY(), 200);
            xx = transx(inset.getMinX(), inset.getMaxX(), data.zoomToo.getMaxX(), 200);
            yy = transy(inset.getMinY(), inset.getMaxY(), data.zoomToo.getMinY(), 200);
            gr2.setColor(Color.RED);
            gr2.drawRect(x, y, xx - x, yy - y);

            grre.drawImage(image2, 695, 5, null);

                 if(tagFrame.agulf.isSelected()) tofile = datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("maps/GULF//GULF"+mapssofar);

            mapssofar++;
        
            File fileToSave = new File(tofile + ".jpeg");
       
            if (fileToSave.exists()) {
                fileToSave.delete();
            }
            fileToSave.getParentFile().mkdirs();
            ImageIO.write(imagere, "jpeg", fileToSave);
            System.out.println("Map saved to:  "+ tofile+ ".jpeg" );
       
            gr.dispose();
            gr2.dispose();
            grre.dispose();
            tagFrame.jProgress.setValue(tagFrame.jProgress.getValue() + 1);
            Rectangle rect = tagFrame.jProgress.getBounds();
            tagFrame.jProgress.paintImmediately(0, 0, rect.width, rect.height);

        } catch (IOException e) {
            System.err.println("Could not write map to file");
            System.err.println(e.getStackTrace());
        }

    }

    public static Style getRasterStyle() {
        StyleBuilder styleBuilder = new StyleBuilder();
        RasterSymbolizer rastSymbolizer = styleBuilder.createRasterSymbolizer();
        Style style = styleBuilder.createStyle(rastSymbolizer);
        return style;
    }

    public static Image toImage(BufferedImage bufferedImage) {
        return Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
    }

    public static int transx(double min, double max, double p, int wi) {
        double dist = min - max;
        double sca = min - p;
        double scale = sca / dist;
        double x = scale * wi;
        int xi = (int) x;
        return xi;
    }

    public static int transy(double min, double max, double p, int he) {
        double dist = max - min;
        double sca = max - p;
        double scale = sca / dist;
        double x = scale * he;
        return (int) x;
    }

    public static Graphics2D drawArrow2(Graphics2D g, int x, int y, int xx, int yy) {
        float arrowWidth = 20.0f;
        float theta = 0.823f;
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        float[] vecLine = new float[2];
        float[] vecLeft = new float[2];
        float fLength;
        float th;
        float ta;
        float baseX, baseY;

        xPoints[ 0] = xx;
        yPoints[ 0] = yy;

        // build the line vector
        vecLine[ 0] = (float) xPoints[ 0] - x;
        vecLine[ 1] = (float) yPoints[ 0] - y;

        // build the arrow base vector - normal to the line
        vecLeft[ 0] = -vecLine[ 1];
        vecLeft[ 1] = vecLine[ 0];

        // setup length parameters
        fLength = (float) Math.sqrt(vecLine[0] * vecLine[0] + vecLine[1] * vecLine[1]);
        th = arrowWidth / (2.0f * fLength);
        ta = arrowWidth / (2.0f * ((float) Math.tan(theta) / 2.0f) * fLength);

        // find the base of the arrow
        baseX = ((float) xPoints[ 0] - ta * vecLine[0]);
        baseY = ((float) yPoints[ 0] - ta * vecLine[1]);

        // build the points on the sides of the arrow
        xPoints[ 1] = (int) (baseX + th * vecLeft[0]);
        yPoints[ 1] = (int) (baseY + th * vecLeft[1]);
        xPoints[ 2] = (int) (baseX - th * vecLeft[0]);
        yPoints[ 2] = (int) (baseY - th * vecLeft[1]);
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        g.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(Color.red);
        g.drawLine(x, y, xx, yy);
       
        g.drawLine(xx, yy, xPoints[ 1], yPoints[ 1]);
        g.drawLine(xx, yy, xPoints[ 2], yPoints[ 2]);
         return g;
    }
    public static Graphics2D drawArrow(Graphics2D g, int x, int y, int xx, int yy) {
        float arrowWidth = 10.0f;
        float theta = 0.423f;
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        float[] vecLine = new float[2];
        float[] vecLeft = new float[2];
        float fLength;
        float th;
        float ta;
        float baseX, baseY;

        xPoints[ 0] = xx;
        yPoints[ 0] = yy;

        // build the line vector
        vecLine[ 0] = (float) xPoints[ 0] - x;
        vecLine[ 1] = (float) yPoints[ 0] - y;

        // build the arrow base vector - normal to the line
        vecLeft[ 0] = -vecLine[ 1];
        vecLeft[ 1] = vecLine[ 0];

        // setup length parameters
        fLength = (float) Math.sqrt(vecLine[0] * vecLine[0] + vecLine[1] * vecLine[1]);
        th = arrowWidth / (2.0f * fLength);
        ta = arrowWidth / (2.0f * ((float) Math.tan(theta) / 2.0f) * fLength);

        // find the base of the arrow
        baseX = ((float) xPoints[ 0] - ta * vecLine[0]);
        baseY = ((float) yPoints[ 0] - ta * vecLine[1]);

        // build the points on the sides of the arrow
        xPoints[ 1] = (int) (baseX + th * vecLeft[0]);
        yPoints[ 1] = (int) (baseY + th * vecLeft[1]);
        xPoints[ 2] = (int) (baseX - th * vecLeft[0]);
        yPoints[ 2] = (int) (baseY - th * vecLeft[1]);
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        g.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(Color.red);
        g.drawLine(x, y, (int) baseX, (int) baseY);
        g.fillPolygon(xPoints, yPoints, 3);
        return g;
    }

    public static void loadPersonData(String year)throws SQLException {
        peop = new LinkedList<person>();
        LinkedList<capdata> ptemp = new LinkedList<capdata>();
        capdata ent;
        person p; 
        forarea = "allareas";
        if(tagFrame.axxxx.isSelected())
        forarea = "4X";
        if(tagFrame.as.isSelected())
        forarea = "SENS";
        if(tagFrame.an.isSelected())
        forarea = "NENS";
          if(tagFrame.agulf.isSelected())
        forarea = "GULF";
     
            /*
            Class.forName(conname);
            String url = dataurl;

            Connection conn;
            if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, user, pass);
            }

            Statement st = conn.createStatement();
            
             */
            String toda = "";
            if (rewardrun.isSelected()) { 
                if(!tagFrame.aall.isSelected()){
                    
                    toda = "Select * from capture join paths on capture.tag = paths.ID AND capture.date = paths.CDATE and capture.rewarded = 'N' and capture.statsarea = '"+ forarea +"';";
                //      toda = "SELECT * FROM capture where rewarded = 'N' and statsarea = '"+ forarea +"';";
                }
                else{
                   toda = "Select * from capture join paths on capture.tag = paths.ID AND capture.date = paths.CDATE and capture.rewarded = 'N';"; 
                 //toda = "SELECT * FROM capture where rewarded = 'N';";
                }
                } else {
                if(!tagFrame.aall.isSelected()){
                      toda = "Select * from capture join paths on capture.tag = paths.ID AND capture.date = paths.CDATE and capture.statsarea = '"+ forarea +"';";
                }
                else{
              toda = "Select * from capture join paths on capture.tag = paths.ID AND capture.date = paths.CDATE;";
                }
           
            }
  Connection conn = null;
      
        try{
         Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
           
            Statement st = conn.createStatement();
            ResultSet back = st.executeQuery(toda);

System.out.println(back.getFetchSize());
            boolean exists = false;
            boolean exists2 = false;

            while (back.next()) {

System.out.println(back.getRow());
                String yea = back.getString("date");
                if (yea.equals("unknown")) {
                    yea = "0";
                } else {
                    //yea = yea.split("/")[2]; TESTING NEW YEAR SELECTION
                    yea = back.getString("year");
                }

             //  if ((yea.equals(year) ||  year.equals("all"))&& !yea.equals("0")) {
 if (yea.equals(year) ||  year.equals("all") ){
                    ent = new capdata();
                    ent.capdat = back.getString("date");
if(yea.equals("0"))ent.capdat = "00/09/"+back.getString("year");
                    ent.captag = back.getString("tag");
                    ent.caplat = back.getString("lat_DD_DDDD");
                    ent.caplong = back.getString("Long_DD_DDDD");
                    ent.releasecode = back.getString("tagcode");
                    ent.capcode = "You Captured";
                    String who = back.getString("person");
                 System.out.println(ent.captag);

                    String delims = ",";
                    if(back.getString("LAT") != ""){
                    StringTokenizer stoklat = new StringTokenizer(back.getString("LAT"), delims);
                    StringTokenizer stoklon = new StringTokenizer(back.getString("LON"), delims);
                    int cnum = 1;
                    Coordinate[] labelcoords = new Coordinate[stoklat.countTokens()];
                    while(stoklat.hasMoreTokens()){
                        labelcoords[cnum-1] = new Coordinate(Double.parseDouble(stoklon.nextElement().toString()), Double.parseDouble(stoklat.nextElement().toString()));
      
                       
                        cnum++;
                        
                    }
                    
                    GeometryFactory geometryFactory2 = JTSFactoryFinder.getGeometryFactory(null);
                    ent.cappath = geometryFactory2.createLineString(labelcoords);
            
                    }
                    else{ 
                        ent.cappath = null;
                    }
                   // String whoelse = back.getString("person_B");
                    exists = false;
                    exists2 = false;
                    tagFrame.jProgress.setString("Loading " + who);
                    tagFrame.jProgress.paintImmediately(0, 0, tagFrame.jProgress.getBounds().width, tagFrame.jProgress.getBounds().height);






                    if (!peop.isEmpty()) {

                        for (int x = 0; x < peop.size() && !exists; x++) {

                            if (peop.get(x).name.equals(who)) {
                                exists = true;
                                if (!ent.caplat.equals("0")) {
                                    ptemp = peop.get(x).caps;
                                    ptemp.add(ent);
                                    peop.get(x).caps = ptemp;
                                    peop.get(x).mult = true;
                                   
                                           
                                }

                            }
                        }
                    }
                    if (!exists && !ent.caplat.equals("0")) {

                        p = new person();
                        p.name = who;
                        ptemp = new LinkedList<capdata>();
                        ptemp.add(ent);
                        p.caps = ptemp;
                        peop.add(p);

                    }
//                    if (!whoelse.equals("NA")) {
//                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                        System.out.println("!!!!                         MORE PEOPLE                      !!!!!!!");
//                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                        if (!peop.isEmpty()) {
//
//                            for (int x = 0; x < peop.size() && !exists2; x++) {
//
//                                if (peop.get(x).name.equals(whoelse)) {
//                                    exists2 = true;
//                                    if (!ent.caplat.equals("0")) {
//                                        ptemp = peop.get(x).caps;
//                                        ptemp.add(ent);
//                                        peop.get(x).caps = ptemp;
//                                        peop.get(x).mult = true;
//                                    }
//
//                                }
//                            }
//                        }
//                        if (!exists2 && !ent.caplat.equals("0")) {
//
//                            p = new person();
//                            p.name = whoelse;
//                            ptemp = new LinkedList<capdata>();
//                            ptemp.add(ent);
//                            p.caps = ptemp;
//                            peop.add(p);
//
//                        }
//
//                    }

                }

            }


st.close();
            back.close();
         //   conn.close();
        } catch (Exception e) {

            System.err.println("Got an exception!");
            e.printStackTrace();
        }finally{
            if(conn != null && !conn.isClosed()){
                
                conn.close();
            }
          
        }



        for (int x = 0; x < peop.size(); x++) {

            LinkedList<String> years = new LinkedList<String>();

            int siz = peop.get(x).caps.size();
            for (int y = 0; y < siz; y++) {

                tagFrame.jProgress.setString("Loading Samples");
                tagFrame.jProgress.paintImmediately(0, 0, tagFrame.jProgress.getBounds().width, tagFrame.jProgress.getBounds().height);
         
                
                 /*   Class.forName(conname);
                    String url = dataurl;

                    Connection conn;
                    if (localdata) {
                        conn = DriverManager.getConnection(url);
                    } else {
                        conn = DriverManager.getConnection(url, user, pass);
                    }

                    Statement st = conn.createStatement();
*/
             
                    toda = "SELECT sample_num FROM bio WHERE tag_id = " + peop.get(x).caps.get(y).captag + ";";
conn = null;
        
         String samp = "";
        try{
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, dbuser, pass);
            
            Statement st = conn.createStatement();
 
                    ResultSet back = st.executeQuery(toda);
                    back.next();
                    samp = back.getString("sample_num");
                 st.close();
                    back.close();
} catch (Exception e) {

                    System.err.println("Got an exception when adding sample!");
                    System.err.println(e.getMessage());
                }finally{
            if(conn != null && !conn.isClosed()){
               
                conn.close();
            }
            
        }
                  //  st = conn.createStatement();
                    toda = "SELECT * FROM sample WHERE sample_id = " + samp + ";";
         conn = null;
       
        try{
           
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, dbuser, pass);
            
            Statement st = conn.createStatement();
 
                    ResultSet back = st.executeQuery(toda);
                    back.next();

                    String datematch = back.getString("trip");

                    toda = "SELECT * FROM trip WHERE trip_id = " + datematch + ";";

               

                    Statement stst = conn.createStatement();
                    ResultSet backback = stst.executeQuery(toda);
                    backback.next();

                  
                    String tag = peop.get(x).caps.get(y).captag;
                    String lat = back.getString("lat_DD_DDDD");
                    String lon = back.getString("long_DD_DDDD");

                    String ye = backback.getString("date");
                    ye = ye.split("/")[2];
                  
                    if (!years.contains(ye)) {
                        years.add(ye);
                    }
       
                    ent = new capdata();
                    if (tag.contains(".")) {
                        ent.capcode = "Tag: " + tag.substring(0, tag.length() - 2);
                    } else {
                        ent.capcode = "Tag: " + tag;
                    }
                    ent.capdat = backback.getString("date");
                
if( ent.capdat.equals("unknown")) ent.capdat = "00/00/"+backback.getString("year");
                    ent.caplat = lat;
                    ent.caplong = lon;
                    ent.captag = tag;

                    if (!lat.equals("0")) {
                        ptemp = peop.get(x).caps;
                        ptemp.add(ent);
                        peop.get(x).caps = ptemp;
                    }
                    back.close();
                    backback.close();
st.close();
stst.close();
                   // conn.close();
                } catch (Exception e) {

                    System.err.println("Got an exception when adding sample!");
                    System.err.println(e.getMessage());
                }finally{
            if(conn != null && !conn.isClosed()){
               
                conn.close();
            }
          
        }
                
                 /*   Class.forName(conname);
                    String url = dataurl;

                    Connection conn;
                    if (localdata) {
                        conn = DriverManager.getConnection(url);
                    } else {
                        conn = DriverManager.getConnection(url, user, pass);
                    }

                    Statement st = conn.createStatement();
*/
System.out.println(peop.get(x).caps.get(y).captag);
System.out.println(peop.get(x).name);
             
                    toda = "Select * from capture join paths on capture.tag = paths.ID AND capture.date = paths.CDATE and capture.tag = " + peop.get(x).caps.get(y).captag + ";";
  conn = null;
        
        try{
          
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, dbuser, pass);
            
            Statement st = conn.createStatement();
 
                    ResultSet back = st.executeQuery(toda);
                    while (back.next()) {
                 
                        //UNTESTED CODE
                        boolean exi = false;
                        String per = back.getString("person");
                        for (int n = 0; n < peop.size(); n++) {

                            if (peop.get(n).name.equals(per)) {
                                exi = true;
                            }
                        }
                        if (!exi) {
                            ent = new capdata();
                            ent.capdat = back.getString("date");
                
if( ent.capdat.equals("unknown")) ent.capdat = "00/09/"+back.getString("year");
                            ent.captag = back.getString("tag");
                            ent.caplat = back.getString("lat_DD_DDDD");
                            ent.caplong = back.getString("long_DD_DDDD");
                            ent.releasecode = back.getString("tagcode");
                            ent.capcode = "You Captured";
                                         String delims = ",";
                    if(back.getString("lat") != null){
                    StringTokenizer stoklat = new StringTokenizer(back.getString("lat"), delims);
                    StringTokenizer stoklon = new StringTokenizer(back.getString("lon"), delims);
                    int cnum = 1;
                    Coordinate[] labelcoords = new Coordinate[stoklat.countTokens()];
                    while(stoklat.hasMoreTokens()){
                        labelcoords[cnum-1] = new Coordinate(Double.parseDouble(stoklon.nextElement().toString()), Double.parseDouble(stoklat.nextElement().toString()));
                  
                       
                        cnum++;
                        
                    }
                    
                    GeometryFactory geometryFactory2 = JTSFactoryFinder.getGeometryFactory(null);
                    ent.cappath = geometryFactory2.createLineString(labelcoords);
                    
                    }
                    else{ 
                        ent.cappath = null;
                    }
                            
                            
                            
                            
                            
                            String who = back.getString("person");
                            p = new person();
                            p.name = who;
                            p.MTC_IDNR = true;
                            ptemp = new LinkedList<capdata>();
                            ptemp.add(ent);
                            p.caps = ptemp;
                            peop.addLast(p);

                        }

                        //END UNTESTED CODE
                        if (!back.getString("date").equals(peop.get(x).caps.get(y).capdat)) {
                
                            ent = new capdata();
                            String d = peop.get(x).caps.get(y).capdat;
                            String dd = back.getString("date");
                            if( dd.equals("unknown")) dd = "00/09/"+back.getString("year");
                            int drep = dateToInt(d);
                            int ddrep = dateToInt(dd);

                            if (drep > ddrep) {
                                ent.capcode = "Captured Before";
                                peop.get(x).before = peop.get(x).before + 1;
                            } else if (drep < ddrep) {
                                ent.capcode = "Captured Later";
                                peop.get(x).after = peop.get(x).after + 1;
                            } else {
                                ent.capcode = "Same day";
                            }

                            if (back.getString("person").equals(peop.get(x).name)) {
                                ent.capcode = "You Captured";
                            }
                            ent.capdat = dd;
                            ent.caplat = back.getString("lat_DD_DDDD");
                            ent.caplong = back.getString("long_DD_DDDD");
                            ent.captag = back.getString("tag");
                             
                            String delims = ",";
                            if(back.getString("lat") != null){
                              StringTokenizer stoklat = new StringTokenizer(back.getString("lat"), delims);
                                StringTokenizer stoklon = new StringTokenizer(back.getString("lon"), delims);
                                 int cnum = 1;
                                Coordinate[] labelcoords = new Coordinate[stoklat.countTokens()];
                            while(stoklat.hasMoreTokens()){
                             labelcoords[cnum-1] = new Coordinate(Double.parseDouble(stoklon.nextElement().toString()), Double.parseDouble(stoklat.nextElement().toString()));
                             cnum++;
                            }
                          GeometryFactory geometryFactory2 = JTSFactoryFinder.getGeometryFactory(null);
                          ent.cappath = geometryFactory2.createLineString(labelcoords);
                    
                            }
                         else{ 
                            ent.cappath = null;
                         }
                            
                            
                            
                            if (!ent.caplat.equals("0")) {
                                ptemp = peop.get(x).caps;
                                ptemp.add(ent);
                                peop.get(x).caps = ptemp;
                            }
                        }
                    }
                    back.close();
st.close();


                 //   conn.close();
                } catch (Exception e) {

                    System.err.println("Got an exception when adding people!");
                    System.err.println(e.getMessage());
                }finally{
            if(conn != null && !conn.isClosed()){
               
                conn.close();
            }
          
        }


            }
            peop.get(x).years = years;
        
        }





    }

    public static int dateToInt(String a) {

        String year = a.split("/")[2];
        String mon = a.split("/")[1];
        String day = a.split("/")[0];
        String tot = year.concat(mon).concat(day);
        return Integer.parseInt(tot);

    }

    public static String formdd(Double pos) {
        String degree = "\u00B0";
        if (pos < 0) {
            pos = pos * -1;
        }
        String dm;
        dm = pos.toString();

        dm = dm.split("\\.")[0];
   
        String ending = pos.toString().replaceFirst(dm, "");

        Double min = Double.parseDouble(ending);
        min = min * 60;
        ending = min.toString();
 
        if (ending.length() < 4) {
            ending = ending.concat("0");
        }
        if (ending.length() < 5) {
            ending = ending.concat("0");
        }
        if (ending.length() < 6) {
            ending = ending.concat("0");
        }

        if (min < 10) {
            ending = ending.substring(0, 4);
            dm = dm.concat(degree).concat("0").concat(ending);
        } else {
            ending = ending.substring(0, 5);
            dm = dm.concat(degree).concat(ending);

        }

        return dm;

    }

    public static Style createPolygonStyle(Color c) {

        // create a partially opaque outline stroke
        Stroke stroke = styleFactory.createStroke(
                filterFactory.literal(Color.BLACK),
                filterFactory.literal(1),
                filterFactory.literal(0.5));

        // create a partial opaque fill
        Fill fill = styleFactory.createFill(
                filterFactory.literal(c),
                filterFactory.literal(1));

        /*
         * Setting the geometryPropertyName arg to null signals that we want to
         * draw the default geomettry of features
         */
        PolygonSymbolizer sym = styleFactory.createPolygonSymbolizer(stroke, fill, null);

        Rule rule = styleFactory.createRule();
        rule.symbolizers().add(sym);
        FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[]{rule});
        Style style = styleFactory.createStyle();
        style.featureTypeStyles().add(fts);

        return style;
    }

    public static Style createLineStyle(Color c, int s) {
        Stroke stroke = styleFactory.createStroke(
                filterFactory.literal(c),
                filterFactory.literal(s));


        /*
         * Setting the geometryPropertyName arg to null signals that we want to
         * draw the default geomettry of features
         */
        LineSymbolizer sym = styleFactory.createLineSymbolizer(stroke, null);

        Rule rule = styleFactory.createRule();
        rule.symbolizers().add(sym);
        FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[]{rule});
        Style style = styleFactory.createStyle();
        style.featureTypeStyles().add(fts);

        return style;
    }

    public static Style createblackLineStyle() {
        Stroke stroke = styleFactory.createStroke(
                filterFactory.literal(Color.BLACK),
                filterFactory.literal(3));

        /*
         * Setting the geometryPropertyName arg to null signals that we want to
         * draw the default geomettry of features
         */
        LineSymbolizer sym = styleFactory.createLineSymbolizer(stroke, null);

        Rule rule = styleFactory.createRule();
        rule.symbolizers().add(sym);
        FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[]{rule});
        Style style = styleFactory.createStyle();
        style.featureTypeStyles().add(fts);

        return style;
    }
}
