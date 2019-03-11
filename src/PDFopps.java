
/*
 * TAGGING APPLICATION
 *
 *  This is an application that allows the dynamic creation of maps and letters from data stored in a database. It also
 *  allows data to be entered into these databases.
 *  It uses the following open source libraries to help with these tasks:
 *      GEOTOOLS -  Open Source Java GIS Toolkit. The GeoTools library is made available under the LGPL license found at
 *       http://www.gnu.org/licenses/lgpl-2.1.html. Geotools is used in this application to render georeferenced images
 *      ITEXT -     Open Source library available under the terms of the GNU Affero General Public License version 3
 *                  available for viewing at http://www.gnu.org/licenses/agpl.html. Aids in the creation of PDF files.
 *                  This is what is used to create the final document to be sent to those who return tags.
 *
 *
 *  
 */
/*
 * PDFopps.java is the class that handles all pdf write operations see the itext manual to learn more.
 * 
 */
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

 
//Class containing methods to generate PDFs
public class PDFopps extends tagFrame {
   
    public static void genskipPDF(){
         Vector<String> mlis = new Vector<String>(); //Stores the list of maps to be written
        String year =  datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("skipmaps/");

        File folder = new File(year);
        File[] listOfFiles = null;

        //add all files in the desired map directory to mlist
        if (folder.isDirectory()) {
            listOfFiles = folder.listFiles();
            Arrays.sort(listOfFiles);
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    mlis.add(listOfFiles[i].getName());
                }
            }

        }

    java.util.Date date = new java.util.Date();  
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");  
     
    String datetime = dateFormat.format(date);  


        Vector<String> llis = new Vector<String>();

        String year2 = datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("skipLetters/");
     
        folder = new File(year2);

        listOfFiles = null;
        //add all files in the desired letter directory to llis
        if (folder.isDirectory()) {
            listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    llis.add(listOfFiles[i].getName());
                }
            }

        } 

        Document document = new Document(PageSize.LETTER); //sets up the resulting pdf document

        try {
            String pdfout = datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("PDFs/SkipperRep/").concat(datetime.replaceAll("\\/", "_")).concat(".pdf");
            File pdff = new File(pdfout);
                  pdff.getParentFile().mkdirs(); 
            PdfWriter.getInstance(document, new FileOutputStream(pdfout));
            document.open();

            // for each letter add text and associated maps
            for (int i = 0; i < llis.size(); i++) {
                String per = llis.get(i).toString();
                per = per.replaceAll(".txt", "");
                
  
                per = per.replaceAll(" ", "");

                FileInputStream fstream = new FileInputStream(datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("SkipLetters/").concat(llis.get(i).toString()));
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;

                //for the current letter read each line of text and do the appropriate operations based on
                // what is read
                while ((strLine = br.readLine()) != null) {
                    if (strLine.equals("\f")) { //if line is a form feen character go to new page
                     
                        document.setMargins(36, 36, 36, 36);
                        document.add(Chunk.NEXTPAGE);
                    }

                    if (strLine.equals("") || strLine.equals("\\n")) { // if line is empty add a new line character
                        Paragraph format = new Paragraph("\n");
                        Font fform = new Font(Font.FontFamily.TIMES_ROMAN, 10);
                        format.setExtraParagraphSpace(2);
                        format.setFont(fform);
                        document.add(format);
                    }

                    Paragraph p = new Paragraph(strLine);
                    Font f = new Font(Font.FontFamily.TIMES_ROMAN, 10);

                    p.setExtraParagraphSpace(2);
                    p.setFont(f);
                    document.add(p); //add the current line to the pdf

                    p = new Paragraph("");
                    p.setExtraParagraphSpace(2);
                    p.setFont(f);
                    document.add(p); //add an empty character, allows proper formatting


                }//end of curret letter
                
                //Close the input stream
                in.close();
//String trip = "";
//String prevtrip = "";
int numint = 0;
int numpg = 0;
                //go through each map and add the ones that go with the lettere to the document
                for (int j = 0; j < mlis.size(); j++) {
                    
                    String permat = mlis.get(j).toString();
                //    prevtrip = trip;
                 //   trip = permat.split(" ")[2];
                
                    //parse out the name
                    permat = permat.split(" ")[0].concat(permat.split(" ")[1]);
                      permat = permat.replaceAll(" ", "");
                 
                    //If name matches write the image and remove from the mlis
                    if (per.matches(permat)) {
                        
                        com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("skipmaps/").concat( mlis.get(j).toString()));
  //                      if(!prevtrip.equals(trip)){
                        
    //                    }
                        if(mlis.get(j).toString().contains("sample")){
                           // image.scalePercent(20);//Scale the image to 10 percent to alow the map to be fitted correctly on the document
                        image.scalePercent(33, 33);
                       // document.add(Chunk.NEXTPAGE); // go to the next page
                      
                        document.add(new Chunk(image, 0, -380));//write the image
                        numpg++;
                         numint++;
                         if(numpg==2){
                            numpg = 0;
                             numint = 0;
                            document.add(Chunk.NEXTPAGE);   
                        }
                        
                       
                        if(numint==1){
                            numint = 0;
                            document.add(Chunk.NEWLINE);    
                            document.add(Chunk.NEWLINE);   document.add(Chunk.NEWLINE); 
                             document.add(Chunk.NEWLINE);   document.add(Chunk.NEWLINE); 
                             document.add(Chunk.NEWLINE);   document.add(Chunk.NEWLINE); 
                            document.add(Chunk.NEWLINE);   document.add(Chunk.NEWLINE); 
                            document.add(Chunk.NEWLINE);   document.add(Chunk.NEWLINE); 
                             document.add(Chunk.NEWLINE);   document.add(Chunk.NEWLINE); 
                        document.add(Chunk.NEWLINE);   document.add(Chunk.NEWLINE); 
                              document.add(Chunk.NEWLINE);   document.add(Chunk.NEWLINE); 
                            document.add(Chunk.NEWLINE);   document.add(Chunk.NEWLINE); 
                            document.add(Chunk.NEWLINE);   document.add(Chunk.NEWLINE); 
                             document.add(Chunk.NEWLINE);   document.add(Chunk.NEWLINE); 
                            document.add(Chunk.NEWLINE);   document.add(Chunk.NEWLINE);  document.add(Chunk.NEWLINE);
                        }
                        }
                        else{
                        image.scalePercent(50);//Scale the image to 66 percent to alow the map to be fitted correctly on the document
                       document.setMargins(10, 36, 36, 36); //set the defaulf left margin to 10 so that space is used efficiently
                        document.add(Chunk.NEXTPAGE); // go to the next page
                        document.add(image);//write the image
                        document.setMargins(50, 0, 0, 0); //set the defaulf left margin to 10 so that space is used efficiently
                         document.add(Chunk.NEXTPAGE); // go to the next page
                        }
                        }
                }
                document.setMargins(36, 36, 36, 36); // reset the margins to default so that the next page will properly write the next letter
               // document.add(Chunk.NEXTPAGE);
            }//end of for loop may loop back to next letter in llis
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        document.close();
        mlis.clear();
        llis.clear();
            tagFrame.skipmess.setText("PDF created in ecomod datadirectory bio.snowcrab/data/tagging/App/PDFs/skipreport and named as todays date."); 
    }
    
    public static void genrewardPDF(){
        
 //String ret_sheet = datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("PDFs/TagRet.pdf");

   Vector<String> mlis = new Vector<String>(); //Stores the list of maps to be written
        String year = tagFrame.yearfld.getText();
        String year2 = year;
       String forarea = "allareas";
        if(tagFrame.axxxx.isSelected())
        forarea = "4X";
        if(tagFrame.as.isSelected())
        forarea = "SENS";
        if(tagFrame.an.isSelected())
        forarea = "NENS";
           if(tagFrame.agulf.isSelected())
        forarea = "GULF";
        //if(forarea.equals("allareas"))
        //year = "maps//".concat(year);
         year = datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("maps/").concat(forarea).concat(year);
       
       
        if (tagFrame.rewardrun.isSelected()) {
            year = year.concat("unrewarded");
        }

        File folder = new File(year);
        File[] listOfFiles = null;

        //add all files in the desired map directory to mlist
        if (folder.isDirectory()) {
            listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    mlis.add(listOfFiles[i].getName());
                }
            }

        } 


        Vector<String> llis = new Vector<String>();
 
       // if(forarea.equals("allareas"))
       // year2 = "Letters//".concat(year2);
         year2 = datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("Letters/").concat(forarea).concat(year2);
       
       
        if (tagFrame.rewardrun.isSelected()) {
            year2 = year2.concat("unrewarded");
        }
       

        folder = new File(year2);

        listOfFiles = null;
        //add all files in the desired letter directory to llis
        if (folder.isDirectory()) {
            listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    llis.add(listOfFiles[i].getName());
                }
            }

        } 

        Document document = new Document(PageSize.LETTER); //sets up the resulting pdf document

        
        String ye = "";
        
       // if(!forarea.equals("allareas"))
        ye = ye.concat(forarea);
       
        ye = ye.concat(tagFrame.yearfld.getText());
       
        
        
        
        if (tagFrame.rewardrun.isSelected()) {
            ye = ye.concat("unrewarded");
        }

        try {
            File towrite = new File(datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("PDFs/").concat(ye).concat(".pdf"));
           towrite.getParentFile().mkdirs();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(towrite));
            
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            int pagec = 0;
            // for each letter add text and associated maps
            for (int i = 0; i < llis.size(); i++) {
               pagec = 0;
                String per = llis.get(i).toString();
                String pat = per;
                per = per.replaceAll(".txt", "");
                per = per.replaceAll(" ", "");

                FileInputStream fstream = new FileInputStream(datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("Letters/").concat(ye + "//").concat(pat));
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;

                //for the current letter read each line of text and do the appropriate operations based on
                // what is read
                while ((strLine = br.readLine()) != null) {
                    if (strLine.equals("\f")) { //if line is a form feen character go to new page
                     
                        document.setMargins(36, 36, 36, 36);
                        
                        document.add(Chunk.NEXTPAGE);
                      
                        document.add(Chunk.NEXTPAGE);
                  
                    }

                    if (strLine.equals("") || strLine.equals("\\n")) { // if line is empty add a new line character
                        Paragraph format = new Paragraph("\n");
                        Font fform = new Font(Font.FontFamily.TIMES_ROMAN, 10);
                        format.setExtraParagraphSpace(2);
                        format.setFont(fform);
                        document.add(format);
                    }

                    Paragraph p = new Paragraph(strLine);
                    Font f = new Font(Font.FontFamily.TIMES_ROMAN, 10);

                    p.setExtraParagraphSpace(2);
                    p.setFont(f);
                    document.add(p); //add the current line to the pdf

                    p = new Paragraph("");
                    p.setExtraParagraphSpace(2);
                    p.setFont(f);
                    document.add(p); //add an empty character, allows proper formatting
             

                }//end of curret letter
                
                //Close the input stream
                in.close();
              document.add(Chunk.NEXTPAGE);
                //go through each map and add the ones that go with the lettere to the document
                for (int j = 0; j < mlis.size(); j++) {
                    
                    String permat = mlis.get(j).toString();
                    String pa = permat;
                    //parse out the name
                    permat = permat.replaceAll(".jpeg", "");
                    permat = permat.replaceAll("\\d", "");
                    //If name matches write the image and remove from the mlis
                    if (per.matches(permat)) {
                        com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("maps/").concat(ye + "//").concat(pa));
                        image.scalePercent(66);//Scale the image to 66 percent to alow the map to be fitted correctly on the document
                            document.setMargins(10, 36, 36, 36); //set the defaulf left margin to 10 so that space is used efficiently
                        document.add(Chunk.NEXTPAGE); // go to the next page
                        document.add(image);//write the image
                        pagec++;
                    
                    }
                }
                document.setMargins(36, 36, 36, 36); // reset the margins to default so that the next page will properly write the next letter
                document.add(Chunk.NEXTPAGE);
                        

        if((pagec & 1)!= 0){
                        document.add(Chunk.NEXTPAGE);
        }
        try{
          // PdfReader reader = new PdfReader(ret_sheet);
   
       //PdfReader reader = new PdfReader(PDFopps.class.getResource("/data/TagRet.pdf"));
       PdfReader reader = new PdfReader("TagRet.pdf");
       PdfImportedPage page = writer.getImportedPage(reader, 1);
                //add the page to the destination pdf
       cb.addTemplate(page, 0, 0);
                       
                        document.add(Chunk.NEXTPAGE);
                    
       page = writer.getImportedPage(reader, 2);
                //add the page to the destination pdf
       
 String ma = Double.toString(Math.cos(90));
       float maf = Float.valueOf(ma);
 String mb = Double.toString(Math.sin(90));
       float mbf = Float.valueOf(mb);
  
       cb.addTemplate(page, 0, -1,1, 0, 0, 800 );
             } catch (IOException ioe){}
	
	
   
                        document.add(Chunk.NEXTPAGE);
                  
            }//end of for loop may loop back to next letter in llis
     

        
           } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        document.close();
        System.out.println("!!PDF saved to:  "+datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("PDFs/").concat(ye).concat(".pdf") );
        mlis.clear();
        llis.clear();
    }
}
