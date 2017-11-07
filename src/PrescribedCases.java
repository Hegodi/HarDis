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
      simu.setL(Lx, Ly);
      simu.setGRA(Grav);
      simu.setECp(ecP);
      simu.setECw(ecW);
      simu.repaint();
    }

    public void twoSpecies(Simulation simu) { 
      simu.clear();
      double Lx = 10;
      double Ly = 10;
      double Grav = 0.05;
      double ecP = 1.0;
      double ecW = 0.5;
      simu.setL(Lx, Ly);
      simu.setGRA(Grav);
      simu.setECp(ecP);
      simu.setECw(ecW);
      double x1 = 0;
      double x2 = Lx;
      double y1 = 0;
      double y2 = Ly;
      double vx = 0.0;
      double vy = 0.0;
      double vth = 0.1;
      simu.addParticles(TypeInj.RECTANGLE, TypeInj.MAXWELL, TypeInj.RANDOM, 200 , 0.2  , x1, x2, y1, y2,  vx, vy, vth, 1);
      simu.addParticles(TypeInj.RECTANGLE, TypeInj.MAXWELL, TypeInj.RANDOM, 500 , 0.05 , x1, x2, y1, y2,  vx, vy, vth, 2);
      simu.repaint();
    }

}
