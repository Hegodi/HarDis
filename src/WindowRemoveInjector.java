import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


class WindowRemoveInjector extends JFrame implements ActionListener{
    Simulation simu;
    Window parent;

    JButton BtnRem;
    JButton BtnCan;

    
    double Lx, Ly;

    public WindowRemoveInjector(Window parent, Simulation simu) {
        this.setTitle("Remove injectors");
        this.parent = parent;
        this.simu = simu;
        this.setLocation(0,0);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        parent.setEnabled(false);

        BtnRem = new JButton("Remove");
        BtnCan = new JButton("Cancel");
        BtnRem.addActionListener(this);
        BtnCan.addActionListener(this);

        JLabel lbl1, lbl2, lbl3, lbl4;

        JPanel panelB = new JPanel();
        panelB.setLayout(new FlowLayout());
        panelB.add(BtnRem);        
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
        if (O == BtnRem) {

          simu.repaint();
          parent.setEnabled(true);
          this.dispose();
        } else if (O == BtnCan) {
          parent.setEnabled(true);
          this.dispose();
        }
    }
}
