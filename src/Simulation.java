import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Simulation extends Canvas{
 
    private int height, width;

    private Image buffer;
    private Graphics g;
    private Color colorBG;
    private Color colorBG2;
    private int marginX;
    private int marginY;
    private int widthS;
    private int heightS;
 
    // Simulation data
    private int cycle = 0;
    private List<Particle> particles = new ArrayList<>();  // List of all particles
    private List<Injector> injectors = new ArrayList<>();  // List of injectors
    private double Lx, Ly;   // Size of the box
    private double px;       // Size of each pixel
    private double dt;       // Time step
    private double ecPP;     // Elastic coefficient particle-particle
    private double ecPW;     // Elastic coefficient particle-wall
    private double GRAVITY;  // Gravity

    // Diagnostics
    List<List<Double>> Ke;
    List<List<Integer>> Np;

    private final int MAX_ID = 4;
    /////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////
    // Methods
    public Simulation() { }

    public void init(double Lx, double Ly) {
	  Dimension D = getSize();
	  this.width = D.width;
	  this.height  = D.height;
	  this.colorBG = new Color(000,000,000);
	  this.colorBG2 = new Color(100,100,100);
      this.Lx = Lx;
      this.Ly = Ly;
      double pxX = Lx/this.width;
      double pxY = Ly/this.height;
      this.px = (pxX < pxY ? pxY : pxX);
      this.widthS  = (int)(Lx/this.px);
      this.heightS = (int)(Ly/this.px);
      this.marginX = (int)((width - widthS)/2.0);
      this.marginY = (int)((height - heightS)/2.0);
      this.dt = 0.1;
      this.ecPP = 0.99; 
      this.ecPW = 0.5; 
      this.GRAVITY = 0.05;

      Ke = new ArrayList<List<Double>>();
      Np = new ArrayList<List<Integer>>();
      Ke.add(new ArrayList<Double>());
      Ke.add(new ArrayList<Double>());
      Ke.add(new ArrayList<Double>());
      Ke.add(new ArrayList<Double>());
      Np.add(new ArrayList<Integer>());
      Np.add(new ArrayList<Integer>());
      Np.add(new ArrayList<Integer>());
      Np.add(new ArrayList<Integer>());
    }

    public void addParticles(int n, double R, double x1, double x2, double y1, double y2, double vx, double vy, double vth, int id) {
      Injector inj = new Injector(n, R, x1, x2, y1, y2, vx, vy, vth, id);
      inj.inject(px, particles);
      relax();
    }

    public void addInjector(int freq, int n, double R, double x1, double x2, double y1, double y2, double vx, double vy, double vth, int id) {
      Injector inj = new Injector(n, R, x1, x2, y1, y2, vx, vy, vth, id, freq);
      injectors.add(inj);
    }

    void moveParticles() {
      for (Injector inj : injectors) {
        inj.inject(cycle, px, particles);
      }

      int np = particles.size();
      for(int p1=0; p1<np; p1++) {
		Particle B1 = particles.get(p1);
        for(int p2=p1+1; p2<np; p2++) {
		  Particle B2 = particles.get(p2);
          double dx = B1.rx - B2.rx;
          double dy = B1.ry - B2.ry;
          double dst2 = dx*dx + dy*dy;
          double md = Math.sqrt(dst2);
          
          if (md < B1.R+B2.R) {
            double dxh = (dx - (B1.R+B2.R)*dx/md)*0.5;
            double dyh = (dy - (B1.R+B2.R)*dy/md)*0.5;
            B1.rx -= dxh;
            B1.ry -= dyh;
            B2.rx += dxh;
            B2.ry += dyh;
            double dpx = dx/md;
            double dpy = dy/md;
            double dnx =  dpy;
            double dny = -dpx;
            double vp1 = B1.vx*dpx + B1.vy*dpy;
            double vn1 = B1.vx*dnx + B1.vy*dny;
            double vp2 = B2.vx*dpx + B2.vy*dpy;
            double vn2 = B2.vx*dnx + B2.vy*dny;
            double tmp = vp1;
            vp1 = ecPP*vp2;
            vp2 = ecPP*tmp;
            B1.vx = vp1*dpx + vn1*dnx;
            B1.vy = vp1*dpy + vn1*dny;
            B2.vx = vp2*dpx + vn2*dnx;
            B2.vy = vp2*dpy + vn2*dny;
          }
        }
      }
      for(int p1=0; p1<np; p1++) {
		Particle B1 = particles.get(p1);
        B1.rx += B1.vx*dt;
        B1.ry += B1.vy*dt;
        B1.vy += GRAVITY*dt;
        if (B1.rx <= B1.R) {
          B1.rx = B1.R;
          if(B1.vx < 0) B1.vx *= -ecPW;
        } else if (B1.rx >= Lx-B1.R) {
          B1.rx = Lx-B1.R;
          if(B1.vx > 0) B1.vx *= -ecPW;
        }
        if (B1.ry <= B1.R) {
          B1.ry = B1.R;
          if (B1.vy < 0) B1.vy *= -ecPW;
        } else if (B1.ry >= Ly-B1.R) {
          B1.ry = Ly-B1.R;
          if (B1.vy > 0) B1.vy *= -ecPW;
        }
      }
      cycle++;
      update();
    }

    void relax() {
      int np = particles.size();
      boolean done = false;
      int itmax = 100;
      int it = 0;
      while (!done) {
        done = true;
        for(int p1=0; p1<np; p1++) {
		  Particle B1 = particles.get(p1);
          for(int p2=p1+1; p2<np; p2++) {
		    Particle B2 = particles.get(p2);
            double dx = B1.rx - B2.rx;
            double dy = B1.ry - B2.ry;
            double dst2 = dx*dx + dy*dy;
            double md = Math.sqrt(dst2);
          
            if (md < B1.R+B2.R) {
              done = false;
              double dxh = (dx - (B1.R+B2.R)*dx/md)*0.5;
              double dyh = (dy - (B1.R+B2.R)*dy/md)*0.5;
              B1.rx -= dxh;
              B1.ry -= dyh;
              B2.rx += dxh;
              B2.ry += dyh;
              double dpx = dx/md;
              double dpy = dy/md;
              double dnx =  dpy;
              double dny = -dpx;
              double vp1 = B1.vx*dpx + B1.vy*dpy;
              double vn1 = B1.vx*dnx + B1.vy*dny;
              double vp2 = B2.vx*dpx + B2.vy*dpy;
              double vn2 = B2.vx*dnx + B2.vy*dny;
              double tmp = vp1;
              vp1 = ecPP*vp2;
              vp2 = ecPP*tmp;
              B1.vx = vp1*dpx + vn1*dnx;
              B1.vy = vp1*dpy + vn1*dny;
              B2.vx = vp2*dpx + vn2*dnx;
              B2.vy = vp2*dpy + vn2*dny;
            }
          }
        }
        for(int p1=0; p1<np; p1++) {
		  Particle B1 = particles.get(p1);
          if (B1.rx <= B1.R) {
            B1.rx = B1.R;
            if(B1.vx < 0) B1.vx *= -ecPW;
          } else if (B1.rx >= Lx-B1.R) {
            B1.rx = Lx-B1.R;
            if(B1.vx > 0) B1.vx *= -ecPW;
          }
          if (B1.ry <= B1.R) {
            B1.ry = B1.R;
            if (B1.vy < 0) B1.vy *= -ecPW;
          } else if (B1.ry >= Ly-B1.R) {
            B1.ry = Ly-B1.R;
            if (B1.vy > 0) B1.vy *= -ecPW;
          }
        }
        it++;
        if (it > itmax) done = true;
      }
    }

    void diagnostics() {
      double[] K = new double[MAX_ID];
      int[] N = new int[MAX_ID];
      for(int i=0; i<MAX_ID; i++) {
        K[i] = 0;
        N[i] = 0;
      }
      for (Particle p : particles) {
        K[p.id-1] += p.vx*p.vx + p.vy*p.vy; 
        N[p.id-1]++;
      }
      for(int i=0; i<MAX_ID; i++) {
        Ke.get(i).add(K[i]);
        Np.get(i).add(N[i]);
      }
    }

    void clear() {
      particles.clear();
      injectors.clear();
      for (int i=0; i<MAX_ID; i++) {
        Ke.get(i).clear();
        Np.get(i).clear();
      }
      cycle = 0;
    }

    public void initPx() {
	  Dimension D = getSize();
	  this.width = D.width;
	  this.height  = D.height;
      double pxX = Lx/this.width;
      double pxY = Ly/this.height;
      this.px = (pxX < pxY ? pxY : pxX);
      this.widthS  = (int)(Lx/this.px);
      this.heightS = (int)(Ly/this.px);
      this.marginX = (int)((width - widthS)/2.0);
      this.marginY = (int)((height - heightS)/2.0);
      for (Particle p : particles) {
        p.Dpx = (int)(2*p.R/px);
      }
    }

    double getLx() {return Lx;}
    double getLy() {return Ly;}
    double getGra() {return GRAVITY;}
    double getECp() {return ecPP;}
    double getECw() {return ecPW;}
    double getDt() {return dt;}

    void setECw(double ecPW){ this.ecPW = ecPW;}
    void setECp(double ecPP){ this.ecPP = ecPP;}
    void setGRA(double GRAVITY){ this.GRAVITY = GRAVITY;}
    void setL(double Lx, double Ly) {
      this.Lx = Lx; 
      this.Ly = Ly;
      initPx();
      update();
    }
    void setDt(double dt) { this.dt = dt; }
    /////////////////////////////////////////////////////////////////
    // Graphics
    public void paint(Graphics g) {
	  draw(g);	
    }

    public void update() {
      Graphics g = getGraphics();
      draw(g);
    }


    public void draw(Graphics gF) {
	  buffer = createImage(width, height);
	  g = buffer.getGraphics();
	  g.setColor(colorBG2);
 	  g.fillRect(0,0,width,height);	
	  g.setColor(colorBG);
 	  g.fillRect(marginX,marginY,widthS,heightS);	

      for (Injector inj : injectors) {
	      int id = inj.getId();
          if (id == 1) {
	        g.setColor(Color.RED);
          } else if (id == 2) {
	        g.setColor(Color.GREEN);
          } else if (id == 3) {
	        g.setColor(Color.BLUE);
          }
          int x1 = marginX + (int)((inj.getX1())/px);
          int y1 = marginY + (int)((inj.getY1())/px);
          int x2 = marginX + (int)((inj.getX2())/px);
          int y2 = marginY + (int)((inj.getY2())/px);
          g.fillRect(x1,y1,(x2-x1),(y2-y1));
      }

      for (Particle p : particles) {
          if (p.id == 1) {
	        g.setColor(Color.RED);
          } else if (p.id == 2) {
	        g.setColor(Color.GREEN);
          } else if (p.id == 3) {
	        g.setColor(Color.BLUE);
          } else if (p.id == 4) {
	        g.setColor(Color.YELLOW);
          }
          int x = marginX + (int)((p.rx-p.R)/px);
          int y = marginY + (int)((p.ry-p.R)/px);
          g.fillOval(x,y,p.Dpx,p.Dpx);
      }

	  gF.drawImage(buffer,0,0,this);
    }
}
