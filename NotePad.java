package com.mynotepad;


import java.io.*;                                                             
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NotePad extends JFrame implements ActionListener,WindowListener{
    JTextArea jta = new JTextArea();                                             // This Is Class Object Of The Text Editing Area Of Layout In The Application
    File fNameContainer;                                                         // This Is The Structure Object For Storing File Names

    public NotePad(){                                                            // NotePad Class Consists Of Ui Logic and All Operations Required For The Business LOgic 
        Font font  =  new Font("Arial",Font.BOLD,15);                            // Font Object For Storing The Style Name Of Text Inside Text Editor 
        Container con = getContentPane();
        JMenuBar jmb = new JMenuBar();                                           // Menu Bar(container) For Displaying Menu's 
        JMenu jmfile = new JMenu("File");                                        // Menu For Displaying File Menu [ New  , Open  , Save , Exit ]   
        JMenu jmedit = new JMenu("Edit");                                        // Menu For Displaying Edit Menu [ Cut , Copy , Paste ] 
        JMenu jmhelp = new JMenu("Help");                                        // Menu For Displaying Help Menu [ About ]


        con.setLayout(new BorderLayout());                                       // For Displaying Layout With Borders
        JScrollPane jsp = new JScrollPane(jta);                                  // For Displaying A Scroll Pane
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);   // VERTICAL_SCROLLBAR_ALWAYS Means It Displays Only Vertical Scroll In The Text Area
        jsp.setVisible(true);                                                    // For Making The Scroll Pane Visible

        jta.setFont(font);
        jta.setLineWrap(true);
        jta.setWrapStyleWord(true);

        con.add(jsp);                                                            // It Adds Scroll Pane To The Container

        createMenuItem(jmfile,"New");                                         //   /*
        createMenuItem(jmfile,"Open");                                        //    *
        createMenuItem(jmfile,"Save");                                        //    * C R E A T I N G 
        jmfile.addSeparator();                                                //    *               M E N U   I T E M S
        createMenuItem(jmfile,"Exit");                                        //    *                                 U S I N G 
                                                                              //    *                                          C U S T O M 
        createMenuItem(jmedit,"Cut");                                         //    *                                                     D E S I G N E D 
        createMenuItem(jmedit,"Copy");                                        //    *                                                                    F U N C T I O N
        createMenuItem(jmedit,"Paste");                                       //    *
                                                                              //    *
        createMenuItem(jmhelp,"About");                                       //    *
 
        jmb.add(jmfile);                                                      // Adding Menu's To The Menu Bar
        jmb.add(jmedit);
        jmb.add(jmhelp);

        setJMenuBar(jmb);                        

        setIconImage(Toolkit.getDefaultToolkit().getImage("notepad.gif"));     // Adding An Icon To The Container
        addWindowListener(this);

        setSize(500,500);                                                      // For Defining Size Of The Window  (Height , Width)
        setTitle("Untitled.txt - Notepad");                                    // For displaying Title Of The Window
        setVisible(true);
    }
    public void createMenuItem(JMenu jm , String txt){
        JMenuItem jmi = new JMenuItem(txt);
        jmi.addActionListener(this);
        jm.add(jmi);
    }

    public void actionPerformed(ActionEvent e){
        JFileChooser jfc = new JFileChooser();
        if(e.getActionCommand().equals("New")){
            this.setTitle("Untitled.txt");
            jta.setText("");
            fNameContainer = null;
        }else if(e.getActionCommand().equals("Open")){
            int ret = jfc.showDialog(this,null);
            if(ret == JFileChooser.APPROVE_OPTION){
                try{
                    File f1 = jfc.getSelectedFile();
                    OpenFile(f1.getAbsolutePath());
                    this.setTitle(f1.getName()+" - Notepad");
                    fNameContainer = f1;
                }catch(IOException ioe){}

            }
        }else if(e.getActionCommand().equals("Save")){
            if(fNameContainer != null){
                jfc.setCurrentDirectory(fNameContainer);
                jfc.setSelectedFile(fNameContainer);
            }else{
                jfc.setSelectedFile(new File("Untitled.txt"));
            }
            int ret = jfc.showSaveDialog(null);
            if(ret == JFileChooser.APPROVE_OPTION){
                try{
                    File f1 = jfc.getSelectedFile();
                    SaveFile(f1.getAbsolutePath());
                    this.setTitle(f1.getName()+" - Notepad");
                    fNameContainer = f1;
                }catch(IOException ioe){}
            }
        }

        else if(e.getActionCommand().equals("Exit")){
            Exiting();
        }else if(e.getActionCommand().equals("Copy")){
            jta.copy();
        }else if(e.getActionCommand().equals("Cut")){
            jta.cut();
        }else if(e.getActionCommand().equals("Paste")){
            jta.paste();
        }else if(e.getActionCommand().equals("About")){
            JOptionPane.showMessageDialog(this,"Created By Adithya Kathi","Text Editor",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void OpenFile(String fname) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
        String l;
        jta.setText("");
        setCursor(new Cursor(Cursor.WAIT_CURSOR));

        while( (l = br.readLine()) != null){
            jta.setText(jta.getText()+l+"\r\n");
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        br.close();
    }

    public void SaveFile(String fname) throws IOException{
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(fname));
        dos.writeBytes(jta.getText());
        dos.close();
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void windowDeactivated(WindowEvent e){}
    public void windowActivated(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowIconified(WindowEvent e){}
    public void windowClosed(WindowEvent e){}
    public void windowClosing(WindowEvent e){Exiting();}

    public void Exiting(){System.exit(0);}
    public void windowOpened(WindowEvent e){}
    }
