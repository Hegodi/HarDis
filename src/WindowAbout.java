import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


class WindowAbout extends JFrame implements ActionListener{
    Window parent;

    JButton BtnClo;

    double Lx, Ly;

    public WindowAbout(Window parent) {
        this.setTitle("About the code");
        this.parent = parent;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        parent.setEnabled(false);


        JTextArea TxtAbo = new JTextArea();
        TxtAbo.setPreferredSize(new Dimension(510, 200));
        TxtAbo.setEditable(false);
        TxtAbo.setLineWrap(true);
        TxtAbo.setMargin( new Insets(10,10,10,10) );
        Color c = parent.getBackground();
        TxtAbo.setBackground(c);
        String txt = "Hardis 1.0\n" + 
                     "\n" +
                     "License: this program has been released under GPL. See the file \"COPYING\" in the "+
                     "source code folder or see <http://www.gnu.org/licenses/>.\n" +
                     "\n" +
                     "Source code: https://github.com/Hegodi/HarDis\n" +
                     "\n" +
                     "Author: Diego Gonzalez (diegonher@gmail.com)\n" ;
        TxtAbo.setText(txt);
        panel.add(TxtAbo);

        BtnClo = new JButton("Close");
        BtnClo.addActionListener(this);

        JPanel panelB = new JPanel();
        panelB.setLayout(new FlowLayout());
        panelB.add(BtnClo);        
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
        if (O == BtnClo) {
          parent.setEnabled(true);
          this.dispose();
        }
    }
}
