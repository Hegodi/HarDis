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
import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.*;


class WindowDiagnostics extends JFrame implements ActionListener{
    Simulation simu;
    Window parent;

    JButton BtnClo;
    JCheckBox[] CckId;
    int Ncck;
    String title;
    Monitor monitor;
    JLabel LblInf;

    public WindowDiagnostics(Window parent, Simulation simu) {
        this.setTitle("Diagnostics");
        this.parent = parent;
        this.simu = simu;
        Point pL = parent.getLocationOnScreen();
        Dimension pS = parent.getSize();
        this.setBounds(pL.x+pS.width,pL.y,500,400);
        this.setPreferredSize(new Dimension(500,420));
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
        Ncck = 5;
        CckId = new JCheckBox[Ncck];
        CckId[0] = new JCheckBox(MyColors.nameId1);
        CckId[1] = new JCheckBox(MyColors.nameId2);
        CckId[2] = new JCheckBox(MyColors.nameId3);
        CckId[3] = new JCheckBox(MyColors.nameId4);
        CckId[4] = new JCheckBox("Average");
        for(int i=0; i<Ncck; i++) {
          CckId[i].setSelected(true);
          CckId[i].addActionListener(this);
        panel1.add(CckId[i]);
        }
        panel.add(panel1);

        JPanel panel2 = new JPanel();
        LblInf = new JLabel("Number of particles: " + simu.getNP());   
        panel2.add(LblInf);
        panel.add(panel2);        

        title = "KINETIC ENERGY";

        JPanel panelB = new JPanel();
        //panelB.setPreferredSize(new Dimension(100,50));
        panelB.setLayout(new FlowLayout());
        panelB.add(BtnClo);        

        panel.add(panelB);

        this.add(panel);
        this.pack();
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
      LblInf.setText("Number of particles: " + simu.getNP());   
      monitor.update();
    }

    public void actionPerformed(ActionEvent e) {
        Object O = e.getSource();
        if (O == BtnClo) {
           parent.CckDia.setSelected(false);
           this.dispose();
        } else {
          for(int i=0; i<Ncck; i++) {
           if (O == CckId[i]) monitor.updateValues();
          }
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
      int[] Y5;
      int[][] Y;
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
        this.Y5 = new int[xlen];
	    this.Y = new int[Ncck][xlen];
 
        this.scaT = 1;
        this.ymax = 1.0;
        this.ymin = 0.0;
        
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
        for (int j=0; j<Ncck; j++) {
          if(CckId[j].isSelected()) {
            int ct = simu.getKe(j).size();
            List<Double> Ke = simu.getKe(j);
            for (int i=0; i<ct; i++) {
              if (i % scaT == 0) {
                if (Ke.get(i) > max) max = Ke.get(i);
              }
            }
          }
        }
        boolean done = false;
        while (!done) {
          done = true;
          if (max < ymax/2) { setScale(scaT, 0, ymax/2); done = false; }
          if (max > ymax)   { setScale(scaT, 0, 2*ymax); done = false; }
        }
        for (int j=0; j<Ncck; j++) {
          if (parent.CckId[j].isSelected()) {
            int ct = simu.getKe(j).size();
            List<Double> K = simu.getKe(j);
            n = 0;
            for (int i=0; i<ct; i++) {
              if (i % scaT == 0) {
                Y[j][n] = y0 + ylen - (int)(K.get(i)*scaY);
                n++;
                if (n >= xlen) {
                  this.scaT *= 2;
                  updateValues();
                }
              }
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

      
        for (int j=0; j<Ncck; j++) {
          if (parent.CckId[j].isSelected()) {
            if (j == 0)      g.setColor(MyColors.Id1);
            else if (j == 1) g.setColor(MyColors.Id2);
            else if (j == 2) g.setColor(MyColors.Id3);
            else if (j == 3) g.setColor(MyColors.Id4);
            else if (j == 4) g.setColor(MyColors.Id5);
            for(int i=1; i<n; i++) g.drawLine(x0+(i-1),Y[j][i-1],x0+i,Y[j][i]);
          }
        }
	    gF.drawImage(buffer,0,0,this);
      }
    }
}
