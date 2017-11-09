////////////////////////////////////////////////////////////////////////////////
////                                                                        ////
////  Hard Disks Simulation (HarDis)                                        ////
////                                                                        ////
////  Basic Molecular Dynamics simulation based on hard disks.              ////
////                                                                        ////
////  Version 1.0                                                           ////
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
    JCheckBox CckDia;
    JLabel  LblInfo;
    JPanel infoPanel;
    Window mainWindow;
    WindowDiagnostics winDia;

    JMenuBar menuBar;
    JMenu helMenu;
    JMenu simMenu;
    JMenu exaMenu;
    JMenuItem addParMenuItem;
    JMenuItem addInjMenuItem;
    JMenuItem addDelMenuItem;
    JMenuItem addObsMenuItem;
    JMenuItem remParMenuItem;

    JMenuItem genMenuItem;

    JMenuItem tc1MenuItem;
    JMenuItem tc2MenuItem;
    JMenuItem tc3MenuItem;
    JMenuItem tc4MenuItem;
    JMenuItem tc5MenuItem;
    JMenuItem tc6MenuItem;

    JMenuItem manMenuItem;
    JMenuItem codMenuItem;
    Timer timer;

    int cycles;   // Number of cycles

    public Window(int w, int h) {
        this.setTitle("Hardis (Hard Disk simulation). Version 1.0");
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

        addDelMenuItem = new JMenuItem("Add delter");
        addDelMenuItem.addActionListener(eventHandler);
        simMenu.add(addDelMenuItem);

        simMenu.addSeparator();

        remParMenuItem = new JMenuItem("Remove particles");
        remParMenuItem.addActionListener(eventHandler);
        simMenu.add(remParMenuItem);


        exaMenu = new JMenu("Examples");
        tc1MenuItem = new JMenuItem("Density 1");
        tc1MenuItem.addActionListener(eventHandler);
        exaMenu.add(tc1MenuItem);

        tc2MenuItem = new JMenuItem("Density 2");
        tc2MenuItem.addActionListener(eventHandler);
        exaMenu.add(tc2MenuItem);

        tc3MenuItem = new JMenuItem("Thermalization");
        tc3MenuItem.addActionListener(eventHandler);
        exaMenu.add(tc3MenuItem);

        tc4MenuItem = new JMenuItem("Diffusion");
        tc4MenuItem.addActionListener(eventHandler);
        exaMenu.add(tc4MenuItem);

        tc5MenuItem = new JMenuItem("Brownian movement");
        tc5MenuItem.addActionListener(eventHandler);
        exaMenu.add(tc5MenuItem);

        tc6MenuItem = new JMenuItem("Test");
        tc6MenuItem.addActionListener(eventHandler);
        exaMenu.add(tc6MenuItem);

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
    
        CckDia = new JCheckBox("Diagnostics");
        CckDia.addActionListener(eventHandler);
        generalPanel.add(CckDia);

        mainPan.add(generalPanel);
        mainPan.add(simu);
        mainPan.add(infoPanel);

        cycles = 0;
        setButtons(false);


        //this.pack();
        this.setVisible(true);


        cases.defaultCase(simu);
        this.addWindowListener(new WindowAdapter()
        {
          public void windowClosing(WindowEvent e) {
            if (CckDia.isSelected()) winDia.dispose();
            
          }
        });

    }

    void setButtons(boolean val) {
        BtnStart.setEnabled(!val);
        BtnStop.setEnabled(val);
        BtnReset.setEnabled(!val);
        BtnStep.setEnabled(!val);
        simMenu.setEnabled(!val);
        exaMenu.setEnabled(!val);
        helMenu.setEnabled(!val);
        setMessage(val);
    }   

    void setMessage(boolean val) {
      if (val) {
        int np = simu.getNP();
        if (np > 1000) {
          LblInfo.setText("WARNING: too many particles -> " + np + " (" + cycles + " cycles)");
          infoPanel.setBackground(Color.RED);
        } else {
          LblInfo.setText("Running (" + cycles + " cycles)");
          infoPanel.setBackground(Color.GREEN);
        }
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
              if (cycles % 10 == 0) {
                setMessage(true);
                simu.diagnostics();
                if (CckDia.isSelected()) winDia.updateValues();
              }
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
          if (cycles % 10 == 0) setMessage(true);
        }else if (O == BtnReset) {
          cycles = 0;
          simu.clear();
          simu.update();
          if (CckDia.isSelected()) winDia.clear();
        }else if (O == CckDia) {
            if (CckDia.isSelected())
              winDia = new WindowDiagnostics(mainWindow, simu);
            else 
              winDia.dispose();
        }else if (O == addParMenuItem) {
            WindowAddPart win = new WindowAddPart(mainWindow, simu);
        }else if (O == addInjMenuItem) {
            WindowAddInjector win = new WindowAddInjector(mainWindow, simu);
        }else if (O == addDelMenuItem) {
            WindowAddDeleter win = new WindowAddDeleter(mainWindow, simu);
        }else if (O == genMenuItem) {
            WindowSettings win = new WindowSettings(mainWindow, simu);
        }else if (O == remParMenuItem) {
            WindowRemovePart win = new WindowRemovePart(mainWindow, simu);
        }else if (O == tc1MenuItem) {
          cases.density1(simu);
          if (CckDia.isSelected()) winDia.clear();
        }else if (O == tc2MenuItem) {
          cases.density2(simu);
          if (CckDia.isSelected()) winDia.clear();
        }else if (O == tc3MenuItem) {
          cases.thermalization(simu);
          if (CckDia.isSelected()) winDia.clear();
        }else if (O == tc4MenuItem) {
          cases.diffusion(simu);
          if (CckDia.isSelected()) winDia.clear();
        }else if (O == tc5MenuItem) {
          cases.brownian(simu);
          if (CckDia.isSelected()) winDia.clear();
        }else if (O == tc6MenuItem) {
          cases.test(simu);
          if (CckDia.isSelected()) winDia.clear();
        }
      }
    }

}
