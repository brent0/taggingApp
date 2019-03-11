/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/*Code not yet fully commented, a work in progress
 *  
 * 
 */
import java.sql.*;

import org.jfree.chart.*;
import org.jfree.data.statistics.*;
import org.jfree.chart.plot.PlotOrientation;
import java.text.SimpleDateFormat;
import org.opengis.style.ContrastMethod;
import java.util.*;

import java.awt.RenderingHints;
import org.geotools.data.FeatureSource;

import org.geotools.factory.CommonFactoryFinder;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import org.opengis.filter.FilterFactory2;


import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import javax.imageio.ImageIO;

import java.io.*;

import com.vividsolutions.jts.geom.*;

import org.geotools.geometry.jts.JTSFactoryFinder;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.map.DefaultMapContext;
import org.geotools.map.MapContext;

import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.lite.StreamingRenderer;


import org.geotools.styling.*;


/**
 *
 * @author brent
 */

class posit{

    String tag;
    LinkedList<String> capd;
    String sampd;
    LinkedList<String> capla;
    LinkedList<String> caplo;
    String samla;
    String samlo;
    LinkedList<String> year;

}
public class statsFrame extends tagFrame{
    public static int width = 3000;  //1100
    public static int height = 2500;  //850
  
    
    
    public static void genSam()throws Exception{
        
        
        
        LinkedList<posit> poslist = new LinkedList<posit>();
    
                   /*
                    
                     Connection conn;
                    
            Class.forName(conname);
            String url = dataurl;

            if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, user, pass);
            }
                    Statement st = conn.createStatement();
                    
         */
        String toda = "";
                    toda = "SELECT * FROM sample INNER JOIN trip ON trip.trip_id = sample.trip;";

        Connection conn = null;
       
        try{
          
          
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            Statement st = conn.createStatement();

                    ResultSet back = st.executeQuery(toda);
                    while(back.next()){
                      
                        posit tem = new posit();
                        if(allyearcheck.isSelected()){
                        if(!back.getString("lat_DD_DDDD").equals("0.0") && !back.getString("long_DD_DDDD").equals("0.0") && !back.getString("long_DD_DDDD").equals("NA") && !back.getString("long_DD_DDDD").equals("0") && !back.getString("lat_DD_DDDD").equals("NA") && !back.getString("lat_DD_DDDD").equals("0")){

                        tem.samlo = back.getString("long_DD_DDDD");
                         tem.samla = back.getString("lat_DD_DDDD");
                        tem.tag = "sample";
                         poslist.add(tem);
                        }
                      }
                        else if(back.getString("date").split("/")[2].equals(yeardata2.getText())){
                            if(!back.getString("lat_DD_DDDD").equals("0.0") && !back.getString("long_DD_DDDD").equals("0.0") && !back.getString("long_DD_DDDD").equals("NA") && !back.getString("long_DD_DDDD").equals("0") && !back.getString("lat_DD_DDDD").equals("NA") && !back.getString("lat_DD_DDDD").equals("0")){

                         tem.samlo = back.getString("long_DD_DDDD");
                         tem.samla = back.getString("lat_DD_DDDD");
                         tem.tag = "sample";
                         poslist.add(tem);
                        }
                        }
                    }

                     back.close() ;
                     st.close();
                      if(allyearcheck.isSelected())
                          toda = "select * from capture;";
                      else toda = "select * from capture where year = '"+yeardata2.getText()+"' ;";

                    back = st.executeQuery(toda);
                    while(back.next()){
                       
                        posit tem = new posit();
                        if(!back.getString("lat_DD_DDDD").equals("0.0") && !back.getString("long_DD_DDDD").equals("0.0") && !back.getString("long_DD_DDDD").equals("NA") && !back.getString("long_DD_DDDD").equals("0") && !back.getString("lat_DD_DDDD").equals("NA") && !back.getString("lat_DD_DDDD").equals("0")){
                        tem.samlo = back.getString("long_DD_DDDD");
                         tem.samla = back.getString("lat_DD_DDDD");
                         tem.tag = "pos";
                         poslist.add(tem);
                        }
                    }

                     back.close() ;



               //conn.close();
        }
                catch (Exception e)
                {

                      System.err.println("Got an exception!");
                    e.printStackTrace();
                }finally{
            if(conn != null && !conn.isClosed()){
               
                conn.close();
            }
          
        }
        

          Coordinate[] labelcoords = new Coordinate[poslist.size()];
 double maxh =0;
                  double minh =180;
                  double maxw = 0;
                  double minw = -100;

         for(int i = 0; i<poslist.size(); i++){
         Coordinate c = new Coordinate(Double.parseDouble(poslist.get(i).samla), Double.parseDouble(poslist.get(i).samlo));
             labelcoords[i] = c;
             if(c.x > maxh)maxh = c.x;
             if(c.x < minh)minh = c.x;
             if(c.y < maxw)maxw = c.y;
             if(c.y > minw)minw = c.y;
         }
        
     
   
        Envelope zoom = new Envelope(minw, maxw, maxh, minh);
         zoom = new Envelope(-57.0, -66.5, 47.5, 42.5);
       Rectangle imageSize = new Rectangle(width,height);
       
       

       
       
       
       MapContext mmap = new DefaultMapContext();
       File shpFile4 = new File("Layers//SCShape//ColourENSChart_004_DM100_region.shp");//Windows

File shpFile6 = new File("Layers//SCShape//ColourENSChart_Coastline_polyline.shp");//Windows
File shpFile7 = new File("Layers//SCShape//ColourENSChart_DM200_region.shp");//Windows
File shpFile8 = new File("Layers//SCShape//ColourENSChart_Fill_Map_Base_region.shp");//Windows
File shpFile9 = new File("Layers//SCShape//ColourENSChart_Landmass_region.shp");//Windows
File shpFile10 = new File("Layers//SCShape//Snow_Crab_Zones2010_polyline.shp");//Windows

 File shpFile12 = new File("Layers//SCShape//ColourENSChart_Coastline_point.shp");//Windows

  
 FileDataStore store8 = FileDataStoreFinder.getDataStore(shpFile8);
  FeatureSource featureSource8 = store8.getFeatureSource();
  
   int intValue = Integer.parseInt( "4C7FFF",16);
Color aColor = new Color( intValue );
  
  mmap.addLayer(featureSource8, mapFrame.createPolygonStyle(aColor));
    
  FileDataStore store7 = FileDataStoreFinder.getDataStore(shpFile7);
  FeatureSource featureSource7 = store7.getFeatureSource();

  intValue = Integer.parseInt( "4CAEFF",16);
  aColor = new Color( intValue );
  mmap.addLayer(featureSource7,  mapFrame.createPolygonStyle(aColor));

  
          FileDataStore storea = FileDataStoreFinder.getDataStore(shpFile4);
        FeatureSource featureSourcea = storea.getFeatureSource();
 
intValue = Integer.parseInt( "BFE3FF",16);
 aColor = new Color( intValue );
mmap.addLayer(featureSourcea, mapFrame.createPolygonStyle(aColor));
    
   FileDataStore store10 = FileDataStoreFinder.getDataStore(shpFile10);
  FeatureSource featureSource10 = store10.getFeatureSource();
  
  intValue = Integer.parseInt( "F0B400",16);
  aColor = new Color( intValue );
  mmap.addLayer(featureSource10,   mapFrame.createLineStyle(aColor, 10));
  
 
 FileDataStore store9 = FileDataStoreFinder.getDataStore(shpFile9);
  FeatureSource featureSource9 = store9.getFeatureSource();

intValue = Integer.parseInt( "D1CA91",16);
aColor = new Color( intValue );
  mmap.addLayer(featureSource9,  mapFrame.createPolygonStyle(aColor));
FileDataStore store6 = FileDataStoreFinder.getDataStore(shpFile6);
  FeatureSource featureSource6 = store6.getFeatureSource();
  mmap.addLayer(featureSource6, mapFrame.createLineStyle(Color.BLACK, 3));
  
  FileDataStore store12 = FileDataStoreFinder.getDataStore(shpFile12);
  FeatureSource featureSource12 = store12.getFeatureSource();
  mmap.addLayer(featureSource12,  null);
    
  mmap.setAreaOfInterest(zoom, mmap.getCoordinateReferenceSystem() );
String label = "";
 if(north.isSelected()){
     zoom = new Envelope(-58.5, -61.5, 47.25, 46.0);
      mmap.setAreaOfInterest(zoom, mmap.getCoordinateReferenceSystem() );
      label = "N-ENS";
 }
 if(south.isSelected()){
     zoom = new Envelope(-57.0, -63.5, 46.1, 43.2);
      mmap.setAreaOfInterest(zoom, mmap.getCoordinateReferenceSystem() );
      label = "S-ENS";
 }
 if(xxxx.isSelected()){
     zoom = new Envelope(-63.0, -66.0, 45.0, 42.0);
      mmap.setAreaOfInterest(zoom, mmap.getCoordinateReferenceSystem() );
      label = "4X";
 }
 
 


 GTRenderer renderer = new StreamingRenderer();
    renderer.setContext( mmap );




    BufferedImage bigmap = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_RGB);


     Graphics2D gr = bigmap.createGraphics();


gr.setPaint(Color.WHITE);
gr.fill(imageSize);

gr.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, 
        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
                           
  gr.setRenderingHint(
        RenderingHints.KEY_RENDERING, 
        RenderingHints.VALUE_RENDER_QUALITY);


renderer.paint(gr, imageSize, mmap.getAreaOfInterest());


for (int k = 0; k<poslist.size(); k++){

         int x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist.get(k).samlo), width);
        int y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist.get(k).samla), height);
       Color c;
       int siz = 0;
       if(poslist.get(k).tag.equals("sample")){
             gr.setColor(Color.red);
             siz = 9;
       }
        else{
             gr.setColor(Color.blue);
             siz = 9 ;
        }
        gr.drawOval(x, y, siz, siz);
        

    }
String yeardata = "All Samples & Captures";
      if(!allyearcheck.isSelected())
         yeardata =  yeardata.concat(" ").concat(yeardata2.getText());

      
      gr.setColor(Color.black);
gr.setFont(new Font("TimesRoman", Font.PLAIN,  120));




BufferedImage bigmap2 = new BufferedImage(imageSize.width+300, imageSize.height+200, BufferedImage.TYPE_INT_RGB);
                    
    Rectangle imageSize2 = new Rectangle(width+300,height+200);
                        
                         Graphics2D gr2 = bigmap2.createGraphics();
                        gr2.setPaint(Color.WHITE);
                           gr2.fill(imageSize2);
                               
                             gr2.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, 
        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
                           
  gr2.setRenderingHint(
        RenderingHints.KEY_RENDERING, 
        RenderingHints.VALUE_RENDER_QUALITY);

                           
                           
                           gr2.setColor(Color.black);
                           gr2.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 72));
                       
                        int x = 1000;
                       int y = 75;
                       label = yeardata+" ".concat(label);
                       int labsiz = label.length()*72; 
                       int startpos = (int)3300/2;
                       startpos = startpos - (int)labsiz/4;
                       
                       gr2.drawString(label, startpos,y );
                      
                     
                   String deg = "\u00B0";
                        
                           gr2.setFont(new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 50));
                           gr2.drawImage(bigmap, 100, 100, null);    
                                gr2.setStroke(new java.awt.BasicStroke(6.0f));
                         gr2.setColor(Color.black);
                       
                        
                         
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -64.0, width);
                         if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("64".concat(deg), x+80, 2665);
                     }
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -62.0, width);
                         if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("62".concat(deg), x+80, 2665);
                     }
                         x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -66.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("66".concat(deg), x+80, 2665);
                      } 
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -62.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("62".concat(deg), x+80, 2665);
                      }
                         
                         x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -58.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("58".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -59.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("59".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -57.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("57".concat(deg), x+80, 2665);
                      }  
                      x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -63.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("63".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -65.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("65".concat(deg), x+80, 2665);
                      }  
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -60.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("60".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -61.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("61".concat(deg), x+80, 2665);
                      }  
                           y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 43.0, height);
                      if(y>=0 && y<=height){
                           gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("43".concat(deg), 5, y+110);
                      }
                       y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 44.0, height);
                      if(y>=0 && y<=height){
                           gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("44".concat(deg), 5, y+110);
                      }
                       y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 46.0, height);
                      if(y>=0 && y<=height){
                           gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("46".concat(deg), 5, y+110);
                      }
                          y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 45.0, height);
                             if(y>=0 && y<=height){
                          gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("45".concat(deg), 5, y+110);
                             }
                          y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 47.0, height);
                             if(y>=0 && y<height){
                          gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("47".concat(deg), 5, y+110);
                             }


    
                       gr2.drawRect(100, 100, width, height);
                         gr2.setColor( new Color(200, 245, 245));
                     
                       gr2.fill3DRect(3110, 2500, 50, 50, true);
                       gr2.setColor(new Color(90, 175, 175));
                       gr2.fill3DRect(3110, 2550, 50, 50, true);
                      gr2.setColor(Color.black);
                       gr2.setStroke(new java.awt.BasicStroke(3.0f));
                       gr2.drawRect(3110, 2500, 50, 100);
                       gr2.setFont(new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 45));
                      gr2.drawString("100 m", 3170, 2540);
                       gr2.drawString("200 m", 3170, 2590);
                 
                       
                         gr2.setColor(Color.white);
                       gr2.fillRect(width-350, height-20, 400, 110);
                       
                       gr2.setColor(Color.red);
                       gr2.drawOval(width-340, height, 20, 20);
                       gr2.drawString("Sample positions", width - 310, height+20);
                       
                       
                       gr2.setColor(Color.blue);
                       gr2.drawOval(width - 340, height + 50, 20, 20);
                        gr2.drawString("Capture positions", width -310, height+70);
                       String tofile = "maps//"+label  ;

                        File fileToSave = new File(tofile + ".jpeg");
                         if (fileToSave.exists()){
                        fileToSave.delete();
                         }


                        ImageIO.write(bigmap2, "jpeg", fileToSave);








      




    }
public static void getStatsForArea(String aname, LineString forarea, String foryear){
    
    
    
    if(forarea == null)
    statstext.append("STATS FOR ALL AREAS");
    else statstext.append("STATS FOR " + aname);
    
    if(forarea == null)
    statstext.append("STATS FOR ALL YEARS");
    else statstext.append("STATS FOR " + foryear);
    
 
    
    
}
    public static void getStats(String foryear)throws Exception{

          LinkedList distlis = new LinkedList();
LinkedList monthlis = new LinkedList();
LinkedList direlis = new LinkedList();
   LinkedList speedlis = new LinkedList();     
        String forarea = "ALL AREAS";
        String foryearmult = foryear;
        if(tagFrame.north.isSelected())forarea = "NENS";
        
        if(tagFrame.south.isSelected()) forarea = "SENS";
        if(tagFrame.xxxx.isSelected()){
            forarea = "4X";
         foryearmult = Integer.toString(Integer.parseInt(foryear)-1).concat("_"+foryear);
        
        }
      
        statstext.append("STATS \n FOR "+forarea+" \n");
        int tagsretuni;
int tagsrecap;
int tagsapplied = 0;
LinkedList<posit> poslist = new LinkedList<posit>();
 LinkedList<posit> poslist2 = new LinkedList<posit>();
if(allyearcheck.isSelected())foryear = "all";

if(foryear.equals("all")){
    statstext.append("FOR ALL YEARS: \n");

 }
 else statstext.append("FOR YEAR " + foryearmult + ": \n");
        
                      Connection conn;
                    try {
            Class.forName(conname);
            String url = dataurl;

            if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, dbuser, pass);
            }

                    Statement st = conn.createStatement();
                    String toda = "";
                    if(foryear.equals("all")){
                        if(forarea.equals("ALL AREAS")){
                          
                            toda = "select COUNT(*) from(select distinct tag from capture)t1;";
                      
                        }
                        else toda = "select COUNT(*) from(select distinct tag from capture where statsarea = '" +forarea+ "' )t1;";
                    }
                    else{
                        if(forarea.equals("ALL AREAS")){
                            toda = "select COUNT(*) from(select distinct tag from capture where year = "+foryear+ " )t1;";
                        }
                        else toda = "select COUNT(*) from(select distinct tag from capture where year= "+foryear+ " AND statsarea = '"+forarea+ "')t1;";
                        }
                    ResultSet back = st.executeQuery(toda);
                    
                    back.next();
                    String res = back.getString(1);
                    
                    tagsretuni = Integer.parseInt(res);

                   statstext.append("Total number of distinct tags returned from this area and year: " + res + "\n");

                     back.close() ;

                     
                     if(!foryear.equals("all")){
                       if(forarea.equals("ALL AREAS")){
                       
                        int count = 0;
                        back = st.executeQuery(toda);
                          back.next();
                        count = Integer.parseInt(back.getString(1));
                        statstext.append("Total number of crabs taged: " + count + "\n");
                        back.close() ;
                       }
                       else{
                        st = conn.createStatement();
                        //Tagged in this area in this year
                         toda = "select COUNT(*) from(select * from (Select bio.tag_id, bio.sample_num, sample.trip, trip.trip_id  from bio, trip join sample where sample.sample_id = bio.sample_num and sample.trip = trip.trip_id and year = "+foryear+" and trip.statsarea = '"+ forarea +"')t2)t1;";
                        
                        int count = 0;
                        back = st.executeQuery(toda);
                          back.next();
                        count = Integer.parseInt(back.getString(1));
                        statstext.append("Total number of crabs taged in this area: " + count + "\n");
                        back.close() ;
                       }
                     }
                     
                     
                     
                     st = conn.createStatement();

                     if(foryear.equals("all")){
                        if(forarea.equals("ALL AREAS"))
                         toda = "SELECT COUNT(*) FROM(select tag from capture)t1;";
                        else toda = "SELECT COUNT(*) FROM(select tag from capture where statsarea = '"+ forarea +"')t1;";
                     }
                     else{ 
                         if(forarea.equals("ALL AREAS"))
                             toda = "SELECT COUNT(*) FROM(select tag from capture where year = "+foryear+ " )t1;";
                         else toda = "SELECT COUNT(*) FROM(select tag from capture where statsarea = '"+ forarea +"' and year = "+foryear+ " )t1;";
                     }
                     back = st.executeQuery(toda);
                       back.next();
                    res = back.getString(1);
                
                    tagsrecap = Integer.parseInt(res) - tagsretuni;
                    statstext.append("Total number of tags returned more than once: " + tagsrecap + "\n");
                    back.close() ;
if(!forarea.equals("ALL AREAS")){
    
                     st = conn.createStatement();
                     
                     toda = "SELECT COUNT(*) FROM (Select * from (select * from trip, sample, bio where trip.trip_id = sample.trip and statsarea = '"+forarea+"'  and bio.sample_num = sample_id)t3 )t1;";
                     back = st.executeQuery(toda);
                       back.next();
                     res = back.getString(1);
                     tagsapplied = Integer.parseInt(res);
                      statstext.append("Total number of tags ever applied in this area: " + tagsapplied + "\n");  
                    back.close() ;
}
if(!forarea.equals("ALL AREAS") && !foryear.equals("all")){
    
                     st = conn.createStatement();
                     
                     toda =  "SELECT COUNT(*) FROM(Select * from (select * from trip, sample, bio  where trip.trip_id = sample.trip and year = "+ foryear +" and statsarea = '"+ forarea +"' and bio.sample_num = sample_id )t3)t1";
                     back = st.executeQuery(toda); 
                     back.next();
                     res = back.getString(1);
                       
                     tagsapplied = Integer.parseInt(res);
                      statstext.append("Total number of tags applied in this area this year: " + tagsapplied + "\n");  
                    back.close() ;
}

                
                     st = conn.createStatement();
                     toda = "SELECT COUNT(*) FROM (Select * from bio)a;";
                     back = st.executeQuery(toda); 
                     back.next();
                     res = back.getString(1);
                     tagsapplied = Integer.parseInt(res);
                  
                      statstext.append("Total number of tags ever applied: " + tagsapplied + "\n");  
                    back.close() ;
                     
                   

                    st = conn.createStatement();
        
                    String toda3 = "";
                    if(foryear.equals("all")){
                       if(forarea.equals("ALL AREAS"))toda3 = "Select DISTINCT tag from capture;" ;
                       else{ 
                           if(forarea.equals("NENS")) toda3 = "Select DISTINCT tag from capture where statsarea = '"+forarea+"' or statsarea = 'GULF';" ;
                           else toda3 = "Select DISTINCT tag from capture where statsarea = '"+forarea+"';"; 
                       }
                    }
                    else{
             
                        if(forarea.equals("ALL AREAS"))toda3 = "Select DISTINCT tag from capture where year = "+foryear+";"; 
                                                
                        else{ 
                           if(forarea.equals("NENS"))
                                   toda3 = "Select DISTINCT tag from capture where year = "+foryear+" and (statsarea = '"+forarea+"' or statsarea = 'GULF');" ;
                           else toda3 = "Select DISTINCT tag from capture where year = "+foryear+" and statsarea = '"+forarea+"';"; 
                       }
                       
                    }
                    back = st.executeQuery(toda3);
                  int time = 0;
        
                    while(back.next()){
           
//if(forarea.equals("ALL AREAS")) inproperarea = true;
//if(foryear.equals("all")) inproperyear = true;
                        Statement st2 = conn.createStatement();
                        String toda2 = "";
             
                        toda2 = "select * from capture where tag = " + back.getString("tag") + ";";
                     
                        ResultSet back2 = st2.executeQuery(toda2);
                           
                          posit tem = new posit();
                          posit tem2 = new posit();
                          tem.capd = new LinkedList<String>();
                          tem.capla = new LinkedList<String>();
                          tem.caplo = new LinkedList<String>();
                           tem.year = new LinkedList<String>();
                           
                          tem2.capd = new LinkedList<String>();
                          tem2.capla = new LinkedList<String>();
                          
                          tem2.caplo = new LinkedList<String>();
                          tem2.year = new LinkedList<String>();
                          
                          boolean once = false;
   boolean once2 = false;

                         Statement st3 =  conn.createStatement();
                         toda = "Select * from sample, trip, bio where trip.trip_id = sample.trip and bio.sample_num = sample.sample_id and bio.tag_id = "+ back.getString("tag")+";";
                      
                         ResultSet back3 = st3.executeQuery(toda);
                         
                         back3.next();
                     
                         tem.tag = back3.getString("tag_id");
                         tem.sampd = back3.getString("date");
                         tem.samlo = back3.getString("long_DD_DDDD");
                         tem.samla = back3.getString("lat_DD_DDDD");
                
                         tem2.tag = back3.getString("tag_id");
                         tem2.sampd = back3.getString("date");
                         tem2.samlo = back3.getString("long_DD_DDDD");
                         tem2.samla = back3.getString("lat_DD_DDDD");
                         String dat = back3.getString("date");
                       
                         back3.close();
                         st3.close();
 
                       

                         while(back2.next()){
                      
                           
                             String cappdat = back2.getString("date");
                               time++;
       
                   if(!cappdat.equals("unknown") && !dat.equals("unknown")){
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date capda  = formatter.parse(cappdat);
                java.util.Date sampda  = formatter.parse(dat);
               
                 Calendar scal = Calendar.getInstance();
                scal.setTime(sampda);
                Calendar ccal = Calendar.getInstance();
                ccal.setTime(capda);
                boolean good = true;
               
                if(back2.getString("Lat_DD_DDDD").equals("0")|| back2.getString("Lat_DD_DDDD").equals("NA")) good = false;
                if(back2.getString("Long_DD_DDDD").equals("0")||back2.getString("Long_DD_DDDD").equals("NA")) good = false;
            
                 if(daysBetween(scal , ccal) > 10  && good){
                                
                                    if(Double.parseDouble(back2.getString("Lat_DD_DDDD")) > 42 && Double.parseDouble(back2.getString("Lat_DD_DDDD")) < 50){
                                    if(Double.parseDouble(back2.getString("Long_DD_DDDD")) > -70 && Double.parseDouble(back2.getString("Long_DD_DDDD")) < -55){
                   
                                    once = true;
                             

                                    tem.capd.add(back2.getString("date"));
                                   tem.capla.add(back2.getString("Lat_DD_DDDD"));
                                   tem.caplo.add(back2.getString("Long_DD_DDDD"));
                                   tem.year.add(back2.getString("year"));
                                }
                   }
                                               //  poslist.add(new posit( back2.getString("TagID"), back2.getString("Date"), back2.getString("Lat_DDDD_DD"), back2.getString("Long_DDDD_DD"), back.getString("Day_Captured"), back.getString("Lat_DDDD_DD"), back.getString("Long_DDDD_DD")));
               }
               }
                   else{
                         boolean good = true;
               
                if(back2.getString("Lat_DD_DDDD").equals("0")|| back2.getString("Lat_DD_DDDD").equals("NA")) good = false;
                if(back2.getString("Long_DD_DDDD").equals("0")||back2.getString("Long_DD_DDDD").equals("NA")) good = false;
            
                 if(good){
                                    
                                    if(Double.parseDouble(back2.getString("Lat_DD_DDDD")) > 42 && Double.parseDouble(back2.getString("Lat_DD_DDDD")) < 50){
                                    if(Double.parseDouble(back2.getString("Long_DD_DDDD")) > -70 && Double.parseDouble(back2.getString("Long_DD_DDDD")) < -55){
                   
                                   once2 = true; 
                                

                                    tem2.capd.add("09/09/9999");
                                   tem2.capla.add(back2.getString("Lat_DD_DDDD"));
                                   tem2.caplo.add(back2.getString("Long_DD_DDDD"));
                                   tem2.year.add(back2.getString("year"));
                                }
                   }
                                               //  poslist.add(new posit( back2.getString("TagID"), back2.getString("Date"), back2.getString("Lat_DDDD_DD"), back2.getString("Long_DDDD_DD"), back.getString("Day_Captured"), back.getString("Lat_DDDD_DD"), back.getString("Long_DDDD_DD")));
               }
                             
                         }
             // curr.labelnames.add(a);
                         }

  //                   if(once && inproperarea && inproperyear)poslist.add(tem);
if(once)poslist.add(tem);

if(once2)poslist2.add(tem2);

back2.close();
                     }
  statstext.append("The total number of tags returned, for tags captured this year (before and after this year) is: "+time+ "\n");

                     back.close();
                     conn.close();
                }
                catch (Exception e)
                {

                     
                    e.printStackTrace();

                }

  statstext.append("\n");
                    statstext.append("The following stats are from captures that occured more than " +
                            "10 days from release date and that have useful positional data : \n");
 //Remove entries that only contain capdata from a undesired year, occurs when legit year data is not added due to <10 day old and other returns from same tag kept   
if(!foryear.equals("all")){
for(int k = 0; k<poslist.size(); k++){
    boolean goodcap = false;
   
    for(int n = 0; n < poslist.get(k).capd.size(); n++){
      if(poslist.get(k).year.get(n).equals(foryear))goodcap = true;
   }
   if(!goodcap) poslist.remove(k);
}
            
}
//Envelope zoom = new Envelope();
            Coordinate[] labelcoords = new Coordinate[(poslist.size()*2) + (poslist2.size()*2)];
          double maxh =0;
                  double minh =180;
                  double maxw = 0;
                  double minw = -100;
          int index = 0;
         for(int i = 0; i<poslist.size(); i++){
         Coordinate c = new Coordinate(Double.parseDouble(poslist.get(i).samla), Double.parseDouble(poslist.get(i).samlo));
             labelcoords[index] = c;
             if(c.x > maxh)maxh = c.x;
             if(c.x < minh)minh = c.x;
             if(c.y < maxw)maxw = c.y;
             if(c.y > minw)minw = c.y;
             c = new Coordinate(Double.parseDouble(poslist.get(i).capla.get(0)), Double.parseDouble(poslist.get(i).caplo.get(0)));
             labelcoords[index+1] = c;
                 index = index + 2;
        if(c.x > maxh)maxh = c.x;
             if(c.x < minh)minh = c.x;
             if(c.y < maxw)maxw = c.y;
             if(c.y > minw)minw = c.y;

         }
          for(int i = 0; i<poslist2.size(); i++){
      
         Coordinate c = new Coordinate(Double.parseDouble(poslist2.get(i).samla), Double.parseDouble(poslist2.get(i).samlo));
             labelcoords[index] = c;
             if(c.x > maxh)maxh = c.x;
             if(c.x < minh)minh = c.x;
             if(c.y < maxw)maxw = c.y;
             if(c.y > minw)minw = c.y;
             c = new Coordinate(Double.parseDouble(poslist2.get(i).capla.get(0)), Double.parseDouble(poslist2.get(i).caplo.get(0)));
             labelcoords[index+1] = c;
                 index = index + 2;
        if(c.x > maxh)maxh = c.x;
             if(c.x < minh)minh = c.x;
             if(c.y < maxw)maxw = c.y;
             if(c.y > minw)minw = c.y;

         }
          GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory( null );
             LineString label = geometryFactory.createLineString(labelcoords);
            
             Envelope zoom = new Envelope(minw, maxw, maxh, minh);
             
       
             double ytraveled = maxh - minh;
                double xtraveled = maxw - minw;
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

                zoom = new Envelope(minw, maxw, minh, maxh);
          
                Coordinate center = zoom.centre();
                zoom = new Envelope(center.x + scale, center.x - scale, center.y - scale, center.y + scale);
          
                    zoom.expandBy(.2, .2);
             
             
             
 
      
   //          if(zoom.getWidth()<zoom.getHeight()) zoom =  new Envelope(minw+((zoom.getHeight()-zoom.getWidth())/2), maxw-((zoom.getHeight()-zoom.getWidth())/2), maxh, minh);
 
     //        else zoom = new Envelope(minw, maxw, maxh+((zoom.getWidth()-zoom.getHeight())/2), minh -((zoom.getWidth()-zoom.getHeight())/2));
             
            //    if (zoom.getHeight() > 0.15) {
              //      zoom.expandBy(.2, .2);
               // } else {
                //    zoom.expandBy(.05, .05);
               // }
           
            // if(zoom.getWidth()<zoom.getHeight()) zoom =  new Envelope(minw+((zoom.getHeight()-zoom.getWidth())/2), maxw-((zoom.getHeight()-zoom.getWidth())/2), maxh, minh);
 
             //else zoom = new Envelope(minw, maxw, maxh+((zoom.getWidth()-zoom.getHeight())/2), minh -((zoom.getWidth()-zoom.getHeight())/2));
             
             //zoom.expandBy(zoom.getHeight());
            // if(zoom.getMaxY()> 47.25) zoom = new Envelope(zoom.getMinX(), zoom.getMaxX(), 47.25, zoom.getMinY());
            // zoom.expandBy(-.1, -.1);


              Rectangle imageSize = new Rectangle(width,height);
    MapContext mmap = new DefaultMapContext();
    
    File shpFile4 = new File("Layers//SCShape//ColourENSChart_004_DM100_region.shp");//Windows

File shpFile6 = new File("Layers//SCShape//ColourENSChart_Coastline_polyline.shp");//Windows
File shpFile7 = new File("Layers//SCShape//ColourENSChart_DM200_region.shp");//Windows
File shpFile8 = new File("Layers//SCShape//ColourENSChart_Fill_Map_Base_region.shp");//Windows
File shpFile9 = new File("Layers//SCShape//ColourENSChart_Landmass_region.shp");//Windows
File shpFile10 = new File("Layers//SCShape//Snow_Crab_Zones2010_polyline.shp");//Windows

 File shpFile12 = new File("Layers//SCShape//ColourENSChart_Coastline_point.shp");//Windows

  
 FileDataStore store8 = FileDataStoreFinder.getDataStore(shpFile8);
  FeatureSource featureSource8 = store8.getFeatureSource();
  
   int intValue = Integer.parseInt( "4C7FFF",16);
Color aColor = new Color( intValue );
  
  mmap.addLayer(featureSource8, mapFrame.createPolygonStyle(aColor));
    
  FileDataStore store7 = FileDataStoreFinder.getDataStore(shpFile7);
  FeatureSource featureSource7 = store7.getFeatureSource();

  intValue = Integer.parseInt( "4CAEFF",16);
  aColor = new Color( intValue );
  mmap.addLayer(featureSource7,  mapFrame.createPolygonStyle(aColor));

  
          FileDataStore storea = FileDataStoreFinder.getDataStore(shpFile4);
        FeatureSource featureSourcea = storea.getFeatureSource();
 
intValue = Integer.parseInt( "BFE3FF",16);
 aColor = new Color( intValue );
mmap.addLayer(featureSourcea, mapFrame.createPolygonStyle(aColor));
    
   FileDataStore store10 = FileDataStoreFinder.getDataStore(shpFile10);
  FeatureSource featureSource10 = store10.getFeatureSource();
  
  intValue = Integer.parseInt( "F0B400",16);
  aColor = new Color( intValue );
  mmap.addLayer(featureSource10,   mapFrame.createLineStyle(aColor, 10));
  
 
 FileDataStore store9 = FileDataStoreFinder.getDataStore(shpFile9);
  FeatureSource featureSource9 = store9.getFeatureSource();

intValue = Integer.parseInt( "D1CA91",16);
aColor = new Color( intValue );
  mmap.addLayer(featureSource9,  mapFrame.createPolygonStyle(aColor));
FileDataStore store6 = FileDataStoreFinder.getDataStore(shpFile6);
  FeatureSource featureSource6 = store6.getFeatureSource();
  mmap.addLayer(featureSource6, mapFrame.createLineStyle(Color.BLACK, 3));
  
  FileDataStore store12 = FileDataStoreFinder.getDataStore(shpFile12);
  FeatureSource featureSource12 = store12.getFeatureSource();
  mmap.addLayer(featureSource12,  null);
  /*  
  if(north.isSelected()){
     zoom = new Envelope(-58.0, -62.0, 47.25, 46.0);
      mmap.setAreaOfInterest(zoom, mmap.getCoordinateReferenceSystem() );
     
 }
 if(south.isSelected()){
     zoom = new Envelope(-57.0, -63.5, 46.1, 43.2);
      mmap.setAreaOfInterest(zoom, mmap.getCoordinateReferenceSystem() );
     
 }
 if(xxxx.isSelected()){
     zoom = new Envelope(-63.0, -66.0, 45.0, 42.0);
      mmap.setAreaOfInterest(zoom, mmap.getCoordinateReferenceSystem() );
    
 }
*/
 mmap.setAreaOfInterest(zoom, mmap.getCoordinateReferenceSystem() );


 GTRenderer renderer = new StreamingRenderer();
    renderer.setContext( mmap );




    BufferedImage bigmap = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_RGB);


     Graphics2D gr = bigmap.createGraphics();


gr.setPaint(Color.WHITE);
gr.fill(imageSize);

gr.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, 
        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
                           
  gr.setRenderingHint(
        RenderingHints.KEY_RENDERING, 
        RenderingHints.VALUE_RENDER_QUALITY);


renderer.paint(gr, imageSize, mmap.getAreaOfInterest());


for (int k = 0; k<poslist.size(); k++){
    posit te = poslist.get(k);
    int inde = 0;

    while(inde<te.capd.size()){
        boolean change = false;

        int y = inde+1;
                while(y<te.capd.size()){
                    
            if(mapFrame.dateToInt(te.capd.get(inde)) > mapFrame.dateToInt(te.capd.get(y))){
                change = true;
                te.capd.addFirst(te.capd.get(y));
                te.capd.remove(y);
                te.capla.addFirst(te.capla.get(y));
                te.capla.remove(y);
                te.caplo.addFirst(te.caplo.get(y));
                te.caplo.remove(y);
                inde=0;
                y = inde+1;

            }
            else {

                y++;
            }
                }
        inde ++;
        //if(change)inde = 0;
        //else
        //   inde = inde++;
    }



}
for (int k = 0; k<poslist2.size(); k++){
    posit te = poslist2.get(k);
    int inde = 0;


    while(inde<te.capd.size()){
        boolean change = false;

        int y = inde+1;
                while(y<te.capd.size()){
            if(mapFrame.dateToInt(te.capd.get(inde)) > mapFrame.dateToInt(te.capd.get(y))){
                change = true;
                te.capd.addFirst(te.capd.get(y));
                te.capd.remove(y);
                te.capla.addFirst(te.capla.get(y));
                te.capla.remove(y);
                te.caplo.addFirst(te.caplo.get(y));
                te.caplo.remove(y);
                inde=0;
                y = inde+1;

            }
            else {

                y++;
            } 
                }
        inde ++;
        //if(change)inde = 0;
        //else
        //   inde = inde++;
    }



}
if(foryear.equals("all")){

long daystot = 0;
  double totaldis = 0;
  double largestdis = 0;
  String lardistag = "";
  String lardays = "";
  long larday = 0;
  double retotaldis = 0;
  
  
 GeodeticCalculator calc = new GeodeticCalculator();


        for(int i = 0; i<poslist.size(); i++){
    
        int x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist.get(i).samlo), width);
        int y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist.get(i).samla), height);
        int xx = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist.get(i).caplo.get(0)), width);
        int yy = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist.get(i).capla.get(0)), height);

        calc.setStartingGeographicPoint(Double.parseDouble(poslist.get(i).samlo), Double.parseDouble(poslist.get(i).samla));
        calc.setDestinationGeographicPoint(Double.parseDouble(poslist.get(i).caplo.get(0)), Double.parseDouble(poslist.get(i).capla.get(0)));

        
        double dist = calc.getOrthodromicDistance(); //in meters
        double azimuth = calc.getAzimuth(); // in degrees between -180 to 180
    
        dist = dist/1000; //in km
        distlis.add(dist);
       totaldis = totaldis + dist;
        if(dist > largestdis){
            largestdis = dist;
            lardistag = poslist.get(i).tag + " between dates " + poslist.get(i).sampd + " and " + poslist.get(i).capd.get(0);
        }

       
SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
java.util.Date capda  = formatter.parse(poslist.get(i).capd.get(0));
               java.util.Date sampda  = formatter.parse(poslist.get(i).sampd);

               Calendar scal = Calendar.getInstance();
               scal.setTime(sampda);
               Calendar ccal = Calendar.getInstance();
               ccal.setTime(capda);
       daystot = daystot + daysBetween(scal , ccal);
       
      if(daysBetween(scal , ccal) > larday){
          larday = daysBetween(scal , ccal);
          lardays = poslist.get(i).tag + " between dates " + poslist.get(i).sampd + " and " + poslist.get(i).capd.get(0) + " with" +
                  " a total distance traveled of "+ dist ;
           
      }
     monthlis.add(daysBetween(scal , ccal));
     

speedlis.add((dist/daysBetween(scal , ccal))*30);

if((dist/(daysBetween(scal , ccal))*30)>40){


}

if(azimuth <= 0)azimuth = azimuth + 360;
if(azimuth <= 360)direlis.add(azimuth);





java.awt.Stroke s  = new BasicStroke(5);
Color c = new Color(0,0,0);


if(Integer.parseInt(poslist.get(i).capd.get(0).split("/")[2]) == Integer.parseInt(poslist.get(i).sampd.split("/")[2]))  c = Color.red ;
else if(Integer.parseInt(poslist.get(i).capd.get(0).split("/")[2]) == Integer.parseInt(poslist.get(i).sampd.split("/")[2])+1) c = Color.blue;
else if(Integer.parseInt(poslist.get(i).capd.get(0).split("/")[2]) == Integer.parseInt(poslist.get(i).sampd.split("/")[2])+2)  c =  new Color( Integer.parseInt( "8E390E",16) );
else c = new Color( Integer.parseInt( "119011",16) );


if(!tagFrame.onlyplotrecaps.isSelected()) {
if(x == xx && y == yy){
           gr.drawLine(x-1, y-1, x+1, y+1);
            gr.drawLine(x-1, y+1, x+1, y-1);
       }
       else gr = drawsmallArrow(gr, x, y, xx, yy, c, s);

}
if(tagFrame.onlyplotrecaps.isSelected() && poslist.get(i).capla.size()>1){
    if(x == xx && y == yy){
           gr.drawLine(x-1, y-1, x+1, y+1);
            gr.drawLine(x-1, y+1, x+1, y-1);
       }
       else gr = drawsmallArrow(gr, x, y, xx, yy, c, s);

    
}







        
           }
        
                for(int i = 0; i<poslist2.size(); i++){

        int x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist2.get(i).samlo), width);
        int y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist2.get(i).samla), height);
        int xx = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist2.get(i).caplo.get(0)), width);
        int yy = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist2.get(i).capla.get(0)), height);

       
java.awt.Stroke s  = new BasicStroke(5);
Color c = new Color(0,0,0);


if(Integer.parseInt(poslist.get(i).capd.get(0).split("/")[2]) == Integer.parseInt(poslist.get(i).sampd.split("/")[2]))  c = Color.red ;
else if(Integer.parseInt(poslist.get(i).capd.get(0).split("/")[2]) == Integer.parseInt(poslist.get(i).sampd.split("/")[2])+1) c = Color.blue;
else if(Integer.parseInt(poslist.get(i).capd.get(0).split("/")[2]) == Integer.parseInt(poslist.get(i).sampd.split("/")[2])+2)  c =  new Color( Integer.parseInt( "8E390E",16) );
else c = new Color( Integer.parseInt( "119011",16) );


if(!tagFrame.onlyplotrecaps.isSelected()) {
if(x == xx && y == yy  ){
           gr.drawLine(x-1, y-1, x+1, y+1);
            gr.drawLine(x-1, y+1, x+1, y-1);
       }
       else gr = drawsmallArrow(gr, x, y, xx, yy, c, s);

}
if(tagFrame.onlyplotrecaps.isSelected() && poslist.get(i).capla.size()>1){
    if(x == xx && y == yy){
           gr.drawLine(x-1, y-1, x+1, y+1);
            gr.drawLine(x-1, y+1, x+1, y-1);
       }
       else gr = drawsmallArrow(gr, x, y, xx, yy, c, s);

    
}
           }

        
    Object [] value = distlis.toArray();

double[] v  = new double[value.length];
for(int i = 0; i < value.length; i++ ) v[i] = Double.parseDouble(value[i].toString());
           int number = 100;
       HistogramDataset dataset = new HistogramDataset();
       dataset.setType(HistogramType.RELATIVE_FREQUENCY);
       dataset.addSeries("Histogram",v,number);
       String plotTitle = "Displacement, All Areas & Years"; 
       String xaxis = "Displacement (km)";
       String yaxis = "Frequency"; 
       PlotOrientation orientation = PlotOrientation.VERTICAL; 
       boolean show = false; 
       boolean toolTips = false;
       boolean urls = false; 
       JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
                dataset, orientation, show, toolTips, urls);
       int chartwidth = 500;
       int chartheight = 300; 
        
        ChartUtilities.saveChartAsPNG(new File("Distancehistogramall.PNG"), chart, chartwidth, chartheight);
        
          value = direlis.toArray();

v  = new double[value.length];
double [] v2 = new double[value.length + 2]; 
double az = 0;
int i = 0;

for(i = 0; i < value.length; i++ ){
   
   az = Double.parseDouble(value[i].toString());

   if(az<22.5) v2[i] = 0;
    else if(az<67.5) v2[i] = 1;
    else if(az<112.5) v2[i] = 2;
    else if(az<157.5) v2[i] = 3;
    else if(az<202.5) v2[i] = 4;
    else if(az<247.5) v2[i] = 5;
    else if(az<292.5) v2[i] = 6;
    else if(az<337.5) v2[i] = 7;
    else v2[i] = 0;
    
   
    
    v[i] = Double.parseDouble(value[i].toString());
}

           number = 16;
       dataset = new HistogramDataset();
       dataset.setType(HistogramType.RELATIVE_FREQUENCY);
       dataset.addSeries("Histogram",v,number);
       plotTitle = "Direction, All Areas & Years"; 
       xaxis = "Direction (degrees)";
       yaxis = "Frequency"; 
       
       orientation = PlotOrientation.VERTICAL; 
       show = false; 
       toolTips = false;
       urls = false; 
       chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
                dataset, orientation, show, toolTips, urls);
       
       chartwidth = 500;
       chartheight = 300; 
        
        ChartUtilities.saveChartAsPNG(new File("Directionhistogramall.PNG"), chart, chartwidth, chartheight);
        
    
         number = 8;
        dataset = new HistogramDataset();
       dataset.setType(HistogramType.RELATIVE_FREQUENCY);
       dataset.addSeries("Histogram",v2,number);
       plotTitle = "Direction, All Areas & Years"; 
       xaxis = "Direction range";
       yaxis = "Frequency"; 
       orientation = PlotOrientation.VERTICAL; 
       show = false; 
       toolTips = false;
       urls = false; 
       chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
                dataset, orientation, show, toolTips, urls);
       chartwidth = 500;
       chartheight = 300; 
        
        ChartUtilities.saveChartAsPNG(new File("basicDirectionhistogramall.PNG"), chart, chartwidth, chartheight);
        
        
        
        
        
        
         value = monthlis.toArray();

v  = new double[value.length];
int yearone = 0;
int yeartwo = 0;
int yearthree = 0;
int yearfour = 0;
int yearfiveplus = 0;

for(i = 0; i < value.length; i++ ){    
   double val = Double.parseDouble(value[i].toString());
    v[i] = val;

    if(val < 140.0) yearone = yearone + 1;
    else if(val < 500.0) yeartwo = yeartwo + 1;
    else if(val < 860.0) yearthree = yearthree + 1;
    else if(val < 1220.0) yearfour = yearfour + 1;
    else if (val > 1220) yearfiveplus = yearfiveplus + 1;

}

//System.out.println("YEAR PERCENTAGES");
int yeartotal = yearone + yeartwo + yearthree + yearfour + yearfiveplus;
//System.out.println(yeartotal);
//System.out.println("Percent returned first year: "+ (double)yearone);
//System.out.println("Percent returned second year: "+ (double)yeartwo);
//System.out.println("Percent returned third year: "+ (double)yearthree);
//System.out.println("Percent returned fourth year: "+ (double)yearfour);
//System.out.println("Percent returned five years or more: "+ yearfiveplus);
           number = 100;
       dataset = new HistogramDataset();
       dataset.setType(HistogramType.RELATIVE_FREQUENCY);
       dataset.addSeries("Histogram",v,number);
       plotTitle = "Days, All Areas & Years"; 
       xaxis = "Days";
       yaxis = "Frequency"; 
       orientation = PlotOrientation.VERTICAL; 
       show = false; 
       toolTips = false;
       urls = false; 
       chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
                dataset, orientation, show, toolTips, urls);
       chartwidth = 500;
       chartheight = 300; 
        
        ChartUtilities.saveChartAsPNG(new File("Monthhistogramall.PNG"), chart, chartwidth, chartheight);
        
    
                 value = speedlis.toArray();

v  = new double[value.length];
for(i = 0; i < value.length; i++ ){

    v[i] = Double.parseDouble(value[i].toString());

}
           number = 50;
       dataset = new HistogramDataset();
       dataset.setType(HistogramType.RELATIVE_FREQUENCY);
       dataset.addSeries("Histogram",v,number);
       plotTitle = "Rate of displacement, All Areas & Years"; 
       xaxis = "km/month";
       yaxis = "Frequency"; 
    
       orientation = PlotOrientation.VERTICAL; 
       show = false; 
       toolTips = false;
       urls = false; 
       chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
                dataset, orientation, show, toolTips, urls);
       chartwidth = 500;
       chartheight = 300; 
        
        ChartUtilities.saveChartAsPNG(new File("Velocityhistogramall.PNG"), chart, chartwidth, chartheight);
        
        
BufferedImage bigmap2 = new BufferedImage(imageSize.width+900, imageSize.height+200, BufferedImage.TYPE_INT_RGB);
                    
    Rectangle imageSize2 = new Rectangle(width+900,height+200);
                        
                         Graphics2D gr2 = bigmap2.createGraphics();
                        gr2.setPaint(Color.WHITE);
                           gr2.fill(imageSize2);
                               
                            gr2.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, 
        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
                           
  gr2.setRenderingHint(
        RenderingHints.KEY_RENDERING, 
        RenderingHints.VALUE_RENDER_QUALITY);

                           
                           
                           gr2.setColor(Color.black);
                           gr2.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 72));
                       
                        int x = 1000;
                       int y = 75;
                       String title = "All Captures for ".concat(forarea);
                       int labsiz = title.length()*72; 
                       int startpos = (int)3300/2;
                       startpos = startpos - (int)labsiz/4;
                       
                       
                       
                       
                       
                       gr2.drawString(title, startpos,y );
                      
                     
                   String deg = "\u00B0";
                        
                           gr2.setFont(new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 50));
                           gr2.drawImage(bigmap, 100, 100, null);    
                                gr2.setStroke(new java.awt.BasicStroke(6.0f));
                         gr2.setColor(Color.black);
                       
                        
                         
                         
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -64.0, width);
                         if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("64".concat(deg), x+80, 2665);
                     }
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -62.0, width);
                         if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("62".concat(deg), x+80, 2665);
                     }
                         x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -66.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("66".concat(deg), x+80, 2665);
                      } 
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -62.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("62".concat(deg), x+80, 2665);
                      }
                         
                         x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -58.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("58".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -59.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("59".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -57.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("57".concat(deg), x+80, 2665);
                      }  
                      x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -63.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("63".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -65.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("65".concat(deg), x+80, 2665);
                      }  
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -60.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("60".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -61.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("61".concat(deg), x+80, 2665);
                      }  
                           y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 43.0, height);
                      if(y>=0 && y<=height){
                           gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("43".concat(deg), 5, y+110);
                      }
                       y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 44.0, height);
                      if(y>=0 && y<=height){
                           gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("44".concat(deg), 5, y+110);
                      }
                       y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 46.0, height);
                      if(y>=0 && y<=height){
                           gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("46".concat(deg), 5, y+110);
                      }
                          y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 45.0, height);
                             if(y>=0 && y<=height){
                          gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("45".concat(deg), 5, y+110);
                             }
                          y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 47.0, height);
                             if(y>=0 && y<height){
                          gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("47".concat(deg), 5, y+110);
                             }


    
                       gr2.drawRect(100, 100, width, height);
                   gr2.setColor( new Color(200, 245, 245));
                     
                       gr2.fill3DRect(3110, 2500, 50, 50, true);
                       gr2.setColor(new Color(90, 175, 175));
                       gr2.fill3DRect(3110, 2550, 50, 50, true);
                      gr2.setColor(Color.black);
                       gr2.setStroke(new java.awt.BasicStroke(3.0f));
                       gr2.drawRect(3110, 2500, 50, 100);
                       gr2.setFont(new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 45));
                      gr2.drawString("100 m", 3170, 2540);
                       gr2.drawString("200 m", 3170, 2590);
                 
                       

                       int ove = width+ 100 + 10;
                       int dow = height + 100 - 580;
                        gr2.setColor(Color.white);
                       gr2.fillRect(ove, dow, 705, 350);
                        gr2.setColor(Color.black);
                       gr2.drawRect(ove, dow, 705, 350);
                       gr2.setColor(Color.red);
                       gr2.setStroke(new BasicStroke(5));
                       
                       gr2.drawLine(ove + 20, dow + 50, ove + 120, dow + 50);
                      gr2.setColor(Color.black);
                       gr2.drawString("Same year as tagged", ove + 150 , dow + 60);
                          gr2.setColor(Color.blue);
                       gr2.drawLine(ove + 20, dow + 120, ove + 120, dow + 120);
                      gr2.setColor(Color.black);
                       gr2.drawString("1 year after tagged", ove + 150, dow + 130);
                          
                       aColor = new Color( Integer.parseInt( "8E390E",16) );
                         gr2.setColor(aColor);
                   
                          
                       gr2.drawLine(ove + 20, dow + 200, ove + 120, dow + 200);
                        gr2.setColor(Color.black);
                       gr2.drawString("2 years after tagged", ove + 150, dow+210);
                       aColor = new Color( Integer.parseInt( "119011",16) );
                         gr2.setColor(aColor);
                       gr2.setColor(aColor);
                       gr2.drawLine(ove + 20, dow + 270, ove + 120, dow + 270);
                       gr2.setColor(Color.black);
                       gr2.drawString(" >2 years after tagged", ove + 150, dow+280);
                  

                       
                       
                       /* 
                       gr2.setColor(Color.white);
                       gr2.fillRect(width-350, height-20, 400, 110);
                       
                       gr2.setColor(Color.red);
                       gr2.drawLine(width-340, height, width-330, height);
                       gr2.drawString("First Capture", width - 310, height+20);
                        gr2.setColor(Color.blue);
                       gr2.drawLine(width-340, height + 50, width-330, height + 50);
                       gr2.drawString("Second Capture", width - 310, height+20);
                      */

  String tofile = "maps//alltags"+forarea;
  
       File fileToSave = new File(tofile + ".jpeg");
   if (fileToSave.exists()){
       fileToSave.delete();
   }


   ImageIO.write(bigmap2, "jpeg", fileToSave);
   calc = new GeodeticCalculator();
   double relarday = 0;
String relardays = "";
   double redaystot = 0;
String relardistag = "";   
double relargestdis = 0;  
int ncap = 0;   
retotaldis = totaldis;

for(i = 0; i<poslist.size(); i++){
   
    ncap++;
int inde = 0;
    while(inde < poslist.get(i).capd.size()-1){
        ncap++;
        x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist.get(i).caplo.get(inde)), width);
        y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist.get(i).capla.get(inde)), height);
        int xx = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist.get(i).caplo.get(inde+1)), width);
        int yy = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist.get(i).capla.get(inde+1)), height);
        Color c;
        if(inde == 0) c = Color.red;
        else if(inde == 1) c = Color.blue;
        else if(inde == 2) c = Color.green;
        else if(inde == 3) c = Color.orange;
        else c = Color.BLACK;
        
        calc.setStartingGeographicPoint(Double.parseDouble(poslist.get(i).samlo), Double.parseDouble(poslist.get(i).samla));
       calc.setDestinationGeographicPoint(Double.parseDouble(poslist.get(i).caplo.get(0)), Double.parseDouble(poslist.get(i).capla.get(0)));
       double redis = calc.getOrthodromicDistance()/1000;
  
        
        
       calc.setStartingGeographicPoint(Double.parseDouble(poslist.get(i).caplo.get(inde)), Double.parseDouble(poslist.get(i).capla.get(inde)));
       calc.setDestinationGeographicPoint(Double.parseDouble(poslist.get(i).caplo.get(inde+1)), Double.parseDouble(poslist.get(i).capla.get(inde+1)));

       

         redis = redis + calc.getOrthodromicDistance()/1000;

         //redis = redis/1000;
         if(redis>=relargestdis){
             relargestdis = redis;
          
             relardistag = poslist.get(i).tag + " between dates " + poslist.get(i).sampd + " and " + poslist.get(i).capd.get(poslist.get(i).capd.size()-1);
         }
        retotaldis = retotaldis + calc.getOrthodromicDistance()/1000; //in km
        
        
              SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date capda  = formatter.parse(poslist.get(i).capd.get(poslist.get(i).capd.size()-1));
               java.util.Date sampda  = formatter.parse(poslist.get(i).sampd);

               Calendar scal = Calendar.getInstance();
               scal.setTime(sampda);
               Calendar ccal = Calendar.getInstance();
               ccal.setTime(capda);
     


       if(daysBetween(scal , ccal) >= relarday){
          relarday = daysBetween(scal , ccal);
          relardays = poslist.get(i).tag + " between dates " + poslist.get(i).sampd + " and " + poslist.get(i).capd.get(poslist.get(i).capd.size()-1) + " with" +
                  " a total distance traveled of "+ redis +"km" ;

      }
        
 
if(Integer.parseInt(poslist.get(i).capd.get(inde+1).split("/")[2]) == Integer.parseInt(poslist.get(i).sampd.split("/")[2]))  c = Color.red ;
else if(Integer.parseInt(poslist.get(i).capd.get(inde+1).split("/")[2]) == Integer.parseInt(poslist.get(i).sampd.split("/")[2])+1) c = Color.blue;
else if(Integer.parseInt(poslist.get(i).capd.get(inde+1).split("/")[2]) == Integer.parseInt(poslist.get(i).sampd.split("/")[2])+2)  c =  new Color( Integer.parseInt( "8E390E",16) );
else c = new Color( Integer.parseInt( "119011",16) );


        
        
        gr.setColor(c);
        if(x == xx && y == yy){
           gr.drawLine(x-1, y-1, x+1, y+1);
            gr.drawLine(x-1, y+1, x+1, y-1);
       }
       else gr = drawsmallArrow(gr, x, y, xx, yy, c, new BasicStroke(5));

        
        inde++;
    }
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date capda  = formatter.parse(poslist.get(i).capd.get(poslist.get(i).capd.size()-1));
               java.util.Date sampda  = formatter.parse(poslist.get(i).sampd);

               Calendar scal = Calendar.getInstance();
               scal.setTime(sampda);
               Calendar ccal = Calendar.getInstance();
               ccal.setTime(capda);
               
       redaystot = redaystot + daysBetween(scal , ccal);


   }
for(i = 0; i<poslist2.size(); i++){
   
    
int inde2 = 0;
    while(inde2 < poslist2.get(i).capd.size()-1){
        
        x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist2.get(i).caplo.get(inde2)), width);
        y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist2.get(i).capla.get(inde2)), height);
        int xx = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist2.get(i).caplo.get(inde2+1)), width);
        int yy = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist2.get(i).capla.get(inde2+1)), height);
        Color c;
        if(inde2 == 0) c = Color.red;
        else if(inde2 == 1) c = Color.blue;
        else if(inde2 == 2) c = Color.green;
        else if(inde2 == 3) c = Color.orange;
        else c = Color.BLACK;
        
        
if(Integer.parseInt(poslist2.get(i).year.get(inde2+1)) == Integer.parseInt(poslist2.get(i).sampd.split("/")[2]))  c = Color.red ;
else if(Integer.parseInt(poslist2.get(i).year.get(inde2+1)) == Integer.parseInt(poslist2.get(i).sampd.split("/")[2])+1) c = Color.blue;
else if(Integer.parseInt(poslist2.get(i).year.get(inde2+1)) == Integer.parseInt(poslist2.get(i).sampd.split("/")[2])+2)  c =  new Color( Integer.parseInt( "8E390E",16) );
else c = new Color( Integer.parseInt( "119011",16) );


        
        gr.setColor(c);
        if(x == xx && y == yy ){
           gr.drawLine(x-1, y-1, x+1, y+1);
            gr.drawLine(x-1, y+1, x+1, y-1);
       }
       else gr = drawsmallArrow(gr, x, y, xx, yy, c, new BasicStroke(5));

        
        inde2++;
    }
    


   }

bigmap2 = new BufferedImage(imageSize.width+900, imageSize.height+200, BufferedImage.TYPE_INT_RGB);
                    
   imageSize2 = new Rectangle(width+900,height+200);
                        
                         gr2 = bigmap2.createGraphics();
                        gr2.setPaint(Color.WHITE);
                           gr2.fill(imageSize2);
                               
                            gr2.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, 
        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
                           
  gr2.setRenderingHint(
        RenderingHints.KEY_RENDERING, 
        RenderingHints.VALUE_RENDER_QUALITY);
 
                           
                           
                           gr2.setColor(Color.black);
                           gr2.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 72));
                       
                        x = 1000;
                       y = 75;
                       title = "All Recaptures for ".concat(forarea);
                       labsiz = title.length()*72; 
                       startpos = (int)3300/2;
                       startpos = startpos - (int)labsiz/4;
                       
                       gr2.drawString(title, startpos,y );
                      
                     
                   deg = "\u00B0";
                        
                           gr2.setFont(new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 50));
                           gr2.drawImage(bigmap, 100, 100, null);    
                                gr2.setStroke(new java.awt.BasicStroke(6.0f));
                         gr2.setColor(Color.black);
                       
                        
                          
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -64.0, width);
                         if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("64".concat(deg), x+80, 2665);
                     }
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -62.0, width);
                         if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("62".concat(deg), x+80, 2665);
                     }
                         x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -66.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("66".concat(deg), x+80, 2665);
                      } 
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -62.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("62".concat(deg), x+80, 2665);
                      }
                         
                         x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -58.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("58".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -59.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("59".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -57.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("57".concat(deg), x+80, 2665);
                      }  
                      x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -63.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("63".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -65.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("65".concat(deg), x+80, 2665);
                      }  
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -60.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("60".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -61.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("61".concat(deg), x+80, 2665);
                      }  
                           y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 43.0, height);
                      if(y>=0 && y<=height){
                           gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("43".concat(deg), 5, y+110);
                      }
                       y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 44.0, height);
                      if(y>=0 && y<=height){
                           gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("44".concat(deg), 5, y+110);
                      }
                       y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 46.0, height);
                      if(y>=0 && y<=height){
                           gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("46".concat(deg), 5, y+110);
                      }
                          y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 45.0, height);
                             if(y>=0 && y<=height){
                          gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("45".concat(deg), 5, y+110);
                             }
                          y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 47.0, height);
                             if(y>=0 && y<height){
                          gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("47".concat(deg), 5, y+110);
                             }


    
                       gr2.drawRect(100, 100, width, height);
                        gr2.setColor( new Color(200, 245, 245));
                       
                     
                       gr2.fill3DRect(3110, 2500, 50, 50, true);
                       gr2.setColor(new Color(90, 175, 175));
                       gr2.fill3DRect(3110, 2550, 50, 50, true);
                      gr2.setColor(Color.black);
                       gr2.setStroke(new java.awt.BasicStroke(3.0f));
                       gr2.drawRect(3110, 2500, 50, 100);
                       gr2.setFont(new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 45));
                      gr2.drawString("100 m", 3170, 2540);
                       gr2.drawString("200 m", 3170, 2590);
                 
                       
                       
                       /*
                       int ove = width+100+10;
                       int dow = height+100-300; 
                       gr2.setColor(Color.white);
                       gr2.fillRect(ove, dow, 480, 180);
                           gr2.setColor(Color.black);
                       gr2.drawRect(ove, dow, 480, 180);
                       gr2.setColor(Color.red);
                       gr2.drawLine(ove + 20, dow +50, ove +40, dow + 50);
                       gr2.drawString("First Capture", ove + 50, dow+60);
                        gr2.setColor(Color.blue);
                       gr2.drawLine(ove +20, dow + 120, ove + 40, dow + 120);
                       gr2.drawString("Second Capture", ove + 50, dow + 130);
                       */
                       


                        ove = width+ 100 + 10;
                       dow = height + 100 - 580;
                        gr2.setColor(Color.white);
                       gr2.fillRect(ove, dow, 705, 350);
                        gr2.setColor(Color.black);
                       gr2.drawRect(ove, dow, 705, 350);
                       gr2.setColor(Color.red);
                       gr2.setStroke(new BasicStroke(5));
                       
                       gr2.drawLine(ove + 20, dow + 50, ove + 120, dow + 50);
                      gr2.setColor(Color.black);
                       gr2.drawString("Same year as tagged", ove + 150 , dow + 60);
                          gr2.setColor(Color.blue);
                       gr2.drawLine(ove + 20, dow + 120, ove + 120, dow + 120);
                      gr2.setColor(Color.black);
                       gr2.drawString("1 year after tagged", ove + 150, dow + 130);
                          
                       aColor = new Color( Integer.parseInt( "8E390E",16) );
                         gr2.setColor(aColor);
                   
                          
                       gr2.drawLine(ove + 20, dow + 200, ove + 120, dow + 200);
                        gr2.setColor(Color.black);
                       gr2.drawString("2 years after tagged", ove + 150, dow+210);
                       aColor = new Color( Integer.parseInt( "119011",16) );
                         gr2.setColor(aColor);
                       gr2.setColor(aColor);
                       gr2.drawLine(ove + 20, dow + 270, ove + 120, dow + 270);
                       gr2.setColor(Color.black);
                       gr2.drawString(" >2 years after tagged", ove + 150, dow+280);
                  

                       
                       
                       
                         tofile = "maps//alltags&recap"+forarea;

                        fileToSave = new File(tofile + ".jpeg");
                         if (fileToSave.exists()){
                        fileToSave.delete();
                         }

                 
                        ImageIO.write(bigmap2, "jpeg", fileToSave);

      
   
  

 

    
double avedays = daystot/poslist.size();
double avedis = totaldis/poslist.size();
double kmpermon = (totaldis/daystot) * 30;

if(relargestdis < largestdis){
    relargestdis = largestdis;
   relardistag = lardistag;
}
if(relarday < larday){
    relarday = larday;
   relardays = lardays;
}

double reavedis = retotaldis/ncap;
double reavedays = redaystot/ncap;
double rekmpermon = (retotaldis/redaystot)*30;
     statstext.append("Number of total eligible captures: "+ncap+"\n");
     statstext.append("THE AVERAGE DISTANCE TRAVELED \n");
     statstext.append("Between tag site and first capture: "+ avedis + "km\n");
     statstext.append("Between tag site, first capture and all recaptures: "+ reavedis + "km\n");
     
     statstext.append("THE LARGEST DISTANCE TRAVELED \n");
     statstext.append("Between tag site and first capture: "+ largestdis + "km," +
             " this was from crab with tag number " + lardistag+"\n");
     statstext.append("Between tag site, firstcapture and all recaptures: "+ relargestdis + "km," +
             " this was from crab with tag number " + relardistag+"\n");

      statstext.append("THE AVERAGE NUMBER OF DAYS \n");
      statstext.append("Between tag site and first capture: "+ avedays + "days \n");
      statstext.append("Between tag site and last known capture:"+ reavedays + "days \n");
      
      statstext.append("THE LONGEST TIME INTERVAL \n");
      statstext.append("Between tag site and first capture: "+ larday + "days," +
             " this was from crab with tag number " + lardays+"\n");
      statstext.append("Between tag site and last known capture: "+ relarday + "days," +
             " this was from crab with tag number " + relardays+"\n");
      
      statstext.append("THE AVERAGE SPEED \n");
      statstext.append("Between tag site and first capture is approximately: "+ kmpermon + "km/month\n ");
      statstext.append("Between tag site, first capture and all recaptures is approximately: "+ rekmpermon + "km/month\n ");
      
      statstext.append("Map of all tags generated can be found in maps folder called alltags.jpeg \n");
      statstext.append("Map of all recaptured tags generated can be found in maps folder called alltags&recap.jpeg \n");

      //gr2.dispose();
        gr.dispose();
       //disttot = disttot* 0.000539956803;


            
}//end if foryears = all

else{
int numcaps = 0;
  //  double totspeed = 0;
  long daystot = 0;
  double totaldis = 0;
  double largestdis = 0;
  String lardistag = "";
  String lardays = "";
  long larday = 0;
 GeodeticCalculator calc = new GeodeticCalculator();

String elig = Integer.toString(poslist.size());
        for(int i = 0; i<poslist.size(); i++){
            numcaps++;

        int x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist.get(i).samlo), width);
        int y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist.get(i).samla), height);
        int xx = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist.get(i).caplo.get(0)), width);
        int yy = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist.get(i).capla.get(0)), height);

        calc.setStartingGeographicPoint(Double.parseDouble(poslist.get(i).samlo), Double.parseDouble(poslist.get(i).samla));
       calc.setDestinationGeographicPoint(Double.parseDouble(poslist.get(i).caplo.get(0)), Double.parseDouble(poslist.get(i).capla.get(0)));


        double dist = calc.getOrthodromicDistance(); //in meters
       

java.awt.Stroke s  = new BasicStroke(5);
Color c = new Color(0,0,0);


if(Integer.parseInt(poslist.get(i).capd.get(0).split("/")[2]) == Integer.parseInt(poslist.get(i).sampd.split("/")[2]))  c = Color.red ;
else if(Integer.parseInt(poslist.get(i).capd.get(0).split("/")[2]) == Integer.parseInt(poslist.get(i).sampd.split("/")[2])+1) c = Color.blue;
else if(Integer.parseInt(poslist.get(i).capd.get(0).split("/")[2]) == Integer.parseInt(poslist.get(i).sampd.split("/")[2])+2)  c =  new Color( Integer.parseInt( "660066",16) );
else c = new Color( Integer.parseInt( "119011",16) );


if(!tagFrame.onlyplotrecaps.isSelected()) {
if(x == xx && y == yy){
           gr.drawLine(x-1, y-1, x+1, y+1);
            gr.drawLine(x-1, y+1, x+1, y-1);
       }
       else gr = drawsmallArrow(gr, x, y, xx, yy, c, s);

}
if(tagFrame.onlyplotrecaps.isSelected() && poslist.get(i).capla.size()>1){
    if(x == xx && y == yy){
           gr.drawLine(x-1, y-1, x+1, y+1);
            gr.drawLine(x-1, y+1, x+1, y-1);
       }
       else gr = drawsmallArrow(gr, x, y, xx, yy, c, s);

    
}
 int times = 0;

        while(times < poslist.get(i).capd.size()-1){
            numcaps++;
         x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist.get(i).caplo.get(times)), width);
        y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist.get(i).capla.get(times)), height);
        xx = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist.get(i).caplo.get(times+1)), width);
        yy = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist.get(i).capla.get(times+1)), height);

        
         calc.setStartingGeographicPoint(Double.parseDouble(poslist.get(i).caplo.get(times)), Double.parseDouble(poslist.get(i).capla.get(times)));
       calc.setDestinationGeographicPoint(Double.parseDouble(poslist.get(i).caplo.get(times+1)), Double.parseDouble(poslist.get(i).capla.get(times+1)));



         dist = dist + calc.getOrthodromicDistance(); //in meters
      
c = new Color(0,0,0);
c = Color.red;
 //if(times == 0) c = Color.blue;
 //if(times == 1) c = Color.green;

s  = new BasicStroke(5);

//if(Integer.parseInt(poslist.get(i).sampd.split("/")[2]) == Integer.parseInt(foryear))  s = new BasicStroke(5);
//else if(Integer.parseInt(poslist.get(i).sampd.split("/")[2]) == Integer.parseInt(foryear)-1)  s = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{30, 8}, 15);
//else s = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{30, 8, 5, 8}, 15);

if(Integer.parseInt(poslist.get(i).capd.get(times+1).split("/")[2]) == Integer.parseInt(poslist.get(i).sampd.split("/")[2]))  c = Color.red ;
else if(Integer.parseInt(poslist.get(i).capd.get(times+1).split("/")[2]) == Integer.parseInt(poslist.get(i).sampd.split("/")[2])+1) c = Color.blue;
else if(Integer.parseInt(poslist.get(i).capd.get(times+1).split("/")[2]) == Integer.parseInt(poslist.get(i).sampd.split("/")[2])+2)  c =  new Color( Integer.parseInt( "660066",16) );
else c = new Color( Integer.parseInt( "119011",16) );

   
        if(x == xx && y == yy){
           gr.drawLine(x-1, y-1, x+1, y+1);
            gr.drawLine(x-1, y+1, x+1, y-1);
       }
       else gr = drawsmallArrow(gr, x, y, xx, yy, c, s);

            times++;
        }
 dist = dist/1000; //in km
 totaldis = totaldis + dist;
if(dist > largestdis){
            largestdis = dist;
            lardistag = poslist.get(i).tag + " between dates " + poslist.get(i).sampd + " and " + poslist.get(i).capd.get(poslist.get(i).capd.size()-1);
        }

 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


java.util.Date sampda2  = formatter.parse(poslist.get(i).sampd);
String temp = poslist.get(i).capd.get(poslist.get(i).capd.size()-1);

java.util.Date capda2  = formatter.parse(temp);

               Calendar scal = Calendar.getInstance();
               scal.setTime(sampda2);
               Calendar ccal = Calendar.getInstance();
               ccal.setTime(capda2);



       daystot = daystot + daysBetween(scal , ccal);
      if(daysBetween(scal , ccal) > larday){
          larday = daysBetween(scal , ccal);
          lardays = poslist.get(i).tag + " between dates " + poslist.get(i).sampd + " and " + poslist.get(i).capd.get(poslist.get(i).capd.size()-1) + " with" +
                  " a total distance traveled of "+ dist + "km" ;

      }




 }
                for(int i = 0; i<poslist2.size(); i++){
            

        int x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist2.get(i).samlo), width);
        int y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist2.get(i).samla), height);
        int xx = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist2.get(i).caplo.get(0)), width);
        int yy = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist2.get(i).capla.get(0)), height);


java.awt.Stroke s  = new BasicStroke(5);

//if(Integer.parseInt(poslist.get(i).sampd.split("/")[2]) == Integer.parseInt(foryear))  s = new BasicStroke(5);
//else if(Integer.parseInt(poslist.get(i).sampd.split("/")[2]) == Integer.parseInt(foryear)-1)  s = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{30, 8}, 15);
//else s = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{30, 8, 5, 8}, 15);

Color c = new Color(0,0,0);


if(Integer.parseInt(poslist2.get(i).year.get(0)) == Integer.parseInt(poslist2.get(i).sampd.split("/")[2]))  c = Color.red ;
else if(Integer.parseInt(poslist2.get(i).year.get(0)) == Integer.parseInt(poslist2.get(i).sampd.split("/")[2])+1) c = Color.blue;
else if(Integer.parseInt(poslist2.get(i).year.get(0)) == Integer.parseInt(poslist2.get(i).sampd.split("/")[2])+2)  c =  new Color( Integer.parseInt( "8E390E",16) );
else c = new Color( Integer.parseInt( "119011",16) );



if(!tagFrame.onlyplotrecaps.isSelected()) {
if(x == xx && y == yy){
           gr.drawLine(x-1, y-1, x+1, y+1);
            gr.drawLine(x-1, y+1, x+1, y-1);
       }
       else gr = drawsmallArrow(gr, x, y, xx, yy, c, s);

}


if(tagFrame.onlyplotrecaps.isSelected() && poslist2.get(i).capla.size()>1){
    if(x == xx && y == yy){
           gr.drawLine(x-1, y-1, x+1, y+1);
            gr.drawLine(x-1, y+1, x+1, y-1);
       }
       else gr = drawsmallArrow(gr, x, y, xx, yy, Color.red, s);
    
    
}
 int times = 0;

        while(times < poslist2.get(i).capla.size()-1){
            numcaps++;
         x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist2.get(i).caplo.get(times)), width);
        y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist2.get(i).capla.get(times)), height);
        xx = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), Double.parseDouble(poslist2.get(i).caplo.get(times+1)), width);
        yy = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), Double.parseDouble(poslist2.get(i).capla.get(times+1)), height);

   
 c = new Color(0,0,0);
c = Color.red;
 if(times == 0) c = Color.blue;
 if(times == 1) c = Color.green;

s  = new BasicStroke(5);

//if(Integer.parseInt(poslist.get(i).sampd.split("/")[2]) == Integer.parseInt(foryear))  s = new BasicStroke(5);
//else if(Integer.parseInt(poslist.get(i).sampd.split("/")[2]) == Integer.parseInt(foryear)-1)  s = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{30, 8}, 15);
//else s = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{30, 8, 5, 8}, 15);

if(Integer.parseInt(poslist2.get(i).year.get(times+1)) == Integer.parseInt(poslist2.get(i).sampd.split("/")[2]))  c = Color.red ;
else if(Integer.parseInt(poslist2.get(i).year.get(times+1)) == Integer.parseInt(poslist2.get(i).sampd.split("/")[2])+1) c = Color.blue;
else if(Integer.parseInt(poslist2.get(i).year.get(times+1)) == Integer.parseInt(poslist2.get(i).sampd.split("/")[2])+2)  c =  new Color( Integer.parseInt( "8E390E",16) );
else c = new Color( Integer.parseInt( "119011",16) );


   
        if(x == xx && y == yy){
           gr.drawLine(x-1, y-1, x+1, y+1);
            gr.drawLine(x-1, y+1, x+1, y-1);
       }
       else gr = drawsmallArrow(gr, x, y, xx, yy, c, s);

            times++;
        }



 }
        
        
        
        
        
int numc = 0;
double onedistot = 0;
double onelardis = 0;
String onelardist = "";
long onedaystot = 0;
double onelarday = 0;
String onelardays = "";
calc = new GeodeticCalculator();
for(int i = 0; i<poslist.size(); i++){
 
    int ind = 0;
    double onedis = 0;
    String lastday = "";
    String curday = "";
    boolean once = false;

    while(ind < poslist.get(i).capd.size()){
 
        if(foryear.equals(poslist.get(i).year.get(ind))){
            //from sample
            if(ind == 0){
                 calc.setStartingGeographicPoint(Double.parseDouble(poslist.get(i).samlo), Double.parseDouble(poslist.get(i).samla));
                 calc.setDestinationGeographicPoint(Double.parseDouble(poslist.get(i).caplo.get(ind)), Double.parseDouble(poslist.get(i).capla.get(ind)));
                 onedistot = onedistot + calc.getOrthodromicDistance()/1000; //in km
                 numc++;
                 onedis = onedis + calc.getOrthodromicDistance()/1000;
                 distlis.add(calc.getOrthodromicDistance()/1000);
                 curday = poslist.get(i).capd.get(ind);
                 if(!once){
                     lastday = poslist.get(i).sampd;
                     once = true;
                 }
            }
            //from prev entry
            else{
                 calc.setStartingGeographicPoint(Double.parseDouble(poslist.get(i).caplo.get(ind-1)), Double.parseDouble(poslist.get(i).capla.get(ind-1)));
                 calc.setDestinationGeographicPoint(Double.parseDouble(poslist.get(i).caplo.get(ind)), Double.parseDouble(poslist.get(i).capla.get(ind)));
                 onedistot = onedistot + calc.getOrthodromicDistance()/1000; //in km
                 numc++;
                  onedis = onedis + calc.getOrthodromicDistance()/1000;
                   distlis.add(calc.getOrthodromicDistance()/1000);
                  curday = poslist.get(i).capd.get(ind);
                  if(!once){
                     lastday = poslist.get(i).capd.get(ind-1);
                     once = true;
                 }

            }
        }
        ind++;
    }
    
     
                  
                  
    if(onedis > onelardis){
        onelardis = onedis;
        onelardist = poslist.get(i).tag + " between dates " + lastday + " and " + curday;
    }
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
     java.util.Date sampda2  = formatter.parse(lastday);
     java.util.Date capda2  = formatter.parse(curday);

               Calendar scal = Calendar.getInstance();
               scal.setTime(sampda2);
               Calendar ccal = Calendar.getInstance();
               ccal.setTime(capda2);



       onedaystot = onedaystot + daysBetween(scal , ccal);
    
  
       if(daysBetween(scal , ccal) > onelarday){
          onelarday = daysBetween(scal , ccal);
          onelardays = poslist.get(i).tag + " between dates " + lastday + " and " + curday + " with" +
                  " a total distance traveled of "+ onedis + "km" ;

      }

}
//double avespeed = totspeed/numcaps;
double avedays = daystot/numcaps;
double avedis = totaldis/numcaps;
double kmpermon = (totaldis/daystot) * 30;

double oneavedis = onedistot/numc;
double oneavedays = onedaystot/numc;
double onekmpermon = (onedistot/onedaystot)*30;





Object [] value = distlis.toArray();

double[] v  = new double[value.length];
for(int i = 0; i < value.length; i++ ) v[i] = Double.parseDouble(value[i].toString());
           int number = 100;
       HistogramDataset dataset = new HistogramDataset();
       dataset.setType(HistogramType.RELATIVE_FREQUENCY);
       dataset.addSeries("Histogram",v,number);
       String plotTitle = "Histogram"; 
       String xaxis = "distance km";
       String yaxis = "Frequency"; 
       PlotOrientation orientation = PlotOrientation.VERTICAL; 
       boolean show = false; 
       boolean toolTips = false;
       boolean urls = false; 
       JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
                dataset, orientation, show, toolTips, urls);
       int chartwidth = 500;
       int chartheight = 300; 
        
        ChartUtilities.saveChartAsPNG(new File("histogram.PNG"), chart, chartwidth, chartheight);
        

 statstext.append("Total number of eligible captures:" + elig + "\n");
 

     statstext.append("THE AVERAGE DISTANCE TRAVELED:\n");
     statstext.append("For the given year, all capture points, posibly future years, is: "+ avedis + "km \n");
     statstext.append("For the given year, between the capture point(s) this year and the last known year of release, is: "+ oneavedis + "km \n");
     
     statstext.append("THE LARGEST DISTANCE TRAVELLED:\n");
     statstext.append("For the given year, all capture points, posibly future years, is: "+ largestdis + "km," +
             " this was from crab with tag number " + lardistag+"\n");
     statstext.append("For the given year, between the capture point(s) this year and the last known year of release, is: "+ onelardis + "km," +
             " this was from crab with tag number " + onelardist+"\n");

      statstext.append("THE AVERAGE NUMBER OF DAYS:\n");
      statstext.append("For the given year, all capture points, posibly future years, is: "+ avedays + "days\n");
      statstext.append("For the given year, between the capture point(s) this year and the last known year of release, is: "+ oneavedays + "days\n");

      statstext.append("THE LONGEST TIME INTERVAL:\n");
      statstext.append("For the given year, all capture points, posibly future years, is: "+ larday + "days," +
             " this was from crab with tag number " + lardays+"\n");
      statstext.append("For the given year, between the capture point(s) this year and the last known year of release, is: "+ onelarday + "days," +
             " this was from crab with tag number " + onelardays+"\n");

      statstext.append("THE TOTAL AVERAGE SPEED:\n");
      statstext.append("For the given year, all capture points, posibly future years, is: "+ kmpermon + "km/month\n ");
      statstext.append("For the given year, between the capture point(s) this year and the last known year of release, is: "+ onekmpermon + "km/month\n ");
     //  statstext.append("The averaged speed between individual crab from tag site to first capture is approximately: "+ avespeed +"km/month\n");


      
      String yeardata = "Tag Returns " + foryearmult +" " + forarea;
gr.setColor(Color.black);
gr.setFont(new Font("TimesRoman", Font.PLAIN,  120));

       BufferedImage bigmap2 = new BufferedImage(imageSize.width+900, imageSize.height+200, BufferedImage.TYPE_INT_RGB);
                    
    Rectangle imageSize2 = new Rectangle(width+900,height+200);
                        
                         Graphics2D gr2 = bigmap2.createGraphics();
                        gr2.setPaint(Color.WHITE);
                           gr2.fill(imageSize2);
                               
                            gr2.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, 
        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
                           
  gr2.setRenderingHint(
        RenderingHints.KEY_RENDERING, 
        RenderingHints.VALUE_RENDER_QUALITY);
 // gr2.setRenderingHint(
   //     RenderingHints.KEY_ANTIALIASING,
     //   RenderingHints.VALUE_ANTIALIAS_ON);
                           
                           
                           gr2.setColor(Color.black);
                           gr2.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 72));
                       
                        int x = 1000;
                       int y = 75;
                       String title = "";
                       title = "All Captures for ".concat(forarea).concat(" "+foryearmult);
                       if(onlyplotrecaps.isSelected())title = "All Recaptures for ".concat(forarea).concat(" "+foryearmult);
                       int labsiz = title.length()*72; 
                       int startpos = (int)3300/2;
                       startpos = startpos - (int)labsiz/4;
                       
                       gr2.drawString(title, startpos,y );
                      
                     
                   String deg = "\u00B0";
                        
                           gr2.setFont(new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 50));
                           gr2.drawImage(bigmap, 100, 100, null);    
                                gr2.setStroke(new java.awt.BasicStroke(6.0f));
                         gr2.setColor(Color.black);
                       
                         
                          
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -64.0, width);
                         if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("64".concat(deg), x+80, 2665);
                     }
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -62.0, width);
                         if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("62".concat(deg), x+80, 2665);
                     }
                         x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -66.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("66".concat(deg), x+80, 2665);
                      } 
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -62.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("62".concat(deg), x+80, 2665);
                      }
                         
                         x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -58.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("58".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -59.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("59".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -57.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("57".concat(deg), x+80, 2665);
                      }  
                      x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -63.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("63".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -65.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("65".concat(deg), x+80, 2665);
                      }  
                          x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -60.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("60".concat(deg), x+80, 2665);
                      }  
                       x = mapFrame.transx(zoom.getMinX(), zoom.getMaxX(), -61.0, width);
                      if(x>=0 && x<=width){
                         gr2.drawLine(x+100, 2600, x+100, 2620);
                         gr2.drawString("61".concat(deg), x+80, 2665);
                      }  
                           y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 43.0, height);
                      if(y>=0 && y<=height){
                           gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("43".concat(deg), 5, y+110);
                      }
                       y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 44.0, height);
                      if(y>=0 && y<=height){
                           gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("44".concat(deg), 5, y+110);
                      }
                       y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 46.0, height);
                      if(y>=0 && y<=height){
                           gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("46".concat(deg), 5, y+110);
                      }
                          y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 45.0, height);
                             if(y>=0 && y<=height){
                          gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("45".concat(deg), 5, y+110);
                             }
                          y = mapFrame.transy(zoom.getMinY(), zoom.getMaxY(), 47.0, height);
                             if(y>=0 && y<height){
                          gr2.drawLine(100, y+100, 80, y+100);
                         gr2.drawString("47".concat(deg), 5, y+110);
                             }

    
                       gr2.drawRect(100, 100, width, height);
                        gr2.setColor( new Color(200, 245, 245));
                     
                       gr2.fill3DRect(3110, 2500, 50, 50, true);
                       gr2.setColor(new Color(90, 175, 175));
                       gr2.fill3DRect(3110, 2550, 50, 50, true);
                      gr2.setColor(Color.black);
                       gr2.setStroke(new java.awt.BasicStroke(3.0f));
                       gr2.drawRect(3110, 2500, 50, 100);
                       gr2.setFont(new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 45));
                      gr2.drawString("100 m", 3170, 2540);
                       gr2.drawString("200 m", 3170, 2590);
                 
                       
                       /*
                       int ove = width+100+10;
                       int dow = height+100-300; 
                       gr2.setColor(Color.white);
                       gr2.fillRect(ove, dow, 480, 180);
                           gr2.setColor(Color.black);
                       gr2.drawRect(ove, dow, 480, 180);
                       gr2.setColor(Color.red);
                       gr2.drawLine(ove + 20, dow +50, ove +40, dow + 50);
                       gr2.drawString("First Capture", ove + 50, dow+60);
                        gr2.setColor(Color.blue);
                       gr2.drawLine(ove +20, dow + 120, ove + 40, dow + 120);
                       gr2.drawString("Second Capture", ove + 50, dow + 130);
                       */
                       


                       int ove = width+ 100 + 10;
                       int dow = height + 100 - 580;
                        gr2.setColor(Color.white);
                       gr2.fillRect(ove, dow, 705, 350);
                        gr2.setColor(Color.black);
                       gr2.drawRect(ove, dow, 705, 350);
                       gr2.setColor(Color.red);
                       gr2.setStroke(new BasicStroke(5));
                       
                       gr2.drawLine(ove + 20, dow + 50, ove + 120, dow + 50);
                      gr2.setColor(Color.black);
                       gr2.drawString("Same year as tagged", ove + 150 , dow + 60);
                          gr2.setColor(Color.blue);
                       gr2.drawLine(ove + 20, dow + 120, ove + 120, dow + 120);
                      gr2.setColor(Color.black);
                       gr2.drawString("1 year after tagged", ove + 150, dow + 130);
                          
                       aColor = new Color( Integer.parseInt( "660066",16) );
                         gr2.setColor(aColor);
                   
                          
                       gr2.drawLine(ove + 20, dow + 200, ove + 120, dow + 200);
                        gr2.setColor(Color.black);
                       gr2.drawString("2 years after tagged", ove + 150, dow+210);
                       aColor = new Color( Integer.parseInt( "119011",16) );
                         gr2.setColor(aColor);
                       gr2.setColor(aColor);
                       gr2.drawLine(ove + 20, dow + 270, ove + 120, dow + 270);
                       gr2.setColor(Color.black);
                       gr2.drawString(" >2 years after tagged", ove + 150, dow+280);
                  


  String tofile = title;
  if(onlyplotrecaps.isSelected())tofile = tofile.concat("Recaps"); 
       File fileToSave = new File("maps//"+tofile + ".jpeg");
   if (fileToSave.exists()){
       fileToSave.delete();
   }


   ImageIO.write(bigmap2, "jpeg", fileToSave);
        
      
   
   statstext.append("Map of all movement of crabs with tags captured this year generated and can be found in maps folder called:"+ title +".jpeg \n");


}
mmap.dispose();



    }
public static LineString getAreaLS(String a){
  Coordinate c ;
      Coordinate[] labelcoords = new Coordinate[5];

      if(a.equals("nens")){
         labelcoords = new Coordinate[5];
         c = new Coordinate(46, -57.5);
          labelcoords[0] = c;
           c = new Coordinate(46, -60.75);
          labelcoords[1] = c;
           c = new Coordinate(47.03, -60.42);
          labelcoords[2] = c;
           c = new Coordinate(47.51, -60.174);
          labelcoords[3] = c;
           c = new Coordinate(46, -57.5);
          labelcoords[4] = c;
     }
      if(a.equals("23")){
           labelcoords = new Coordinate[15];
          c = new Coordinate(46, -59.850000);
          labelcoords[0] = c;
           c = new Coordinate(46, -58.416882);
          labelcoords[1] = c;
           c = new Coordinate(46, -57.798730);
          labelcoords[2] = c;
           c = new Coordinate(45.140559, -56.656889);
          labelcoords[3] = c;
           c = new Coordinate(43.33261844218, -55.1456554573);
          labelcoords[4] = c;
           c = new Coordinate(42.6033004851, -55.68125469362);
          labelcoords[5] = c;
          c = new Coordinate(42.22771953875, -56.05085314958);
          labelcoords[6] = c;
           c = new Coordinate(41.85752664363, -56.38852285113);
          labelcoords[7] = c;
           c = new Coordinate(41.39016427721, -57.0097735616);
          labelcoords[8] = c;
           c = new Coordinate(41.04951089832, -57.66101911857);
          labelcoords[9] = c;
           c = new Coordinate(43.089428, -58.815778);
          labelcoords[10] = c;
           c = new Coordinate(44.829624, -59.959007);
          labelcoords[11] = c;
           c = new Coordinate(45.616670, -60.516670);
          labelcoords[12] = c;
           c = new Coordinate(45.900000, -60.3);
          labelcoords[13] = c;
           c = new Coordinate(46.000000, -59.85);
          labelcoords[14] = c;

     }
            if(a.equals("24")){
                 labelcoords = new Coordinate[20];
          c = new Coordinate(45.616670, -60.516670);
          labelcoords[0] = c;
           c = new Coordinate(44.829624, -59.959007);
          labelcoords[1] = c;
           c = new Coordinate(43.089428,-58.815778 );
          labelcoords[2] = c;
           c = new Coordinate(41.04951089832, -57.66101911857);
          labelcoords[3] = c;
           c = new Coordinate(40.77661043992, -58.45709619645);
          labelcoords[4] = c;
           c = new Coordinate(40.56670150747, -59.73115926281);
          labelcoords[5] = c;
          c = new Coordinate( 40.55566801018, -60.37103016535);
          labelcoords[6] = c;
           c = new Coordinate( 40.63769695632, -60.99776951352);
          labelcoords[7] = c;
           c = new Coordinate( 40.64436402001, -61.34503695018);
          labelcoords[8] = c;
           c = new Coordinate( 40.92666730458, -62.57451365555);
          labelcoords[9] = c;
           c = new Coordinate( 40.74553223475, -63.03564465657);
          labelcoords[10] = c;
           c = new Coordinate(40.5,-63.333333 );
          labelcoords[11] = c;
           c = new Coordinate(42.613790, -63.333333);
          labelcoords[12] = c;
           c = new Coordinate(44.332904,-63.333384 );
          labelcoords[13] = c;
           c = new Coordinate(44.502358, -63.502420);
          labelcoords[14] = c;
           c = new Coordinate(45, -64);
          labelcoords[15] = c;
           c = new Coordinate(45.166670, -63.000000);
          labelcoords[16] = c;
           c = new Coordinate(45.391144,-61.858804 );
          labelcoords[17] = c;
           c = new Coordinate(45.641670,-61.404170 );
          labelcoords[18] = c;
           c = new Coordinate(45.616670,-60.516670 );
          labelcoords[19] = c;
  
            }
           if(a.equals("4x")){
          
              c = new Coordinate(43, -68);
          labelcoords[15] = c;
          
              c = new Coordinate(40.5,-63.333333 );
          labelcoords[11] = c;
           c = new Coordinate(42.613790, -63.333333);
          labelcoords[12] = c;
           c = new Coordinate(44.332904,-63.333384 );
          labelcoords[13] = c;
           c = new Coordinate(44.502358, -63.502420);
          labelcoords[14] = c;
           c = new Coordinate(45, -64);
          labelcoords[15] = c;
           
              c = new Coordinate(43, -68);
          labelcoords[15] = c;
          
          

     }

   GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory( null );
             LineString label = geometryFactory.createLineString(labelcoords);
             return label;
}

public static Graphics2D drawsmallArrow( Graphics2D g, int x, int y, int xx, int yy, Color a, java.awt.Stroke b )
  {
      
    //float arrowWidth = 3.0f ;
    //float theta = 0.735f ;
      float arrowWidth = 16.0f ;
    float theta = 0.8f ;
    int[] xPoints = new int[ 3 ] ;
    int[] yPoints = new int[ 3 ] ;
    float[] vecLine = new float[ 2 ] ;
    float[] vecLeft = new float[ 2 ] ;
    float fLength;
    float th;
    float ta;
    float baseX, baseY ;

    xPoints[ 0 ] = xx ;
    yPoints[ 0 ] = yy ;

    // build the line vector
    vecLine[ 0 ] = (float)xPoints[ 0 ] - x ;
    vecLine[ 1 ] = (float)yPoints[ 0 ] - y ;

    // build the arrow base vector - normal to the line
    vecLeft[ 0 ] = -vecLine[ 1 ] ;
    vecLeft[ 1 ] = vecLine[ 0 ] ;

    // setup length parameters
    fLength = (float)Math.sqrt( vecLine[0] * vecLine[0] + vecLine[1] * vecLine[1] ) ;
    th = arrowWidth / ( 1f * fLength ) ;
    ta = arrowWidth / ( 1f * ( (float)Math.tan( theta ) / 1f ) * fLength ) ;

    // find the base of the arrow
    baseX = ( (float)xPoints[ 0 ] - ta * vecLine[0]);
    baseY = ( (float)yPoints[ 0 ] - ta * vecLine[1]);

    // build the points on the sides of the arrow
    xPoints[ 1 ] = (int)( baseX + th * vecLeft[0] );
    yPoints[ 1 ] = (int)( baseY + th * vecLeft[1] );
    xPoints[ 2 ] = (int)( baseX - th * vecLeft[0] );
    yPoints[ 2 ] = (int)( baseY - th * vecLeft[1] );
    g.setColor(a);
    //g.setStroke(new BasicStroke(5));
    g.setStroke(b);
    g.drawLine( x, y, (int)baseX, (int)baseY ) ;
    g.fillPolygon( xPoints, yPoints, 3 ) ;
    
  return g;
}
public static long daysBetween(Calendar startDate, Calendar endDate) {
  Calendar date = (Calendar) startDate.clone();
  long daysBetween = 0;
  while (date.before(endDate)) {
    date.add(Calendar.DAY_OF_MONTH, 1);
    daysBetween++;
  }
  return daysBetween;
}
 private static StyleFactory sf = CommonFactoryFinder.getStyleFactory(null);
    private static FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2(null);
public static Style createGreyscaleStyle(int band) {
        ContrastEnhancement ce = sf.contrastEnhancement(ff.literal(1.0), ContrastMethod.NORMALIZE);
        SelectedChannelType sct = sf.createSelectedChannelType(String.valueOf(band), ce);

        RasterSymbolizer sym = sf.getDefaultRasterSymbolizer();
        ChannelSelection sel = sf.channelSelection(sct);
        sym.setChannelSelection(sel);

        return SLD.wrapSymbolizers(sym);
    }
}


