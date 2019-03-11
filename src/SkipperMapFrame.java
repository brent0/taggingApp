/*

/*
 *
 *
*/

// Import statement that loads the nessesary libraries
import java.awt.RenderingHints;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.map.DefaultMapContext;
import org.geotools.map.MapContext;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.data.shapefile.ShapefileDataStore;
import com.vividsolutions.jts.geom.*;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

//Class that generates the nessesary maps for the report to skippers
public class SkipperMapFrame extends tagFrame {
    //Objects to be used throughout the map making process
    public static MapContext mainmap = new DefaultMapContext(); // Declareing the main map
    public static MapContext insetmap = new DefaultMapContext(); // Declaring the inset map
    public static Rectangle imageresized;
    public static BufferedImage imagere;
    public static MapContext nmain;
    public static MapContext ninset;
    public static Graphics2D grre;
    public static Graphics2D gr;
    public static Envelope boundary;
    public static Rectangle imageSize;
    public static BufferedImage image2;
    public static BufferedImage image;
   // public static Connection conn;
    private static int wid = 1000; //The width of the main map, A change to this will most likely require a change to
    //other positions such as the inset map and thet axis plotting
    private static int hei = 1000; //The height of the main map. Again, changing this may affect the positioning of the other map objects
    private static ResultSet r;
    private static ResultSet temp;
    public static int tagsinrow;

    //Method used to reset a map back to initial values. This is needed after one specific map is finished being created.
    public static void resetMap() {
        nmain = mainmap;
        ninset = insetmap;

        imageSize = new Rectangle(wid, hei);
        image = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_RGB);
        image2 = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_RGB);


        imageresized = new Rectangle(wid + 100, hei + 100);

        imagere = new BufferedImage(imageresized.width, imageresized.height, BufferedImage.TYPE_INT_RGB);

        grre = imagere.createGraphics();
        grre.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        grre.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);


        grre.setPaint(Color.WHITE);
        grre.fill(imageresized);

        grre.setColor(Color.black);

    }

    // Load the desired layers into the map contexts. This may be option you would like to change. Notice different load methods 
    // between shape files and geotiffs
    public static void loadmaplayers() throws Exception {

        File tiffFile2 = new File(datadir.concat("/").concat("bio.snowcrab/maps/rasters/").concat("TrueLand.tif"));//Windows           
        AbstractGridCoverage2DReader rdr2 = new GeoTiffReader(tiffFile2);

        mainmap.addLayer(rdr2, statsFrame.createGreyscaleStyle(1));
        insetmap.addLayer(rdr2, mapFrame.getRasterStyle());

        File tiffFile = new File(datadir.concat("/").concat("bio.snowcrab/maps/rasters/").concat("bathycoulourWGS.tif"));//Windows
        AbstractGridCoverage2DReader rdr = new GeoTiffReader(tiffFile);

        mainmap.addLayer(rdr, mapFrame.getRasterStyle());
        insetmap.addLayer(rdr, mapFrame.getRasterStyle());

        File shpFile = new File(datadir.concat("/").concat("bio.snowcrab/maps/shapefiles/").concat("Chart 801 Shape File/801-bord_r.shp"));//Windows

        ShapefileDataStore store = (ShapefileDataStore) FileDataStoreFinder.getDataStore(shpFile);
        store.forceSchemaCRS(DefaultGeographicCRS.WGS84);
        FeatureSource featureSource = store.getFeatureSource();

        mainmap.addLayer(featureSource, mapFrame.createLineStyle(Color.BLACK, 1));

        File shpFile2 = new File(datadir.concat("/").concat("bio.snowcrab/maps/shapefiles/").concat("Chart 801 Shape File/801-bord_p.shp"));//Windows
        ShapefileDataStore store2 = (ShapefileDataStore) FileDataStoreFinder.getDataStore(shpFile2);
        store2.forceSchemaCRS(DefaultGeographicCRS.WGS84);
        FeatureSource featureSource2 = store2.getFeatureSource();

        mainmap.addLayer(featureSource2, mapFrame.createLineStyle(Color.BLACK, 1));

        File shpFile3 = new File(datadir.concat("/").concat("bio.snowcrab/maps/shapefiles/").concat("Chart 801 Shape File/801-bord_n.shp"));//Windows
        ShapefileDataStore store3 = (ShapefileDataStore) FileDataStoreFinder.getDataStore(shpFile3);
        store3.forceSchemaCRS(DefaultGeographicCRS.WGS84);
        FeatureSource featureSource3 = store3.getFeatureSource();

        mainmap.addLayer(featureSource3, mapFrame.createLineStyle(Color.BLACK, 1));

        File shpFile4 = new File(datadir.concat("/").concat("bio.snowcrab/maps/shapefiles/").concat("Chart 801 Shape File//801-bord_l.shp"));//Windows

        ShapefileDataStore store4 = (ShapefileDataStore) FileDataStoreFinder.getDataStore(shpFile4);
        store4.forceSchemaCRS(DefaultGeographicCRS.WGS84);
        FeatureSource featureSource4 = store4.getFeatureSource();
        mainmap.addLayer(featureSource4, mapFrame.createLineStyle(Color.BLACK, 1));


    }

    // This method generates the large map that show all movement from tagged crab on a particular day, The map gets saved 
    // to file in a specific format. It may then be matched by filename to the letter and other smaller maps
    public static void genSkipperRep() throws Exception {
        loadmaplayers();
        //try database operations
   
            /*
            Class.forName(conname);
            String url = dataurl;

            if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, user, pass);
            }
            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();
           
             */
            //A very nice SQL statement. This statement saves me from creating complex for loops later to organize the data. This is much 
            //better than the methods used to load data in the regular report to tag returnees.
            String toda = "Select * from "
                    + "(SELECT  bio.tag_id, bio.sample_num, str_to_date( capture.date, '%d/%m/%Y' ) date, capture.lat_DD_DDDD, capture.long_DD_DDDD, capture.year  "
                    + "from capture join bio where bio.tag_id = capture.tag) t1 "
                    + "JOIN (SELECT trip.trip_id, trip.captain, trip.Reported, str_to_date( trip.date, '%d/%m/%Y' ) date, sample.lat_DD_DDDD, sample.long_DD_DDDD, sample.sample_id"
                    + " from trip join sample where sample.trip = trip.trip_id )t2 "
                    + "ON t1.sample_num = t2.sample_id  and t2.Reported = 0  "
                    + "where t2.date < DATE_SUB(NOW(), INTERVAL 18 MONTH)"
                    + "ORDER BY captain, trip_id, tag_id, t1.date;";

            //Get back two result sets, temp needed to go through and check lable placement
            
            Connection conn = null;
      
        try{
           
             
          
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
             
            Statement st = conn.createStatement();

            r = st.executeQuery(toda);
            temp = st.executeQuery(toda);

            // conn.close();


        //Initialize needed variables
        String prevDate = "xxx";
        String prevSkip = "xxx";
        String prevTrip = "xxx";
        String prevTag = "xxx";
        String prevLat = "xxx";
        String prevLong = "xxx";
        boolean writemap = true;

        resetMap();

        r.beforeFirst();  //set the sql result to before the first entry
        //Variables to track the number of times a tag is captured, need for proper drawing of arrows and legend 
        boolean twocaps = false;
        boolean threecaps = false;
        
        //loop through each entry returned from the sql statement
        while (r.next()) {
            //If different trip or captain, we must save the map and reset variables
            if (!prevSkip.equals(r.getString("captain")) || !prevTrip.equals(r.getString("trip_id"))) {
                //Check to see if it is the first enrty, if so skip step that writes map.
                if (!prevSkip.equals("xxx")) {
                    //Only write the map if conditions have been fulfilled during the creation process
                    if (writemap) {
                        //Call to draw samples method to draw all sample location on top of the graphics currently on the map
                        drawSamples(prevTrip, true);
                        //Following code gererates the legend
                        int ove = wid - 250;
                        int dow = hei - 200;

                        int legh = 1;

                        gr.setColor(Color.white);
                        gr.fillRect(ove, dow - 50, 245, 50);
                        gr.setColor(new Color(148, 0, 211));
                        gr.setStroke(new BasicStroke(3));
                        gr.drawOval(ove + 30, dow - 25, 4, 4);
                        gr.setColor(Color.black);
                        gr.drawString("Sample locations", ove + 60, dow - 20);



                        gr.setColor(Color.white);
                        gr.fillRect(ove, dow, 245, 50);
                        gr.setColor(Color.red);
                        gr.setStroke(new BasicStroke(3));
                        gr.drawLine(ove + 10, dow + 25, ove + 50, dow + 25);
                        gr.setColor(Color.black);
                        gr.drawString("To first capture", ove + 60, dow + 30);



                        if (twocaps) {
                            dow = dow + 50;
                            legh++;
                            gr.setColor(Color.white);
                            gr.fillRect(ove, dow, 245, 50);
                            gr.setColor(Color.green);
                            gr.drawLine(ove + 10, dow + 25, ove + 50, dow + 25);
                            gr.setColor(Color.black);
                            gr.drawString("To second capture", ove + 60, dow + 25);
                        }

                        if (threecaps) {
                            dow = dow + 50;
                            legh++;
                            gr.setColor(Color.white);
                            gr.fillRect(ove, dow, 245, 50);
                            // Color aColor = new Color( Integer.parseInt( "8E390E",16) );
                            gr.setColor(Color.YELLOW);
                            //gr.setColor(aColor);
                            gr.drawLine(ove + 10, dow + 25, ove + 50, dow + 25);
                            gr.setColor(Color.black);
                            gr.drawString("To third + capture", ove + 60, dow + 30);
                        }
                        gr.setStroke(new BasicStroke(1));
                        gr.setColor(Color.black);
                        gr.drawRect(ove, hei - 250, 245, (50 * legh) + 50);
                        grre.drawImage(image, 75, 25, null);
                        grre.setColor(Color.black);
                        grre.setStroke(new java.awt.BasicStroke(6F));
                        grre.draw3DRect(75, 25, wid, hei, false);

                        //Draw the map on a white back image so that inset and lat lond axis can be drawn
                        grre.drawImage(image2, 790, 5, null);

                        grre.setFont(new Font("Courier", Font.PLAIN, 20));
                        
                        //Format the date
                        String da = prevDate;
                        if (!da.equals("unknown")) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            java.util.Date dat = sdf.parse(da);
                            sdf = new SimpleDateFormat("dd-MMM-yy");
                            da = sdf.format(dat);
                        }

                        //Set up and draw title in the centre
                        String title;
                        String mn;
                        if (prevSkip.equals("")) {
                            title = "Person Unknown Crab tagged on " + da;
                            mn = "Person Unknown";
                        } else {
                            title = prevSkip + " Crab tagged on " + da;
                            mn = prevSkip;
                        }
                        grre.drawString(title, (wid + 100) / 2 - ((title.length() * 10) / 2), 20);
                       
                        //Save the map and overwrite previous
                        File fileToSave = new File(datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("skipmaps/").concat(mn.concat(" ")).concat(prevDate.replaceAll("-", "_")).concat(".jpeg"));
                        
                        if (fileToSave.exists()) {
                            fileToSave.delete();
                        }

                      fileToSave.getParentFile().mkdirs();
            
                        ImageIO.write(imagere, "jpeg", fileToSave);
                        loadtext(prevSkip);
                 
                    }
                    writemap = true;
                }
                //Reset variable to blank
                twocaps = false;
                threecaps = false;
                resetMap();
                //Move the current resultset item before the first entry
                temp.beforeFirst();
                //Variables to find goemetry of desired tag data              
                double maxh = 0;
                double minh = 100;
                double maxl = 0;
                double maxr = -300;
                double x;
                double y;
                double xx;
                double yy;
                boundary = null;

                int j = 0;
                while (temp.next()) {

                    if (temp.getString("captain").equals(r.getString("captain")) && temp.getString("trip_id").equals(r.getString("trip_id"))) {
                      

                        try {
                            y = Double.parseDouble(temp.getString("t1.Lat_DD_DDDD"));
                            x = Double.parseDouble(temp.getString("t1.Long_DD_DDDD"));
                            yy = Double.parseDouble(temp.getString("t2.Lat_DD_DDDD"));
                            xx = Double.parseDouble(temp.getString("t2.Long_DD_DDDD"));

                        } catch (NumberFormatException nfe) {
                            System.err.println("Got a NumberFormatException!");
                            System.err.println(nfe.getMessage());
                            y = yy = x = xx = 0;

                        }

                        //test the maximum and minimum bounds
                        if (y > 0) {

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


                        if (yy > 0) {


                            if (yy > maxh) {
                                maxh = yy;
                            }
                            if (yy < minh) {
                                minh = yy;
                            }
                            if (xx < maxl) {
                                maxl = xx;
                            }
                            if (xx > maxr) {
                                maxr = xx;
                            }
                        }
                    }


                }
                //If no area, must not be any good data, do not write a map
                if (maxh == 0) {
                    writemap = false;
                }

                //get x y values
                double ytraveled = maxh - minh;
                double xtraveled = maxl - maxr;
                if (xtraveled < 1) {
                    xtraveled = xtraveled * -1;
                }
                double scale;
                //find which is longer and get a scale to make both the same  
                if (ytraveled > xtraveled) {
                    scale = ytraveled;
                } else {
                    scale = xtraveled;
                }

                scale = scale * .85;

                boundary = new Envelope(maxr + scale, maxl - scale, minh - scale, maxh + scale);
                Coordinate center = boundary.centre();
                boundary = new Envelope(center.x + scale, center.x - scale, center.y - scale, center.y + scale);
                //while the area is too small, keep increasing the boundary
                while (boundary.getHeight() < .06) {
                    boundary.expandBy(.1, .1);
                }

                //Set the area of intrest of the map contexts to the newely found boundary
                mainmap.setAreaOfInterest(boundary, mainmap.getCoordinateReferenceSystem());
                mainmap.setAreaOfInterest(boundary, mainmap.getCoordinateReferenceSystem());

                // Set up the map context to image renderer
                GTRenderer renderer = new StreamingRenderer();
                renderer.setContext(mainmap);

                imageSize = new Rectangle(wid, hei);
                image = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_RGB);
                gr = image.createGraphics();
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
                // Method that draws the latitude and longitude axis on the white background grre image. This needs the maps boundary to draw correctly 
                drawLatLongAxis(boundary);



                ReferencedEnvelope inset = mainmap.getAreaOfInterest();
                //Expand the inset map to the coordinates below, this must be done to recognize where in the world they are
                Coordinate c = inset.centre();
                Coordinate toinc = new Coordinate(-62, 45.25);
                inset.expandBy(c.distance(toinc));
                insetmap.setAreaOfInterest(inset, mainmap.getCoordinateReferenceSystem());

                //Set up the inset map renderer gr2
                GTRenderer renderer2 = new StreamingRenderer();
                renderer2.setContext(insetmap);


                Rectangle imageSize2 = new Rectangle(300, 300);
                image2 = new BufferedImage(imageSize2.width, imageSize2.height, BufferedImage.TYPE_INT_RGB);
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
                //Set up and draw the rectangle that show where in the world the larger map is showing
                int sx = mapFrame.transx(inset.getMinX(), inset.getMaxX(), boundary.getMinX(), 300);
                int sy = mapFrame.transy(inset.getMinY(), inset.getMaxY(), boundary.getMaxY(), 300);
                int sxx = mapFrame.transx(inset.getMinX(), inset.getMaxX(), boundary.getMaxX(), 300);
                int syy = mapFrame.transy(inset.getMinY(), inset.getMaxY(), boundary.getMinY(), 300);
                gr2.setColor(Color.RED);
                gr2.setStroke(new java.awt.BasicStroke(6F));
                gr2.draw3DRect(sx, sy, sxx - sx, syy - sy, true);


                gr.setColor(Color.red);
                gr.setPaintMode();

            }

            int x = 0;
            int xx = 0;
            int yy = 0;
            int y = 0;
            //IF latitude and longitude correct, draw arrows.
            if (!r.getString("t2.Long_DD_DDDD").equals("unknown") && !r.getString("t2.Lat_DD_DDDD").equals("unknown") && !r.getString("t1.Long_DD_DDDD").equals("unknown") && !r.getString("t1.Lat_DD_DDDD").equals("unknown")) {
                //IF same tag number draw in proper place and color else draw normal in red
                if (prevTag.equals(r.getString("tag_id"))) {
                    x = mapFrame.transx(boundary.getMinX(), boundary.getMaxX(), Double.parseDouble(prevLong), wid);
                    y = mapFrame.transy(boundary.getMinY(), boundary.getMaxY(), Double.parseDouble(prevLat), hei);
                    xx = mapFrame.transx(boundary.getMinX(), boundary.getMaxX(), Double.parseDouble(r.getString("t1.Long_DD_DDDD")), wid);
                    yy = mapFrame.transy(boundary.getMinY(), boundary.getMaxY(), Double.parseDouble(r.getString("t1.Lat_DD_DDDD")), hei);
                    tagsinrow++;
                    //choose proper color
                    if (tagsinrow == 2) {
                        twocaps = true;
                        gr.setColor(Color.GREEN);
                    }
                    if (tagsinrow == 3) {
                        threecaps = true;
                        gr.setColor(Color.MAGENTA);
                    }
                    if (tagsinrow == 4) {
                        gr.setColor(Color.YELLOW);
                    }
                } else {
                    x = mapFrame.transx(boundary.getMinX(), boundary.getMaxX(), Double.parseDouble(r.getString("t2.Long_DD_DDDD")), wid);
                    y = mapFrame.transy(boundary.getMinY(), boundary.getMaxY(), Double.parseDouble(r.getString("t2.Lat_DD_DDDD")), hei);
                    xx = mapFrame.transx(boundary.getMinX(), boundary.getMaxX(), Double.parseDouble(r.getString("t1.Long_DD_DDDD")), wid);
                    yy = mapFrame.transy(boundary.getMinY(), boundary.getMaxY(), Double.parseDouble(r.getString("t1.Lat_DD_DDDD")), hei);
                    gr.setColor(Color.red);
                    tagsinrow = 1;
                }


            }
            //if y or yy pixel location is within reason draw movement
            if (y < 10000 && yy < 10000) {
                java.awt.Stroke s = new BasicStroke(2);

                if (x == xx && y == yy) {
                    gr.drawLine(x - 1, y - 1, x + 1, y + 1);
                    gr.drawLine(x - 1, y + 1, x + 1, y - 1);
                } else {
                    gr = drawsmallArrow(gr, x, y, xx, yy, gr.getColor(), s);
                }

            }

            //Change variables to keep track of this entry in the next step
            prevSkip = r.getString("captain");
            prevTrip = r.getString("trip_id");
            prevTag = r.getString("tag_id");
            prevLong = r.getString("t1.Long_DD_DDDD");
            prevLat = r.getString("t1.Lat_DD_DDDD");
            prevDate = r.getString("t2.date");
     
        }//go to next result set entry
        //Call function to generate the smaller sub maps
        genSkippersubRep();
        //close the database connection and result sets
       // conn.close();
        r.close();
        temp.close();
        st.close();
        
        
        } catch (Exception e) {

            System.err.println("Got an SQL Exception!");
            System.err.println(e.getMessage());
        }finally{
            if(conn != null && !conn.isClosed()){
               
                conn.close();
            }
           
        }
        
        
        
        tagFrame.skipmess.setText("Maps created in folder skipmaps.\r\n");    
         tagFrame.skipmess.setText("Letters created in folder skipmaps.\r\n");
            tagFrame.skipmess.setText("Press 'generate PDF' to combine into a report.\r\n"); 
    }

    //Method to create sub maps. Very similar to the above method just with different boundary operations, graphic options and contition
    // to save map after different sample number. See coments in above method 
    public static void genSkippersubRep() throws Exception {
        loadmaplayers();

        
            /*
            Class.forName(conname);
            String url = dataurl;

            if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, user, pass);
            }
            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();
           
             */
            String toda = "Select * from "
                    + "(SELECT  bio.tag_id, bio.sample_num, str_to_date( capture.date, '%d/%m/%Y' ) date, capture.lat_DD_DDDD, capture.long_DD_DDDD, capture.year  "
                    + "from capture join bio where bio.tag_id = capture.tag) t1 "
                    + "JOIN (SELECT trip.trip_id, trip.captain, trip.Reported, str_to_date( trip.date, '%d/%m/%Y' ) date, sample.lat_DD_DDDD, sample.long_DD_DDDD, sample.sample_id"
                    + " from trip join sample where sample.trip = trip.trip_id )t2 "
                    + "ON t1.sample_num = t2.sample_id  and t2.Reported = 0  "
                    + "where t2.date < DATE_SUB(NOW(), INTERVAL 18 MONTH)"
                    + "ORDER BY captain, trip_id, tag_id, t1.date;";
  Connection conn = null;
       
        try{
           
          
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            Statement st = conn.createStatement();
            r = st.executeQuery(toda);
            temp = st.executeQuery(toda);


  

        LinkedList<Rectangle> labels = new LinkedList<Rectangle>();

        String prevSamp = "xxx";
        String prevSkip = "xxx";
        String prevTrip = "xxx";
        String prevTag = "xxx";
        String prevLat = "xxx";
        String prevLong = "xxx";
        String prevDate = "xxx";
        LinkedList<Double> widlis = new LinkedList();
        boolean writemap = true;
        int samp = -1;
        //Needed for proper map labeling
        String[] sampletter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", ""
            + "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "A-2", "B-2", "C-2", "D-2", "E-2", "F-2", "G-2"
            + "", "H-2", "I-2", "J-2", "K-2", "L-2", "M-2", "N-2", "O-2", "P-2", "Q-2", "R-2", "S-2", "T-2", "U-2", ""
            + "V-2", "W-2", "X-2", "Y-2", "Z-2"};
        //  String prevTag = "";
        resetMap();

        r.beforeFirst();
        while (r.next()) {

            if (!prevSkip.equals(r.getString("captain")) || !prevTrip.equals(r.getString("trip_id"))) {
                samp = -1;
            }
            //Condition to check if sample is also different
            if (!prevSkip.equals(r.getString("captain")) || !prevSamp.equals(r.getString("sample_num"))) {
                labels = new LinkedList<Rectangle>();
                if (!prevSkip.equals("xxx")) {

                    if (writemap) {
                        samp++;
                        grre.drawImage(image, 75, 25, null);
                        grre.setColor(Color.black);
                        grre.setStroke(new java.awt.BasicStroke(6F));

                        grre.draw3DRect(75, 25, wid, hei, true);
                        grre.setFont(new Font("Courier", Font.PLAIN, 20));

                        String da = prevDate;
                        if (!da.equals("unknown")) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            java.util.Date dat = sdf.parse(da);
                            sdf = new SimpleDateFormat("dd-MMM-yy");
                            da = sdf.format(dat);
                        }
                        String title;
                        String mn;
                        if (prevSkip.equals("")) {
                            title = "Person Unknown " + da + " Subchart: " + sampletter[samp];
                            mn = "Person Unknown";
                        } else {
                            title = title = prevSkip + " " + da + " Subchart: " + sampletter[samp];
                            mn = prevSkip;
                        }
                        grre.drawString(title, (wid + 100) / 2 - ((title.length() * 10) / 2), 20);
                        File fileToSave = new File(datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("skipmaps/").concat(mn.concat(" ")) + prevDate.replaceAll("-", "_") + "sample" + sampletter[samp] + ".jpeg");
                        if (fileToSave.exists()) {
                            fileToSave.delete();
                        }

            fileToSave.getParentFile().mkdirs();
            
                        ImageIO.write(imagere, "jpeg", fileToSave);
                    }
                    writemap = true;
                }



                resetMap();


                temp.beforeFirst();
                double maxh = 0;
                double minh = 100;
                double maxl = 0;
                double maxr = -300;
                double x = 0;
                double y = 0;
                double xx = 0;
                double yy = 0;
                boundary = null;

                while (temp.next()) {

                    if (temp.getString("captain").equals(r.getString("captain")) && temp.getString("sample_num").equals(r.getString("sample_num"))) {
             
                        y = Double.parseDouble(temp.getString("t1.Lat_DD_DDDD"));
                        x = Double.parseDouble(temp.getString("t1.Long_DD_DDDD"));
                        yy = Double.parseDouble(temp.getString("t2.Lat_DD_DDDD"));
                        xx = Double.parseDouble(temp.getString("t2.Long_DD_DDDD"));

                        if (y == 0) {
                            writemap = false;
                        }

                       
                        if (y > 0) {

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


                        if (yy > 0) {


                            if (yy > maxh) {
                                maxh = yy;
                            }
                            if (yy < minh) {
                                minh = yy;
                            }
                            if (xx < maxl) {
                                maxl = xx;
                            }
                            if (xx > maxr) {
                                maxr = xx;
                            }
                        }
                    }


                }
                if (maxh == 0) {
                    writemap = false;
                }


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
                 //Scaled differently than the above method
                scale = scale * .6;

                boundary = new Envelope(maxr, maxl, minh, maxh);
                Coordinate center = boundary.centre();
                boundary = new Envelope(center.x + scale, center.x - scale, center.y - scale, center.y + scale);
                if (boundary.getHeight() > 0.15) {
                    boundary.expandBy(.2, .2);
                } else {
                    boundary.expandBy(.05, .05);
                }
           
                temp.beforeFirst();
                while (temp.next()) {

                    if (temp.getString("captain").equals(r.getString("captain")) && temp.getString("sample_num").equals(r.getString("sample_num"))) {
                
                        y = Double.parseDouble(temp.getString("t1.Lat_DD_DDDD"));
                        x = Double.parseDouble(temp.getString("t1.Long_DD_DDDD"));
                     
                        //Set up and add a rectangle arround the arrow head. The map looks better when no label covers a arrow head.
                        //Add the rectangle to the list to check overlap against
                        Rectangle rr = new Rectangle();
                        rr.x = mapFrame.transx(boundary.getMinX(), boundary.getMaxX(), x, wid) - 4;
                        rr.y = mapFrame.transy(boundary.getMinY(), boundary.getMaxY(), y, hei) - 8;
                        rr.width = 8;
                        rr.height = 8;
                        labels.add(rr);
                    }
                }

                mainmap.setAreaOfInterest(boundary, mainmap.getCoordinateReferenceSystem());

                mainmap.setAreaOfInterest(boundary, mainmap.getCoordinateReferenceSystem());

                GTRenderer renderer = new StreamingRenderer();
                renderer.setContext(mainmap);

                imageSize = new Rectangle(wid, hei);
                image = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_RGB);
                gr = image.createGraphics();
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

                gr.setColor(Color.BLACK);

                drawLatLongAxis(boundary);
                drawOthers(r.getString("captain"), r.getString("trip_id"));


                gr.setColor(Color.yellow);


                gr.setColor(Color.red);
                gr.setPaintMode();

            }

            int x = 0;
            int xx = 0;
            int yy = 0;
            int y = 0;
            if (!r.getString("t2.Long_DD_DDDD").equals("unknown") && !r.getString("t2.Lat_DD_DDDD").equals("unknown") && !r.getString("t1.Long_DD_DDDD").equals("unknown") && !r.getString("t1.Lat_DD_DDDD").equals("unknown")) {

                if (prevTag.equals(r.getString("tag_id"))) {
                    x = mapFrame.transx(boundary.getMinX(), boundary.getMaxX(), Double.parseDouble(prevLong), wid);
                    y = mapFrame.transy(boundary.getMinY(), boundary.getMaxY(), Double.parseDouble(prevLat), hei);
                    xx = mapFrame.transx(boundary.getMinX(), boundary.getMaxX(), Double.parseDouble(r.getString("t1.Long_DD_DDDD")), wid);
                    yy = mapFrame.transy(boundary.getMinY(), boundary.getMaxY(), Double.parseDouble(r.getString("t1.Lat_DD_DDDD")), hei);
                    tagsinrow++;

                    if (tagsinrow == 2) {
                        gr.setColor(Color.GREEN);

                    }
                    if (tagsinrow == 3) {

                        gr.setColor(Color.YELLOW);
                    }
                    if (tagsinrow == 4) {
                        gr.setColor(Color.BLACK);
                    }
                } else {
                    x = mapFrame.transx(boundary.getMinX(), boundary.getMaxX(), Double.parseDouble(r.getString("t2.Long_DD_DDDD")), wid);
                    y = mapFrame.transy(boundary.getMinY(), boundary.getMaxY(), Double.parseDouble(r.getString("t2.Lat_DD_DDDD")), hei);
                    xx = mapFrame.transx(boundary.getMinX(), boundary.getMaxX(), Double.parseDouble(r.getString("t1.Long_DD_DDDD")), wid);
                    yy = mapFrame.transy(boundary.getMinY(), boundary.getMaxY(), Double.parseDouble(r.getString("t1.Lat_DD_DDDD")), hei);
                    gr.setColor(Color.red);
                    tagsinrow = 1;
                }


            }

            if (y < 10000 && yy < 10000) {
                java.awt.Stroke s = new BasicStroke(2);


                if (x == xx && y == yy) {
                    gr.drawLine(x - 1, y - 1, x + 1, y + 1);
                    gr.drawLine(x - 1, y + 1, x + 1, y - 1);
                } else {


                    gr = drawsmallArrow(gr, x, y, xx, yy, gr.getColor(), s);
                    if (gr.getColor().equals(Color.red)) {
                        gr.setColor(new Color(148, 0, 211));

                        java.awt.Stroke str = gr.getStroke();
                        gr.setStroke(new java.awt.BasicStroke(3F) {
                        });
                        gr.drawOval(x - 2, y - 2, 4, 4);
                        gr.setStroke(str);
                    }
                }

                int stx = xx;
                int yst = yy;
                int[] ypos;
                int[] xpos;
                boolean right = false;
                //Label placement differs if the arrow is pointed to the left 
                if (x > xx) {
                    stx = xx - 116;
                    right = true;
                    //LABEL PLACEMENT EXPERIMENTS MAY WISH TO ADD NEW POSITIONS OR COMPLETLY CHANGE. Values are added with each new try untill
                    //the placement passes
                    //int []ypo = {-20, 0, 0, 40, 0, 0, 0, 0, -20, -20, 0};
                    //int[]xpo = {0, 50, 50, -100, 50, 50, 50, 50, 0, 0, -50};
                    //     int []xpo = {5, 0, 0, 0, 0, 0, -20, -50, 0, -50, 0, -50, 0, -100, 0, 0, 0, -100, 0};
                    //      int[]ypo = {5, 35, 35,35, -135, -35, -35, 75, 50, -75, 100, -125, 150, -175, 30, 30, 60, 30, 50, -300};
                    int[] xpo = {0, 0, -20, -50, 0, -50, 0, -50, 0, -100, 0, 0, 0, -100, 0};
                    int[] ypo = {-35, 70, -35, 75, 50, -75, 100, -125, 150, -175, 30, 30, 60, 30, 50, -300};
                    ypos = ypo;
                    xpos = xpo;
                } else {
                    //LABEL PLACEMENT EXPERIMENTS MAY WISH TO ADD NEW POSITIONS OR COMPLETLY CHANGE. Values are added with each new try untill
                    //the placement passes
                    // int []ypo = {-20, 0, 0, 40, 0, 0, 0, 0, -20, -20, 0};
                    // int[]xpo = {0, -50, -50, 100, -50, -50, -50, -50, 0, 0, 50};
                    //  int []xpo = {0, 0, 0, 0, 0, 0, 20, 50, 0, 50, 0, 50, 0, 100, 0, 0, 0, 100, 0};
                    //  int[]ypo = {5, 35, 35, 35, -135, -35, -35, 75, 50, -75, 100, -125, 150, -175, 30, 30, 60, 30, 50, -300};
                    int[] xpo = {0, 0, 20, 50, 0, 50, 0, 50, 0, 100, 0, 0, 0, 100, 0};
                    int[] ypo = {-35, 70, -35, 75, 50, -75, 100, -125, 150, -175, 30, 30, 60, 30, 50, -300};
                    ypos = ypo;
                    xpos = xpo;
                }

                //Set up rectangle arround label
                Rectangle rr = new Rectangle();
                rr.x = stx + 5;
                rr.y = yst - 28;
                rr.width = 100;
                rr.height = 31;



                int n = 0;
                //If first label add the retangle that was set up above else check find and good location for this label
                if (labels.size() == 0) {
                    labels.add(rr);
                } else {
                    //for each rectangle in the list to not be overlaped 
                    for (int k = 0; k < labels.size() && n < 16; k++) {
                        //Make the initial rectangle at the most desired position
                        rr = new Rectangle();
                        rr.x = stx + 5;
                        rr.y = yst - 29;
                        rr.width = 100;
                        rr.height = 30;

                        //test if their is an overlap if so advance to next desired location
                        if (labels.get(k).intersects(rr)) {
                            stx = stx + xpos[n];
                            yst = yy + ypos[n];
                            k = 0;
                            n++;

                        } 

                    }
                    //Add the newly created lable so that it is not overlaped in the future
                    labels.add(rr);
                }
                //Labels should be monospaced, other fonts that claim to be monospaced are not always in java, tried couruir however bad visuall spacing 
                //due to not being evenly spaced
                gr.setFont(new Font("Monospaced", Font.BOLD, 20));
                String tag = r.getString("tag_id");
                tag = tag.replaceAll("\\.0", "");

                String da = r.getString("t1.date");
                if (r.wasNull()) {
                    da = "unknown";
                }
                if (!da.equals("unknown")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date dat = sdf.parse(da);
                    sdf = new SimpleDateFormat("dd-MMM-yy");
                    da = sdf.format(dat);
                }

                //Draw label with white halo effect
                gr.setColor(Color.white);
                gr.drawString(da, stx + 7, yst);
                gr.drawString(da, stx + 3, yst);
                gr.drawString(da, stx + 5, yst + 2);
                gr.drawString(da, stx + 5, yst - 1);
                gr.setColor(Color.black);
                gr.drawString(da, stx + 5, yst);

                gr.setComposite(java.awt.AlphaComposite.SrcIn);
                grre.setStroke(new java.awt.BasicStroke(1F));

                if (right) {
                    gr.drawLine(xx, yy, stx + 115, yst);
                } else {
                    gr.drawLine(xx, yy, stx, yst);
                }

            }

            prevSkip = r.getString("captain");
        
            prevSamp = r.getString("sample_num");
            prevTrip = r.getString("trip_id");
            prevTag = r.getString("tag_id");
            prevLong = r.getString("t1.Long_DD_DDDD");
            prevLat = r.getString("t1.Lat_DD_DDDD");
            prevDate = r.getString("t2.date");

        }

       // conn.close();
        r.close();
        temp.close();
        st.close();
              } catch (Exception e) {

            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }finally{
            if(conn != null && !conn.isClosed()){
               
                conn.close();
            }
         
        }
        
    }
    
    // Method draw others draws a faint arrow on the sub maps where movement from this trip(larger map) happens. Gives good reference to larger map
    //Very hard to set the transparency. Overlaping transparent arrows get darker. Soulution was to draw white arrow each time so that the blend
    //occurs with white each time instead of compound blending of red
    public static void drawOthers(String psk, String ptp) throws Exception {
        temp.beforeFirst();
        String pTag = "xxx";
        String pLong = "xxx";
        String pLat = "xxx";
        int tinrow = 0;
        while (temp.next()) {
            if (psk.equals(temp.getString("captain")) && ptp.equals(temp.getString("trip_id"))) {


                int x = 0;
                int xx = 0;
                int yy = 0;
                int y = 0;
                if (!temp.getString("t2.Long_DD_DDDD").equals("unknown") && !temp.getString("t2.Lat_DD_DDDD").equals("unknown") && !temp.getString("t1.Long_DD_DDDD").equals("unknown") && !temp.getString("t1.Lat_DD_DDDD").equals("unknown")) {

                    if (pTag.equals(temp.getString("tag_id"))) {
                        x = mapFrame.transx(boundary.getMinX(), boundary.getMaxX(), Double.parseDouble(pLong), wid);
                        y = mapFrame.transy(boundary.getMinY(), boundary.getMaxY(), Double.parseDouble(pLat), hei);
                        xx = mapFrame.transx(boundary.getMinX(), boundary.getMaxX(), Double.parseDouble(temp.getString("t1.Long_DD_DDDD")), wid);
                        yy = mapFrame.transy(boundary.getMinY(), boundary.getMaxY(), Double.parseDouble(temp.getString("t1.Lat_DD_DDDD")), hei);
                        tinrow++;

                        if (tinrow == 2) {
                            gr.setColor(Color.GREEN);
                        }
                        if (tinrow == 3) {
                            gr.setColor(Color.MAGENTA);
                        }
                        if (tinrow == 4) {
                            gr.setColor(Color.YELLOW);
                        }
                    } else {
                        x = mapFrame.transx(boundary.getMinX(), boundary.getMaxX(), Double.parseDouble(temp.getString("t2.Long_DD_DDDD")), wid);
                        y = mapFrame.transy(boundary.getMinY(), boundary.getMaxY(), Double.parseDouble(temp.getString("t2.Lat_DD_DDDD")), hei);
                        xx = mapFrame.transx(boundary.getMinX(), boundary.getMaxX(), Double.parseDouble(temp.getString("t1.Long_DD_DDDD")), wid);
                        yy = mapFrame.transy(boundary.getMinY(), boundary.getMaxY(), Double.parseDouble(temp.getString("t1.Lat_DD_DDDD")), hei);
                        gr.setColor(Color.red);
                        tinrow = 1;
                    }


                }

                if (y < 10000 && yy < 10000) {
                    java.awt.Stroke s = new BasicStroke(2);

                    Color old = gr.getColor();


                    Color c = new Color(Math.abs(gr.getColor().getRed()), Math.abs(gr.getColor().getGreen()), Math.abs(gr.getColor().getBlue()), 100);
                    gr.setColor(Color.white);
                    Color cw = new Color(Math.abs(gr.getColor().getRed()), Math.abs(gr.getColor().getGreen()), Math.abs(gr.getColor().getBlue()), 110);





                    if (x == xx && y == yy) {
                        gr.drawLine(x - 1, y - 1, x + 1, y + 1);
                        gr.drawLine(x - 1, y + 1, x + 1, y - 1);
                    } else {

                        gr.setColor(cw);

                        gr = drawsmallArrow(gr, x, y, xx, yy, gr.getColor(), s);
                        gr.setColor(c);
                        gr.setColor(gr.getColor().darker());
                        gr = drawsmallArrow(gr, x, y, xx, yy, gr.getColor(), s);

                    }
                    gr.setColor(old);
                }
             

                pTag = temp.getString("tag_id");
                pLong = temp.getString("t1.Long_DD_DDDD");
                pLat = temp.getString("t1.Lat_DD_DDDD");


            }
        }

    }
   //Method that draws the sample locations from this trip
    public static void drawSamples(String trip, boolean circles) throws SQLException {

          
            
            String toda = "Select sample_id, Lat_DD_DDDD, Long_DD_DDDD from sample where trip = " + trip + ";";
            String res = "";
  Connection conn = null;
      
        try{
         
             
          
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            Statement st = conn.createStatement();

            
            
            ResultSet back = st.executeQuery(toda);
            int cracou = 0;
            while (back.next()) {

                if (circles) {
                    String toda2 = "SELECT COUNT(*) FROM(select * from bio where sample_num = " + back.getString("sample_id") + " )t3;";
  
                    ResultSet back2 = st.executeQuery(toda2);
                    back2.next();
                    res = back2.getString(1);
                    cracou = cracou + Integer.parseInt(res);
                st.close();
                    back2.close();
                }

                int ax = mapFrame.transx(boundary.getMinX(), boundary.getMaxX(), Double.parseDouble(back.getString("Long_DD_DDDD")), wid);
                int ay = mapFrame.transy(boundary.getMinY(), boundary.getMaxY(), Double.parseDouble(back.getString("Lat_DD_DDDD")), hei);
                //May wish to change graphics options here
                //float alpha = 0.8F;
                 
                Color color = new Color(148, 0, 211);
                gr.setFont(new Font("Courier", Font.BOLD, 16));
                gr.setStroke(new java.awt.BasicStroke(3F));
                gr.setColor(color);
                if (circles) {
                    gr.drawOval(ax - 2, ay - 2, 4, 4);
                 //   st2.close();
                }
            }
            gr.setColor(Color.black);
            gr.setFont(new Font("Courier", Font.BOLD, 20));
          
            //Draw the total number of crab tagged an this day
            gr.drawString(Integer.toString(cracou) + " crab tagged", 820, hei - 20);
            gr.setFont(new Font("Courier", Font.BOLD, 16));

        } catch (Exception e) {

            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }finally{
            if(conn != null && !conn.isClosed()){
               
                conn.close();
            }
           
        
        }
            
    }

    //Method that draws the arrows, this arrow has a smaller head that the arrows used in some other maps
    public static Graphics2D drawsmallArrow(Graphics2D g, int x, int y, int xx, int yy, Color a, java.awt.Stroke b) {

        //float arrowWidth = 3.0f ;
        //float theta = 0.735f ;
        float arrowWidth = 8.0f;
        float theta = 0.8f;
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
        th = arrowWidth / (1f * fLength);
        ta = arrowWidth / (1f * ((float) Math.tan(theta) / 1f) * fLength);

        // find the base of the arrow
        baseX = ((float) xPoints[ 0] - ta * vecLine[0]);
        baseY = ((float) yPoints[ 0] - ta * vecLine[1]);

        // build the points on the sides of the arrow
        xPoints[ 1] = (int) (baseX + th * vecLeft[0]);
        yPoints[ 1] = (int) (baseY + th * vecLeft[1]);
        xPoints[ 2] = (int) (baseX - th * vecLeft[0]);
        yPoints[ 2] = (int) (baseY - th * vecLeft[1]);
        g.setColor(a);
        //g.setStroke(new BasicStroke(5));
        g.setStroke(b);
        g.drawLine(x, y, (int) baseX, (int) baseY);
        g.fillPolygon(xPoints, yPoints, 3);

        return g;
    }

    //Draw the latitude longitude axis based on the passed envelope boundary
    public static void drawLatLongAxis(Envelope b) {
        String degree = "\u00B0";
        grre.setFont(new Font("Courier", Font.BOLD, 16));
        grre.setStroke(new java.awt.BasicStroke(5F));
        double conx = b.getWidth() / wid;  //degrees per pixel

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

        gr.setStroke(new java.awt.BasicStroke(3F));
        gr.setColor(Color.black);
        gr.drawLine(barstart, wid - 40, barstart, wid - 30);
        gr.drawLine(barstart, wid - 30, barstart + lenin, wid - 30);
        gr.drawLine(barstart + lenin, wid - 40, barstart + lenin, wid - 30);
        outsca = outsca.replaceAll("'", " nautical miles");
        gr.drawString(outsca, nmpos, wid - 10);

        String miny = mapFrame.formdd(b.getMinY());
        miny = miny.replaceAll(degree, "");

        double min = Double.parseDouble(miny.substring(miny.length() - 5, miny.length()));
        int deg = Integer.parseInt(miny.substring(0, miny.length() - 5));


        double starting = Math.floor(min / degoff) * degoff;
        double pixof = ((min - starting) / degoff) * leng;
        int pixoff = (int) (((min - starting) / degoff) * lenin);


        String maxy = mapFrame.formdd(b.getMaxY());
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
            grre.setStroke(new java.awt.BasicStroke(3F));
            out = Integer.toString(deg).concat(degree).concat(stout).concat("N");
            down = down + lenin;
            downd = downd + leng;
            if (wid + 25 - (int) downd < wid + 25 && wid + 25 - (int) downd > 25) {
                grre.drawLine(75, wid + 25 - (int) downd, 70, wid + 25 - (int) downd);
                grre.drawString(out, 0, wid + 25 - (int) downd);
            }
        }
        String minx = mapFrame.formdd(b.getMinX());
        minx = minx.replaceAll("\\D", "");


        String maxx = mapFrame.formdd(b.getMaxX());
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
    }

    //Method that writes the text file
    public static void loadtext(String name)throws SQLException {
     

        String base = layout1.getText(); // Stores the main text form shown in the text frame on the rewards tab
        // The fllowing are variables used to keep track of the different circumstances for each person such as
        // singular or plural or things that are unique to the letter such as the persons name

        String filenam;
        if (name.equals("")) {
            name = "Person Unknown";
        }

        String address = "";
        String na = name;

        //Attempt to query the database to get this particular persons address information
     
            /*
            Class.forName(conname);
            String url = dataurl;

            Connection con;
            if (localdata) {
                con = DriverManager.getConnection(url);
            } else {
                con = DriverManager.getConnection(url, user, pass);
            }

            Statement st = con.createStatement();
            
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
        //    con.close(); //close connection to database
            st.close();
            back.close(); //close the resultset returned by the database
   }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null && !conn.isClosed()){
               
                conn.close();
            }
           
        }
            //format the address
            address = address.concat(na);
            address = address.concat("\n");
            if (add != null) {

                // Split the address on any commas and put the rest on a new line,
                // This will solve the problem of all address info in one line seperated by commas
                String[] a = add.split(",");
                for (int rq = 0; rq < a.length; rq++) {
                    address = address.concat(a[rq].trim());
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

      

        //Format the address so that it is initiall over so many spaces, if more spaces needed, add the same amount to the next two lines of code
        address = "                                                                                      ".concat(address);
        address = address.replaceAll("\\n", "\n                                                                                      ");
        address = address.concat("\n\f");


        //Set up the location to print the letters
        filenam = datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("skipLetters/");

        File destination = new File(filenam);
        // if dir location does not exist create one
        if (!destination.exists()) {
            destination.mkdir();
        }

        // Name the file to be written to the location
        filenam = filenam.concat(name) + ".txt";

        //Variables used to break down, store and modify the sections of patragraphs used in the template(base)
        //getBetween method simply grabs all word in base that are between the specified tags
        String myinfo = mapFrame.getBetween(base, "PARAGRAPH A"); // between <PARAGRAPH A> and </PARAGRAPH A>
        String intro = mapFrame.getBetween(base, "PARAGRAPH B");

        name.replaceAll(" ", "_");
        intro = intro.replaceAll("<name>", name.split(" ")[0]); // add persons name


        // Variables to store the closing paragraphs that are added to each letter
        String fin = mapFrame.getBetween(base, "PARAGRAPH final");
        String end = mapFrame.getBetween(base, "PARAGRAPH end");

        // Variable set up in the case that somones released a taged crab that was captured in a later year
        // or assesment but had no returns themselves. They still get a letter.

        String outstring = "";

        //set up the my address with the proper spacing remember to add or subtract the same number of spaces
        // in each of the following lines of code when reformatting the output
        myinfo = myinfo.replaceAll("\\n", "\r                                                                                    ");
        myinfo = "                                                                                    ".concat(myinfo);



        //format this paragraph in the same maner as the above paragraph
        intro = intro.replaceAll("\\n", " ");
        intro = intro.replaceAll("  ", " ");
        intro = intro.replaceFirst(", ", ",\n\n");
        intro = intro.trim();

        fin = fin.replaceAll("\\n", " ");
        fin = fin.replaceAll("  ", " ");
        fin = fin.trim();

        // Put all the paragraphs back together in the outstring variable on the conditions
        outstring = myinfo + "\n\n" + intro + "\n\n" + fin + "\n\n" + end;

        //Set up the return address paragraph
        String retinfo = mapFrame.getBetween(base, "PARAGRAPH addresslabel");

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
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();

        }

     
    }//end loadText method
}