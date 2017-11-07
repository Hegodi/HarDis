import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


class WindowAddInjector extends JFrame implements ActionListener{
    Simulation simu;
    JFrame parent;

    JButton BtnAdd;
    JButton BtnCan;

    JTextField TxtFreq;
    JTextField TxtNum;
    JTextField TxtSiz;
    JTextField TxtVdx;
    JTextField TxtVdy;
    JTextField TxtVth;
    JTextField TxtX1;
    JTextField TxtX2;
    JTextField TxtY1;
    JTextField TxtY2;
    JComboBox ComCol;
    

    public WindowAddInjector(JFrame parent, Simulation simu) {
        this.setTitle("Add injector");
        this.parent = parent;
        this.simu = simu;
        this.setLocation(0,0);
        //this.setBounds(0,0,460,200);
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

        double Lx = simu.getLx();
        double Ly = simu.getLy();
        String np  = Integer.toString(5);
        String r   = Double.toString(0.1);
        String vx  = Double.toString(0.1);
        String vy  = Double.toString(0.0);
        String vth = Double.toString(0.1);
        String x1  = Double.toString(0);
        String x2  = Double.toString(1.0);
        String y1  = Double.toString(0);
        String y2  = Double.toString(1.0);
        String freq = Integer.toString(10);

        JPanel panel0 = new JPanel();
        //panel1.setAlignmentX( Component.RIGHT_ALIGNMENT );
        TxtFreq = new JTextField(freq);
        TxtFreq.setPreferredSize(new Dimension(50, 25));
        lbl1 = new JLabel("Injection frequency:");
        panel0.add(lbl1);
        panel0.add(TxtFreq);
        panel.add(panel0);

        JPanel panel1 = new JPanel();
        //panel1.setAlignmentX( Component.RIGHT_ALIGNMENT );
        TxtNum = new JTextField(np);
        TxtNum.setPreferredSize(new Dimension(50, 25));
        TxtSiz = new JTextField(r);
        TxtSiz.setPreferredSize(new Dimension(50, 25));
        String[] strC = {"Red", "Blue", "Green"};
        ComCol = new JComboBox(strC);
        lbl1 = new JLabel("Number:");
        lbl2 = new JLabel("Radius:");        
        lbl3 = new JLabel("Color:");        
        panel1.add(lbl1);
        panel1.add(TxtNum);
        panel1.add(lbl2);
        panel1.add(TxtSiz);
        panel1.add(lbl3);
        panel1.add(ComCol);
        panel.add(panel1);

        JPanel panel2 = new JPanel();
        //panel2.setAlignmentX( Component.RIGHT_ALIGNMENT );
        TxtVdx = new JTextField(vx);
        TxtVdx.setPreferredSize(new Dimension(50, 25));
        TxtVdy = new JTextField(vy);
        TxtVdy.setPreferredSize(new Dimension(50, 25));
        TxtVth = new JTextField(vth);
        TxtVth.setPreferredSize(new Dimension(50, 25));
        lbl1 = new JLabel("Velocity  x:");
        lbl2 = new JLabel("y:");
        lbl3 = new JLabel("th:");
        panel2.add(lbl1);
        panel2.add(TxtVdx);
        panel2.add(lbl2);
        panel2.add(TxtVdy);
        panel2.add(lbl3);
        panel2.add(TxtVth);
        panel.add(panel2);

        //JPanel panel3 = new JPanel();
        ////panel3.setAlignmentX( Component.RIGHT_ALIGNMENT );
        //String[] str = {"Rectangle", "Circle"};
        //ComDis = new JComboBox(str);
        //lbl1 = new JLabel("Shape:");
        //panel3.add(lbl1);
        //panel3.add(ComDis);
        //panel.add(panel3);

        JPanel panel4 = new JPanel();
        //panel4.setAlignmentX( Component.RIGHT_ALIGNMENT );
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
          int n     = Integer.valueOf(TxtNum.getText());
          double R  = Double.valueOf(TxtSiz.getText());
          double x1 = Double.valueOf(TxtX1.getText());
          double x2 = Double.valueOf(TxtX2.getText());
          double y1 = Double.valueOf(TxtY1.getText());
          double y2 = Double.valueOf(TxtY2.getText());
          double vx = Double.valueOf(TxtVdx.getText());
          double vy = Double.valueOf(TxtVdy.getText());
          double vth = Double.valueOf(TxtVth.getText());
          int freq = Integer.valueOf(TxtFreq.getText());
          int id = 1;
          String strColor = (String)ComCol.getSelectedItem();
          if      (strColor == "Red")   id = 1;
          else if (strColor == "Green") id = 2;
          else if (strColor == "Blue")  id = 3;


          simu.addInjector(freq, n , R , x1, x2, y1, y2,  vx, vy, vth, id);
          simu.repaint();
          parent.setEnabled(true);
          this.dispose();
        } else if (O == BtnCan) {
          parent.setEnabled(true);
          this.dispose();
        }
    }
}