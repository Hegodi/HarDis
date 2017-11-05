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

    Timer timer;

    public Window(int w, int h) {
        this.setBounds(0,0,w,h);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setResizable(false);
        this.setLayout(null);

        simu = new Simulation();
        simu.setBounds(5,5,790,450);
        simu.init(40.0, 10.0);
        this.add(simu);

        EventHandler eventHandler = new EventHandler();

        BtnStart = new JButton("Start");
        BtnStart.setBounds(5,h-110,100,30);
        BtnStart.addActionListener(eventHandler);
        this.add(BtnStart);

        BtnStop = new JButton("Stop");
        BtnStop.setBounds(5,h-40,100,30);
        BtnStop.addActionListener(eventHandler);
        this.add(BtnStop);

        simu.addParticles(1000 , 0.05 , 0.0,  5.0, 2.5,  7.5,  0.4, 0.0, 0.0, 1);
        simu.addParticles(1000 , 0.05 ,35.0, 40.0, 2.5,  7.5, -0.4, 0.0, 0.0, 2);
        //simu.addParticles(2000 , 0.04, 0.0, 2.0, 8.0, 10.0, 0.1, -0.1, 0.0);

        this.setVisible(true);
    }
    // Evente handler:
    class EventHandler implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        Object O = e.getSource();
        if (O == BtnStart) {
          System.out.printf("Start\n");
          timer = new Timer(10, new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e) {
            simu.moveParticles(); 
          }
          });
          timer.start();

        }else if (O == BtnStop) {
          System.out.printf("Stop\n");
          if (timer != null) timer.stop();
        }
      }
    }

}
