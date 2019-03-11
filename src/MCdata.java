/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.text.SimpleDateFormat;
import java.awt.geom.AffineTransform;
import java.util.*;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
/**
 *
 * @author Brent
 */
public class MCdata extends tagFrame  {
    public static void createfromstatement(String toda)throws SQLException{
        LinkedList taglist = new LinkedList();

    
     
          
          
             /*    Connection conn;
          ResultSet r;
            Class.forName(conname);
            String url = dataurl;

            if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, user, pass);
            }
            Statement st = conn.createStatement();
            
               */
        
          Connection conn = null;
       
        try{
              
          
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
           
            Statement st = conn.createStatement();
              ResultSet r = st.executeQuery(toda);
             //  int diff = Integer.parseInt(tagFrame.toyear.getText()) - Integer.parseInt(tagFrame.fromyear.getText());
               
             int diff = 7;
             tagFrame.MCout.append("id, cc, b, d");
                        
             for(int ii = 0; ii< diff; ii++){
                   tagFrame.MCout.append(", y"+Integer.toString(ii+1));
               }
             
             String prevyear = "";
             String prevtag = "";
              int yeardiff = 0;
              int count = diff;
         boolean cont = true;
              while(r.next()){
                  cont  = true;
                
                  String tc = r.getString("tagcode");
                  
                  if(tc.contains("."))tc = tc.split("\\.")[0];
             if(knownrel.isSelected() && Integer.parseInt(tc) > 1)cont = false;
              if(r.getString("t1.date") == null)cont = false;
             if(cont){
             taglist.add(r.getString("tag_id"));
            if(!r.getString("year").equals("unknown")){ 
           if(r.getString("tag_id").equals(prevtag)){
               yeardiff = Integer.parseInt(r.getString("year")) - Integer.parseInt(prevyear);
               for(int ii = 0; ii< yeardiff; ii++){
                   tagFrame.MCout.append(", 0");
                   count++;
               }
               if(yeardiff>0){
                   tagFrame.MCout.append(", 1");
                   count++;
               }
           }
           else{
                for(int j = count; j< diff; j++){
                   tagFrame.MCout.append(", 0");
               }
              
               tagFrame.MCout.append("\n");
               count = 0;
                tagFrame.MCout.append(r.getString("tag_id"));
                  tagFrame.MCout.append(", "+r.getString("cc"));
                tagFrame.MCout.append(", 0");
                tagFrame.MCout.append(", 0");
                yeardiff = Integer.parseInt(r.getString("year")) - Integer.parseInt(r.getString("t2.date").split("-")[0]);
                for(int ii = 0; ii< yeardiff; ii++){
                   tagFrame.MCout.append(", 0");
                   count ++;
               }
               tagFrame.MCout.append(", 1");
               count++;
           }
          prevtag = r.getString("tag_id");   
          prevyear = r.getString("year");
         
         } 
         }
         }
             // conn.close();
r.close(); 
st.close();
       for(int j = count; j< diff; j++){
                   tagFrame.MCout.append(",0");
               }
           
          } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }finally{
            if(conn != null && !conn.isClosed()){
                
                conn.close();
            }
           
        }
        

       
          /*

          try {
               Connection conn;
                  ResultSet r;
            Class.forName(conname);
            String url = dataurl;

            if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, user, pass);
            }
            Statement st = conn.createStatement();
            String toda2 = "select * from (Select bio.tag_id, bio.sample_num, sample.trip, trip.trip_id  from bio, trip join sample where sample.sample_id = bio.sample_num and sample.trip = trip.trip_id and year >= "+fromyear.getText()+" and year < "+toyear.getText()+")t2";
 
            r = st.executeQuery(toda2);
           
              //     int diff = Integer.parseInt(tagFrame.toyear.getText()) - Integer.parseInt(tagFrame.fromyear.getText());
            int diff = 7;
         while(r.next()){
            if(!taglist.contains(r.getString("tag_id"))){
                tagFrame.MCout.append("\n"+r.getString("tag_id"));
                tagFrame.MCout.append(", 0, 0");
          
            
       for(int j = 0; j< diff; j++){
                   tagFrame.MCout.append(",0");
               }
         }
         }
         conn.close();
r.close();
          } catch (Exception e) {

            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
*/



          
    }
}
