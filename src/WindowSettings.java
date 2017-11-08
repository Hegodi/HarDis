import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/* To Do:
     - Collisions between particles
     - Friction Particle-Particle
     - Friction Particle-Wall
*/


class WindowSettings extends JFrame implements ActionListener{
    Simulation simu;
    Window parent;

    JButton BtnSav;
    JButton BtnCan;
    JButton BtnDef;

    JTextField TxtGra;
    JTextField TxtECp;
    JTextField TxtECw;
    JTextField TxtLx;
    JTextField TxtLy;
    JTextField TxtDt;
    

    public WindowSettings(Window parent, Simulation simu) {
        this.setTitle("Settings");
        this.parent = parent;
        this.simu = simu;
        this.setLocation(0,0);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        parent.setEnabled(false);

        BtnSav = new JButton("Save");
        BtnCan = new JButton("Cancel");
        BtnDef = new JButton("Default");
        BtnSav.addActionListener(this);
        BtnCan.addActionListener(this);
        BtnDef.addActionListener(this);

        JLabel lbl1, lbl2, lbl3, lbl4;

        double Gra = -simu.getGra();
        double ECp = simu.getECp();
        double ECw = simu.getECw();
        double Lx  = simu.getLx();
        double Ly  = simu.getLy();
        double Dt  = simu.getDt();

        JPanel panel0 = new JPanel();
        //panel1.setAlignmentX( Component.RIGHT_ALIGNMENT );
        TxtLx = new JTextField(Double.toString(Lx));
        TxtLx.setPreferredSize(new Dimension(50, 25));
        TxtLy = new JTextField(Double.toString(Ly));
        TxtLy.setPreferredSize(new Dimension(50, 25));
        lbl1 = new JLabel("Size of the domain  Lx:");
        lbl2 = new JLabel("Ly:");
        panel0.add(lbl1);
        panel0.add(TxtLx);
        panel0.add(lbl2);
        panel0.add(TxtLy);
        panel.add(panel0);

        JPanel panel1 = new JPanel();
        //panel1.setAlignmentX( Component.RIGHT_ALIGNMENT );
        TxtGra = new JTextField(Double.toString(Gra));
        TxtGra.setPreferredSize(new Dimension(50, 25));
        lbl1 = new JLabel("Gravity:");
        panel1.add(lbl1);
        panel1.add(TxtGra);
        panel.add(panel1);

        JPanel panel2 = new JPanel();
        //panel2.setAlignmentX( Component.RIGHT_ALIGNMENT );
        TxtECp = new JTextField(Double.toString(ECp));
        TxtECp.setPreferredSize(new Dimension(50, 25));
        lbl1 = new JLabel("Elastic coefficient Particle-Particle:");
        panel2.add(lbl1);
        panel2.add(TxtECp);
        panel.add(panel2);

        JPanel panel3 = new JPanel();
        TxtECw = new JTextField(Double.toString(ECw));
        TxtECw.setPreferredSize(new Dimension(50, 25));
        lbl1 = new JLabel("Elastic coefficient Particle-Wall:");
        panel3.add(lbl1);
        panel3.add(TxtECw);
        panel.add(panel3);

        JPanel panel4 = new JPanel();
        TxtDt = new JTextField(Double.toString(Dt));
        TxtDt.setPreferredSize(new Dimension(50, 25));
        lbl1 = new JLabel("Time step");
        panel4.add(lbl1);
        panel4.add(TxtDt);
        panel.add(panel4);

        JPanel panelB = new JPanel();
        panelB.setLayout(new FlowLayout());
        panelB.add(BtnSav);        
        panelB.add(BtnCan);        
        panelB.add(BtnDef);        
        panel.add(panelB);

        this.add(panel);
        this.pack();
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter()
        {
          public void windowClosing(WindowEvent e) {
            parent.setEnabled(true);
          }
        });
    }

    public void setDefault() {
      String ecPP = Double.toString(1.0);
      String ecPW = Double.toString(0.5);
      String Grav = Double.toString(0.05);
      String Lx   = Double.toString(10.0);
      String Ly   = Double.toString(10.0);
      String Dt   = Double.toString(0.1);
      TxtECp.setText(ecPP);
      TxtECw.setText(ecPW);
      TxtGra.setText(Grav);
      TxtLx.setText(Lx);
      TxtLy.setText(Ly);
      TxtDt.setText(Dt);
    }

    public void actionPerformed(ActionEvent e) {
        Object O = e.getSource();
        if (O == BtnSav) {
          double ecPP = Double.valueOf(TxtECp.getText());
          double ecPW = Double.valueOf(TxtECw.getText());
          double Grav = -Double.valueOf(TxtGra.getText());
          double Lx   = Double.valueOf(TxtLx.getText());
          double Ly   = Double.valueOf(TxtLy.getText());
          double Dt   = Double.valueOf(TxtDt.getText());
          simu.setECp(ecPP);
          simu.setECw(ecPW);
          simu.setGRA(Grav);
          simu.setL(Lx, Ly);
          simu.setDt(Dt);
          simu.repaint();
          parent.setEnabled(true);
          this.dispose();
        } else if (O == BtnCan) {
          parent.setEnabled(true);
          this.dispose();
        } else if (O == BtnDef) {
          setDefault(); 
        }
    }
}
