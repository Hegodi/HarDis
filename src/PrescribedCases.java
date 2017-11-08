import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class PrescribedCases {
 
    public PrescribedCases() {}

    /////////////////////////////////////////////////////////////////
    // Methods
    public void defaultCase(Simulation simu) { 
      simu.clear();
      double Lx = 10;
      double Ly = 10;
      double Grav = 0.05;
      double ecP = 1.0;
      double ecW = 0.5;
      double dt  = 0.1;
      simu.setL(Lx, Ly);
      simu.setGRA(Grav);
      simu.setECp(ecP);
      simu.setECw(ecW);
      simu.setDt(dt);
      simu.repaint();
    }

    public void density1(Simulation simu) { 
      simu.clear();
      double Lx = 10;
      double Ly = 10;
      double Grav = 0.05;
      double ecP = 1.0;
      double ecW = 0.9;
      double dt  = 0.1;
      simu.setL(Lx, Ly);
      simu.setGRA(Grav);
      simu.setECp(ecP);
      simu.setECw(ecW);
      simu.setDt(dt);
      double x1 = 0;
      double x2 = Lx;
      double y1 = 0;
      double y2 = Ly;
      double vx = 0.0;
      double vy = 0.0;
      double vth = 0.0;
      simu.addParticles(20  , 0.4 , x1, x2, y1, y2,  vx, vy, vth, 1);
      simu.addParticles(100 , 0.2 , x1, x2, y1, y2,  vx, vy, vth, 2);
      simu.addParticles(200 , 0.1 , x1, x2, y1, y2,  vx, vy, vth, 3);
      simu.repaint();
    }

    public void density2(Simulation simu) { 
      simu.clear();
      double Lx = 10;
      double Ly = 10;
      double Grav = 0.05;
      double ecP = 1.0;
      double ecW = 1.0;
      double dt  = 0.1;
      simu.setL(Lx, Ly);
      simu.setGRA(Grav);
      simu.setECp(ecP);
      simu.setECw(ecW);
      simu.setDt(dt);
      simu.addParticles(400 , 0.2 , 0, Lx, 4, Ly,      0, 0, 0.01, 3);
      simu.addParticles(1   , 1.0 , 5, 5, Ly-1, Ly-1,  0, 0, 0.01, 2);
      simu.repaint();
    }

    public void testInjector(Simulation simu) { 
      simu.clear();
      double Lx = 10;
      double Ly = 10;
      double Grav = 0.02;
      double ecP = 0.8;
      double ecW = 0.5;
      double dt  = 0.1;
      simu.setL(Lx, Ly);
      simu.setGRA(Grav);
      simu.setECp(ecP);
      simu.setECw(ecW);
      simu.setDt(dt);
      double x1 = 0;
      double x2 = 1;
      double y1 = 0;
      double y2 = 1;
      double vx = 0.2;
      double vy = 0.0;
      double vth = 0.01;
      simu.addInjector(20 ,1 , 0.1  , x1, x2, y1, y2,  vx, vy, vth, 1);
      simu.repaint();
    }

    public void test(Simulation simu) { 
      simu.clear();
      double Lx = 10;
      double Ly = 10;
      double Grav = 0.02;
      double ecP = 0.8;
      double ecW = 0.5;
      double dt  = 0.1;
      simu.setL(Lx, Ly);
      simu.setGRA(Grav);
      simu.setECp(ecP);
      simu.setECw(ecW);
      simu.setDt(dt);
      double x1 = 0;
      double x2 = 5;
      double y1 = 0;
      double y2 = 5;
      double vx = 0.0;
      double vy = 0.0;
      double vth = 0.01;
      simu.addParticles(100 , 0.1  , x1, x2, y1, y2,  vx, vy, vth, 1);
      simu.repaint();
    }

}
