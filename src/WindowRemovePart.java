import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


class WindowRemovePart extends JFrame implements ActionListener{
    Simulation simu;
    Window parent;

    JButton BtnRem;
    JButton BtnCan;

    JTextField TxtX1;
    JTextField TxtX2;
    JTextField TxtY1;
    JTextField TxtY2;
    JCheckBox[] CckIds;
    
    double Lx, Ly;

    public WindowRemovePart(Window parent, Simulation simu) {
        this.setTitle("Remove particles");
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

        Lx = simu.getLx();
        Ly = simu.getLy();
        String x1  = Double.toString(0);
        String x2  = Double.toString(Lx);
        String y1  = Double.toString(0);
        String y2  = Double.toString(Ly);
        JPanel panel1 = new JPanel();
        CckIds = new JCheckBox[4];
        CckIds[0] = new JCheckBox(MyColors.nameId1);
        CckIds[1] = new JCheckBox(MyColors.nameId2);
        CckIds[2] = new JCheckBox(MyColors.nameId3);
        CckIds[3] = new JCheckBox(MyColors.nameId4);
        for(int i=0; i<MyColors.MAX_IDS; i++) panel1.add(CckIds[i]);
        panel.add(panel1);

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
          double x1 = Double.valueOf(TxtX1.getText());
          double x2 = Double.valueOf(TxtX2.getText());
          double y1 = Double.valueOf(TxtY1.getText());
          double y2 = Double.valueOf(TxtY2.getText());

          for (int i=0; i<MyColors.MAX_IDS; i++) {
            if (CckIds[i].isSelected()) simu.removeParticles(i+1, x1, x2, (Ly-y2), (Ly-y1));
          }
          simu.repaint();
          parent.setEnabled(true);
          this.dispose();
        } else if (O == BtnCan) {
          parent.setEnabled(true);
          this.dispose();
        }
    }
}
