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
 *  Created on Dec 2, 2010, 11:00:06 AM
 */


/*
 * TagFrame.java is primarily the visual components of the tagging application. If opened in netbeans,
 * a GUI will be available to help quickly reorganize or change all visualy components of this application.
 * Some actions performed will call on other java classes/files with contain some of the more advanced
 * logical operations
 */

/*
 * tagFrame.java
 *
 * Created on Dec 2, 2010, 11:00:06 AM
 *
 * TagFrame.java is primarily the visual components of the tagging application. If opened in netbeans,
 * a GUI will be available to help quickly reorganize or change all visualy components of this application.
 * Some actions performed will call on other java classes/files with contain some of the more advanced
 * logical operations
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
//Import Statements that load the nessesary libraries into memory
import org.jfree.chart.*;
import org.jfree.data.statistics.*;
import org.jfree.chart.plot.PlotOrientation;
import java.sql.*;
import java.io.*;
import java.text.SimpleDateFormat;
import org.geotools.referencing.GeodeticCalculator;
import com.vividsolutions.jts.geom.Coordinate;
import java.sql.*;
import java.util.*;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.ImageIO;
import com.vividsolutions.jts.geom.*;
import org.geotools.geometry.jts.JTSFactoryFinder;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class tagFrame extends javax.swing.JFrame {
   
    public static int getRowsssh(String table)throws SQLException {
       int mysqlout = 0;
        int lport=3307;
        String rhost="www.enssnowcrab.com";
        String host="www.enssnowcrab.com";
        String dbuser = "enssnowc_admin";
        int rport=3306;
 
        String url = "jdbc:mysql://localhost:"+lport+"/enssnowc_Taging";
        String driverName="com.mysql.jdbc.Driver";
        Connection conn = null;
  
        try{
            //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
        
             
          
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            Statement st = conn.createStatement();

            String cou = "SELECT COUNT(*) AS rowcount FROM " + table + ";";


            ResultSet r = st.executeQuery(cou);
            r.next();
            mysqlout = r.getInt("rowcount");
            
            r.close();
            st.close();
            System.out.println("DONE");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null && !conn.isClosed()){
               
                conn.close();
            }
           
        }
        
        
        
        return(mysqlout);
    
    }
    
    public static int insertmysqlssh(String toinsert)throws SQLException {
        int mysqlout = 0;
     int lport=3307;
        String rhost="www.enssnowcrab.com";
        String host="www.enssnowcrab.com";
        String dbuser = "enssnowc_admin";
        int rport=3306;
 
        String url = "jdbc:mysql://localhost:"+lport+"/enssnowc_Taging";
        String driverName="com.mysql.jdbc.Driver";
        Connection conn = null;
       
        try{
          
             
                  
          
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            Statement st = conn.createStatement();

            mysqlout = st.executeUpdate(toinsert);

            
            
            System.out.println("DONE");
            st.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null && !conn.isClosed()){
                 conn.close();
            }
           
        }
        
        
        
        return(mysqlout);
    }
    
   
    
    
    
    /** Creates new form tagFrame */
    public tagFrame() {

        initComponents();
       // JOptionPane.showMessageDialog(rootPane, "Please make sure you are connected to the internet before using this application");

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPop = new javax.swing.JPopupMenu();
        one = new javax.swing.JMenuItem();
        two = new javax.swing.JMenuItem();
        three = new javax.swing.JMenuItem();
        four = new javax.swing.JMenuItem();
        five = new javax.swing.JMenuItem();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPop2 = new javax.swing.JPopupMenu();
        c0 = new javax.swing.JMenuItem();
        c1 = new javax.swing.JMenuItem();
        c2 = new javax.swing.JMenuItem();
        c3 = new javax.swing.JMenuItem();
        c4 = new javax.swing.JMenuItem();
        jPop3 = new javax.swing.JPopupMenu();
        e1 = new javax.swing.JMenuItem();
        e2 = new javax.swing.JMenuItem();
        e3 = new javax.swing.JMenuItem();
        e4 = new javax.swing.JMenuItem();
        jFileChooser1 = new javax.swing.JFileChooser();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        button1 = new java.awt.Button();
        MCareaGroup = new javax.swing.ButtonGroup();
        bg4 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        cappan = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        tagcap = new javax.swing.JTextField();
        daycap = new javax.swing.JTextField();
        latcap = new javax.swing.JTextField();
        longcap = new javax.swing.JTextField();
        deptcap = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        comment = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        vess = new javax.swing.JTextField();
        capt = new javax.swing.JTextField();
        rel = new javax.swing.JRadioButton();
        unk = new javax.swing.JRadioButton();
        ret = new javax.swing.JRadioButton();
        jLabel30 = new javax.swing.JLabel();
        carap = new javax.swing.JTextField();
        nens = new javax.swing.JRadioButton();
        sens = new javax.swing.JRadioButton();
        fourx = new javax.swing.JRadioButton();
        gulf = new javax.swing.JRadioButton();
        ButtonEnterCapData = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jCo = new javax.swing.JComboBox();
        addcap = new javax.swing.JTextField();
        town = new javax.swing.JTextField();
        post = new javax.swing.JTextField();
        phoa = new javax.swing.JTextField();
        phob = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        prov = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        post1 = new javax.swing.JTextField();
        town1 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        addcap1 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        email1 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        phoa1 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        phob1 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        prov1 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jCo1 = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        ButtonRewardsMapFetch = new javax.swing.JButton();
        ButtonRewardsMapPreview = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        piclist = new javax.swing.JList();
        RewardsPDF = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        yearfld = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        layout = new javax.swing.JTextArea();
        ButtonRewardsGen = new javax.swing.JButton();
        jProgress = new javax.swing.JProgressBar();
        jPanel9 = new javax.swing.JPanel();
        ButtonRewardsTextPreview = new javax.swing.JButton();
        ButtonRewardsTextFetch = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        textlist = new javax.swing.JList();
        rewardrun = new javax.swing.JCheckBox();
        RewardsMarkAsRewarded = new javax.swing.JButton();
        jLabel68 = new javax.swing.JLabel();
        an = new javax.swing.JRadioButton();
        as = new javax.swing.JRadioButton();
        aall = new javax.swing.JRadioButton();
        axxxx = new javax.swing.JRadioButton();
        agulf = new javax.swing.JRadioButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        layout1 = new javax.swing.JTextArea();
        ButtonGenSkip = new javax.swing.JButton();
        ButtonSkipPDF = new javax.swing.JButton();
        skipmess = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        ButtonMarkCapCreate = new javax.swing.JButton();
        nens2 = new javax.swing.JRadioButton();
        sens2 = new javax.swing.JRadioButton();
        xxxx2 = new javax.swing.JRadioButton();
        allareas2 = new javax.swing.JRadioButton();
        fromyear = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        toyear = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        MCout = new javax.swing.JTextArea();
        knownrel = new javax.swing.JCheckBox();
        gulfarea = new javax.swing.JRadioButton();
        ensarea = new javax.swing.JRadioButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        statstext = new javax.swing.JTextArea();
        ButtonGenStats = new javax.swing.JButton();
        ButtonStatsGenSamp = new javax.swing.JButton();
        north = new javax.swing.JRadioButton();
        south = new javax.swing.JRadioButton();
        xxxx = new javax.swing.JRadioButton();
        allareas = new javax.swing.JRadioButton();
        yeardata2 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        allyearcheck = new javax.swing.JCheckBox();
        jLabel45 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        onlyplotrecaps = new javax.swing.JCheckBox();
        jLabel67 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        Jpann = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        latitude = new javax.swing.JTextField();
        longnitude = new javax.swing.JTextField();
        depth = new javax.swing.JTextField();
        commen = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jpan2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        daySampled = new javax.swing.JTextField();
        sampler = new javax.swing.JTextField();
        vessel = new javax.swing.JTextField();
        cfa = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        tcap = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tagBios = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        ButtonEnterTagData = new javax.swing.JButton();
        jLabel70 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        jPop.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        one.setText("1 New Soft");
        one.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oneActionPerformed(evt);
            }
        });
        jPop.add(one);

        two.setText("2 New Hard");
        two.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twoActionPerformed(evt);
            }
        });
        jPop.add(two);

        three.setText("3 Hard Clean");
        three.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threeActionPerformed(evt);
            }
        });
        jPop.add(three);

        four.setText("4 Hard Light Moss");
        four.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fourActionPerformed(evt);
            }
        });
        jPop.add(four);

        five.setText("5 Old Heavy Moss");
        five.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fiveActionPerformed(evt);
            }
        });
        jPop.add(five);

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(jList1);

        c0.setText("0 Absent");
        c0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c0ActionPerformed(evt);
            }
        });
        jPop2.add(c0);

        c1.setText("1 1-49%");
        c1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c1ActionPerformed(evt);
            }
        });
        jPop2.add(c1);

        c2.setText("2 50-74%");
        c2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c2ActionPerformed(evt);
            }
        });
        jPop2.add(c2);

        c3.setText("3 75-99%");
        c3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c3ActionPerformed(evt);
            }
        });
        jPop2.add(c3);

        c4.setText("4 100%");
        c4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c4ActionPerformed(evt);
            }
        });
        jPop2.add(c4);

        e1.setText("1 Light Orange");
        e1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e1ActionPerformed(evt);
            }
        });
        jPop3.add(e1);

        e2.setText("2 Dark Orange");
        e2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e2ActionPerformed(evt);
            }
        });
        jPop3.add(e2);

        e3.setText("3 Black");
        e3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e3ActionPerformed(evt);
            }
        });
        jPop3.add(e3);

        e4.setText("4 White(cocoon)");
        e4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e4ActionPerformed(evt);
            }
        });
        jPop3.add(e4);

        button1.setLabel("button1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Snow Crab Tagging");
        setBackground(new java.awt.Color(102, 102, 255));
        setIconImage(Toolkit.getDefaultToolkit().getImage("data/crab2.jpg"));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(204, 204, 255));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(102, 102, 255)));
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(1000, 1000));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(10, 10));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(800, 800));
        jTabbedPane1.setVerifyInputWhenFocusTarget(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""))));
        jPanel1.setMaximumSize(new java.awt.Dimension(1500, 1500));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 900));
        jPanel1.setLayout(null);

        cappan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel12.setText("Tag Num");

        jLabel14.setText("Latitude");

        jLabel15.setText("Longitude");

        jLabel16.setText("Depht(fathoms)");

        jLabel17.setText("Date Captured");

        jLabel18.setText("Capture Data");

        jLabel27.setText("Comments");

        jLabel36.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel36.setForeground(new java.awt.Color(102, 102, 102));
        jLabel36.setText("dd/mm/yyyy");

        jLabel37.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel37.setForeground(new java.awt.Color(102, 102, 102));
        jLabel37.setText("4430.98");

        jLabel38.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel38.setForeground(new java.awt.Color(102, 102, 102));
        jLabel38.setText("6020.77");

        jLabel42.setText("Captain");

        jLabel43.setText("Vessel");

        buttonGroup1.add(rel);
        rel.setText("Released");

        buttonGroup1.add(unk);
        unk.setText("Unknown");

        buttonGroup1.add(ret);
        ret.setText("Retained");

        jLabel30.setText("CC");

        buttonGroup3.add(nens);
        nens.setText("NENS");

        buttonGroup3.add(sens);
        sens.setText("SENS");

        buttonGroup3.add(fourx);
        fourx.setText("4X");

        buttonGroup3.add(gulf);
        gulf.setText("GULF");

        javax.swing.GroupLayout cappanLayout = new javax.swing.GroupLayout(cappan);
        cappan.setLayout(cappanLayout);
        cappanLayout.setHorizontalGroup(
            cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cappanLayout.createSequentialGroup()
                .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cappanLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel42)
                            .addComponent(jLabel43)
                            .addComponent(jLabel27)
                            .addComponent(jLabel16)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(carap)
                            .addComponent(deptcap)
                            .addComponent(longcap)
                            .addComponent(latcap)
                            .addComponent(daycap)
                            .addComponent(tagcap, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(capt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(vess, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(comment, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(cappanLayout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(jLabel18))
                    .addGroup(cappanLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cappanLayout.createSequentialGroup()
                                .addComponent(nens)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sens)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fourx)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gulf))
                            .addGroup(cappanLayout.createSequentialGroup()
                                .addComponent(rel)
                                .addGap(18, 18, 18)
                                .addComponent(ret)
                                .addGap(18, 18, 18)
                                .addComponent(unk)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        cappanLayout.setVerticalGroup(
            cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cappanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addGap(9, 9, 9)
                .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(tagcap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(daycap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(latcap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(longcap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deptcap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(capt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vess, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cappanLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel30))
                    .addGroup(cappanLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(carap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nens)
                    .addComponent(sens)
                    .addComponent(fourx)
                    .addComponent(gulf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(cappanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ret)
                    .addComponent(rel)
                    .addComponent(unk))
                .addContainerGap())
        );

        jPanel1.add(cappan);
        cappan.setBounds(10, 10, 350, 387);

        ButtonEnterCapData.setText("Enter Data");
        ButtonEnterCapData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEnterCapDataActionPerformed(evt);
            }
        });
        jPanel1.add(ButtonEnterCapData);
        ButtonEnterCapData.setBounds(10, 370, 120, 25);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(10, 440, 579, 96);

        jLabel28.setText("Messages");
        jPanel1.add(jLabel28);
        jLabel28.setBounds(10, 420, 56, 16);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setMaximumSize(new java.awt.Dimension(370, 2096));

        jLabel19.setText("Person Data");

        jLabel20.setText("Name");

        jLabel21.setText("Address");

        jLabel22.setText("Town");

        jLabel23.setText("Postal Code");

        jLabel24.setText("Phone 1");

        jLabel25.setText("Phone 2");

        jLabel26.setText("Email");

        jCo.getEditor().getEditorComponent().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCoKeyPressed(evt);
            }

            public void keyReleased(java.awt.event.KeyEvent evt) {
                jCoKeyReleased(evt);
            }

        });
        jCo.setBackground(new java.awt.Color(153, 204, 255));
        jCo.setEditable(true);
        jCo.setBorder(null);
        jCo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCo.setOpaque(false);
        jCo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCoActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel32.setForeground(new java.awt.Color(102, 102, 102));
        jLabel32.setText("100 Crab Rd");

        jLabel33.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel33.setForeground(new java.awt.Color(102, 102, 102));
        jLabel33.setText("Crabton ");

        jLabel34.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel34.setForeground(new java.awt.Color(102, 102, 102));
        jLabel34.setText("C1R 2Q3");

        jLabel35.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel35.setForeground(new java.awt.Color(102, 102, 102));
        jLabel35.setText("2526252");

        jLabel41.setText("Hit Enter to autofill ");

        jLabel46.setText("Province");

        jLabel47.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel47.setForeground(new java.awt.Color(153, 153, 153));
        jLabel47.setText("NS");

        jLabel48.setText("Name");

        jLabel49.setText("Optional Second Person");

        jLabel50.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel50.setForeground(new java.awt.Color(153, 153, 153));
        jLabel50.setText("NS");

        jLabel51.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel51.setForeground(new java.awt.Color(102, 102, 102));
        jLabel51.setText("100 Crab Rd");

        jLabel52.setText("Address");

        jLabel53.setText("Town");

        jLabel54.setText("Postal Code");

        jLabel55.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel55.setForeground(new java.awt.Color(102, 102, 102));
        jLabel55.setText("2526252");

        jLabel56.setText("Phone 1");

        jLabel57.setText("Hit Enter to autofill ");

        jLabel58.setText("Phone 2");

        jLabel59.setText("Province");

        jLabel60.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel60.setForeground(new java.awt.Color(102, 102, 102));
        jLabel60.setText("Crabton ");

        jLabel61.setText("Email");

        jLabel62.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel62.setForeground(new java.awt.Color(102, 102, 102));
        jLabel62.setText("C1R 2Q3");

        jCo1.getEditor().getEditorComponent().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCo1KeyPressed(evt);
            }

            public void keyReleased(java.awt.event.KeyEvent evt) {
                jCo1KeyReleased(evt);
            }

        });
        jCo1.setBackground(new java.awt.Color(153, 204, 255));
        jCo1.setEditable(true);
        jCo1.setBorder(null);
        jCo1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCo1.setOpaque(false);
        jCo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCo1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel21)
                                            .addComponent(jLabel22))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(town, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                                            .addComponent(addcap, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCo, 0, 229, Short.MAX_VALUE)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel41))
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(33, 33, 33)))
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addComponent(jLabel46))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(prov, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(email, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(phob, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(phoa, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(post, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47))
                        .addGap(62, 62, 62))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel52)
                                            .addComponent(jLabel53))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(town1, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                                            .addComponent(addcap1, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel48)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCo1, 0, 229, Short.MAX_VALUE)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel57))
                                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel54)
                                    .addComponent(jLabel56)
                                    .addComponent(jLabel58)
                                    .addComponent(jLabel61)
                                    .addComponent(jLabel59))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(prov1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                                    .addComponent(email1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                                    .addComponent(phob1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                                    .addComponent(phoa1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                                    .addComponent(post1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel50))
                                .addGap(29, 29, 29)))
                        .addGap(33, 33, 33))))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jLabel49)
                .addContainerGap(229, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jCo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(addcap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(town, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel46)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(prov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel47)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(post, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(phoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(phob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jLabel49)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(jCo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(addcap1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51))
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(town1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel59)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(prov1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel50)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(post1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(phoa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(phob1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(email1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jScrollPane9.setViewportView(jPanel4);

        jPanel1.add(jScrollPane9);
        jScrollPane9.setBounds(369, 13, 430, 350);

        jTabbedPane1.addTab("Enter Capture Data", jPanel1);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel5.setMaximumSize(new java.awt.Dimension(1500, 1500));
        jPanel5.setPreferredSize(new java.awt.Dimension(900, 900));

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setForeground(new java.awt.Color(153, 153, 153));

        ButtonRewardsMapFetch.setText("Fetch Map Preview");
        ButtonRewardsMapFetch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRewardsMapFetchActionPerformed(evt);
            }
        });

        ButtonRewardsMapPreview.setText("Preview Selected Map(s)");
        ButtonRewardsMapPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRewardsMapPreviewActionPerformed(evt);
            }
        });

        jScrollPane6.setViewportView(piclist);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonRewardsMapPreview)
                    .addComponent(ButtonRewardsMapFetch))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(ButtonRewardsMapFetch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonRewardsMapPreview)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        RewardsPDF.setText("Write Results to PDF");
        RewardsPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RewardsPDFActionPerformed(evt);
            }
        });

        jLabel40.setText("Enter year");

        layout.setColumns(20);
        layout.setRows(5);
        layout.setText("<PARAGRAPH addresslabel>\nBrent Cameron \nDFO Snow Crab Science\nPopulation Ecology Division, ESS\nPO Box 1006 Dartmouth, NS\nB2Y 4A2\n\n\n</PARAGRAPH addresslabel>\n\n<PARAGRAPH A>\nBrent Cameron Snow Crab Technician \nDepartment of Fisheries and Oceans \nBedford Institute of Oceanography \nP.O. Box 1006, Dartmouth, N.S. \nB2Y4A2 \nPh. (902) 403-8403\n</PARAGRAPH A>\n\n<PARAGRAPH mytagcapturedbutihavenoreturns>\n<name>,\nThank you for participating in the tagging program. I thought you would be \ninterested to know that a crab you had previously released was captured again\nand information sent in. This information is shown in a chart provided. Recaptures such as these will help us better track \nthe movement of crab across the Scotian shelf.  \n</PARAGRAPH mytagcapturedbutihavenoreturns>\n\n\n\n<PARAGRAPH B>\n<name>,\nThanks for returning the snow crab <tag/tags> caught last season. I have included a token \nof our appreciation for your efforts in returning this information. I have also \nincluded a chart showing the release and recapture positions for the <tag/tags>.\nThe information provided by such tagging recaptures is helpful in determining the movement \nof snow crab throughout the Scotian Shelf. \n</PARAGRAPH B>\n\n<PARAGRAPH info>\nThe tagged crab you caught <was/were/wereall> tagged in the <yeartagged/yearstagged/season/seasons>.\n</PARAGRAPH info>\n\n<PARAGRAPH capturedbefore>\n<onebefore/somebefore> of the tagged crab you caught <wasb/wereb> captured before and released. Helpful\nknowledge is gained from captures such as these, especially if they are once again returned to the water. \nThe data for this is shown in the charts provided.\n</PARAGRAPH capturedbefore>\n\n<PARAGRAPH capturedafter>\n<oneafter/someafter> of the tagged crab you caught and released in the past <wasa/werea> captured this season. Thank you\nfor releasing <this/these> crab. This knowledge is very helpful in tracking the movements of crab.\nThe data for this is shown in the charts provided.\n</PARAGRAPH capturedafter>\n\n<PARAGRAPH capturedbeforeandafter>\n<onebefore/somebefore> of the tagged crab you caught <wasb/wereb> captured before and released. \nAs well, <oneafter/someafter> of the tagged crab you caught and released in the past <wasa/werea> captured this season. Helpful \nknowledge is gained from captures such as these, especially if they are once again returned to the water. \nThis knowledge is very helpful in tracking the movements of crab. The data for this is shown in the charts provided.\n</PARAGRAPH capturedbeforeandafter>\n\n<PARAGRAPH notreleased>\nIn the future, please release all tagged crab back to the water alive after \nrecording the tag number. This tag number can then be returned to DFO Science with the \nrelevant information such as date and location of capture. It will be treated in the same \nmanner as when we receive the actual tag. We hope that additional knowledge will be gained \nby tracking subsequent recaptures of individual crab over time. \n</PARAGRAPH notreleased>\n\n<PARAGRAPH released>\nThank you for releasing all tagged crab back to the water alive after recording the tag \nnumber and relevant information such as date and location of capture. The data you submitted\nwill be treated in the same manner as when we receive the actual tag. We hope that additional\nknowledge will be gained by tracking subsequent recaptures of individual crab over time. \n</PARAGRAPH released>\n\n<PARAGRAPH mixedrelret>\nThank you for releasing some of the tagged crab back to the water. The relevant data that you \nsubmit from these releases will be treated in the same manner as when we receive the actual tag. \nWe hope that additional knowledge will be gained by tracking subsequent recaptures of individual \ncrab over time. In the future, please release all tagged crab back to the water.  \n</PARAGRAPH mixedrelret>\n\n<PARAGRAPH unknownrel>\nIt was unclear whether you released or retained the tagged crab.\nIn the future please include this data along with the other relevant data. Our hope is that all \ntagged crab will be released back to the water so that additional knowledge will be gained by \ntracking subsequent recaptures of individual crab over time. \n</PARAGRAPH unknownrel>\n\n\n<PARAGRAPH final>\nI have included a one page information sheet on our tagging program. On the reverse side of \nthis sheet is a form which can easily be used to record all required information on any \ntagged crab you may catch in the future. This entire form can be mailed to DFO Science at \nthe end of the snow crab season. \n</PARAGRAPH final>\n\n<PARAGRAPH end>\nThanks for your help.\n\n\nBrent Cameron\n</PARAGRAPH end>");
        jScrollPane5.setViewportView(layout);

        ButtonRewardsGen.setText("Generate Maps & Text");
        ButtonRewardsGen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRewardsGenActionPerformed(evt);
            }
        });

        jProgress.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel9.setForeground(new java.awt.Color(153, 153, 153));

        ButtonRewardsTextPreview.setText("Preview Selected Text(s)");
        ButtonRewardsTextPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRewardsTextPreviewActionPerformed(evt);
            }
        });

        ButtonRewardsTextFetch.setText("Fetch Text Preview");
        ButtonRewardsTextFetch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRewardsTextFetchActionPerformed(evt);
            }
        });

        jScrollPane7.setViewportView(textlist);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(ButtonRewardsTextFetch, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                        .addGap(148, 148, 148))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(ButtonRewardsTextPreview)
                        .addContainerGap(117, Short.MAX_VALUE))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(ButtonRewardsTextFetch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonRewardsTextPreview)
                .addContainerGap())
        );

        rewardrun.setText("Only Unrewarded");

        RewardsMarkAsRewarded.setText("Mark as Rewarded");
        RewardsMarkAsRewarded.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RewardsMarkAsRewardedActionPerformed(evt);
            }
        });

        jLabel68.setText("* 4X YEAR ENTRIES MAY BE DIFFERENT FROM THE CAPTURE DATE SO THAT THEY CAN BE GROUPED IN A SINGLE REPORT");

        bg4.add(an);
        an.setText("NENS");

        bg4.add(as);
        as.setText("SENS");

        bg4.add(aall);
        aall.setText("All Areas");
        aall.setSelected(true);

        bg4.add(axxxx);
        axxxx.setText("4X");

        bg4.add(agulf);
        agulf.setText("GULF");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel68)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel9, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel7, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(rewardrun)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel40)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(yearfld, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(aall)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(axxxx)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(as)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(an))))
                                .addComponent(ButtonRewardsGen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(RewardsMarkAsRewarded, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                            .addComponent(RewardsPDF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(agulf))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)))))
                .addGap(117, 117, 117))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel68)
                .addGap(6, 6, 6)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(yearfld, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(aall))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rewardrun)
                    .addComponent(an)
                    .addComponent(as)
                    .addComponent(axxxx)
                    .addComponent(agulf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(ButtonRewardsGen, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(RewardsPDF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RewardsMarkAsRewarded))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE))
                .addGap(282, 282, 282))
        );

        jTabbedPane1.addTab("Rewards", jPanel5);

        layout1.setColumns(20);
        layout1.setRows(5);
        layout1.setText("<PARAGRAPH addresslabel>\nBen Zisserson \nDFO Snow Crab Science\nPopulation Ecology Division, ESS\nPO Box 1006 Dartmouth, NS\nB2Y 4A2\n\n\n</PARAGRAPH addresslabel>\n\n<PARAGRAPH A>\nBen Zisserson Snow Crab Technician \nDepartment of Fisheries and Oceans \nBedford Institute of Oceanography \nP.O. Box 1006, Dartmouth, N.S. \nB2Y4A2 \nPh. (902) 426-9325\n</PARAGRAPH A>\n\n<PARAGRAPH B>\nGreetings <name>,\nWe want to express our gratitude for your assistance with \nour tagging program. We are sending you a report showing \nwhere the crab tagged on your vessel have been recaptured \nafter their release. There is one large chart for each day \nof tagging followed by smaller charts providing additional information on the movement of these tagged crab. \n\n</PARAGRAPH B>\n\n<PARAGRAPH final>\nWe have also included a one page \ninformation sheet on our tagging program. \nOn the reverse side of this sheet is a form \nwhich can easily be used to record all \nrequired information on any tagged crab \nyou may catch in the future. This entire \nform can be mailed to DFO Science at the \nend of the snow crab season. We continue \nto encourage all fishers to release any tagged \ncrab back into the water after recording the \ntag number and other information. This is proving\n to be helpful as we are now seeing multiple \ncaptures in different locations for the same \ntagged crab over time.\n</PARAGRAPH final>\n\n\n<PARAGRAPH end>\nWe have included a small token of our appreciation with the report.\n\nThanks again.\n\n\n\nBen Zisserson, DFO Science\n</PARAGRAPH end>");
        jScrollPane10.setViewportView(layout1);

        ButtonGenSkip.setText("Generate report");
        ButtonGenSkip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonGenSkipActionPerformed(evt);
            }
        });

        ButtonSkipPDF.setText("Generate PDF");
        ButtonSkipPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSkipPDFActionPerformed(evt);
            }
        });

        jLabel71.setBackground(new java.awt.Color(255, 255, 153));
        jLabel71.setText("Code needs some work, Not sure why I stopped working on this, works but no progress feedback and a little outdated.");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ButtonSkipPDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ButtonGenSkip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(skipmess, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(jLabel71)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel71)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(ButtonGenSkip)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSkipPDF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(skipmess, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Tagging Vessel Report", jPanel11);

        jLabel64.setText("The following options determine the data to be loaded from the database to create the resulting mark capture data file to be used by stats programs.  ");

        ButtonMarkCapCreate.setText("Create Data");
        ButtonMarkCapCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMarkCapCreateActionPerformed(evt);
            }
        });

        MCareaGroup.add(nens2);
        nens2.setText("NENS");

        MCareaGroup.add(sens2);
        sens2.setText("SENS");

        MCareaGroup.add(xxxx2);
        xxxx2.setText("4X");

        MCareaGroup.add(allareas2);
        allareas2.setText("All Areas");
        allareas2.setSelected(true);

        jLabel65.setText("From trips between years");

        jLabel66.setText("To year (non-inclusive)");

        MCout.setColumns(20);
        MCout.setRows(5);
        jScrollPane11.setViewportView(MCout);

        knownrel.setText("From crab Known to have been released");

        gulfarea.setText("GULF");

        ensarea.setText("All Areas ENS");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel64, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonMarkCapCreate, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel65)
                            .addComponent(toyear, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fromyear, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel66))
                        .addGap(98, 98, 98)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(sens2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(gulfarea))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(nens2)
                                .addGap(103, 103, 103)
                                .addComponent(knownrel))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(xxxx2)
                                .addGap(18, 18, 18)
                                .addComponent(ensarea))
                            .addComponent(allareas2))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel64)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fromyear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(toyear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(ButtonMarkCapCreate))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nens2)
                            .addComponent(knownrel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sens2)
                            .addComponent(gulfarea))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(xxxx2)
                            .addComponent(ensarea))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(allareas2)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(242, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("To Mark Capture Format", jPanel12);

        jPanel10.setBackground(new java.awt.Color(255, 175, 175));

        statstext.setColumns(20);
        statstext.setRows(5);
        jScrollPane8.setViewportView(statstext);

        ButtonGenStats.setText("Generate");
        ButtonGenStats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonGenStatsActionPerformed(evt);
            }
        });

        ButtonStatsGenSamp.setText("Generate map of Samples");
        ButtonStatsGenSamp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonStatsGenSampActionPerformed(evt);
            }
        });

        buttonGroup2.add(north);
        north.setText("NENS");

        buttonGroup2.add(south);
        south.setText("SENS");

        buttonGroup2.add(xxxx);
        xxxx.setText("4X");

        buttonGroup2.add(allareas);
        allareas.setText("All Areas");

        yeardata2.setText("2011");

        jLabel44.setText("YEAR");

        allyearcheck.setText("All Years");

        jLabel45.setText("AREA");

        jButton9.setText("Generate distributions");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        onlyplotrecaps.setText("Only plot recaps");

        jLabel67.setText("* 4X YEAR ENTRIES MAY DIFFER FROM DATE SO THAT THEY CAN BE GROUPED INTO A SINGLE REPORT");

        jLabel69.setText("NO LONGER USED. STATS AVAILABLE ON WEBSITE (Call tag.stats.site() from R SCtagging package)");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel45)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(south)
                                            .addComponent(north)
                                            .addComponent(xxxx)
                                            .addComponent(allareas)))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel44)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(allyearcheck)
                                            .addComponent(yeardata2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(ButtonGenStats)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(onlyplotrecaps))
                                    .addComponent(ButtonStatsGenSamp, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton9))
                                .addGap(10, 10, 10)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel67)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(282, 282, 282)
                        .addComponent(jLabel69)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel69)
                .addGap(8, 8, 8)
                .addComponent(jLabel67)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel45)
                            .addComponent(north))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(south)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xxxx)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(allareas)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(yeardata2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(allyearcheck)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ButtonGenStats)
                            .addComponent(onlyplotrecaps))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ButtonStatsGenSamp)
                        .addGap(93, 93, 93)
                        .addComponent(jButton9)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Stats", jPanel10);

        jPanel2.setBackground(new java.awt.Color(255, 175, 175));
        jPanel2.setMaximumSize(new java.awt.Dimension(1500, 1500));
        jPanel2.setPreferredSize(new java.awt.Dimension(800, 900));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 586, Short.MAX_VALUE)
        );

        Jpann.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel6.setText("Sample Data");

        jLabel7.setText("Latitude");

        jLabel8.setText("Longitude");

        jLabel9.setText("Depth(fathoms)");

        jLabel10.setText("Comments");

        jLabel29.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel29.setForeground(new java.awt.Color(102, 102, 102));
        jLabel29.setText("DDMM.MM");

        jLabel31.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel31.setForeground(new java.awt.Color(102, 102, 102));
        jLabel31.setText("DDMM.MM");

        javax.swing.GroupLayout JpannLayout = new javax.swing.GroupLayout(Jpann);
        Jpann.setLayout(JpannLayout);
        JpannLayout.setHorizontalGroup(
            JpannLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpannLayout.createSequentialGroup()
                .addGroup(JpannLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpannLayout.createSequentialGroup()
                        .addGap(324, 324, 324)
                        .addComponent(jLabel6))
                    .addGroup(JpannLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(174, 174, 174)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JpannLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel7)
                        .addGap(6, 6, 6)
                        .addComponent(latitude, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel8)
                        .addGap(6, 6, 6)
                        .addComponent(longnitude, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addComponent(jLabel9)
                        .addGap(4, 4, 4)
                        .addComponent(depth, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JpannLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jLabel10)
                        .addGap(10, 10, 10)
                        .addComponent(commen, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(166, Short.MAX_VALUE))
        );
        JpannLayout.setVerticalGroup(
            JpannLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpannLayout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(19, 19, 19)
                .addGroup(JpannLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(jLabel31))
                .addGap(6, 6, 6)
                .addGroup(JpannLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpannLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel7))
                    .addComponent(latitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JpannLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel8))
                    .addComponent(longnitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JpannLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel9))
                    .addComponent(depth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(JpannLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpannLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel10))
                    .addComponent(commen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jpan2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Trip Data");

        jLabel2.setText("Day Sampled ");

        jLabel3.setText("Sampler");

        jLabel4.setText("Vessel");

        jLabel5.setText("CFA");

        daySampled.setForeground(new java.awt.Color(153, 153, 153));
        daySampled.setText("dd/mm/yyyy");
        daySampled.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                daySampledFocusLost(evt);
            }
        });
        daySampled.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                daySampledKeyTyped(evt);
            }
        });

        jLabel63.setText("Captain");

        tcap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tcapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpan2Layout = new javax.swing.GroupLayout(jpan2);
        jpan2.setLayout(jpan2Layout);
        jpan2Layout.setHorizontalGroup(
            jpan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpan2Layout.createSequentialGroup()
                .addGroup(jpan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpan2Layout.createSequentialGroup()
                        .addGap(345, 345, 345)
                        .addComponent(jLabel1))
                    .addGroup(jpan2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(daySampled, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3)
                        .addGap(10, 10, 10)
                        .addComponent(sampler, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addGap(10, 10, 10)
                        .addComponent(vessel, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(10, 10, 10)
                        .addComponent(cfa, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel63)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tcap, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jpan2Layout.setVerticalGroup(
            jpan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpan2Layout.createSequentialGroup()
                .addGroup(jpan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpan2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(jpan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpan2Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel5))
                            .addGroup(jpan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cfa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel63)
                                .addComponent(tcap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpan2Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel4))
                            .addComponent(vessel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpan2Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(jpan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(daySampled, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(sampler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpan2Layout.createSequentialGroup()
                        .addContainerGap(34, Short.MAX_VALUE)
                        .addComponent(jLabel2)))
                .addGap(22, 22, 22))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tagBios.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tagBios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Tag Num", "Carapace Width", "Claw Height", "Carapace Cond", "Durometer", "Clutch", "Egg Color"
            }
        ));
        tagBios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tagBiosMouseReleased(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tagBiosMousePressed(evt);
            }
        });
        tagBios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tagBiosKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tagBios);

        jLabel11.setText("Crab Biologicals");

        jLabel39.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel39.setForeground(new java.awt.Color(102, 102, 102));
        jLabel39.setText("Shift or right click on columns ( cara cond, clutch,  egg )  for options");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(323, 323, 323)
                        .addComponent(jLabel11))
                    .addComponent(jLabel39)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 838, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel13.setText("Messages");

        ButtonEnterTagData.setText("Enter Data");
        ButtonEnterTagData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEnterTagDataActionPerformed(evt);
            }
        });

        jLabel70.setText("NO LONGER USED. STATS AVAILABLE ON WEBSITE (Call tag.stats.site() from R SCtagging package)");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel13)
                        .addGap(573, 573, 573)
                        .addComponent(ButtonEnterTagData, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(760, 760, 760)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Jpann, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(7, 7, 7))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jpan2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(jLabel70)
                .addContainerGap(137, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel70)
                .addGap(18, 18, 18)
                .addComponent(jpan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Jpann, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel13))
                    .addComponent(ButtonEnterTagData))
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Enter Tag Data", jPanel2);

        jMenuBar1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jMenuBar1.setMaximumSize(new java.awt.Dimension(156, 121));
        jMenuBar1.setMinimumSize(new java.awt.Dimension(56, 21));

        jMenu1.setText("File");

        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Set Database");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 875, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // User wants to safely close the application
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(1);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

   
  
    //method that returns the number of rows in a specified table
    private int getRows(String T)throws SQLException { 
        int retu;
        
           /* Class.forName(conname);
            String url = dataurl;
            Connection conn;
            if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, user, pass);
            }
            Statement st = conn.createStatement();
*/
            String cou = "SELECT COUNT(*) AS rowcount FROM " + T + ";";

        Connection conn = null;
      
        try{
           
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            Statement st = conn.createStatement();
      
            ResultSet r = st.executeQuery(cou);
            retu = r.getInt("rowcount");
            
        
        
        
            r.close();
        st.close();
        }catch(Exception e){
                retu = -1;
            e.printStackTrace();
        
            jTextArea1.append("Got an error when attempting to get the number of row in the table. \n");
            jTextArea1.append(e.getMessage());
            jTextArea1.append("\n");
            System.err.println("Got! ");
            System.err.println(e.getMessage());
        
        }finally{
        
            if(conn != null && !conn.isClosed()){
                 conn.close();
            }
            
        }
        return retu;
    }
    // Method that tests whether a supplied date is in the proper format

    private boolean isdatProper(String d, String form) {
        boolean tes = true;
        try {
            String[] spl = d.split("/");
            int mon, da, ye = 0;
            if(form.equals("dmy")){
                 mon = Integer.parseInt(spl[1]);
            da = Integer.parseInt(spl[0]);
            ye = Integer.parseInt(spl[2]);
            }
            else{
             mon = Integer.parseInt(spl[0]);
             da = Integer.parseInt(spl[1]);
             ye = Integer.parseInt(spl[2]);
            }
            if (mon < 13 && da < 32 && ye > 2000 && mon > 0 && da > 0) {
                tes = true;
            } else {
                throw new Exception("The date is not in the proper format");
            }



        } catch (Exception e) {
            tes = false;
            jTextArea1.append("Got an error when testing date format: \n");
            jTextArea1.append(e.getMessage());
            jTextArea1.append("\n");
            System.err.println("Date is not in proper format: m/d/Y ");
            System.err.println(e.getMessage());
        }

        return tes;
    }
    // Method that tests whether a supplied date is in the proper format, differs from above method as it writes to
    // a different text field.

    private boolean isdatProperCap(String d){
        boolean tes = true;
        try {
            String[] spl = d.split("/");
            int da = Integer.parseInt(spl[0]);
            int mon = Integer.parseInt(spl[1]);
            int ye = Integer.parseInt(spl[2]);
            if (mon < 13 && da < 32 && ye > 2000 && mon > 0 && da > 0) {
                tes = true;
            } else {
                throw new Exception("The date is not in the proper format");
            }



        } catch (Exception e) {
            tes = false;
            jTextArea2.append("Got an error when testing date format: \n");
            jTextArea2.append(e.getMessage());
            jTextArea2.append("\n");
            System.err.println("Date is not in proper format: m/d/Y ");
            System.err.println(e.getMessage());
        }

        return tes;
    }

    // Method that attempts to convert latitude and longnitude entries to decimal degrees, also
    // tests formatting of these entries
    private double minToDecd(String pos) {

        double aa = 0;

        try {

            String a = pos.substring(0, 2);
            String b = pos.substring(2, 4);
            String c = pos.substring(4, pos.length());
            aa = Double.parseDouble(a);
            double bb = Double.parseDouble(b);
            double cc = Double.parseDouble(c);
            if (bb < 0 || bb >= 60) {
                throw new Exception("minuits not exceptable ");
            }
            if (cc > 1) {
                throw new Exception("seconds not exceptable ");
            }
            bb = bb + cc;
            bb = (bb / 60);

            aa = aa + bb;
        } catch (Exception e) {
            aa = -1;
            jTextArea1.append("Got a position format error: \n");
            jTextArea1.append(e.getMessage());
            jTextArea1.append("\n");
            System.err.println("Position is not in proper format:");
            System.err.println(e.getMessage());
        }

        return aa;


    }

//Same as above method just writes to different text field since it is called at a different tab
    private double minToDecdCap(String pos) {
//catch format and realistic
        double aa = 0;

        try {

            String a = pos.substring(0, 2);
            String b = pos.substring(2, 4);
            String c = pos.substring(4, pos.length());
            aa = Double.parseDouble(a);
            double bb = Double.parseDouble(b);
            double cc = Double.parseDouble(c);
            if (bb < 0 || bb >= 60) {
                throw new Exception("minuits not exceptable ");
            }
            if (cc > 1) {
                throw new Exception("seconds not exceptable ");
            }
            bb = bb + cc;
            bb = (bb / 60);

            aa = aa + bb;
        } catch (Exception e) {
            aa = -1;
            jTextArea2.append("Got a position format error: \n");
            jTextArea2.append(e.getMessage());
            jTextArea2.append("\n");
            System.err.println("Position is not in proper format:");
            System.err.println(e.getMessage());
        }

        return aa;


    }
    // This method is called when the Enter Data buton is pushed in the Enter Capture Data tab
    private void writeperson(String per, String address, String tow, String pr, String postcod, String phonea, String phoneb, String emai) throws SQLException {
        boolean writepeo = true; //Keeps track of whether person data should be written in the person table
        boolean cont = true; //keeps track of whether data should be written
        if (address.isEmpty()) {
            int opt = JOptionPane.showConfirmDialog(rootPane, "The address field has not been supplied, would you like to continue anyway?", "", JOptionPane.YES_NO_OPTION);
            address = "unknown";
            if (opt == 1) {
                writepeo = false;
            }

        }

        //If both person and address is unknown we do not want to enter them into the database
        if (per.equals("unknown") && address.equals("unknown")) {
            writepeo = false;
        }

        //Check person fields where address equals address ask if person should equal this
        if (!address.equals("unknown") && per.equals("unknown") && writepeo) {


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
                String toda = "SELECT name FROM people where civic = '" + address + "';";
                //ResultSet back = selectmysqlssh(toda);
           
        Connection conn = null;
       
        try{
    
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            Statement st = conn.createStatement();
      
            ResultSet back = st.executeQuery(toda);
             
             while (back.next()) {
                    String temp = back.getString(1);
                    int opt = JOptionPane.showConfirmDialog(rootPane, "Found the name " + temp + " that matches this address, replace " + per + " with this found name?", "", JOptionPane.YES_NO_OPTION);

                    if (opt != 1) {
                        per = temp;
                    }


                }
st.close();
                back.close();
               // conn.close();
                 
        }catch(Exception e){
                     writepeo = false;
                jTextArea2.append("Got an error when attempting to check for address matches in the person table. \n");
                jTextArea2.append(e.getMessage());
                jTextArea2.append("\n");
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
        
        }finally{
        
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
           
        }
            
        }//end check for person where address equals address

        //If person is known and we want to write person data enter into more checks before adding
        if (!per.equals("unknown") && writepeo) {
            //Check if person is already in database, ask if an overwrite is nessesary
            boolean update = false;
/*          Class.forName(conname);
                String url = dataurl;
                Connection conn;
                if (localdata) {
                    conn = DriverManager.getConnection(url);
                } else {
                    conn = DriverManager.getConnection(url, user, pass);
                }
                Statement st = conn.createStatement();
*/
                String toda = "SELECT * FROM people where name = '" + per + "';";
Connection conn = null;
        
        try{
             Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
             
            Statement st = conn.createStatement();
      
        
                ResultSet back = st.executeQuery(toda);
                int count = 0;
                //Enter while loop if person/persons exists with same name
                while (back.next()) {
                    count++;



                    int op = JOptionPane.showConfirmDialog(rootPane, "Found a name that matches this person. Overwrite previous person with new data?", "", JOptionPane.YES_NO_OPTION);
                    if (op == 1) {
                        update = false;
                    } else {
                        update = true;
                    }
                    if (back.getString("civic") != null && !back.getString("civic").equals(address)) {
                        int opt = JOptionPane.showConfirmDialog(rootPane, "Found previos address entry '" + back.getString(2) + "' that matches this person, remove previous value and replace with '" + address + "'? (Ansering no will keep old value and ignore new one)", "", JOptionPane.YES_NO_OPTION);
                        if (opt == 1) {
                            tow = back.getString(3);
                        }
                    }
                    if (back.getString("town") != null && !back.getString("town").equals(tow)) {
                        int opt = JOptionPane.showConfirmDialog(rootPane, "Found previos town entry '" + back.getString(3) + "' that matches this person, remove previous value and replace with '" + tow + "'? (Ansering no will keep old value and ignore new one)", "", JOptionPane.YES_NO_OPTION);
                        if (opt == 1) {
                            tow = back.getString(3);
                        }
                    }
                    if (back.getString("post") != null && !back.getString("post").equals(postcod)) {
                        int opt = JOptionPane.showConfirmDialog(rootPane, "Found previos postal code entry '" + back.getString(4) + "' that matches this person, remove previous value and replace with '" + postcod + "'? (Ansering no will keep old value and ignore new one)", "", JOptionPane.YES_NO_OPTION);
                        if (opt == 1) {
                            postcod = back.getString(4);
                        }
                    }
                    if (back.getString("pho1") != null && !back.getString("pho1").equals(phonea)) {
                        int opt = JOptionPane.showConfirmDialog(rootPane, "Found previos phone1 entry '" + back.getString(5) + "' that matches this person, remove previous value and replace with '" + phonea + "'? (Ansering no will keep old value and ignore new one)", "", JOptionPane.YES_NO_OPTION);
                        if (opt == 1) {
                            phonea = back.getString(5);
                        }
                    }
                    if (back.getString("pho2") != null && !back.getString("pho2").equals(phoneb)) {
                        int opt = JOptionPane.showConfirmDialog(rootPane, "Found previos phone2 entry '" + back.getString(6) + "' that matches this person, remove previous value and replace with '" + phoneb + "'? (Ansering no will keep old value and ignore new one)", "", JOptionPane.YES_NO_OPTION);
                        if (opt == 1) {
                            phoneb = back.getString(6);
                        }
                    }
                    if (back.getString("email") != null && !back.getString("email").equals(emai)) {
                        int opt = JOptionPane.showConfirmDialog(rootPane, "Found previos email entry '" + back.getString(7) + "' that matches this person, remove previous value and replace with '" + emai + "'? (Ansering no will keep old value and ignore new one)", "", JOptionPane.YES_NO_OPTION);
                        if (opt == 1) {
                            emai = back.getString(7);
                        }
                    }


                }


st.close();
                back.close();
    //            conn.close();
            } catch (Exception e) {
                writepeo = false;
                jTextArea2.append("Got an error when attempting to check for matches in the person table. \n");
                jTextArea2.append(e.getMessage());
                jTextArea2.append("\n");
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }finally{
        
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
          
        }

            //Overwrite person data to database if an overwrite was inticated
            if (update && writepeo) {
                try {
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

                    String toda2 = "UPDATE people SET name = '" + per + "', civic = '" + address + "' , town = '" + tow + "' , prov = '" + pr + "' , post = '" + postcod + "' , email = '" + emai + "' , pho1 = '" + phonea + "' , pho2 = '" + phoneb + "' , country = Canada'" + "' WHERE name = '" + per + "';";

                    insertmysqlssh(toda2);

                    //conn.close();
                    jTextArea2.append("Updated entry in the people table\n");
                } catch (Exception e) {
                    cont = false;
                    jTextArea2.append("Got an error when trying to update the people table \n");
                    jTextArea2.append(e.getMessage());
                    jTextArea2.append("\n");
                    System.err.println("Got an exception!");
                    System.err.println(e.getMessage());
                }


            } else {
                //Write new person entry if overwrite was not intended
                if (writepeo) {
                     String toda3 = "INSERT INTO people (name, civic, town, prov, post, email, pho1, pho2) VALUES( '" + per + "' , '" + address + "' , '" + tow + "' , '" + prov.getText() + "' , '" + postcod + "' , '" + emai + "' , '" + phonea + "' , '" + phoneb + "');";
try {
                    insertmysqlssh(toda3);
                         jTextArea2.append("New entry added to people table\n");
                    } catch (Exception e) {
                        cont = false;
                        jTextArea2.append("Got an error when trying to write to the people table \n");
                        jTextArea2.append(e.getMessage());
                        jTextArea2.append("\n");
                        System.err.println("Got an exception!");
                        System.err.println(e.getMessage());
                    }
                    
                    /*
                    try {
                        Class.forName(conname);
                        String url = dataurl;
                        Connection conn;
                        if (localdata) {
                            conn = DriverManager.getConnection(url);
                        } else {
                            conn = DriverManager.getConnection(url, user, pass);
                        }
                        Statement st = conn.createStatement();

                        String toda = "INSERT INTO people (name, civic, town, prov, post, email, pho1, pho2) VALUES( '" + per + "' , '" + address + "' , '" + tow + "' , '" + prov.getText() + "' , '" + postcod + "' , '" + emai + "' , '" + phonea + "' , '" + phoneb + "');";

                        st.executeUpdate(toda);

                        conn.close();
                        jTextArea2.append("New entry added to people table\n");
                    } catch (Exception e) {
                        cont = false;
                        jTextArea2.append("Got an error when trying to write to the people table \n");
                        jTextArea2.append(e.getMessage());
                        jTextArea2.append("\n");
                        System.err.println("Got an exception!");
                        System.err.println(e.getMessage());
                    }
*/

                }//end write new entry to database

            }

        }

    }

    // IF Set database menu item option selected the following method executes
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        localdata = true;
        dataurl = "jdbc:sqlite:";
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose tagging database");
        chooser.setCurrentDirectory(new File("."));




        int r = chooser.showOpenDialog(new JFrame());


        dataurl = dataurl.concat(chooser.getSelectedFile().getPath());



    }//GEN-LAST:event_jMenuItem2ActionPerformed

    //following group of methods are called when the tag entry cells are operated on
    // to set values without using the keyboard, not very usefull in practice
    private void oneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oneActionPerformed
        tagBios.setValueAt("1", tagBios.getSelectedRow(), tagBios.getSelectedColumn());
    }//GEN-LAST:event_oneActionPerformed

    private void twoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twoActionPerformed
        tagBios.setValueAt("2", tagBios.getSelectedRow(), tagBios.getSelectedColumn());

    }//GEN-LAST:event_twoActionPerformed

    private void threeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threeActionPerformed
        tagBios.setValueAt("3", tagBios.getSelectedRow(), tagBios.getSelectedColumn());
    }//GEN-LAST:event_threeActionPerformed

    private void fourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fourActionPerformed
        tagBios.setValueAt("4", tagBios.getSelectedRow(), tagBios.getSelectedColumn());
    }//GEN-LAST:event_fourActionPerformed

    private void fiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fiveActionPerformed
        tagBios.setValueAt("5", tagBios.getSelectedRow(), tagBios.getSelectedColumn());
    }//GEN-LAST:event_fiveActionPerformed
    
    private void e3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e3ActionPerformed
        tagBios.setValueAt("3", tagBios.getSelectedRow(), tagBios.getSelectedColumn());
    }//GEN-LAST:event_e3ActionPerformed

    private void c0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c0ActionPerformed
        tagBios.setValueAt("0", tagBios.getSelectedRow(), tagBios.getSelectedColumn());
    }//GEN-LAST:event_c0ActionPerformed

    private void c1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c1ActionPerformed
        tagBios.setValueAt("1", tagBios.getSelectedRow(), tagBios.getSelectedColumn());
    }//GEN-LAST:event_c1ActionPerformed

    private void c2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c2ActionPerformed
        tagBios.setValueAt("2", tagBios.getSelectedRow(), tagBios.getSelectedColumn());
    }//GEN-LAST:event_c2ActionPerformed

    private void c3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c3ActionPerformed
        tagBios.setValueAt("3", tagBios.getSelectedRow(), tagBios.getSelectedColumn());
    }//GEN-LAST:event_c3ActionPerformed

    private void c4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c4ActionPerformed
        tagBios.setValueAt("4", tagBios.getSelectedRow(), tagBios.getSelectedColumn());
    }//GEN-LAST:event_c4ActionPerformed

    private void e1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e1ActionPerformed
        tagBios.setValueAt("1", tagBios.getSelectedRow(), tagBios.getSelectedColumn());
    }//GEN-LAST:event_e1ActionPerformed

    private void e2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e2ActionPerformed
        tagBios.setValueAt("2", tagBios.getSelectedRow(), tagBios.getSelectedColumn());
    }//GEN-LAST:event_e2ActionPerformed

    private void e4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e4ActionPerformed
        tagBios.setValueAt("4", tagBios.getSelectedRow(), tagBios.getSelectedColumn());
    }//GEN-LAST:event_e4ActionPerformed

    private void ButtonRewardsTextFetchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRewardsTextFetchActionPerformed
 textlist.clearSelection();
        Vector<String> a = new Vector<String>();
        String year = yearfld.getText();
 
     
          String forarea = "allareas";
        if(tagFrame.axxxx.isSelected())
        forarea = "4X";
        if(tagFrame.as.isSelected())
        forarea = "SENS";
        if(tagFrame.an.isSelected())
        forarea = "NENS";
        
    //    if(forarea.equals("allareas"))
     //   year = "Letters//".concat(year);


         year = datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("Letters/").concat(forarea).concat(year);
        if (rewardrun.isSelected()) {
            year = year.concat("unrewarded");
        }
        
        

        File folder = new File(year);
        File[] listOfFiles = null;
        if (folder.isDirectory()) {
            listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    a.add(listOfFiles[i].getName());
                }
            }

        } 

        textlist.setListData(a);
    }//GEN-LAST:event_ButtonRewardsTextFetchActionPerformed

    private void ButtonRewardsTextPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRewardsTextPreviewActionPerformed
  Object a[] = textlist.getSelectedValues();
        String path;

        for (int i = 0; i < a.length; i++) {
            
                 String year = yearfld.getText();
       
          String forarea = "allareas";
        if(tagFrame.axxxx.isSelected())
        forarea = "4X";
        if(tagFrame.as.isSelected())
        forarea = "SENS";
        if(tagFrame.an.isSelected())
        forarea = "NENS";
        
  //      if(forarea.equals("allareas"))
  //      year = "Letters//".concat(year);
         year = datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("Letters/").concat(forarea).concat(year);
        if (rewardrun.isSelected()) {
            year = year.concat("unrewarded");
        }
        
            path = a[i].toString();
            path = year.concat("//").concat(path);

            String res = "";
            JTextArea text = new JTextArea();
            text.setBounds(0, 0, 510, 1000);
            text.setLineWrap(true);
            text.setWrapStyleWord(true);

            try {
              
                FileInputStream fstream = new FileInputStream(path);
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
              
                while ((strLine = br.readLine()) != null) {
                    text.append("\n");
                    text.append(strLine);
                    res = res.concat(strLine);
             
                }
                //Close the input stream
                in.close();
            } catch (Exception e) {//Catch exception if any
                System.err.println("Error: " + e.getMessage());
            }

            JFrame texframe = new JFrame("Preview Text");
            JPanel texpan = new JPanel();
            texframe.add(texpan);
            texpan.add(text);
            texframe.setSize(1000, 1000);
            texframe.setVisible(true);
        }
    }//GEN-LAST:event_ButtonRewardsTextPreviewActionPerformed

    private void ButtonRewardsGenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRewardsGenActionPerformed
        jProgress.setEnabled(true);
         jProgress.setIndeterminate(true);
         jProgress.setStringPainted(true);
         jProgress.setString("Loading Data");
         jProgress.paintImmediately( 0,0,  tagFrame.jProgress.getBounds().width ,  tagFrame.jProgress.getBounds().height );


         (new docalcs()).start();//start the new thread that does the calculations

    }//GEN-LAST:event_ButtonRewardsGenActionPerformed

    private void RewardsPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RewardsPDFActionPerformed

        PDFopps.genrewardPDF();
     
    }//GEN-LAST:event_RewardsPDFActionPerformed

    private void ButtonRewardsMapPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRewardsMapPreviewActionPerformed
        Object a[] = piclist.getSelectedValues();
        String path;
        BufferedImage img;





        for (int i = 0; i < a.length; i++) {
            path = a[i].toString();

            String forarea = "allareas";
            if (tagFrame.axxxx.isSelected()) {
                forarea = "4X";
            }
            if (tagFrame.as.isSelected()) {
                forarea = "SENS";
            }
            if (tagFrame.an.isSelected()) {
                forarea = "NENS";
            }
            String year = yearfld.getText();
           // if (forarea.equals("allareas")) {
            //    year = "maps//".concat(year);
            //} else {
                year = datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("maps/").concat(forarea).concat(year);
            //}
            if (rewardrun.isSelected()) {
                year = year.concat("unrewarded");
            }
            path = year.concat("//").concat(path);

            try {
                img = ImageIO.read(new File(path));
                JFrame picframe = new JFrame("Preview Image");
                //picframe.setDefaultCloseOperation(JFrame.ABORT);
                picframe.setSize(1000, 1000);
                picframe.setResizable(true);
                picframe.setLocationRelativeTo(null);

                // Inserts the image icon



                ImageIcon image = new ImageIcon(path);
                JLabel piclabel1 = new JLabel(" ", image, JLabel.CENTER);
                picframe.getContentPane().add(piclabel1);

                picframe.validate();
                picframe.setVisible(true);




            } catch (IOException e) {
                System.err.println("got preview exception");
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_ButtonRewardsMapPreviewActionPerformed

    private void ButtonRewardsMapFetchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRewardsMapFetchActionPerformed
piclist.clearSelection();
        Vector<String> a = new Vector<String>();
        String year = yearfld.getText();
       
          String forarea = "allareas";
        if(tagFrame.axxxx.isSelected())
        forarea = "4X";
        if(tagFrame.as.isSelected())
        forarea = "SENS";
        if(tagFrame.an.isSelected())
        forarea = "NENS";
        
        //if(forarea.equals("allareas"))
       // year = "maps//".concat(year);
        year = datadir.concat("/").concat("bio.snowcrab/data/tagging/javaApp/").concat("maps/").concat(forarea).concat(year);
        if (rewardrun.isSelected()) {
            year = year.concat("unrewarded");
        }
        
        File folder = new File(year);
        File[] listOfFiles = null;
        if (folder.isDirectory()) {
            listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    a.add(listOfFiles[i].getName());
                }
            }

        } 

        piclist.setListData(a);
    }//GEN-LAST:event_ButtonRewardsMapFetchActionPerformed

    private void ButtonSkipPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSkipPDFActionPerformed
       PDFopps.genskipPDF();
    }//GEN-LAST:event_ButtonSkipPDFActionPerformed

    private void ButtonGenSkipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonGenSkipActionPerformed
try{
        SkipperMapFrame.genSkipperRep();
        
}catch(Exception e){
    System.err.println(e);
    e.printStackTrace();
}
    }//GEN-LAST:event_ButtonGenSkipActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String foryear = "";
        String forarea = "ALL AREAS";
        if (north.isSelected()) {
            forarea = "NENS";
        }
        if (tagFrame.south.isSelected()) {
            forarea = "SENS";
        }
        if (tagFrame.xxxx.isSelected()) {
            forarea = "4X";
        }

        int tagsretuni;
        int tagsrecap;
        int tagsapplied = 0;
        LinkedList<posit> poslist = new LinkedList<posit>();

        if (allyearcheck.isSelected()) {
            foryear = "all";
        }


        
            /*Class.forName(conname);
            String url = dataurl;

            Connection conn;
            if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, user, pass
            }

            Statement st = conn.createStatement();
            */
            String toda = "";
            if (foryear.equals("all")) {
                if (forarea.equals("ALL AREAS")) {
                    toda = "select COUNT(*) from(select distinct 'tag' from 'capture');";

                } else {
                    toda = "select COUNT(*) from(select distinct 'tag' from 'capture' where 'statsarea' = '" + forarea + "' );";
                }
            } else {
                if (forarea.equals("ALL AREAS")) {
                    toda = "select COUNT(*) from(select distinct 'tag' from 'capture' where 'year' = '" + foryear + "' );";
                } else {
                    toda = "select COUNT(*) from(select distinct 'tag' from 'capture' where 'year'= '" + foryear + "' AND 'statsarea' = '" + forarea + "');";
                }
            }
            
            
        Connection conn = null;
     
        try{
               Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            Statement st = conn.createStatement();
     
            ResultSet back = st.executeQuery(toda);
            String res = back.getString(1);
    
            tagsretuni = Integer.parseInt(res);

            statstext.append("Total number of distinct tags returned from this area and year: " + res + "\n");
st.close();
            back.close();


            if (!foryear.equals("all")) {
                if (forarea.equals("ALL AREAS")) {
                 //   st = conn.createStatement();
                    toda = "select count(*) from (select 'bio.tag_id' from 'bio' join 'sample' on 'sample_id' = 'sample_num' join 'trip' on 'trip_id' = 'sample.trip' and 'year' = '" + foryear + "');";
                    int count = 0;
                     conn = null;
                   
        try{
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            st = conn.createStatement();
      
        
                    back = st.executeQuery(toda);
                    count = Integer.parseInt(back.getString(1));
                    statstext.append("Total number of crabs taged: " + count + "\n");
                 st.close();
                    back.close();
                    
            } catch (Exception e) {
           
                jTextArea2.append("Got an error when attempting to check total crab tagged. \n");
                jTextArea2.append(e.getMessage());
                jTextArea2.append("\n");
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }finally{
        
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
           
        }        
                    
                } else {
                  //  st = conn.createStatement();
                    //Tagged in this area in this year
                    conn = null;
                 
        try{
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            st = conn.createStatement();
      
        
                    toda = "select count(*) from (select bio.tag_id from bio join sample on sample_id = sample_num join trip on trip_id = sample.trip and trip.statsarea = '" + forarea + "' and year = '" + foryear + "');";
                    int count = 0;
                    back = st.executeQuery(toda);
                    count = Integer.parseInt(back.getString(1));
                    statstext.append("Total number of crabs taged in this area: " + count + "\n");
                    back.close();
                    st.close();
                    
                                } catch (Exception e) {
           
                jTextArea2.append("Got an error when attempting to check total crab tagged in area. \n");
                jTextArea2.append(e.getMessage());
                jTextArea2.append("\n");
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }finally{
        
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
         
        }        

                    
                    
                }
            }

            if (foryear.equals("all")) {
                if (forarea.equals("ALL AREAS")) {
                    toda = "SELECT COUNT(*) FROM(select tag from capture);";
                } else {
                    toda = "SELECT COUNT(*) FROM(select tag from capture where statsarea = '" + forarea + "');";
                }
            } else {
                if (forarea.equals("ALL AREAS")) {
                    toda = "SELECT COUNT(*) FROM(select tag from capture where year = '" + foryear + "' );";
                } else {
                    toda = "SELECT COUNT(*) FROM(select tag from capture where statsarea = '" + forarea + "' and year = '" + foryear + "' );";
                }
            }
            
            conn = null;
                   
        try{
           
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            st = conn.createStatement();
      
        
            
            back = st.executeQuery(toda);
            res = back.getString(1);
          
            tagsrecap = Integer.parseInt(res) - tagsretuni;
            statstext.append("Total number of tags returned more than once: " + tagsrecap + "\n");
            back.close();
            st.close();
            
                                } catch (Exception e) {
           
                jTextArea2.append("Got an error when attempting to check total crab tagged in area. \n");
                jTextArea2.append(e.getMessage());
                jTextArea2.append("\n");
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }finally{
        
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
           
        }        

            
            
            if (!forarea.equals("ALL AREAS")) {

                //st = conn.createStatement();

                toda = "SELECT COUNT(*) FROM (Select * from (select * from trip join sample where trip_id = trip and statsarea = '" + forarea + "') join bio where bio.sample_num = sample_id );";
            
                conn = null;
                  
        try{
           
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            st = conn.createStatement();
      
                
                back = st.executeQuery(toda);
                res = back.getString(1);
                tagsapplied = Integer.parseInt(res);
                statstext.append("Total number of tags ever applied in this area: " + tagsapplied + "\n");
                back.close();
                st.close();
                                } catch (Exception e) {
           
                jTextArea2.append("Got an error when attempting to check total tags ever applied in area. \n");
                jTextArea2.append(e.getMessage());
                jTextArea2.append("\n");
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }finally{
        
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
           
        }       
                
                
            }
            if (!forarea.equals("ALL AREAS") && !foryear.equals("all")) {

               // st = conn.createStatement();

                toda = "SELECT COUNT(*) FROM (Select * from (select * from trip join sample where trip_id = trip and year = '" + foryear + "' and statsarea = '" + forarea + "') join bio where bio.sample_num = sample_id );";
               
                
            conn = null;
                   
        try{
          
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            st = conn.createStatement();
      
                back = st.executeQuery(toda);
                res = back.getString(1);
                tagsapplied = Integer.parseInt(res);
                statstext.append("Total number of tags applied in this area this year: " + tagsapplied + "\n");
                back.close();
                
                                } catch (Exception e) {
           
                jTextArea2.append("Got an error when attempting to check total tags applied in area and year. \n");
                jTextArea2.append(e.getMessage());
                jTextArea2.append("\n");
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }finally{
        
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
            
        }       
                
            }


            //st = conn.createStatement();
            tagsapplied = getRowsssh("bio");
            statstext.append("Total number of tags ever applied: " + tagsapplied + "\n");
            back.close();
st.close();


             //st = conn.createStatement();

            String toda3 = "";
            if (foryear.equals("all")) {
                if (forarea.equals("ALL AREAS")) {
                    toda3 = "Select DISTINCT tag from capture;";
                } else {
                    if (forarea.equals("NENS")) {
                        toda3 = "Select DISTINCT tag from capture where statsarea = '" + forarea + "' or statsarea = 'GULF';";
                    } else {
                        toda3 = "Select DISTINCT tag from capture where statsarea = '" + forarea + "';";
                    }
                }
            } else {

                if (forarea.equals("ALL AREAS")) {
                    toda3 = "Select DISTINCT tag from capture where year = " + foryear + ";";
                } else {
                    if (forarea.equals("NENS")) {
                        toda3 = "Select DISTINCT tag from capture where year = '" + foryear + "' and (statsarea = '" + forarea + "' or statsarea = 'GULF');";
                    } else {
                        toda3 = "Select DISTINCT tag from capture where year = '" + foryear + "' and statsarea = '" + forarea + "';";
                    }
                }

            }
      
            
            conn = null;
                
        try{
           
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            st = conn.createStatement();
      
            
            back = st.executeQuery(toda3);
            int time = 0;
            while (back.next()) {

//if(forarea.equals("ALL AREAS")) inproperarea = true;
//if(foryear.equals("all")) inproperyear = true;
                Statement st2 = conn.createStatement();
                String toda2 = "";

                toda2 = "select * from capture where tag = " + back.getString("tag") + ";";

                ResultSet back2 = st2.executeQuery(toda2);
                posit tem = new posit();
                tem.capd = new LinkedList<String>();
                tem.capla = new LinkedList<String>();
                tem.caplo = new LinkedList<String>();

                boolean once = false;


                Statement st3 = conn.createStatement();
                toda = "Select * from sample join bio where bio.sample_num = sample.sample_id and bio.tag_id = " + back.getString("tag") + ";";
                ResultSet back3 = st3.executeQuery(toda);
                tem.tag = back3.getString("tag_id");
                tem.sampd = back3.getString("date");
                tem.samlo = back3.getString("long_DD_DDDD");
                tem.samla = back3.getString("lat_DD_DDDD");
                String dat = back3.getString("date");
                back3.close();
                st3.close();

                st3 = conn.createStatement();
                toda = "Select statsarea, year from trip join (Select * from sample join bio where bio.sample_num = sample.sample_id and bio.tag_id = " + tem.tag + ") where trip_id = trip;";
                back3 = st3.executeQuery(toda);
           
                while (back2.next()) {
                    String cappdat = back2.getString("date");
                    time++;

                    if (!cappdat.equals("unknown") && !dat.equals("unknown")) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        java.util.Date capda = formatter.parse(cappdat);
                        java.util.Date sampda = formatter.parse(dat);

                        Calendar scal = Calendar.getInstance();
                        scal.setTime(sampda);
                        Calendar ccal = Calendar.getInstance();
                        ccal.setTime(capda);
                        boolean good = true;

                        if (back2.getString("lat_DD_DDDD").equals("0") || back2.getString("lat_DD_DDDD").equals("NA")) {
                            good = false;
                        }
                        if (back2.getString("long_DD_DDDD").equals("0") || back2.getString("long_DD_DDDD").equals("NA")) {
                            good = false;
                        }

                        if (statsFrame.daysBetween(scal, ccal) > 10 && good) {
                          
                            if (Double.parseDouble(back2.getString("lat_DD_DDDD")) > 42 && Double.parseDouble(back2.getString("lat_DD_DDDD")) < 50) {
                                if (Double.parseDouble(back2.getString("long_DD_DDDD")) > -70 && Double.parseDouble(back2.getString("long_DD_DDDD")) < -55) {

                                    once = true;
                                  

                                    tem.capd.add(back2.getString("date"));
                                    tem.capla.add(back2.getString("lat_DD_DDDD"));
                                    tem.caplo.add(back2.getString("long_DD_DDDD"));

                                }
                            }
                            //  poslist.add(new posit( back2.getString("TagID"), back2.getString("Date"), back2.getString("Lat_DDDD_DD"), back2.getString("Long_DDDD_DD"), back.getString("Day_Captured"), back.getString("Lat_DDDD_DD"), back.getString("Long_DDDD_DD")));
                        }
                    }
                    // curr.labelnames.add(a);
                }

                //                   if(once && inproperarea && inproperyear)poslist.add(tem);
                if (once) {
                    poslist.add(tem);
                }
                back2.close();
                st2.close();
            }


            back.close();
            st.close();
                    } catch (Exception e) {
           
                jTextArea2.append("Got an error when building captured data. \n");
                jTextArea2.append(e.getMessage());
                jTextArea2.append("\n");
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }finally{
        
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
           
        }    



            for (int k = 0; k < poslist.size(); k++) {
                posit te = poslist.get(k);
                int inde = 0;

                while (inde < te.capd.size()) {


                    int y = inde + 1;
                    while (y < te.capd.size()) {
                        if (mapFrame.dateToInt(te.capd.get(inde)) > mapFrame.dateToInt(te.capd.get(y))) {

                            te.capd.addFirst(te.capd.get(y));
                            te.capd.remove(y);
                            te.capla.addFirst(te.capla.get(y));
                            te.capla.remove(y);
                            te.caplo.addFirst(te.caplo.get(y));
                            te.caplo.remove(y);
                            inde = 0;
                            y = inde + 1;

                        } else {

                            y++;
                        }
                    }
                    inde++;

                }
            }
            LinkedList distlis = new LinkedList();
            LinkedList displis = new LinkedList();
            LinkedList direlis = new LinkedList();
         
            //  double totspeed = 0;

            GeodeticCalculator calc = new GeodeticCalculator();


            int numc = 0;
            double onedistot = 0;
            double onelardis = 0;
            String onelardist = "";
            long onedaystot = 0;
            double onelarday = 0;
            String onelardays = "";
            calc = new GeodeticCalculator();
 
            for (int i = 0; i < poslist.size(); i++) {
                int ind = 0;
                double onedis = 0;
                String lastday = "";
                String curday = "";
                boolean once = false;
                while (ind < poslist.get(i).capd.size()) {
                    if (foryear.equals(poslist.get(i).capd.get(ind).split("/")[2])) {
                        //from sample
                        if (ind == 0) {
                            calc.setStartingGeographicPoint(Double.parseDouble(poslist.get(i).samlo), Double.parseDouble(poslist.get(i).samla));
                            calc.setDestinationGeographicPoint(Double.parseDouble(poslist.get(i).caplo.get(ind)), Double.parseDouble(poslist.get(i).capla.get(ind)));
                            onedistot = onedistot + calc.getOrthodromicDistance() / 1000; //in km
                            numc++;
                            onedis = calc.getOrthodromicDistance() / 1000;
                            direlis.add(calc.getAzimuth());
                           
                            distlis.add(onedis);


                            curday = poslist.get(i).capd.get(ind);
                            if (!once) {
                                lastday = poslist.get(i).sampd;
                                once = true;
                            }
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                            java.util.Date sampda2 = formatter.parse(lastday);
                            java.util.Date capda2 = formatter.parse(curday);
                            Calendar scal = Calendar.getInstance();
                            scal.setTime(sampda2);
                            Calendar ccal = Calendar.getInstance();
                            ccal.setTime(capda2);
                       
                            displis.add(statsFrame.daysBetween(scal, ccal));



                        } else {
                            calc.setStartingGeographicPoint(Double.parseDouble(poslist.get(i).caplo.get(ind - 1)), Double.parseDouble(poslist.get(i).capla.get(ind - 1)));
                            calc.setDestinationGeographicPoint(Double.parseDouble(poslist.get(i).caplo.get(ind)), Double.parseDouble(poslist.get(i).capla.get(ind)));
                            onedistot = onedistot + calc.getOrthodromicDistance() / 1000; //in km
                            numc++;

                            curday = poslist.get(i).capd.get(ind);

                            onedis = calc.getOrthodromicDistance() / 1000;
                            direlis.add(calc.getAzimuth());
                            distlis.add(onedis);
                        

                            if (!once) {
                                lastday = poslist.get(i).capd.get(ind - 1);
                                once = true;
                            }
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                            java.util.Date sampda2 = formatter.parse(lastday);
                            java.util.Date capda2 = formatter.parse(curday);
                            Calendar scal = Calendar.getInstance();
                            scal.setTime(sampda2);
                            Calendar ccal = Calendar.getInstance();
                            ccal.setTime(capda2);

                            displis.add(statsFrame.daysBetween(scal, ccal));
                        }
                    }
                    ind++;
                }
                if (onedis > onelardis) {
                    onelardis = onedis;
                    onelardist = poslist.get(i).tag + " between dates " + lastday + " and " + curday;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
              
                java.util.Date sampda2 = formatter.parse(lastday);
                java.util.Date capda2 = formatter.parse(curday);

                Calendar scal = Calendar.getInstance();
                scal.setTime(sampda2);
                Calendar ccal = Calendar.getInstance();
                ccal.setTime(capda2);



                onedaystot = onedaystot + statsFrame.daysBetween(scal, ccal);


                if (statsFrame.daysBetween(scal, ccal) > onelarday) {
                    onelarday = statsFrame.daysBetween(scal, ccal);
                    onelardays = poslist.get(i).tag + " between dates " + lastday + " and " + curday + " with"
                            + " a total distance traveled of " + onedis + "km";

                }
            }





            Object[] value = distlis.toArray();

            double[] v = new double[value.length];
            for (int i = 0; i < value.length; i++) {
                v[i] = Double.parseDouble(value[i].toString());
            }
            int number = 10;
            HistogramDataset dataset = new HistogramDataset();
            dataset.setType(HistogramType.RELATIVE_FREQUENCY);
            dataset.addSeries("Histogram", v, number);
            String plotTitle = "Histogram";
            String xaxis = "number";
            String yaxis = "Distance";
            PlotOrientation orientation = PlotOrientation.VERTICAL;
            boolean show = false;
            boolean toolTips = false;
            boolean urls = false;
            JFreeChart chart = ChartFactory.createHistogram(plotTitle, xaxis, yaxis,
                    dataset, orientation, show, toolTips, urls);
            int width = 500;
            int height = 300;

            ChartUtilities.saveChartAsPNG(new File("histogram.PNG"), chart, width, height);









        } catch (Exception e) {

            System.err.println("Got an exception!");
            e.printStackTrace();
        }


    }//GEN-LAST:event_jButton9ActionPerformed

    private void ButtonStatsGenSampActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonStatsGenSampActionPerformed

        try {
            statsFrame.genSam();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }//GEN-LAST:event_ButtonStatsGenSampActionPerformed

    private void ButtonGenStatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonGenStatsActionPerformed

        // TODO add your handling code here:
        try {
            statsFrame.getStats(yeardata2.getText());
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }//GEN-LAST:event_ButtonGenStatsActionPerformed

    // If the Enter Data button is pushed, in the Enter tag data tab, the following method is called  
    int carryover = 0;
    private void ButtonEnterTagDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEnterTagDataActionPerformed
        // Variable cont used to keep track of whether or not to write the entered information into the database. Certian conditions may
        // arise that would make the user not want to enter this data.
        try {
            Boolean cont = true;

            // If no database has been supplied prompt user.
            if (dataurl.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please locate the Tagging database in the file menu, set database");
                cont = false;
            }


            jTextArea1.removeAll(); // Clear the user messege area

            // Move the cell editor so that new focus can be gained
            tagBios.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
            tagBios.editCellAt(0, 0);
            tagBios.editCellAt(1, 1);
            tagBios.editCellAt(0, 0);

            // Create a new list to store the commands to be sent to the database
            LinkedList<String> bioadd = new LinkedList<String>(); //****//

            String dat = "";
            String date = daySampled.getText();

            //Check to see if the date entered is in the proper format. Add leading zeros
            // if not added
            try {
                String[] data = date.split("/");

                String x = data[1];
                String y = data[0];
                String z = data[2];
                if (x.length() == 1) {
                    x = "0".concat(x);
                }
                if (y.length() == 1) {
                    y = "0".concat(y);
                }
                dat = dat.concat(y).concat("/").concat(x).concat("/").concat(z);

            } catch (Exception e) {
                cont = false;
                jTextArea1.append("Got an error when operating on the Date");
                jTextArea1.append(e.getMessage());
                jTextArea1.append("\n");

                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }


            String sam = sampler.getText();
            String ves = vessel.getText();
            String zone = cfa.getText();

            // Check if entered date is properly formatted
            if (!isdatProper(dat, "dmy")) {
                cont = false;
            }
            // Integer set to 1, will later test to see if entry has already been added
            int exis = 1;
            int res = 0;
            if (cont) {

                // attempt to connect to database to see if data has already been added


                //   String url = dataurl;


                //  Class.forName(conname);
                /*
                Connection conn;
                
                if (localdata) {
                conn = DriverManager.getConnection(url);
                } else {
                conn = DriverManager.getConnection(url, user, pass);
                }
                Statement st = conn.createStatement();
                 */
                String toda = "SELECT COUNT(*) AS rowcount from trip where date = \"" + dat + "\" AND technician = \"" + sam + "\";";
                Connection conn = null;
              
                try {
                  
                    Class.forName(driverName);
                    conn = DriverManager.getConnection(url, dbuser, pass);
       
                    Statement st = conn.createStatement();


                    ResultSet back = st.executeQuery(toda);

                    back.next();
                    exis = back.getInt("rowcount");
                    back.close();
                    st.close();
                } catch (Exception e) {

                    jTextArea2.append("Got an error when counting trips or skip report\n");
                    jTextArea2.append(e.getMessage());
                    jTextArea2.append("\n");
                    System.err.println("Got an exception!");
                    System.err.println(e.getMessage());
                } finally {

                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                    
                }
                if (exis != 0) {
                    //              st = conn.createStatement();
                    toda = "SELECT trip_id from trip where date = \"" + dat + "\" AND technician = \"" + sam + "\";";

                    conn = null;
                   
                    try {
                          Class.forName(driverName);
                        conn = DriverManager.getConnection(url, dbuser, pass);
       
                        Statement st = conn.createStatement();


                        ResultSet back = st.executeQuery(toda);
                        back.next();
                        res = back.getInt("trip_id");


                        back.close();
                        st.close();
                        // conn.close();

                    } catch (Exception e) {
                        cont = false;
                        jTextArea1.append("Got an error when querying database to find if trip data has been added \n");
                        jTextArea1.append(e.getMessage());
                        jTextArea1.append("\n");
                        System.err.println("Got an exception!");
                        System.err.println(e.getMessage());
                    } finally {

                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                      
                    }


                    //Not yet added, want to add
                    if (exis == 0) {


                        try {
                            /* Class.forName(conname);
                            
                            Connection conn;
                            String url = dataurl;
                            if (localdata) {
                            conn = DriverManager.getConnection(url);
                            } else {
                            conn = DriverManager.getConnection(url, user, pass);
                            }
                             */


                            //   String toda = "SELECT COUNT(*) FROM(select trip_id from trip);";
                            //    Statement st = conn.createStatement();

                            //   ResultSet back = st.executeQuery(toda);
                            //  res = Integer.parseInt(back.getString(1))+1;
                            res = getRowsssh("trip") + 1;

                            String sta = "";
                            if (zone.equals("4X")) {
                                sta = "4X";
                            }
                            if (zone.equals("22")) {
                                sta = "NENS";
                            }
                            if (zone.equals("21")) {
                                sta = "NENS";
                            }
                            if (zone.equals("20")) {
                                sta = "NENS";
                            }
                            if (zone.equals("NENS")) {
                                sta = "NENS";
                            }
                            if (zone.equals("N-ENS")) {
                                sta = "NENS";
                            }
                            if (zone.equals("23")) {
                                sta = "SENS";
                            }
                            if (zone.equals("24")) {
                                sta = "SENS";
                            }
                            String suba = "";
                            if (sta == "NENS") {
                                suba = "(all)(ens)(nens)(nens_gulf)(allandgulf)";
                            }
                            if (sta == "SENS") {
                                if (zone.equals("23")) {
                                    suba = "(cfa23)(all)(ens)(sens)(allandgulf)(cfa23zoom)(cfa24zoom)(all.holes)";
                                }
                                if (zone.equals("24")) {
                                    suba = "(cfa24)(all)(ens)(sens)(allandgulf)(cfa24zoom)(cfa24zoom)(all.holes)";
                                }

                            }
                            if (sta == "4X") {
                                suba = "(all)(ens)(cfa4x)(allandgulf)";
                            }





                            // Statement st = conn.createStatement();
                            String yrf = date.split("/")[2];
                            if (sta.equals("4X")) {
                                String monf = date.split("/")[1];
                                int monfi = Integer.parseInt(monf);
                                if (monfi > 7) {
                                    yrf = Integer.toString(Integer.parseInt(monf) + 1);
                                }
                            }
                            toda = "INSERT INTO trip VALUES( \"" + res + "\" , \"" + sam + "\" , \"" + ves + "\" , \"" + zone + "\" , \"" + dat + "\" , \"" + yrf + "\" , \"" + sta + "\" , \"" + "0" + "\" , \"" + tcap.getText() + "\" , \"" + suba + "\");";

                            insertmysqlssh(toda);

                            //  st.executeUpdate(toda);

                            // conn.close();
                            jTextArea1.append("New entry added to trip table\n");
                        } catch (Exception e) {
                            cont = false;
                            jTextArea1.append("Got an error when trying to write to the trip table \n");
                            jTextArea1.append(e.getMessage());
                            jTextArea1.append("\n");
                            System.err.println("Got an exception!");
                            System.err.println(e.getMessage());
                        }
                    } //Entry existed want to know what the trip id was
                    else {
                    }


                }

                // Find the number of rows in the sample table so that the new entry is unique
                int rowcount = -1;
                try {
                    rowcount = getRowsssh("sample");
                } catch (Exception e) {
                    cont = false;
                    jTextArea1.append("Got an error when querying database to find unique sample number\n");
                    jTextArea1.append(e.getMessage());
                    jTextArea1.append("\n");
                    System.err.println("Got an exception!");
                    System.err.println(e.getMessage());
                }


                if (rowcount == -1) {
                    cont = false;
                }
                rowcount = rowcount + 1; //unique sample id for this entry

                //Check if already added if not add
                double lat = 0;
                double lon = 0;
                double latdd = 0;
                double londd = 0;
                double dept = 0;
                double deptm = 0;
                String comm = "";


                // Attempts to load depth, latitude and longnitude and comments fields supplied by user
                try {
                    lat = Double.parseDouble(latitude.getText());
                    lon = Double.parseDouble(longnitude.getText());

                    //catch if realistic
                    if (depth.getText().isEmpty()) {
                        // Prompt user if depth has not been supplied
                        int opt = JOptionPane.showConfirmDialog(rootPane, "The depth has not been supplied would, you like to continue anyway?", "", JOptionPane.YES_NO_OPTION);

                        if (opt == 1) {
                            throw new Exception("Depth field not supplied, you choose not to continue");
                        }

                        dept = 0;
                    } else {
                        dept = Double.parseDouble(depth.getText());
                    }


                    latdd = minToDecd(latitude.getText());
                    londd = minToDecd(longnitude.getText());
                    londd = londd * -1;
                    comm = commen.getText();

                    //Catch if proper format
                    deptm = (dept * 1.8288);
                    if (latdd == -1 || londd == -1) {
                        throw new Exception("Latitude or Longnitude not properly formatted");
                    }

                    if (deptm < 0) {
                        throw new Exception("Unexceptable Depth entry");
                    }



                } catch (Exception e) {
                    cont = false;
                    jTextArea1.append("Got an error when trying to operate on position/depth values \n");
                    jTextArea1.append(e.getMessage());
                    jTextArea1.append("\n");
                    System.err.println("Wrong position/depth format");
                    System.err.println(e.getMessage());
                }


                int exis2 = 0; //exis2 set up to test if entry already exists in database

                boolean wriSamp = true; // wriSamp to tell whether or not to write the data to the sample table

                if (cont) {

                    /*         Class.forName(conname);
                    
                    Connection conn;
                    String url = dataurl;
                    if (localdata) {
                    conn = DriverManager.getConnection(url);
                    } else {
                    conn = DriverManager.getConnection(url, user, pass);
                    }
                    
                    Statement st = conn.createStatement();
                     */
                    toda = "SELECT COUNT(*) AS rowcount from sample where trip = \"" + res + "\" AND Lat_DDMM_MM = \"" + lat + "\" AND Long_DDMM_MM = \"" + lon + "\";";



                    conn = null;
                 
                    try {
                              Class.forName(driverName);
                        conn = DriverManager.getConnection(url, dbuser, pass);
       
                        Statement st = conn.createStatement();

                        ResultSet back = st.executeQuery(toda);

                        back.next();
                        exis2 = back.getInt("rowcount");

                        back.close();
st.close();
                    } catch (Exception e) {

                        jTextArea2.append("Got an error when attempting to check total crab tagged. \n");
                        jTextArea2.append(e.getMessage());
                        jTextArea2.append("\n");
                        System.err.println("Got an exception!");
                        System.err.println(e.getMessage());
                    } finally {

                        if (conn != null && !conn.isClosed()) {
                             conn.close();
                        }
                        
                    }
                    if (exis2 >= 1) {
                        toda = "SELECT Sample_id FROM sample where trip = \"" + res + "\" AND Lat_DDMM_MM = \"" + lat + "\" AND Long_DDMM_MM = \"" + lon + "\";";
                        conn = null;
                       
                        try {
                              Class.forName(driverName);
                            conn = DriverManager.getConnection(url, dbuser, pass);
           
                            Statement st = conn.createStatement();


                            ResultSet back = st.executeQuery(toda);

                            String sampl = back.getString(1);
                            Double rc = Double.parseDouble(sampl);

                            rowcount = rc.intValue();

                            back.close();
st.close();

                        } catch (Exception e) {

                            jTextArea2.append("Got an error when attempting to check total crab tagged. \n");
                            jTextArea2.append(e.getMessage());
                            jTextArea2.append("\n");
                            System.err.println("Got an exception!");
                            System.err.println(e.getMessage());
                        } finally {

                            if (conn != null && !conn.isClosed()) {
                                conn.close();
                            }
                            
                        }


                    }

                    // conn.close();


                    //If sample already exists prompt the use if the bio data should be added for this sample
                    if (exis2 >= 1) {

                        int opt = JOptionPane.showConfirmDialog(rootPane, "This sample already exists, would you like to add the biological data to this sample?", "", JOptionPane.YES_NO_OPTION);
                        if (opt == 1) {
                            cont = false;
                        }
                        wriSamp = false;
                    }

                }

                Object se = null; // null object to later reset the cells


                //loop through each row of of cells add data to dataase
                for (int i = 0; i < 100; i++) {

                    Object tag = null;
                    Object cara = null;
                    Object cla = null;
                    Object cond = null;
                    Object dur = null;
                    Object clu = null;
                    Object egg = null;
                    int itag = 0;
                    int icara = 0;
                    int icla = 0;
                    int icond = 0;
                    int idur = 0;
                    int iclu = 0;
                    int iegg = 0;
                    //loop through each cell in current row storing the data in the above declared variables
                    for (int j = 0; j <= 6; j++) {


                        if (j == 0) {
                            tag = tagBios.getModel().getValueAt(i, j);
                        }
                        if (j == 1) {
                            cara = tagBios.getModel().getValueAt(i, j);
                        }
                        if (j == 2) {
                            cla = tagBios.getModel().getValueAt(i, j);
                        }
                        if (j == 3) {
                            cond = tagBios.getModel().getValueAt(i, j);
                        }
                        if (j == 4) {
                            dur = tagBios.getModel().getValueAt(i, j);
                        }
                        if (j == 5) {
                            clu = tagBios.getModel().getValueAt(i, j);
                        }
                        if (j == 6) {
                            egg = tagBios.getModel().getValueAt(i, j);
                        }


                    }//end loop through each cell in current row. Data added

                    // Start of looking at only the rows that contain data
                    if (tag == null && cara == null && cla == null && cond == null && dur == null && clu == null && egg == null) {
                    } else {

                        try {
                            if (tag != null && !tag.equals("")) {
                                itag = Integer.parseInt((String) tag);
                            }
                            if (cara != null && !cara.equals("")) {
                                icara = Integer.parseInt((String) cara);
                            }
                            if (cla != null && !cla.equals("")) {
                                icla = Integer.parseInt((String) cla);
                            }
                            if (cond != null && !cond.equals("")) {
                                icond = Integer.parseInt((String) cond);
                            }
                            if (dur != null && !dur.equals("")) {
                                idur = Integer.parseInt((String) dur);
                            }
                            if (clu != null && !clu.equals("")) {
                                iclu = Integer.parseInt((String) clu);
                            }
                            if (egg != null && !egg.equals("")) {
                                iegg = Integer.parseInt((String) egg);
                            }
                        } catch (Exception e) {
                            cont = false;
                            jTextArea1.append("Got an error when trying to operate on biological data, are there any leters in the table? \n");
                            jTextArea1.append(e.getMessage());
                            jTextArea1.append("\n");
                            System.err.println("Got an exception!");
                            System.err.println(e.getMessage());

                        }

                        //Check the formatting of the data may want to add or refine conditions
                        try {
                            if (itag < 0 || icara < 0 || icla < 0 || icond < 0 || idur < 0 || iclu < 0 || iegg < 0) {
                                throw new Exception("Improper biological format, value < 0");
                            }
                            if (icara > 200 || icla > 80 || icond > 5 || idur > 100 || iclu > 4 || iegg > 4) {
                                throw new Exception("Improper biological format, value > accepted value");
                            }

                            if (icara > 150) {
                                int ans = JOptionPane.showConfirmDialog(rootPane, "There seems to be a unusually large carapace "
                                        + " width, Are you sure of the values?");
                                if (ans == 1 || ans == 2) {
                                    cont = false;
                                }
                            }
                            if (icara != 0 && icara < 50) {
                                int ans = JOptionPane.showConfirmDialog(rootPane, "There seems to be a unusually small carapace "
                                        + " width, Are you sure of the values?");
                                if (ans == 1 || ans == 2) {
                                    cont = false;
                                }
                            }

                            //   if (icara / 7 > icla) {
                            //      int ans = JOptionPane.showConfirmDialog(rootPane, "There seems to be an unusually small claw "
                            //             + " height at row " + Integer.toString(i+1) + ", Are you sure of the values?");
                            //    if (ans == 1 || ans == 2) {
                            //       cont = false;
                            //  }
                            // }

                            if (icla != 0 && icla > 40) {
                                int ans = JOptionPane.showConfirmDialog(rootPane, "There seems to be an unusually large claw "
                                        + " height at row " + Integer.toString(i + 1) + ", Are you sure of the values?");
                                if (ans == 1 || ans == 2) {
                                    cont = false;
                                }
                            }

                            if (idur != 0 && idur < 50) {
                                int ans = JOptionPane.showConfirmDialog(rootPane, "There seems to be an unusually low "
                                        + " durometer reading at " + Integer.toString(i + 1) + ", Are you sure of the values?");
                                if (ans == 1 || ans == 2) {
                                    cont = false;
                                }
                            }
                            //  if (icla != 0 && icla < 5) {
                            //     int ans = JOptionPane.showConfirmDialog(rootPane, "There seems to be an unusually small claw "
                            //             + "height at row " + Integer.toString(i+1) + ", Are you sure of the values?");
                            //     if (ans == 1 || ans == 2) {
                            //          cont = false;
                            //      }
                            //  }
                            if (latdd < 42.0000 || latdd > 47.0000) {
                                int ans = JOptionPane.showConfirmDialog(rootPane, "Latitude seems to be out of bounds "
                                        + " (between 4200.00 and 4700.00). Are you sure of the values?");
                                if (ans == 1 || ans == 2) {
                                    cont = false;
                                }
                            }


                        } catch (Exception e) {
                            cont = false;
                            jTextArea1.append("Got an error when trying to operate on biological data \n");
                            jTextArea1.append(e.getMessage());
                            jTextArea1.append("\n");
                            System.err.println("Got an exception!");
                            System.err.println(e.getMessage());
                        }//end of check for conditions


                        //A check to make sure that this tag has not already been entered
                        if (itag != 0 && cont) {


                            /*       Class.forName(conname);
                            
                            Connection conn;
                            String url = dataurl;
                            if (localdata) {
                            conn = DriverManager.getConnection(url);
                            } else {
                            conn = DriverManager.getConnection(url, user, pass);
                            }
                            Statement st = conn.createStatement();
                             */
                            toda = "SELECT COUNT(*) AS rowcount from bio where tag_id = \"" + itag + "\";";
                            conn = null;
                           
                            try {
                                  Class.forName(driverName);
                                conn = DriverManager.getConnection(url, dbuser, pass);
              
                                Statement st = conn.createStatement();



                                ResultSet back = st.executeQuery(toda);

                                back.next();
                                exis2 = back.getInt("rowcount");

                                back.close();
st.close();

                                //                      conn.close();
                            } catch (Exception e) {
                                cont = false;
                                jTextArea1.append("Got an error when attempting to check if a tag number has already been added to the bio table. \n");
                                jTextArea1.append(e.getMessage());
                                jTextArea1.append("\n");
                                System.err.println("Got an exception!");
                                System.err.println(e.getMessage());
                            } finally {

                                if (conn != null && !conn.isClosed()) {
                                    conn.close();
                                }
                            
                            }


                            try {
                                if (exis2 >= 1) {
                                    throw new Exception("This tag already exists in the bio table");
                                }
                            } catch (Exception e) {
                                cont = false;
                                jTextArea1.append("Got an error when attempting to check if a tag number has already been added to the bio table. \n");
                                jTextArea1.append(e.getMessage());
                                jTextArea1.append("\n");
                                System.err.println("Got an exception!");
                                System.err.println(e.getMessage());
                            }


                            String toadd = "INSERT INTO bio VALUES( \"" + rowcount + "\" , \"" + itag + "\" , \"" + icara + "\" , \"" + icla + "\" , \"" + icond + "\" , \"" + idur + "\");";
                            bioadd.add(toadd);




                        }//end check for tag entered

                    }//end of looking at row that contain data


                }//end of loop through each row of cells


                //start of adding data to database

                if (cont) {
                    try {
                        /*  Class.forName(conname);
                        
                        Connection conn;
                        String url = dataurl;
                        if (localdata) {
                        conn = DriverManager.getConnection(url);
                        } else {
                        conn = DriverManager.getConnection(url, user, pass);
                        }
                         */

                        if (wriSamp) {
                            String toadd = "INSERT INTO sample VALUES( \"" + rowcount + "\" , \"" + res + "\" , \"" + lat + "\" , \"" + lon + "\" , \"" + latdd + "\" , \"" + londd + "\" , \"" + dept + "\" , \"" + comm + "\");";
                            bioadd.add(toadd);
                        }


                        while (!bioadd.isEmpty()) {
                            //  Statement bat = conn.createStatement();
                            String a = bioadd.pop().toString();
                            insertmysqlssh(a);
                            // bat.close();  
                            //bat.addBatch(a);

                        }

                        //bat.executeBatch();
                        //bat.close();
                        //   conn.close();
                        jTextArea1.append("New entry added to sample table\n");
                        jTextArea1.append("New entry/entries added to bio table\n");
                    } catch (Exception e) {
                        cont = false;
                        jTextArea1.append("Got an error when attempting to write bio and sample tables. \n");
                        jTextArea1.append(e.getMessage());
                        jTextArea1.append("\n");
                        System.err.println("problem When adding to bio and sample table");
                        System.err.println(e.getMessage());
                    }
                }

                //reset the fields for further entries
                if (cont) {
                    latitude.setText("");
                    longnitude.setText("");
                    depth.setText("");
                    commen.setText("");


                    int co = 0;

                    for (int i = 0; i < 100; i++) {

                        if (tagBios.getValueAt(i, 0) != null) {
                            if (!tagBios.getValueAt(i, 0).toString().equals("")) {
                                if (Integer.parseInt(tagBios.getValueAt(i, 0).toString()) > co) {

                                    co = Integer.parseInt(tagBios.getValueAt(i, 0).toString());
                                }
                            }
                        }
                        for (int j = 0; j <= 6; j++) {

                            tagBios.setValueAt(se, i, j);
                        }


                    }
                    tagBios.editCellAt(1, 0);
                    tagBios.setValueAt(se, 0, 0);
                    tagBios.editCellAt(0, 1);
                    carryover = co + 1;

                    tagBios.setValueAt((Object) carryover, 0, 0);


                }
            }
        } catch (SQLException e) {
        }

    }//GEN-LAST:event_ButtonEnterTagDataActionPerformed

    private void tagBiosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tagBiosKeyPressed
 
        String colna = "Carapace Cond";
        String cluf = "Clutch";
        String egco = "Egg Color";
        String tcol = "Tag Num";

        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_SHIFT && colna.equals(tagBios.getColumnName(tagBios.getSelectedColumn()))) {
            jPop.show(evt.getComponent(), tagBios.convertColumnIndexToModel(tagBios.getSelectedColumn()), tagBios.convertRowIndexToModel(tagBios.getSelectedRow()));
        }

        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_SHIFT && cluf.equals(tagBios.getColumnName(tagBios.getSelectedColumn()))) {
            jPop2.show(evt.getComponent(), tagBios.convertColumnIndexToModel(tagBios.getSelectedColumn()), tagBios.convertRowIndexToModel(tagBios.getSelectedRow()));
        }

        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_SHIFT && egco.equals(tagBios.getColumnName(tagBios.getSelectedColumn()))) {
            jPop3.show(evt.getComponent(), tagBios.convertColumnIndexToModel(tagBios.getSelectedColumn()), tagBios.convertRowIndexToModel(tagBios.getSelectedRow()));
        }

        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN && tcol.equals(tagBios.getColumnName(tagBios.getSelectedColumn()))) {


            int x = tagBios.getSelectedRow();
            int y = tagBios.getSelectedColumn();
            tagBios.editCellAt(x + 1, y);
            tagBios.editCellAt(x, y);
            if (tagBios.getValueAt(x, y) != null && !tagBios.getValueAt(x, y).equals("")) {
                int z = Integer.parseInt(tagBios.getValueAt(x, y).toString()) + 1;

                String zs = Integer.toString(z);

                tagBios.setValueAt(zs, x + 1, y);
            }
    }//GEN-LAST:event_tagBiosKeyPressed
    }
    
    private void tagBiosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tagBiosMouseReleased
   showPopup(evt);
    }//GEN-LAST:event_tagBiosMouseReleased

    private void tagBiosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tagBiosMousePressed
   showPopup(evt);
    }//GEN-LAST:event_tagBiosMousePressed

    private void tcapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcapActionPerformed

    }//GEN-LAST:event_tcapActionPerformed

    private void daySampledKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_daySampledKeyTyped
     daySampled.setText(daySampled.getText().replaceAll("dd/mm/yyyy", ""));
        daySampled.setForeground(java.awt.Color.BLACK);
    }//GEN-LAST:event_daySampledKeyTyped

    private void daySampledFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_daySampledFocusLost
  
        if (daySampled.getText().isEmpty()) {
            daySampled.setForeground(new java.awt.Color(153, 153, 153));
            daySampled.setText("dd/mm/yyyy");
        }
    }//GEN-LAST:event_daySampledFocusLost

    private void jCo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCo1ActionPerformed

    }//GEN-LAST:event_jCo1ActionPerformed

    private void jCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCoActionPerformed

 
    }//GEN-LAST:event_jCoActionPerformed

    private void ButtonEnterCapDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEnterCapDataActionPerformed
try{
        boolean cont = true; //keeps track of whether data should be written
        boolean writepeo = true; //Keeps track of whether person data should be written in the person table

        //If no database has been suppplied
        if (dataurl.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please locate the Tagging database in the file menu, set database");
            cont = false;
            writepeo = false;
        }
        //Variables to store capture data
        String dat = daycap.getText();
        String tag = tagcap.getText();
        String lat = latcap.getText();
        String lon = longcap.getText();
        String dep = deptcap.getText();
        String captain = capt.getText();
        String ves = vess.getText();
     
         if (!nens.isSelected() && !sens.isSelected() && !fourx.isSelected() && !gulf.isSelected()) {
            cont = false;
            JOptionPane.showMessageDialog(rootPane, "You must choose a stats area.");
        }
String sa = "";
        //Store stats area
        if (sens.isSelected()) {
            sa = "SENS";
        }
        if (nens.isSelected()) {
            sa = "NENS";
        }
        if (gulf.isSelected()) {
            sa = "GULF";
        }
       if (fourx.isSelected()) {
            sa = "4X";
        }

        
       
        String per = jCo.getEditor().getItem().toString();
        String comme = comment.getText();
        double dlon = 0;
        double dlat = 0;
        double latd = 0;
        double lond = 0;
        double ddep = 0;
        
        int tagint = 0;
        int tagcod = 3;

String perb = "";

if(!jCo1.getEditor().getItem().toString().isEmpty())perb = jCo1.getEditor().getItem().toString();

        //If no return type selected
        if (!ret.isSelected() && !rel.isSelected() && !unk.isSelected()) {
            cont = false;
            JOptionPane.showMessageDialog(rootPane, "You must choose either released, retained or unknown.");
        }

        //Store return type
        if (ret.isSelected()) {
            tagcod = 2;
        }
        if (rel.isSelected()) {
            tagcod = 1;
        }
        if (unk.isSelected()) {
            tagcod = 3;
        }

        //Check if user want to continue even if certian data fields are empty
        if (per.isEmpty()) {
            int opt = JOptionPane.showConfirmDialog(rootPane, "The person field has not been supplied would, you like to continue anyway?", "", JOptionPane.YES_NO_OPTION);
            per = "unknown";

            if (opt == 1) {
                cont = false;
            }
        }
        if (captain.isEmpty()) {
            int opt = JOptionPane.showConfirmDialog(rootPane, "The captain field has not been supplied would, you like to continue anyway?", "", JOptionPane.YES_NO_OPTION);
            captain = "";

            if (opt == 1) {
                cont = false;
            }
        }
        if (ves.isEmpty()) {
            int opt = JOptionPane.showConfirmDialog(rootPane, "The vessel field has not been supplied would, you like to continue anyway?", "", JOptionPane.YES_NO_OPTION);
            ves = "";

            if (opt == 1) {
                cont = false;
            }
        }

        //Date format check
        if (!isdatProperCap(dat)) {
            int opt = JOptionPane.showConfirmDialog(rootPane, "The Date is not properly formatted or has not been supplied would, you like to continue anyway?", "", JOptionPane.YES_NO_OPTION);

            if (opt == 1) {
                cont = false;
            } else {
                dat = "unknown";
            }
        }

        //Convert dates
        latd = minToDecdCap(lat);
        lond = minToDecdCap(lon);
        lond = lond * -1;
        if (latd == -1 || lond == -1) {
            int opt = JOptionPane.showConfirmDialog(rootPane, "One or both positions are not properly formatted or have not been supplied, would you like to continue anyway?", "", JOptionPane.YES_NO_OPTION);

            if (opt == 1) {
                cont = false;
            } else {
                lat = "0";
                lon = "0";
                latd = 0;
                lond = 0;
            }

        }

         Polygon tt = getArea("23");
         Polygon tf = getArea("24");
        
         String subarea = "";
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
                Coordinate c = new Coordinate(latd, lond);
                Point p = geometryFactory.createPoint(c);
                if (tt.covers(p)) {
                    subarea = "23";

                }

                if (tf.covers(p)) {
                    subarea = "24";
                }

        //Check tag format
        try {
            tagint = Integer.parseInt(tag);

        } catch (Exception e) {
            cont = false;
            jTextArea2.append("Got an tag format error");
            jTextArea2.append(e.getMessage());
            jTextArea2.append("\n");
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());

        }
        //Check depth format
        try {
            ddep = Double.parseDouble(dep);

        } catch (Exception e) {
            int opt = JOptionPane.showConfirmDialog(rootPane, "The depth field is not properly formatted or has not been supplied, would you like to continue anyway?", "", JOptionPane.YES_NO_OPTION);

            if (opt == 1) {
                cont = false;
            } else {
                ddep = 0;
            }

        }

       // depmet = ddep * 1.8288; //fathoms to meters

        try {
            dlon = Double.parseDouble(lon);
            dlat = Double.parseDouble(lat);

        } catch (Exception e) {
            cont = false;
            jTextArea2.append("Got an position format error");
            jTextArea2.append(e.getMessage());
            jTextArea2.append("\n");
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());


        }
        //check if tag exists in bio table, print if it does not.
        int exist = 0;
        if (tagint != 0 && cont) {

            
               /*      Class.forName(conname);
                
                Connection conn;
                   String url = dataurl;
                 if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, user, pass);
            }
          
                Statement st = conn.createStatement();
*/
                String toda = "SELECT COUNT(*) AS rowcount from bio where tag_id = " + tagint + ";";

        Connection conn = null;
  
        try{
               Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            Statement st = conn.createStatement();
     
                ResultSet back = st.executeQuery(toda);

                back.next();
                exist = back.getInt("rowcount");

                back.close();
                st.close();
  //              conn.close();
            } catch (Exception e) {
                cont = false;
                jTextArea2.append("Got an error when attempting to check if a tag number is in fact in bio table. \n");
                jTextArea2.append(e.getMessage());
                jTextArea2.append("\n");
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }finally {

                                if (conn != null && !conn.isClosed()) {
                                    conn.close();
                                }
                                
                            }

            try {
                if (exist == 0) {
                    throw new Exception("This tag does not yet exist in the bio table. Please add or check entered tag validity ");
                }
            } catch (Exception e) {
                cont = false;
                jTextArea2.append("Got an tag number error. \n");
                jTextArea2.append(e.getMessage());
                jTextArea2.append("\n");
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }

        }//end check if tag has been added to bio table


    


        //Variables to store person data
        String address = addcap.getText();
        String tow = town.getText();
        String postcod = post.getText();
        String phonea = phoa.getText();
        String phoneb = phob.getText();
        String emai = email.getText();
        String pro = prov.getText();

        //if(per.equals("unknown")) writepeo = false;
        //Check if address is supplied, ask if user want to continue without address
        if (address.isEmpty()) {
            int opt = JOptionPane.showConfirmDialog(rootPane, "The address field has not been supplied, would you like to continue anyway?", "", JOptionPane.YES_NO_OPTION);
            address = "unknown";
            if (opt == 1) {
                writepeo = false;
            }

        }

        //If both person and address is unknown we do not want to enter them into the database
        if (per.equals("unknown") && address.equals("unknown")) {
            writepeo = false;
        }

         //Check person fields where address equals address ask if person should equal this
        if (!address.equals("unknown") && per.equals("unknown") && writepeo) {
           

            
              /*      Class.forName(conname);
                
                Connection conn;
                   String url = dataurl;
                 if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, user, pass);
            }
                Statement st = conn.createStatement();
*/
                String toda = "SELECT name FROM people where civic = \"" + address + "\";";

                    Connection conn = null;
       
        try{
           Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            Statement st = conn.createStatement();
    
                
                ResultSet back = st.executeQuery(toda);
                while (back.next()) {
                    String temp = back.getString(1);
                    if(!temp.equals(per)){
                    int opt = JOptionPane.showConfirmDialog(rootPane, "Found the name " + temp + " that matches this address, replace " + per + " with this found name?", "", JOptionPane.YES_NO_OPTION);

                    if (opt != 1) {
                        per = temp;
                    }
                    }

                }
st.close();
                back.close();
               // conn.close();
            } catch (Exception e) {
                writepeo = false;
                jTextArea2.append("Got an error when attempting to check for address matches in the person table. \n");
                jTextArea2.append(e.getMessage());
                jTextArea2.append("\n");
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }finally {

                                if (conn != null && !conn.isClosed()) {
                                    conn.close();
                                }
                               
                            }


        }//end check for person where address equals address

        //If person is known and we want to write person data enter into more checks before adding
        if(per.equals("unknown")) jTextArea2.append("Cannot add unknown people. Manually insert if needed. \n");
        if (!per.equals("unknown") && writepeo) {
            //Check if person is already in database, ask if an overwrite is nessesary
            boolean update = false;
            boolean insert = true;
            boolean changename = false;
            
            /*         Class.forName(conname);
                
                Connection conn;
                   String url = dataurl;
                 if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, user, pass);
            }
                Statement st = conn.createStatement();
*/
                String toda = "SELECT * FROM people where name = \"" + per + "\";";
    Connection conn = null;
     
        try{
           Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
             
            Statement st = conn.createStatement();
    
                ResultSet back = st.executeQuery(toda);
                int count = 0;
                //Enter while loop if person/persons exists with same name
                while (back.next()) {
                    count++;
                   
                  

                    int op = JOptionPane.showConfirmDialog(rootPane, "Found a name that matches this person with civic address of "+ back.getString("civic") +". Overwrite previous person with new data?", "", JOptionPane.YES_NO_OPTION);
                    if (op == 1) {
                        update = false;
                        insert = true;
                    } else {
                        update = true;
                        insert = false;
                    }
                    
                    if(update){
                    if (back.getString("town") != null && !back.getString("town").equals(tow)) {
                        int opt = JOptionPane.showConfirmDialog(rootPane, "Previous town entry was '" + back.getString("town") + "' would you like to replace with '" + tow + "'? (Ansering no will keep old value and ignore new one)", "", JOptionPane.YES_NO_OPTION);
                        if (opt == 1) {
                            tow = back.getString("town");
                        }
                    }
                    if (back.getString("prov") != null && !back.getString("prov").equals(prov.getText())) {
                        int opt = JOptionPane.showConfirmDialog(rootPane, "Previous province entry was '" + back.getString("prov") + "' would you like to replace with '" + prov.getText() + "'? (Ansering no will keep old value and ignore new one)", "", JOptionPane.YES_NO_OPTION);
                        if (opt == 1) {
                            pro = back.getString("prov");
                        }
                    }
                    if (back.getString("post") != null && !back.getString("post").equals(postcod)) {
                        int opt = JOptionPane.showConfirmDialog(rootPane, "Previous postal code entry was '" + back.getString("post") + "' would you like to replace with '" + postcod + "'? (Ansering no will keep old value and ignore new one)", "", JOptionPane.YES_NO_OPTION);
                        if (opt == 1) {
                            postcod = back.getString("post");
                        }
                    }
                    if (back.getString("pho1") != null && !back.getString("pho1").equals(phonea)) {
                        int opt = JOptionPane.showConfirmDialog(rootPane, "Previous phone1 entry was '" + back.getString("pho1") + "' would you like to replace with '" + phonea + "'? (Ansering no will keep old value and ignore new one)", "", JOptionPane.YES_NO_OPTION);
                        if (opt == 1) {
                            phonea = back.getString("pho1");
                        }
                    }
                     if (back.getString("pho2") != null && !back.getString("pho2").equals(phoneb)) {
                        int opt = JOptionPane.showConfirmDialog(rootPane, "Previous phone2 entry was '" + back.getString("pho2") + "' would you like to replace with '" + phoneb + "'? (Ansering no will keep old value and ignore new one)", "", JOptionPane.YES_NO_OPTION);
                        if (opt == 1) {
                            phonea = back.getString("pho2");
                        }
                    }
                    if (back.getString("email") != null && !back.getString("email").equals(emai)) {
                        int opt = JOptionPane.showConfirmDialog(rootPane, "Previous email entry '" + back.getString("email") + "' would you like to replace with '" + emai + "'? (Ansering no will keep old value and ignore new one)", "", JOptionPane.YES_NO_OPTION);
                        if (opt == 1) {
                            emai = back.getString("email");
                        }
                    }
                    }
                    else{
                         int opt = JOptionPane.showConfirmDialog(rootPane, "You have choosen not to update the record, Are there two people with the same name?", "", JOptionPane.YES_NO_OPTION);
                    //no
                         if (opt == 1) {
                       //Append a @town to their name and update their previous capture entry
                                System.out.println("Do not want to add any person data");
                              update = false;
                        insert = false;
                        
                       
                    }
                      //yes
                    else{
                       System.out.println("Want to add person @ town");
                             insert = true;
                    
                        per = per.concat("@").concat(tow);
                      update = false;     
                        
                    }  
                    }

                }

if(count>1){
     jTextArea2.append("There may be some duplicates in the database that have not been updated please check the database. \n");
}
st.close();
                back.close();
                //conn.close();
            } catch (Exception e) {
                writepeo = false;
                jTextArea2.append("Got an error when attempting to check for matches in the person table. \n");
                jTextArea2.append(e.getMessage());
                jTextArea2.append("\n");
                System.err.println("Got an exception!! !!!");
                System.err.println(e.getMessage());
            }finally {

                                if (conn != null && !conn.isClosed()) {
                                    conn.close();
                                }
                              
                            }


            //Overwrite person data to database if an overwrite was inticated
            if (update && writepeo) {
             
                try {
                  /*       Class.forName(conname);
                
                Connection conn;
                   String url = dataurl;
                 if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, user, pass);
            }
                    Statement st = conn.createStatement();

                  */
                    toda = "UPDATE people SET name = \"" + per + "\", civic = \"" + address + "\" , town = \"" + tow + "\" , prov = \"" + pro + "\" , post = \"" + postcod + "\" , pho1 = \"" + phonea + "\" , pho2 = \"" + phoneb + "\" , country = Canada " + "\" , email = \"" + emai + "\" WHERE name = \"" + per + "\";";
                    
                    insertmysqlssh(toda);

                    //conn.close();
                    jTextArea2.append("Updated entry in the people table\n");
                } catch (Exception e) {
                    cont = false;
                    jTextArea2.append("Got an error when trying to update the people table \n");
                    jTextArea2.append(e.getMessage());
                    jTextArea2.append("\n");
                    System.err.println("Got an exception!");
                    System.err.println(e.getMessage());
                }

             
            } 
                //Write new person entry if overwrite was not intended
                if (writepeo && insert) {
                    try {
                      /*      Class.forName(conname);
                
                Connection conn;
                   String url = dataurl;
                 if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, user, pass);
            }
                        Statement st = conn.createStatement();
*/
                        toda = "INSERT INTO people VALUES( \"" + per + "\" , \"" + address + "\" , \"" + tow + "\" , \"" + pro + "\", \"" + postcod + "\" , \"" + emai + "\" , \"" + phonea + "\" , \"" + phoneb + "\", \"Canada\");";

                        insertmysqlssh(toda);


                        //conn.close();
                        jTextArea2.append("New entry added to people table\n");
                    } catch (Exception e) {
                        cont = false;
                        jTextArea2.append("Got an error when trying to write to the people table \n");
                        jTextArea2.append(e.getMessage());
                        jTextArea2.append("\n");
                        System.err.println("Got an exception!");
                        System.err.println(e.getMessage());
                    }


                }//end write new entry to database

            //Insert data on above conditions
        if (cont) {
            try {
                   /*  Class.forName(conname);
                
                Connection conn;
                   String url = dataurl;
                 if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, user, pass);
            }
                Statement st = conn.createStatement();
            
                 */
                String isyear = "";
                if(!dat.matches("unknown"))
                    isyear = dat.split("/")[2];
                  if(sa.equals("4X")){
                    String monf = dat.split("/")[1];
                    int monfi = Integer.parseInt(monf);
                    if(monfi > 7) isyear = Integer.toString(Integer.parseInt(monf)+1);
                    }
                
                
               // else
                 //   isyear = Integer.toString(java.util.Calendar.getInstance().get(Calendar.YEAR));
      String suba = "";
                  if(sa == "NENS") suba =  "(all)(ens)(nens)(nens_gulf)(allandgulf)";
                  if(sa == "SENS"){
                      if(subarea == "23") suba = "(cfa23)(all)(ens)(sens)(allandgulf)(cfa23zoom)(all.holes)";
                      if(subarea == "24") suba = "(cfa24)(all)(ens)(sens)(allandgulf)(cfa24zoom)(all.holes)";
                  }
                  if(sa == "4X") suba =  "(all)(ens)(cfa4x)(allandgulf)";
                  
                toda = "INSERT INTO capture VALUES( \"" + tagint + "\" , \"" + dat + "\" , \"" + per + "\" , \"" + perb + "\", \"" + dlat + "\" , \"" + dlon + "\" , \"" + latd + "\" , \"" + lond + "\" , \"" + ddep + "\" , \"" + tagcod + "\" , \"" + comme + "\" , \"" + captain + "\" , \"" + ves + "\" ,\"" + isyear  + "\" ,\"" + sa  + "\" ,\"" + carap.getText()  + "\" , \"" + "N"  + "\" ,\"" + suba + "\");";

                insertmysqlssh(toda);

                //conn.close();
                jTextArea2.append("New entry added to Capture table\n");
            } catch (Exception e) {
                cont = false;
                jTextArea2.append("Got an error when trying to write to the capture table \n");
                jTextArea2.append(e.getMessage());
                jTextArea2.append("\n");
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }
        }//end insert data

        }
} catch (Exception e) {

            System.err.println("Got an exception!");
            e.printStackTrace();

        }

    }//GEN-LAST:event_ButtonEnterCapDataActionPerformed

    private void RewardsMarkAsRewardedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RewardsMarkAsRewardedActionPerformed
        // TODO add your handling code here:
        
        try {
             //Class.forName(conname);
             //String url = dataurl;
             //Connection conn;
             //if (localdata) {
             //    conn = DriverManager.getConnection(url); 
            //} else { 
            //    conn = DriverManager.getConnection(url, user, pass); 
           //  }             
             //Statement st = conn.createStatement();   
             
          String toda = "";
             String forarea = "allareas";
        if(tagFrame.axxxx.isSelected())
        forarea = "4X";
        if(tagFrame.as.isSelected())
        forarea = "SENS";
        if(tagFrame.an.isSelected())
        forarea = "NENS";
        
        if(forarea.equals("allareas"))
            if(yearfld.getText().equals("")) toda = "UPDATE capture set rewarded = 'Y' where rewarded != 'NA';"; 
            else toda = "UPDATE capture set rewarded = 'Y' where year = '" + yearfld.getText() + "';"; 
        else{
             if(yearfld.getText().equals("")) toda = "UPDATE capture set rewarded = 'Y' whererewarded != 'NA' and statsarea = '"+ forarea +"';";
             else toda = "UPDATE capture set rewarded = 'Y' where year = '" + yearfld.getText() + "' and statsarea = '"+ forarea +"';";
        
        } 
            insertmysqlssh(toda);
            // st.executeUpdate(toda);
             //conn.close(); 
        } catch (Exception e) { 
            System.err.println(e.getMessage()); 
            
 }
   
    }//GEN-LAST:event_RewardsMarkAsRewardedActionPerformed

    
    public static Polygon getArea(String a){
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
               labelcoords = new Coordinate[7];
              c = new Coordinate(43, -68);
          labelcoords[0] = c;
          
              c = new Coordinate(40.5,-63.333333 );
          labelcoords[1] = c;
           c = new Coordinate(42.613790, -63.333333);
          labelcoords[2] = c;
           c = new Coordinate(44.332904,-63.333384 );
          labelcoords[3] = c;
           c = new Coordinate(44.502358, -63.502420);
          labelcoords[4] = c;
           c = new Coordinate(45, -64);
          labelcoords[5] = c;
           
              c = new Coordinate(43, -68);
          labelcoords[6] = c;
          
          

     }

   GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory( null );
           //  LineString label = geometryFactory.createLineString(labelcoords);
   LinearRing ring = geometryFactory.createLinearRing( labelcoords );          
   Polygon polygon = geometryFactory.createPolygon(ring, null );   
   return polygon;
}

public static Polygon getAreaatl(String a){
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
          /**
          POINT    NORTH LATITUDE   WEST LONGITUDE 
1)       46º 00' 00"N  59º 50' 57"W 
2)       46º 00' 00"N  57º 46' 57"W 
3)       43º 24' 00"N  54º 47' 57"W  THENCE ALONG THE BOUNDARY OF THE CANADIAN 200 MILE LIMIT 
TO 
4)       41º 05' 01"N  57º 29' 56"W 
5)       45º 37' 00"N  60º 30' 57"W 
6)       46º 00' 00"N  59º 50' 57"W 
2. FISHING IS ONLY AUTHORIZED IN THOSE WATERS OF CRAB FISHING AREA 23 EAST OF A RHUMB LINE 
(SIMILAR TO STRAIGHT LINES PLOTTED ON A NAUTICAL CHART)  FORMED BY THE JOINING THE FOLLOWING 
POINTS BELOW: 
NOTE: WHEN THE GEOGRAPHIC BOUNDARY OF AN AREA IS EXPRESSED IN LATITUDE AND LONGITUDE, THOSE 
POINT REFERENCES ARE BASED ON THE GEODESIC SYSTEM NORTH AMERICAN DATUM 1983 (NAD83). 
POINT NORTH LATITUDE WEST LONGITUDE 
1. 46° 00'  00"N 58° 24'  56"W 
2. 44° 42'  52"N 59° 54'  58"W
           */
           labelcoords = new Coordinate[15];
          c = new Coordinate(46, -59.84916666666666666666666666666667); //0.84916666666666666666666666666667
          labelcoords[0] = c;
           c = new Coordinate(46, -57.7825); //.7825
          labelcoords[1] = c;
           c = new Coordinate(43.4, -57.79916666666666666666666666666667); //0.79916666666666666666666666666667
          labelcoords[2] = c;
          
          //Add Along 200mile
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
           c = new Coordinate(45.6166666666666666666666666666667, -60.51583333333333333333);
          labelcoords[12] = c;
           c = new Coordinate(45.900000, -60.3);
          labelcoords[13] = c;
           c = new Coordinate(46.000000, -59.84916666666666666666666666666667);
          labelcoords[14] = c;

     }
            if(a.equals("24")){
                 labelcoords = new Coordinate[20];
          c = new Coordinate(45.6166666666666666666666666666667, -60.51583333333333333333); //NE corner
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
           labelcoords = new Coordinate[7];
              c = new Coordinate(43, -68);
          labelcoords[0] = c;
          
              c = new Coordinate(40.5,-63.333333 );
          labelcoords[1] = c;
           c = new Coordinate(42.613790, -63.333333);
          labelcoords[2] = c;
           c = new Coordinate(44.332904,-63.333384 );
          labelcoords[3] = c;
           c = new Coordinate(44.502358, -63.502420);
          labelcoords[4] = c;
           c = new Coordinate(45, -64);
          labelcoords[5] = c;
           
              c = new Coordinate(43, -68);
          labelcoords[6] = c;
          
          

     }


 


   GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory( null );
           //  LineString label = geometryFactory.createLineString(labelcoords);
   LinearRing ring = geometryFactory.createLinearRing( labelcoords );          
   Polygon polygon = geometryFactory.createPolygon(ring, null );   
   return polygon;
}
    
    
    private void ButtonMarkCapCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMarkCapCreateActionPerformed
        // TODO add your handling code here:
        String area = "";
        if(nens2.isSelected()){
            area = "NENS";
        }
        if(sens2.isSelected()){
            area = "SENS";
        }
        if(xxxx2.isSelected()){
            area = "4X";
        }
           if(gulfarea.isSelected()){
            area = "GULF";
        }
        if(allareas2.isSelected()){
            area = "allareas";
        }
          if(ensarea.isSelected()){
            area = "allens";
        }

        String toda = "";
        if(area.equals("allareas")){
            toda = "Select * from "
                    + "(SELECT  bio.tag_id, bio.sample_num, bio.cc, str_to_date( capture.date, '%d/%m/%Y' ) date, capture.lat_DD_DDDD, capture.long_DD_DDDD, capture.year, capture.tagcode "
                    + "from capture join bio where bio.tag_id = capture.tag) t1 "
                    + "JOIN (SELECT trip.trip_id, trip.captain, str_to_date( trip.date, '%d/%m/%Y' ) date, sample.lat_DD_DDDD, sample.long_DD_DDDD, sample.sample_id "
                    + "from trip join sample where sample.trip = trip.trip_id )t2 "
                    + "ON t1.sample_num = t2.sample_id "
                    + "where t2.date < str_to_date(\""+"01/01/".concat(toyear.getText().trim())+"\", '%d/%m/%Y'  ) and t2.date >  str_to_date(\""+"01/01/".concat(fromyear.getText().trim())+"\", '%d/%m/%Y'  ) "
                    + "ORDER BY captain, trip_id, tag_id, t1.date;";
        }
        else if(area.equals("allens")){
       toda = "Select * from (SELECT  bio.tag_id, bio.cc, bio.sample_num, str_to_date( capture.date, '%d/%m/%Y' ) date, capture.lat_DD_DDDD, capture.long_DD_DDDD, capture.year, capture.tagcode "
                    + "from capture join bio where bio.tag_id = capture.tag) t1 "
                    + "JOIN (SELECT trip.trip_id, trip.statsarea as sa,  trip.captain, str_to_date( trip.date, '%d/%m/%Y' ) date, sample.lat_DD_DDDD, sample.long_DD_DDDD, sample.sample_id "
                    + "from trip join sample where sample.trip = trip.trip_id )t2 "
                    + "ON t1.sample_num = t2.sample_id "
                    + "where t2.date < str_to_date(\""+"01/01/".concat(toyear.getText().trim())+"\", '%d/%m/%Y'  ) and t2.date >  str_to_date(\""+"01/01/".concat(fromyear.getText().trim())+"\", '%d/%m/%Y'  ) and t2.sa != "+ area +" "
                    + " ORDER BY captain, trip_id, tag_id, t1.date;";
        }
        else{
            toda = "Select * from (SELECT  bio.tag_id, bio.cc, bio.sample_num, str_to_date( capture.date, '%d/%m/%Y' ) date, capture.lat_DD_DDDD, capture.long_DD_DDDD, capture.year, capture.tagcode "
                    + "from capture join bio where bio.tag_id = capture.tag) t1 "
                    + "JOIN (SELECT trip.trip_id, trip.statsarea as sa,  trip.captain, str_to_date( trip.date, '%d/%m/%Y' ) date, sample.lat_DD_DDDD, sample.long_DD_DDDD, sample.sample_id "
                    + "from trip join sample where sample.trip = trip.trip_id )t2 "
                    + "ON t1.sample_num = t2.sample_id "
                    + "where t2.date < str_to_date(\""+"01/01/".concat(toyear.getText().trim())+"\", '%d/%m/%Y'  ) and t2.date >  str_to_date(\""+"01/01/".concat(fromyear.getText().trim())+"\", '%d/%m/%Y'  ) and t2.sa = "+ area +" "
                    + " ORDER BY captain, trip_id, tag_id, t1.date;";
        }
   
        if(!area.equals("")){
            try{
            MCdata.createfromstatement(toda);
            }catch(Exception e){}
            
            }
        
        
    }//GEN-LAST:event_ButtonMarkCapCreateActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

         if(session !=null && session.isConnected()){
                System.out.println("Closing SSH Connection");
                session.disconnect();
            }
        
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
    System.out.println(arg4);
        if(arg4 == "reward"){
                  jTabbedPane1.setSelectedIndex(1);
        }
        if(arg4 == "enter"){
                  jTabbedPane1.setSelectedIndex(0);
        }
    }//GEN-LAST:event_formComponentShown

    
    
    
    
    
    private void showPopup(java.awt.event.MouseEvent e) {
        String colna = "Carapace Cond";
        String cluf = "Clutch";
        String egco = "Egg Color";
        if (e.isPopupTrigger() && colna.equals(tagBios.getColumnName(tagBios.getSelectedColumn()))) {
            jPop.show(e.getComponent(), e.getX(), e.getY());
        }
        if (e.isPopupTrigger() && cluf.equals(tagBios.getColumnName(tagBios.getSelectedColumn()))) {
            jPop2.show(e.getComponent(), e.getX(), e.getY());
        }
        if (e.isPopupTrigger() && egco.equals(tagBios.getColumnName(tagBios.getSelectedColumn()))) {
            jPop3.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    //The following method gets called when the generate maps button gets pushed in the rewards tab.
    // This method calls another java class to handel the generation of the maps//A class that send the work involved in creating the maps to a new thread. Doing this
//frees up the thread that draws the progress bar animation so that the progress bar displays
//properly
    public static class docalcs extends Thread {

        @Override
        public void run() {
              
            String yearsend = "all";
            if(!yearfld.getText().equals("")) yearsend = yearfld.getText() ;
      
            //Load person data into a data structure
            try{
            mapFrame.loadPersonData(yearsend);
            }catch(Exception e){}
            //Attempt to load the map layers
            try {
                mapFrame.loadMap();
            } catch (Exception e) {
                System.err.println("Got load map exception");
                e.printStackTrace();
            }

            //Attempt to set up the maps and write them to file
            try {
                mapFrame.loadPics();
            } catch (Exception e) {

                System.err.println("Got a load pics exception");
                                    e.printStackTrace();
                
            }

            //Attempt to generate the letter
            try {
                if(!agulf.isSelected())
                mapFrame.loadtext();
            } catch (Exception e) {

                System.err.println("got  crazy exception");
                System.err.println(e);
            }



            //Stop the progress bar
            jProgress.setString("Complete");
            jProgress.setEnabled(false);
            jProgress.setIndeterminate(false);
            jProgress.setValue(0);
            jProgress.paintImmediately(0, 0, tagFrame.jProgress.getBounds().width, tagFrame.jProgress.getBounds().height);

        }
    }

    // This method gets called when the 'Write results to pdf' button gets pushed
    // in the the rewards tab. This method grabs the letters and associated maps
    // and writes them to a pdf file. This methods uses the IText library  
    
    // This method get called when the 'fetch map preview' button gets pushed and grabs the pictures
    // from the desired directory and displays them on the screen
    // This method get called when the 'fetch map preview' button gets pushed and grabs the pictures
    // from the desired directory and displays them on the screen    // This method get called when the 'preview selected text' button gets pushed and
    // displays the text on the screen    }

    // This method get called when the 'fetch text preview' button gets pushed and grabs the letters
    // from the desired directory and displays them on the screen
    // This method gets called when the 'Mark as Rewarded' button gets pushed. It marks all unrewarded capture
    // entries for the given year as rewarded
    // Following methods handel the cursor operations when a field has sample text
    // written in it and the user is to write their own text into it
    // This method gets called when the 'all areas/all years' button gets pushed in
    // the stats tab. It calls a new class to handel the generation of the stats    
    // This method gets called when the 'all areas' button gets pushed in
    // the stats tab. It calls a new class to handel the generation of the stats    // This method gets called when the 'generate map of samples' button gets pushed in
    // the stats tab. It calls a new class to handel the generation of the map    private void pointInArea() {
    
    public void insertArea()throws SQLException {
    LineString TheArea = null;
        String areaname = "All Areas";

        LineString n = statsFrame.getAreaLS("nens");
        LineString s = statsFrame.getAreaLS("23");
        LineString sa = statsFrame.getAreaLS("24");
        //LineString x = statsFrame.getArea("4X");
        
            /*Class.forName(conname);
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

            toda = "select * from capture;";

                Connection conn = null;
   
        try{
           
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
           
            Statement st = conn.createStatement();
    
                
                       
            
            
            ResultSet back = st.executeQuery(toda);
            String are = "4X";
            while (back.next()) {
                if (!back.getString("lat_DD_DDDD").matches("NA")) {
                    if (Double.parseDouble(back.getString("lat_DD_DDDD")) != 0) {

                        /* 
                        Coordinate[] c = new Coordinate[4];
                        
                        
                        
                        c[0] = new Coordinate(Double.parseDouble(back.getString("Lat_DDDD_DD")), Double.parseDouble(back.getString("Long_DDDD_DD")));
                        c[1] = new Coordinate(Double.parseDouble(back.getString("Lat_DDDD_DD"))+.1, Double.parseDouble(back.getString("Long_DDDD_DD")));
                        c[2] = new Coordinate(Double.parseDouble(back.getString("Lat_DDDD_DD")), Double.parseDouble(back.getString("Long_DDDD_DD"))+.1);
                        c[3] = new Coordinate(Double.parseDouble(back.getString("Lat_DDDD_DD")), Double.parseDouble(back.getString("Long_DDDD_DD")));
                        
                        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory( null );
                         */
                        Coordinate c = new Coordinate(Double.parseDouble(back.getString("lat_DD_DDDD")), Double.parseDouble(back.getString("long_DD_DDDD")));
                        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
                        Point po = geometryFactory.createPoint(c);

                        GeometryFactory geometryFactory2 = JTSFactoryFinder.getGeometryFactory(null);
                        LinearRing lr = geometryFactory2.createLinearRing(sa.getCoordinates());
                        Polygon sa2 = geometryFactory2.createPolygon(lr, null);

                        geometryFactory2 = JTSFactoryFinder.getGeometryFactory(null);
                        lr = geometryFactory2.createLinearRing(s.getCoordinates());
                        Polygon s2 = geometryFactory2.createPolygon(lr, null);

                        geometryFactory2 = JTSFactoryFinder.getGeometryFactory(null);
                        lr = geometryFactory2.createLinearRing(n.getCoordinates());
                        Polygon n2 = geometryFactory2.createPolygon(lr, null);
                 

                        are = "4X";
                        if (sa2.contains(po)) {
                            are = "SENS";
                         
                        } 

                        if (s2.contains(po)) {
                            are = "SENS";
                            
                        } 
                        if (n2.contains(po)) {
                            are = "NENS";
                      
                        } 

                        if (are.matches("4X") && Double.parseDouble(back.getString("lat_DD_DDDD")) > 45) {
                            are = "GULF";
                        }
                      
/*
                        Connection conn2;
                        if (localdata) {
                            conn2 = DriverManager.getConnection(url);
                        } else {
                            conn2 = DriverManager.getConnection(url, user, pass);
                        }

                        Statement st2 = conn2.createStatement();
  
                         */
                        String toda2 = "UPDATE capture SET statsarea = '" + are + "' where tag = '" + back.getString("tag") + "' AND tag = '" + back.getString("date") + "';";
                        insertmysqlssh(toda2);
                        //st2.close();
                        //conn2.close();


                    }
                }
            }

            back.close();
st.close();
        
            } catch (Exception e) {
                jTextArea2.append("Got an error when attempting to check for address matches in the person table. \n");
                jTextArea2.append(e.getMessage());
                jTextArea2.append("\n");
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }finally {

                                if (conn != null && !conn.isClosed()) {
                                    conn.close();
                                }
                               
                            }







    }
    // The following methods get called if the name field in the enter capture data tab is edited

    private void jCoKeyPressed(java.awt.event.KeyEvent evt) {
     //  if(localdata){
        if (evt.getKeyCode() != java.awt.event.KeyEvent.VK_DOWN
                && evt.getKeyCode() != java.awt.event.KeyEvent.VK_UP
                && evt.getKeyCode() != java.awt.event.KeyEvent.VK_LEFT
                && evt.getKeyCode() != java.awt.event.KeyEvent.VK_RIGHT) {
            Object b = jCo.getEditor().getItem();


            jCo.removeAllItems();
            jCo.getEditor().setItem(b);

            addcap.setText("");
            town.setText("");
            post.setText("");
            prov.setText("");
            phoa.setText("");
            phob.setText("");
            email.setText("");
        }
      // }
    }

    // When the key is released the dosearch method gets called to search for names that start
    //with the entry in the field
    private void jCoKeyReleased(java.awt.event.KeyEvent evt) {
       //      if(localdata){
        if (evt.getKeyCode() != java.awt.event.KeyEvent.VK_DOWN
                && evt.getKeyCode() != java.awt.event.KeyEvent.VK_UP
                && evt.getKeyCode() != java.awt.event.KeyEvent.VK_LEFT
                && evt.getKeyCode() != java.awt.event.KeyEvent.VK_RIGHT) {
            try{
            dosearch("a");
            }catch(SQLException e){}
        }
         //    }
    }
    // The following methods get called if the name field in the enter capture data tab is edited

    private void jCo1KeyPressed(java.awt.event.KeyEvent evt) {
       //      if(localdata){
        if (evt.getKeyCode() != java.awt.event.KeyEvent.VK_DOWN
                && evt.getKeyCode() != java.awt.event.KeyEvent.VK_UP
                && evt.getKeyCode() != java.awt.event.KeyEvent.VK_LEFT
                && evt.getKeyCode() != java.awt.event.KeyEvent.VK_RIGHT) {
            Object b = jCo1.getEditor().getItem();


            jCo1.removeAllItems();
            jCo1.getEditor().setItem(b);

            addcap1.setText("");
            town1.setText("");
            post1.setText("");
            phoa1.setText("");
            phob1.setText("");
            email1.setText("");
        }
         //    }
    }
             

    // When the key is released the dosearch method gets called to search for names that start
    //with the entry in the field
    private void jCo1KeyReleased(java.awt.event.KeyEvent evt) {
        //    if(localdata){
        if (evt.getKeyCode() != java.awt.event.KeyEvent.VK_DOWN
                && evt.getKeyCode() != java.awt.event.KeyEvent.VK_UP
                && evt.getKeyCode() != java.awt.event.KeyEvent.VK_LEFT
                && evt.getKeyCode() != java.awt.event.KeyEvent.VK_RIGHT) {
            try{
            dosearch("b");
     }catch(SQLException e){}
        }
          //  }
    }

    private void dosearch(String opt)throws SQLException {
        LinkedList<String> firslast = new LinkedList<String>();
        LinkedList<String> firslasttemp = new LinkedList<String>();
        if (opt.equals("a")) {
            jCo.hidePopup();
        }
        if (opt.equals("b")) {
            jCo1.hidePopup();
        }
        //    namcap.setCaretPosition(namcap.getCaretPosition());
        LinkedList<String> peadd = new LinkedList<String>();
        LinkedList<String> tow = new LinkedList<String>();
        LinkedList<String> pr = new LinkedList<String>();
        LinkedList<String> pos = new LinkedList<String>();
        LinkedList<String> ph1 = new LinkedList<String>();
        LinkedList<String> ph2 = new LinkedList<String>();
        LinkedList<String> em = new LinkedList<String>();
   
          /*  Class.forName(conname);
            String url = dataurl;
            Connection conn;
            if (localdata) {
                conn = DriverManager.getConnection(url);
            } else {
                conn = DriverManager.getConnection(url, user, pass);
            }
            Statement st = conn.createStatement();
*/
            String toda = "SELECT * From people;";

                Connection conn = null;
      
        try{
            //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
           
            Class.forName(driverName);
            conn = DriverManager.getConnection (url, dbuser, pass);
            
            Statement st = conn.createStatement();

            ResultSet back = st.executeQuery(toda);

            while (back.next()) {
                firslast.add(back.getString("name"));
                firslasttemp.add(back.getString("name"));
                peadd.add(back.getString("civic"));
                tow.add(back.getString("town"));
                pr.add(back.getString("prov"));
                pos.add(back.getString("post"));
                ph1.add(back.getString("pho1"));
                ph2.add(back.getString("pho2"));
                em.add((String) back.getString("email"));
            }
            st.close();
            back.close();
     //       conn.close();

        } catch (Exception e) {

            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }finally {

                                if (conn != null && !conn.isClosed()) {
                                    conn.close();
                                }
                              
                            }


        String a;
        Object c = null;
        if (opt.equals("a")) {
            c = jCo.getEditor().getItem();
        }
        if (opt.equals("b")) {
            c = jCo1.getEditor().getItem();
        }

        String b = c.toString();

        while (!firslast.isEmpty()) {
            a = firslast.pop().toString();
            if ((a.toLowerCase().startsWith(b) || a.startsWith(b)) && !b.isEmpty()) {
                if (opt.equals("a")) {
                    jCo.addItem(makeObj(a));
                }
                if (opt.equals("b")) {
                    jCo1.addItem(makeObj(a));
                }

            }
        }
        if (opt.equals("a")) {
            jCo.getEditor().setItem(b);
            jCo.showPopup();
        }

        if (opt.equals("b")) {
            jCo1.getEditor().setItem(b);
            jCo1.showPopup();
        }




        if (firslasttemp.contains(c.toString())) {


            int in = firslasttemp.indexOf(c);
        
            if (peadd.get(in) != null) {
                if (opt.equals("a")) {
                    addcap.setText(peadd.get(in).toString());
                }
                if (opt.equals("b")) {
                    addcap1.setText(peadd.get(in).toString());
                }


            }
            if (tow.get(in) != null) {
                if (opt.equals("a")) {
                    town.setText(tow.get(in).toString());
                }
                if (opt.equals("b")) {
                    town1.setText(tow.get(in).toString());
                }


            }
            if (pos.get(in) != null) {
                if (opt.equals("a")) {
                    post.setText(pos.get(in).toString());
                }
                if (opt.equals("b")) {
                    post1.setText(pos.get(in).toString());
                }


            }
            if (ph1.get(in) != null) {
                if (opt.equals("a")) {
                    phoa.setText(ph1.get(in).toString());
                }
                if (opt.equals("b")) {
                    phoa1.setText(ph1.get(in).toString());
                }


            }
            if (ph2.get(in) != null) {
                if (opt.equals("a")) {
                    phob.setText(ph2.get(in).toString());
                }
                if (opt.equals("b")) {
                    phob1.setText(ph2.get(in).toString());
                }


            }
            if (em.get(in) != null) {
                if (opt.equals("a")) {
                    email.setText(em.get(in).toString());
                }
                if (opt.equals("b")) {
                    email1.setText(em.get(in).toString());
                }


            }


        }



    }

    //Following is used to elimate warnings when adding string objects to jcombo vectors
    private Object makeObj(final String item) {
        return new Object() {

            public String toString() {
                return item;
            }
        };
    }

    
    
    
    

    
    
    /**
     *
     * @author brent
     */
    
    public static void main(String args[]) {
        // This is the applications main method. It is the main point of entry, it creates a new tagFrame object which
        // initialazes all the variables nessesary for displaying the application and all the events that may occur
        // when the application is running
        if(args.length <= 2){
        
             JFrame framepro1 = new JFrame();
              dbuser = JOptionPane.showInputDialog(framepro1, "Enter tagging database username:");           
       
             JFrame framepro2 = new JFrame();
              pass = JOptionPane.showInputDialog(framepro2, "Enter tagging database password:");           
        
              JFrame framepro3 = new JFrame();
              datadir = JOptionPane.showInputDialog(framepro3, "Enter path to data directory:");           
            
        }
        
        else{
            
            dbuser = args[0];
            pass = args[1];
            datadir = args[2];
           
        }
         if(args.length == 4){
           
                if(args[3].equals("reward")){
               
                    arg4 = "reward";
                }
                if(args[3].equals("enter")){
                    arg4 = "enter";

                }
                
            }
 try{       
            
            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session=jsch.getSession(dbuser, host, 22);
            session.setPassword(pass);
            session.setConfig(config);
            session.connect();
            System.out.println("Connected");
            int assinged_port=session.setPortForwardingL(lport, "localhost", rport);
          //  System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
            System.out.println("Port Forwarded");
 }catch(Exception e){}   
                
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

                new tagFrame().setVisible(true);


            }
        });
           
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonEnterCapData;
    public static javax.swing.JButton ButtonEnterTagData;
    private javax.swing.JButton ButtonGenSkip;
    private javax.swing.JButton ButtonGenStats;
    private javax.swing.JButton ButtonMarkCapCreate;
    public javax.swing.JButton ButtonRewardsGen;
    private javax.swing.JButton ButtonRewardsMapFetch;
    private javax.swing.JButton ButtonRewardsMapPreview;
    private javax.swing.JButton ButtonRewardsTextFetch;
    private javax.swing.JButton ButtonRewardsTextPreview;
    private javax.swing.JButton ButtonSkipPDF;
    private javax.swing.JButton ButtonStatsGenSamp;
    public static javax.swing.JPanel Jpann;
    private javax.swing.ButtonGroup MCareaGroup;
    public static javax.swing.JTextArea MCout;
    private javax.swing.JButton RewardsMarkAsRewarded;
    private javax.swing.JButton RewardsPDF;
    public static javax.swing.JRadioButton aall;
    public static javax.swing.JTextField addcap;
    public static javax.swing.JTextField addcap1;
    public static javax.swing.JRadioButton agulf;
    public static javax.swing.JRadioButton allareas;
    private javax.swing.JRadioButton allareas2;
    public static javax.swing.JCheckBox allyearcheck;
    public static javax.swing.JRadioButton an;
    public static javax.swing.JRadioButton as;
    public static javax.swing.JRadioButton axxxx;
    private javax.swing.ButtonGroup bg4;
    private java.awt.Button button1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JMenuItem c0;
    private javax.swing.JMenuItem c1;
    private javax.swing.JMenuItem c2;
    private javax.swing.JMenuItem c3;
    private javax.swing.JMenuItem c4;
    private javax.swing.JPanel cappan;
    public static javax.swing.JTextField capt;
    public static javax.swing.JTextField carap;
    private javax.swing.JTextField cfa;
    private javax.swing.JTextField commen;
    public static javax.swing.JTextField comment;
    private javax.swing.JTextField daySampled;
    public static javax.swing.JTextField daycap;
    public static javax.swing.JTextField deptcap;
    private javax.swing.JTextField depth;
    private javax.swing.JMenuItem e1;
    private javax.swing.JMenuItem e2;
    private javax.swing.JMenuItem e3;
    private javax.swing.JMenuItem e4;
    public static javax.swing.JTextField email;
    public static javax.swing.JTextField email1;
    private javax.swing.JRadioButton ensarea;
    private javax.swing.JMenuItem five;
    private javax.swing.JMenuItem four;
    public static javax.swing.JRadioButton fourx;
    public static javax.swing.JTextField fromyear;
    public static javax.swing.JRadioButton gulf;
    private javax.swing.JRadioButton gulfarea;
    private javax.swing.JButton jButton9;
    public static javax.swing.JComboBox jCo;
    public static javax.swing.JComboBox jCo1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    public static javax.swing.JLabel jLabel12;
    public static javax.swing.JLabel jLabel13;
    public static javax.swing.JLabel jLabel14;
    public static javax.swing.JLabel jLabel15;
    public static javax.swing.JLabel jLabel16;
    public static javax.swing.JLabel jLabel17;
    public static javax.swing.JLabel jLabel18;
    public static javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    public static javax.swing.JLabel jLabel20;
    public static javax.swing.JLabel jLabel21;
    public static javax.swing.JLabel jLabel22;
    public static javax.swing.JLabel jLabel23;
    public static javax.swing.JLabel jLabel24;
    public static javax.swing.JLabel jLabel25;
    public static javax.swing.JLabel jLabel26;
    public static javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    public static javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    public static javax.swing.JLabel jLabel32;
    public static javax.swing.JLabel jLabel33;
    public static javax.swing.JLabel jLabel34;
    public static javax.swing.JLabel jLabel35;
    public static javax.swing.JLabel jLabel36;
    public static javax.swing.JLabel jLabel37;
    public static javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    public static javax.swing.JLabel jLabel41;
    public static javax.swing.JLabel jLabel42;
    public static javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    public static javax.swing.JLabel jLabel46;
    public static javax.swing.JLabel jLabel47;
    public static javax.swing.JLabel jLabel48;
    public static javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    public static javax.swing.JLabel jLabel50;
    public static javax.swing.JLabel jLabel51;
    public static javax.swing.JLabel jLabel52;
    public static javax.swing.JLabel jLabel53;
    public static javax.swing.JLabel jLabel54;
    public static javax.swing.JLabel jLabel55;
    public static javax.swing.JLabel jLabel56;
    public static javax.swing.JLabel jLabel57;
    public static javax.swing.JLabel jLabel58;
    public static javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    public static javax.swing.JLabel jLabel60;
    public static javax.swing.JLabel jLabel61;
    public static javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public static javax.swing.JPanel jPanel6;
    public static javax.swing.JPanel jPanel7;
    public javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPop;
    private javax.swing.JPopupMenu jPop2;
    private javax.swing.JPopupMenu jPop3;
    public static javax.swing.JProgressBar jProgress;
    public static javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    public static javax.swing.JTextArea jTextArea2;
    public static javax.swing.JPanel jpan2;
    public static javax.swing.JCheckBox knownrel;
    public static javax.swing.JTextField latcap;
    private javax.swing.JTextField latitude;
    public static javax.swing.JTextArea layout;
    public static javax.swing.JTextArea layout1;
    public static javax.swing.JTextField longcap;
    private javax.swing.JTextField longnitude;
    public static javax.swing.JRadioButton nens;
    private javax.swing.JRadioButton nens2;
    public static javax.swing.JRadioButton north;
    private javax.swing.JMenuItem one;
    public static javax.swing.JCheckBox onlyplotrecaps;
    public static javax.swing.JTextField phoa;
    public static javax.swing.JTextField phoa1;
    public static javax.swing.JTextField phob;
    public static javax.swing.JTextField phob1;
    public static javax.swing.JList piclist;
    public static javax.swing.JTextField post;
    public static javax.swing.JTextField post1;
    public static javax.swing.JTextField prov;
    public static javax.swing.JTextField prov1;
    public static javax.swing.JRadioButton rel;
    public static javax.swing.JRadioButton ret;
    public static javax.swing.JCheckBox rewardrun;
    private javax.swing.JTextField sampler;
    public static javax.swing.JRadioButton sens;
    private javax.swing.JRadioButton sens2;
    public static javax.swing.JTextField skipmess;
    public static javax.swing.JRadioButton south;
    public static javax.swing.JTextArea statstext;
    private javax.swing.JTable tagBios;
    public static javax.swing.JTextField tagcap;
    private javax.swing.JTextField tcap;
    private javax.swing.JList textlist;
    private javax.swing.JMenuItem three;
    public static javax.swing.JTextField town;
    public static javax.swing.JTextField town1;
    public static javax.swing.JTextField toyear;
    private javax.swing.JMenuItem two;
    public static javax.swing.JRadioButton unk;
    public static javax.swing.JTextField vess;
    private javax.swing.JTextField vessel;
    public static javax.swing.JRadioButton xxxx;
    private javax.swing.JRadioButton xxxx2;
    public static javax.swing.JTextField yeardata2;
    public static javax.swing.JTextField yearfld;
    // End of variables declaration//GEN-END:variables
    
    
    
    
    public static String dataurl = "jdbc:mysql://www.enssnowcrab.com/enssnowc_Taging"; //added variable
    public static String conname = "com.mysql.jdbc.Driver";
    public static boolean localdata = false;
    public static String user = "enssnowc_guest";
public static String pass = "";
    public static String datadir = "";

    public static Session session = null;
public static int lport=3309;
        public static String rhost="www.enssnowcrab.com";
        public static String host="www.enssnowcrab.com";
        public static String dbuser = "enssnowc_admin";
        public static int rport=3306;
 
        public static String url = "jdbc:mysql://localhost:"+lport+"/enssnowc_Taging";
        public static String driverName="com.mysql.jdbc.Driver";
     public static String arg4 = "";


}
