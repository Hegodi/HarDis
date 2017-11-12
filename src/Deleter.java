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
