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
      simu.addParticles(1   , 0.8 , 5, 5, Ly-1, Ly-1,  0, 0, 0.01, 2);
      simu.addParticles(800 , 0.12 , 0, Lx, 4, Ly-1,      0, 0, 0.01, 3);
      simu.repaint();
    }

    public void diffusion(Simulation simu) { 
      simu.clear();
      double Lx = 12;
      double Ly = 4;
      double Grav = 0.00;
      double ecP = 1.0;
      double ecW = 1.0;
      double dt  = 0.1;
      simu.setL(Lx, Ly);
      simu.setGRA(Grav);
      simu.setECp(ecP);
      simu.setECw(ecW);
      simu.setDt(dt);
      simu.addParticles(200 , 0.1  , 0   , Lx/2+0.1, 0, Ly,  0, 0, 0.3, 1);
      simu.addParticles(200 , 0.1  , Lx/2-0.1, Lx  , 0, Ly,  0, 0, 0.0, 4);
      simu.repaint();
    }


    public void thermalization(Simulation simu) { 
      simu.clear();
      double Lx = 10;
      double Ly = 10;
      double Grav = 0.0;
      double ecP = 1.0;
      double ecW = 1.0;
      double dt  = 0.005;
      simu.setL(Lx, Ly);
      simu.setGRA(Grav);
      simu.setECp(ecP);
      simu.setECw(ecW);
      simu.setDt(dt);
      simu.addParticles(100 , 0.1  , 0, Lx, 0, Ly,  0.0, 0.0, 0.0, 4);
      simu.addParticles(100 , 0.1  , 0, Lx, 0, Ly,  0.0, 0.0, 2.0 , 3);
      simu.repaint();
    }

    public void brownian(Simulation simu) { 
      simu.clear();
      double Lx = 10;
      double Ly = 10;
      double Grav = 0.0;
      double ecP = 1.0;
      double ecW = 1.0;
      double dt  = 0.1;
      simu.setL(Lx, Ly);
      simu.setGRA(Grav);
      simu.setECp(ecP);
      simu.setECw(ecW);
      simu.setDt(dt);
      simu.addParticles(400 , 0.15 , 0, Lx, 0, Ly,  0.0, 0.0, 0.2, 2);
      simu.addParticles(1   , 0.4  , 4,  6, 4,  6,  0.0, 0.0, 0.2, 1);
      simu.repaint();
    }

    public void shooting(Simulation simu) { 
      simu.clear();
      double Lx = 20;
      double Ly = 10;
      double Grav = 0.0;
      double ecP = 0.5;
      double ecW = 1.0;
      double dt  = 0.05;
      simu.setL(Lx, Ly);
      simu.setGRA(Grav);
      simu.setECp(ecP);
      simu.setECw(ecW);
      simu.setDt(dt);
      simu.addParticles(400 , 0.15 , 3*Lx/4, Lx, 0, Ly,  0.0, 0.0, 0.0, 2);
      //simu.addParticles(1 , 0.15 , 0,  1, 4.5,  5.5,  2.0, 0.0, 0.05, 1);
      simu.addInjector(300, 1 , 0.05 , 0,  1, 4.5,  5.5,  2.0, 0.0, 0.05, 1);
      simu.addDeleter(0,  3*Lx/4,  0.0, 0.2);
      simu.addDeleter(0,  3*Lx/4,  Ly-0.2, Ly);
      simu.repaint();
    }


    public void test(Simulation simu) { 
      simu.clear();
      double Lx = 10;
      double Ly = 10;
      double Grav = 0.02;
      double ecP = 1.0;
      double ecW = 0.5;
      double dt  = 0.1;
      simu.setL(Lx, Ly);
      simu.setGRA(Grav);
      simu.setECp(ecP);
      simu.setECw(ecW);
      simu.setDt(dt);
      simu.addInjector(10, 1 , 0.1  , 8, 9, 0,  1, -0.4, 0, 0.1, 1);
      simu.addInjector(10, 1 , 0.1  , 2, 3, 0,  1, -0.4, 0, 0.1, 2);
      simu.addInjector(10, 1 , 0.1  , 2, 3, 4,  5, -0.4, 0, 0.1, 3);
      simu.addInjector(10, 1 , 0.1  , 4, 6, 8,  9, -0.4, 0, 0.1, 4);
      simu.addDeleter( 0, 1, 0,  1);
      simu.addDeleter( 0, 1, 9,  10);
      //simu.addParticles(100 , 0.1  , x1, x2, y1, y2,  vx, vy, vth, 1);
      simu.repaint();
    }

}
