import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


class WindowDiagnostics extends JFrame implements ActionListener{
    Simulation simu;
    Window parent;

    JButton BtnClo;

    public WindowDiagnostics(Window parent, Simulation simu) {
        this.setTitle("Diagnostics");
        this.parent = parent;
        this.simu = simu;
        //this.setLocation(100,10);
        this.setBounds(0,0,460,200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        BtnClo = new JButton("Close");
        BtnClo.addActionListener(this);

        JLabel lbl1, lbl2, lbl3, lbl4;

        JPanel panelB = new JPanel();
        panelB.setLayout(new FlowLayout());
        panelB.add(BtnClo);        
        panel.add(panelB);

        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter()
        {
          public void windowClosing(WindowEvent e) {
            parent.CckDia.setSelected(false);
          }
        });

    }

    public void actionPerformed(ActionEvent e) {
        Object O = e.getSource();
        if (O == BtnClo) {
           parent.CckDia.setSelected(false);
           this.dispose();
        }
    }
}
