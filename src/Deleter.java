import java.util.ArrayList;
import java.util.List;
public class Deleter {

  private double x1,x2,y1,y2;  // Location of the deleter

  public Deleter(double x1, double x2, double y1, double y2) {
    this.x1 = x1;
    this.x2 = x2;
    this.y1 = y1;
    this.y2 = y2;
  }

  public double getX1() { return x1;}
  public double getX2() { return x2;}
  public double getY1() { return y1;}
  public double getY2() { return y2;}

  public void delete(List<Particle> particles) {
    int n = particles.size();
    int ind = 0;
    for(int i=0; i<n; i++) {
      Particle p = particles.get(ind);
      if (p.rx > x1 && p.rx < x2 && p.ry > y1 && p.ry < y2) {
        particles.remove(ind);
      } else {
        ind++;
      }
    }
  }

}
