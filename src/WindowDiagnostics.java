import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.*;


class WindowDiagnostics extends JFrame implements ActionListener{
    Simulation simu;
    Window parent;

    JButton BtnClo;
    JCheckBox CckId1;
    JCheckBox CckId2;
    JCheckBox CckId3;
    JCheckBox CckId4;
    String title;
    Monitor monitor;

    public WindowDiagnostics(Window parent, Simulation simu) {
        this.setTitle("Diagnostics");
        this.parent = parent;
        this.simu = simu;
        Point pL = parent.getLocationOnScreen();
        Dimension pS = parent.getSize();
        this.setBounds(pL.x+pS.width,pL.y,500,400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        monitor = new Monitor(this);
        monitor.setBounds(0,0,500,300);
        panel.add(monitor);

        BtnClo = new JButton("Close");
        BtnClo.addActionListener(this);

        JPanel panel1 = new JPanel();
        CckId1 = new JCheckBox(MyColors.nameId1);
        CckId2 = new JCheckBox(MyColors.nameId2);
        CckId3 = new JCheckBox(MyColors.nameId3);
        CckId4 = new JCheckBox(MyColors.nameId4);
        CckId1.setSelected(true);
        CckId2.setSelected(true);
        CckId3.setSelected(true);
        CckId4.setSelected(true);
        CckId1.addActionListener(this);
        CckId2.addActionListener(this);
        CckId3.addActionListener(this);
        CckId4.addActionListener(this);
        panel1.add(CckId1);
        panel1.add(CckId2);
        panel1.add(CckId3);
        panel1.add(CckId4);
        panel.add(panel1);

        title = "KINETIC ENERGY";

        JPanel panelB = new JPanel();
        //panelB.setPreferredSize(new Dimension(100,50));
        panelB.setLayout(new FlowLayout());
        panelB.add(BtnClo);        
        panel.add(panelB);

        this.add(panel);
        //this.pack();
        this.setVisible(true);
        monitor.init();

        this.addWindowListener(new WindowAdapter()
        {
          public void windowClosing(WindowEvent e) {
            parent.CckDia.setSelected(false);
          }
        });

    }

    public void clear() {
      monitor.clear();
      monitor.update();
    }

    public void updateValues() {
      monitor.updateValues();
      monitor.update();
    }

    public void actionPerformed(ActionEvent e) {
        Object O = e.getSource();
        if (O == BtnClo) {
           parent.CckDia.setSelected(false);
           this.dispose();
        } else if (O == CckId1 || O == CckId2 || O == CckId3 || O == CckId4) {
           this.updateValues();
        }
    }

    private class Monitor extends Canvas {
      WindowDiagnostics parent;
      int height, width;
      Image buffer;
      Graphics g;
      Color colorBG;

      int[] Y1;
      int[] Y2;
      int[] Y3;
      int[] Y4;
      int n;

      int xlen, ylen;
      final int x0 = 15;
      final int y0 = 15;
      double ymin, ymax;
      double scaY;
      int scaT;

      public Monitor(WindowDiagnostics parent) {this.parent = parent;};

      public void init() {
	    Dimension D = getSize();
	    this.width = D.width;
	    this.height  = D.height;
	    this.colorBG = new Color(000,000,000);

        this.xlen = width-2*x0;
        this.ylen = height-2*y0;
        this.Y1 = new int[xlen];
        this.Y2 = new int[xlen];
        this.Y3 = new int[xlen];
        this.Y4 = new int[xlen];
 
        this.scaT = 1;
        
        updateValues();
        
      }

      public void setScale(int scaT, double ymin, double ymax) {
        this.ymin = ymin;
        this.ymax = ymax;
        this.scaY = ((double)ylen)/(ymax-ymin);
        this.scaT = scaT;
        if (this.scaT < 1) this.scaT = 1;
      }

      public void updateValues() {
        double max = 0;
        int ct = simu.getKe(1).size();
        for (int j=0; j<4; j++) {
          List<Double> Ke = simu.getKe(j);
          for (int i=0; i<ct; i++) {
            if (i % scaT == 0) {
              if (Ke.get(i) > max) max = Ke.get(i);
            }
          }
        }
        this.ymax = ((int)(max/0.1)+1)*0.1;
        setScale(scaT, 0, ymax);
        n = 0;
        for (int i=0; i<ct; i++) {
          if (i % scaT == 0) {
			if (parent.CckId1.isSelected()) {
              List<Double> K = simu.getKe(0);
              Y1[n] = y0 + ylen - (int)(K.get(i)*scaY);
            }
			if (parent.CckId2.isSelected()) {
              List<Double> K = simu.getKe(1);
              Y2[n] = y0 + ylen - (int)(K.get(i)*scaY);
            }
			if (parent.CckId3.isSelected()) {
              List<Double> K = simu.getKe(2);
              Y3[n] = y0 + ylen - (int)(K.get(i)*scaY);
            }
			if (parent.CckId4.isSelected()) {
              List<Double> K = simu.getKe(3);
              Y4[n] = y0 + ylen - (int)(K.get(i)*scaY);
            }
            n++;
            if (n >= xlen) {
              this.scaT *= 2;
              updateValues();
            }
          }
        }
        this.update();
      }

      public void clear() {
        this.n = 0;
      }


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
        g.setColor(colorBG);
        g.fillRect(0,0,width,height);	
    
        g.setColor(Color.WHITE);
        g.fillRect(x0,y0,2,ylen);
        g.fillRect(x0,height - y0,xlen,2);
        g.drawString(title, width-150,20);

        if (parent.CckId1.isSelected()) {
          g.setColor(MyColors.Id1);
          for(int i=1; i<n; i++) g.drawLine(x0+(i-1),Y1[i-1],x0+i,Y1[i]);
        }
        if (parent.CckId2.isSelected()) {
          g.setColor(MyColors.Id2);
          for(int i=1; i<n; i++) g.drawLine(x0+(i-1),Y2[i-1],x0+i,Y2[i]);
        }
        if (parent.CckId3.isSelected()) {
          g.setColor(MyColors.Id3);
          for(int i=1; i<n; i++) g.drawLine(x0+(i-1),Y3[i-1],x0+i,Y3[i]);
        }
        if (parent.CckId4.isSelected()) {
          g.setColor(MyColors.Id4);
          for(int i=1; i<n; i++) g.drawLine(x0+(i-1),Y4[i-1],x0+i,Y4[i]);
        }
	    gF.drawImage(buffer,0,0,this);
      }
    }
}
