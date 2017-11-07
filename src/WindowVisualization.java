import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/* To Do:
     - Collisions between particles
     - Friction Particle-Particle
     - Friction Particle-Wall
*/


class WindowVisualization extends JFrame implements ActionListener{
    Simulation simu;
    JFrame parent;

    JButton BtnSav;
    JButton BtnCan;


    public WindowVisualization(JFrame parent, Simulation simu) {
        this.setTitle("Visualization");
        this.parent = parent;
        this.simu = simu;
        this.setLocation(0,0);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));


        BtnSav = new JButton("Save");
        BtnCan = new JButton("Cancel");
        BtnSav.addActionListener(this);
        BtnCan.addActionListener(this);

        
        JPanel panelB = new JPanel();
        panelB.setLayout(new FlowLayout());
        panelB.add(BtnSav);        
        panelB.add(BtnCan);        
        panel.add(panelB);

        this.add(panel);
        this.pack();
        this.setVisible(true);

        parent.setEnabled(false);
      
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
          parent.setEnabled(true);
          this.dispose();
        } else if (O == BtnCan) {
          parent.setEnabled(true);
          this.dispose();
        }
    }
}
