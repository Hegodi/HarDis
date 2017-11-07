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
    

    public WindowSettings(JFrame parent, Simulation simu) {
        this.setTitle("Settings");
        this.parent = parent;
        this.simu = simu;
        this.setBounds(0,0,460,200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        this.setVisible(true);
        parent.setEnabled(false);

        BtnSav = new JButton("Save");
        BtnCan = new JButton("Cancel");
        BtnSav.addActionListener(this);
        BtnCan.addActionListener(this);

        JLabel lbl1, lbl2, lbl3, lbl4;

        double Gra = simu.getGra();
        double ECp = simu.getECp();
        double ECw = simu.getECw();

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
          simu.setECp(ecPP);
          simu.setECw(ecPW);
          simu.setGRA(Grav);
          simu.repaint();
          parent.setEnabled(true);
          this.dispose();
        } else if (O == BtnCan) {
          parent.setEnabled(true);
          this.dispose();
        }
    }
}
