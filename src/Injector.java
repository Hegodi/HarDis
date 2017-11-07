import java.util.ArrayList;
import java.util.List;
public class Injector {

  private double x1,x2,y1,y2;  // Location of the injector
  private double InjR;         // Radius of the cycle
  private double vx,vy;        // Drift velocity
  private double vth;          // Thermal velocity
  private TypeInj shape;       // Shape of the injector
  private TypeInj velDst;      // Velocity distribution
  private TypeInj posDst;      // Particle distribution
  private int freq;            // Injection frequency (0 if only at the begining)
  private double R;            // Radius of the particle
  private int id;              // Id of the particles

  public Injector(TypeInj shape, TypeInj disP, TypeInj disV, double R, double x1, double x2, double y1, double y2, double vx, double vy, double vth, int id) {
    this.shape = shape;
    this.posDst = disP;
    this.velDst = disV;
    this.R  = R;
    this.x1 = x1+R;
    this.x2 = x2-R;
    this.y1 = y1+R;
    this.y2 = y2-R;
    this.vx  = vx;
    this.vy  = vy;
    this.vth = vth;
    this.id = id;
  }

  public void inject(double px, int n, List<Particle> particles) {
    for (int i=0; i<n; i++) {
      Particle B = new Particle();
      B.R = R;
      B.rx = x1 + (x2-x1)*Math.random();
      B.ry = y1 + (y2-y1)*Math.random();
      B.vx = vx + vth*(1 - 2*Math.random());
      B.vy = vy + vth*(1 - 2*Math.random());
      B.id = id;
      B.Dpx = (int)(2*B.R/px);
      if (B.Dpx < 2) B.Dpx = 2;
        particles.add(B);
    }
  }
}
