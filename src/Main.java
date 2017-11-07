////////////////////////////////////////////////////////////////////////////////
////                                                                        ////
////  Hard Disks Simulation (HarDis)                                        ////
////                                                                        ////
////  Basic Molecular Dynamics simulation based on hard disks.              ////
////                                                                        ////
////  Diego Gonzalez                                                        ////
////  November 2017                                                         ////
////                                                                        ////
////////////////////////////////////////////////////////////////////////////////
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class Main {

    public static void main(String args[]) {
        Window window = new Window(800,600);
    }
}

class Window extends JFrame {
    Simulation simu;
    PrescribedCases cases;

    JButton BtnStart;
    JButton BtnStop;
    JButton BtnReset;
    JButton BtnStep;
    JLabel  LblInfo;
    JPanel infoPanel;
    JFrame mainWindow;

    JMenuBar menuBar;
    JMenu helMenu;
    JMenu simMenu;
    JMenu exaMenu;
    JMenuItem addParMenuItem;
    JMenuItem addInjMenuItem;
    JMenuItem addAbsMenuItem;
    JMenuItem addObsMenuItem;

    JMenuItem genMenuItem;

    JMenuItem tc1MenuItem;
    JMenuItem tc2MenuItem;
    JMenuItem tc3MenuItem;

    JMenuItem manMenuItem;
    JMenuItem codMenuItem;
    Timer timer;

    long cycles;   // Number of cycles

    public Window(int w, int h) {
        this.setTitle("Hardis (Hard Disk simulation)");
        mainWindow = this;
        this.setLocation(0,0);
        this.setBounds(0,0,w,h);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setResizable(false);
        JPanel mainPan = new JPanel();
        mainPan.setBounds(0,0,w,h);
        this.add(mainPan);
        mainPan.setLayout(new BoxLayout(mainPan, BoxLayout.PAGE_AXIS));

        simu = new Simulation();
        simu.setBounds(0,0,w,515);
        simu.init(40.0, 10.0);

        cases = new PrescribedCases();

        EventHandler eventHandler = new EventHandler();
        
        // Menu Bar
        menuBar = new JMenuBar();

        simMenu = new JMenu("Simulation");

        genMenuItem = new JMenuItem("Settings");
        genMenuItem.addActionListener(eventHandler);
        simMenu.add(genMenuItem);

        simMenu.addSeparator();

        addParMenuItem = new JMenuItem("Add particles");
        addParMenuItem.addActionListener(eventHandler);
        simMenu.add(addParMenuItem);

        addInjMenuItem = new JMenuItem("Add injector");
        addInjMenuItem.addActionListener(eventHandler);
        simMenu.add(addInjMenuItem);

        addAbsMenuItem = new JMenuItem("Add absorbing area");
        addAbsMenuItem.addActionListener(eventHandler);
        simMenu.add(addAbsMenuItem);

        exaMenu = new JMenu("Examples");
        tc1MenuItem = new JMenuItem("Default");
        tc1MenuItem.addActionListener(eventHandler);
        exaMenu.add(tc1MenuItem);

        tc2MenuItem = new JMenuItem("Densities");
        tc2MenuItem.addActionListener(eventHandler);
        exaMenu.add(tc2MenuItem);

        tc3MenuItem = new JMenuItem("Test");
        tc3MenuItem.addActionListener(eventHandler);
        exaMenu.add(tc3MenuItem);

        helMenu = new JMenu("Help");


        manMenuItem = new JMenuItem("Manual");
        manMenuItem.addActionListener(eventHandler);
        helMenu.add(manMenuItem);

        codMenuItem = new JMenuItem("About the code");
        codMenuItem.addActionListener(eventHandler);
        helMenu.add(codMenuItem);

        menuBar.add(simMenu);
        menuBar.add(exaMenu);
        menuBar.add(helMenu);

        this.setJMenuBar(menuBar);

        // Info panel
        infoPanel = new JPanel();
        infoPanel.setLayout( new FlowLayout());
 
        LblInfo = new JLabel("");
        infoPanel.add(LblInfo);

        // Buttons panel
        JPanel generalPanel = new JPanel();
        generalPanel.setLayout( new FlowLayout());

        BtnStart = new JButton("Start");
        BtnStart.addActionListener(eventHandler);
        generalPanel.add(BtnStart);

        BtnStop = new JButton("Stop");
        BtnStop.addActionListener(eventHandler);
        generalPanel.add(BtnStop);

        BtnReset = new JButton("Reset");
        BtnReset.addActionListener(eventHandler);
        generalPanel.add(BtnReset);

        BtnStep = new JButton("Step");
        BtnStep.addActionListener(eventHandler);
        generalPanel.add(BtnStep);

        mainPan.add(generalPanel);
        mainPan.add(simu);
        mainPan.add(infoPanel);

        cycles = 0;
        setButtons(false);


        //this.pack();
        this.setVisible(true);


        cases.defaultCase(simu);
        /////////////// TEST
        //simu.addParticles(TypeInj.RECTANGLE, TypeInj.MAXWELL, TypeInj.RANDOM, 1000 , 0.05 , 0.0,  5.0, 2.5,  7.5,  1.0, 0.0, 0.0, 1);
        //simu.addParticles(TypeInj.RECTANGLE, TypeInj.MAXWELL, TypeInj.RANDOM, 1000 , 0.05 ,35.0, 40.0, 2.5,  7.5, -1.0, 0.0, 0.0, 2);
        //simu.addParticles(2000 , 0.04, 0.0, 2.0, 8.0, 10.0, 0.1, -0.1, 0.0);
    }

    void setButtons(boolean val) {
        BtnStart.setEnabled(!val);
        BtnStop.setEnabled(val);
        BtnReset.setEnabled(!val);
        BtnStep.setEnabled(!val);
        if (val ) {
          LblInfo.setText("Running (" + cycles + " cycles)");
          infoPanel.setBackground(Color.GREEN);
        } else {
          LblInfo.setText("Stoped");
          infoPanel.setBackground(Color.GRAY);
        }
    }   
 
    // Evente handler:
    class EventHandler implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        Object O = e.getSource();
        if (O == BtnStart) {
          if (timer == null) {
            timer = new Timer(10, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
              simu.moveParticles(); 
              cycles++;
              if (cycles % 10 == 0) LblInfo.setText("Running (" + cycles + " cycles)");
            }
            });
            timer.start();
            setButtons(true);
          } 

        }else if (O == BtnStop) {
          if (timer != null) {
            timer.stop();
            timer = null;
            setButtons(false);
          }
        }else if (O == BtnStep) {
          simu.moveParticles(); 
          cycles++;
          if (cycles % 10 == 0) LblInfo.setText("Running (" + cycles + " cycles)");
        }else if (O == BtnReset) {
          cycles = 0;
          simu.clear();
          simu.update();
        }else if (O == addParMenuItem) {
            WindowAddPart win = new WindowAddPart(mainWindow, simu);
        }else if (O == addInjMenuItem) {
            WindowAddInjector win = new WindowAddInjector(mainWindow, simu);
        }else if (O == genMenuItem) {
            WindowSettings win = new WindowSettings(mainWindow, simu);
        }else if (O == tc1MenuItem) {
          cases.defaultCase(simu);
        }else if (O == tc2MenuItem) {
          cases.densities(simu);
        }else if (O == tc3MenuItem) {
          cases.test(simu);
        }
      }
    }

}
