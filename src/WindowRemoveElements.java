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
import java.awt.*; import javax.swing.*;
import java.awt.event.*;
import java.util.List;


class WindowRemoveElements extends JFrame implements ActionListener{
    Simulation simu;
    Window parent;

    JButton BtnRem;
    JButton BtnExi;
    JList   JlsEle;
    DefaultListModel LstEle;

    
    double Lx, Ly;

    public WindowRemoveElements(Window parent, Simulation simu) {
        this.setTitle("Remove elements");
        this.parent = parent;
        this.simu = simu;
        this.setBounds(0,0,400,200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        parent.setEnabled(false);

        LstEle = new DefaultListModel();
        JlsEle = new JList(LstEle);
        JlsEle.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(JlsEle);
        listScrollPane.setPreferredSize(new Dimension(300, 150));
        panel.add(listScrollPane);


        BtnRem = new JButton("Remove");
        BtnExi = new JButton("Return");
        BtnRem.addActionListener(this);
        BtnExi.addActionListener(this);

        JLabel lbl1, lbl2, lbl3, lbl4;

        JPanel panelB = new JPanel();
        panelB.setLayout(new FlowLayout());
        panelB.add(BtnRem);        
        panelB.add(BtnExi);        
        panel.add(panelB);

        this.add(panel);
        this.pack();

        Point pL = parent.getLocationOnScreen();
        Dimension pS = parent.getSize();
        Dimension mS = this.getSize();
        this.setLocation(pL.x+pS.width/2 - mS.width/2, pL.y+pS.height/2 - mS.height/2);
        this.setVisible(true);

        updateList();

        this.addWindowListener(new WindowAdapter()
        {
          public void windowClosing(WindowEvent e) {
            parent.setEnabled(true);
          }
        });
    }

    void updateList() {
          LstEle.clear();
          List<Injector> injectors = simu.getInjectors();
          for (Injector inj : injectors) {
            int id = inj.getId();
            String txt = "Injector: ";
            if (id ==1) txt = txt + MyColors.nameId1;
            if (id ==2) txt = txt + MyColors.nameId2;
            if (id ==3) txt = txt + MyColors.nameId3;
            if (id ==4) txt = txt + MyColors.nameId4;
            txt = txt + "  x=[" + inj.getX1() + ", "+inj.getX2()+"]";
            txt = txt + "  y=[" + (simu.getLy() - inj.getY1()) + ", "+(simu.getLy() - inj.getY2())+"]";
            LstEle.addElement(txt);
          }
          List<Deleter> deleters = simu.getDeleters();
          for (Deleter del : deleters) {
            String txt = "Deleter: ";
            txt = txt + "  x=[" + del.getX1() + ", "+del.getX2()+"]";
            txt = txt + "  y=[" + (simu.getLy() - del.getY1()) + ", "+(simu.getLy() - del.getY2())+"]";
            LstEle.addElement(txt);
          }
    }

    public void actionPerformed(ActionEvent e) {
        Object O = e.getSource();
        if (O == BtnRem) {
          int selItem = JlsEle.getSelectedIndex();
          int nInj = simu.getNumberInj();
          if (selItem >= 0) {
            if (selItem < nInj) simu.removeInjector(selItem);
            else simu.removeDeleter(selItem-nInj);
          }
          simu.repaint();
          updateList();
        } else if (O == BtnExi) {
          parent.setEnabled(true);
          this.dispose();
        }
    }
}
