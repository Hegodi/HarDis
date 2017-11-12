/*
  This file is part of HARDIS.
  
  HARDIS is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  
  HARDIS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  
  You should have received a copy of the GNU General Public License
  along with Hardis 1.0.  If not, see <http://www.gnu.org/licenses/>.

 ---------------------------------------------------------------------

  Author: Diego Gonzalez.
  Email: diegonher@gmail.com
  November 2017

*/
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Injector {

  private int n;
  private double x1,x2,y1,y2;  // Location of the injector
  private double InjR;         // Radius of the cycle
  private double vx,vy;        // Drift velocity
  private double vth;          // Thermal velocity
  private int freq;            // Injection frequency (0 if only at the begining)
  private double R;            // Radius of the particle
  private int id;              // Id of the particles

  private Random rand;

  public Injector(int n, double R, double x1, double x2, double y1, double y2, double vx, double vy, double vth, int id) {
    this.n = n;
    this.R  = R;
    this.x1 = x1;
    this.x2 = x2;
    this.y1 = y1;
    this.y2 = y2;
    this.vx  = vx;
    this.vy  = vy;
    this.vth = vth;
    this.id = id;
    this.freq = 0;
    rand = new Random(id);
  }

  public Injector(int n, double R, double x1, double x2, double y1, double y2, double vx, double vy, double vth, int id, int freq) {
    this.n = n;
    this.R  = R;
    this.x1 = x1;
    this.x2 = x2;
    this.y1 = y1;
    this.y2 = y2;
    this.vx  = vx;
    this.vy  = vy;
    this.vth = vth;
    this.id = id;
    this.freq = freq;
    rand = new Random(id);
  }

  public double getX1() { return x1;}
  public double getX2() { return x2;}
  public double getY1() { return y1;}
  public double getY2() { return y2;}
  public int    getId() { return id;}

  public void inject(double px, List<Particle> particles) {
    for (int i=0; i<n; i++) {
      Particle B = new Particle();
      B.R = R;
      B.rx = x1 + (x2-x1)*rand.nextDouble();
      B.ry = y1 + (y2-y1)*rand.nextDouble();
      B.vx = vx + vth*(1 - 2*rand.nextDouble());
      B.vy = vy + vth*(1 - 2*rand.nextDouble());
      B.id = id;
      B.Dpx = (int)(2*B.R/px);
      if (B.Dpx < 2) B.Dpx = 2;
        particles.add(B);
    }
  }

  public void inject(int cycle, double px, List<Particle> particles) {
    if (cycle % freq == 0) inject(px, particles);
  }
}
