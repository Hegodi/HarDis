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
    JButton BtnStart;
    JButton BtnStop;
    JButton BtnReset;
    JLabel  LblInfo;
    JPanel infoPanel;
    JFrame mainWindow;

    JMenuBar menuBar;
    JMenu aboMenu;
    JMenu setMenu;
    JMenu simMenu;
    JMenuItem addInjMenuItem;
    JMenuItem addParMenuItem;
    JMenuItem addAbsMenuItem;
    JMenuItem addObsMenuItem;
    JMenuItem visMenuItem;
    JMenuItem genMenuItem;
    Timer timer;

    long cycles;   // Number of cycles

    public Window(int w, int h) {
        mainWindow = this;
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

        EventHandler eventHandler = new EventHandler();
        
        // Menu Bar
        menuBar = new JMenuBar();

        aboMenu = new JMenu("About");
   
        setMenu = new JMenu("Settings");
        visMenuItem = new JMenuItem("Visualization");
        visMenuItem.addActionListener(eventHandler);
        setMenu.add(visMenuItem);
        genMenuItem = new JMenuItem("General settings");
        genMenuItem.addActionListener(eventHandler);
        setMenu.add(genMenuItem);
        
        simMenu = new JMenu("Simulation");
        addParMenuItem = new JMenuItem("Particles");
        addParMenuItem.addActionListener(eventHandler);
        simMenu.add(addParMenuItem);

        addInjMenuItem = new JMenuItem("Injector");
        addInjMenuItem.addActionListener(eventHandler);
        simMenu.add(addInjMenuItem);

        addAbsMenuItem = new JMenuItem("Absorbing area");
        addAbsMenuItem.addActionListener(eventHandler);
        simMenu.add(addAbsMenuItem);

        menuBar.add(setMenu);
        menuBar.add(simMenu);
        menuBar.add(aboMenu);

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

        mainPan.add(generalPanel);
        mainPan.add(simu);
        mainPan.add(infoPanel);

        cycles = 0;
        setButtons(false);

        this.setVisible(true);


        /////////////// TEST
        //simu.addParticles(TypeInj.RECTANGLE, TypeInj.MAXWELL, TypeInj.RANDOM, 1000 , 0.05 , 0.0,  5.0, 2.5,  7.5,  1.0, 0.0, 0.0, 1);
        //simu.addParticles(TypeInj.RECTANGLE, TypeInj.MAXWELL, TypeInj.RANDOM, 1000 , 0.05 ,35.0, 40.0, 2.5,  7.5, -1.0, 0.0, 0.0, 2);
        //simu.addParticles(2000 , 0.04, 0.0, 2.0, 8.0, 10.0, 0.1, -0.1, 0.0);
    }

    void setButtons(boolean val) {
        BtnStart.setEnabled(!val);
        BtnStop.setEnabled(val);
        BtnReset.setEnabled(!val);
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
          System.out.printf("Start\n");
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
          System.out.printf("Stop\n");
          if (timer != null) {
            timer.stop();
            timer = null;
            setButtons(false);
          }
        }else if (O == BtnReset) {
          System.out.printf("Reset\n");
          cycles = 0;
          simu.clear();
          simu.update();
        }else if (O == addParMenuItem) {
            WindowAddPart win = new WindowAddPart(mainWindow, simu);
        }
      }
    }

}
