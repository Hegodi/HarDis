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
import java.awt.event.*;


class WindowAddDeleter extends JFrame implements ActionListener{
    Simulation simu;
    Window parent;

    JButton BtnAdd;
    JButton BtnCan;

    JTextField TxtX1;
    JTextField TxtX2;
    JTextField TxtY1;
    JTextField TxtY2;
    
    double Lx, Ly;

    public WindowAddDeleter(Window parent, Simulation simu) {
        this.setTitle("Add deleter");
        this.parent = parent;
        this.simu = simu;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        parent.setEnabled(false);

        BtnAdd = new JButton("Add");
        BtnCan = new JButton("Cancel");
        BtnAdd.addActionListener(this);
        BtnCan.addActionListener(this);

        JLabel lbl1, lbl2, lbl3, lbl4;

        Lx = simu.getLx();
        Ly = simu.getLy();
        String x1  = Double.toString(2*Lx/5);
        String x2  = Double.toString(3*Lx/5);
        String y1  = Double.toString(0);
        String y2  = Double.toString(Ly/20);

        JPanel panel4 = new JPanel();
        TxtX1 = new JTextField(x1);
        TxtX1.setPreferredSize(new Dimension(40, 25));
        TxtX2 = new JTextField(x2);
        TxtX2.setPreferredSize(new Dimension(40, 25));
        TxtY1 = new JTextField(y1);
        TxtY1.setPreferredSize(new Dimension(40, 25));
        TxtY2 = new JTextField(y2);
        TxtY2.setPreferredSize(new Dimension(40, 25));
        lbl1 = new JLabel("xmin:");
        lbl2 = new JLabel("xmax:");
        lbl3 = new JLabel("ymin:");
        lbl4 = new JLabel("ymax:");
        panel4.add(lbl1);
        panel4.add(TxtX1);
        panel4.add(lbl2);
        panel4.add(TxtX2);
        panel4.add(lbl3);
        panel4.add(TxtY1);
        panel4.add(lbl4);
        panel4.add(TxtY2);
        panel.add(panel4);

        JPanel panelB = new JPanel();
        panelB.setLayout(new FlowLayout());
        panelB.add(BtnAdd);        
        panelB.add(BtnCan);        
        panel.add(panelB);

        this.add(panel);
        this.pack();

        Point pL = parent.getLocationOnScreen();
        Dimension pS = parent.getSize();
        Dimension mS = this.getSize();
        this.setLocation(pL.x+pS.width/2 - mS.width/2, pL.y+pS.height/2 - mS.height/2);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter()
        {
          public void windowClosing(WindowEvent e) {
            parent.setEnabled(true);
          }
        });
    }

    public void actionPerformed(ActionEvent e) {
        Object O = e.getSource();
        if (O == BtnAdd) {
          double x1 = Double.valueOf(TxtX1.getText());
          double x2 = Double.valueOf(TxtX2.getText());
          double y1 = Double.valueOf(TxtY1.getText());
          double y2 = Double.valueOf(TxtY2.getText());

          simu.addDeleter(x1, x2, (Ly-y2), (Ly-y1));
          simu.repaint();
          parent.setEnabled(true);
          this.dispose();
        } else if (O == BtnCan) {
          parent.setEnabled(true);
          this.dispose();
        }
    }
}
