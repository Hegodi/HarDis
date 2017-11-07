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
    JFrame parent;

    JButton BtnSav;
    JButton BtnCan;

    JTextField TxtGra;
    JTextField TxtECp;
    JTextField TxtECw;
    JTextField TxtLx;
    JTextField TxtLy;
    

    public WindowSettings(JFrame parent, Simulation simu) {
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
        BtnSav.addActionListener(this);
        BtnCan.addActionListener(this);

        JLabel lbl1, lbl2, lbl3, lbl4;

        double Gra = simu.getGra();
        double ECp = simu.getECp();
        double ECw = simu.getECw();
        double Lx  = simu.getLx();
        double Ly  = simu.getLy();

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

        JPanel panelB = new JPanel();
        panelB.setLayout(new FlowLayout());
        panelB.add(BtnSav);        
        panelB.add(BtnCan);        
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

    public void actionPerformed(ActionEvent e) {
        Object O = e.getSource();
        if (O == BtnSav) {
          double ecPP = Double.valueOf(TxtECp.getText());
          double ecPW = Double.valueOf(TxtECw.getText());
          double Grav = Double.valueOf(TxtGra.getText());
          double Lx   = Double.valueOf(TxtLx.getText());
          double Ly   = Double.valueOf(TxtLy.getText());
          simu.setECp(ecPP);
          simu.setECw(ecPW);
          simu.setGRA(Grav);
          simu.setL(Lx, Ly);
          simu.repaint();
          parent.setEnabled(true);
          this.dispose();
        } else if (O == BtnCan) {
          parent.setEnabled(true);
          this.dispose();
        }
    }
}
