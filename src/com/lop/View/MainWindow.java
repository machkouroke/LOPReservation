/*
 * Created by JFormDesigner on Wed Apr 27 19:38:50 WET 2022
 */

package com.lop.View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class MainWindow extends JFrame {
    public MainWindow() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        label1 = new JLabel();
        panel2 = new JPanel();
        label2 = new JLabel();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //---- label1 ----
            label1.setText("LOPReservation");
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            dialogPane.add(label1, BorderLayout.NORTH);

            //======== panel2 ========
            {
                panel2.setAlignmentY(3.5F);
                panel2.setPreferredSize(new Dimension(152, 76));
                panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

                //---- label2 ----
                label2.setText("Veuillez s\u00e9lectionner une op\u00e9ration");
                panel2.add(label2);

                //---- button1 ----
                button1.setText("Ajouter");
                button1.setHorizontalTextPosition(SwingConstants.CENTER);
                panel2.add(button1);

                //---- button2 ----
                button2.setText("Supprimer");
                panel2.add(button2);

                //---- button3 ----
                button3.setText("text");
                panel2.add(button3);
            }
            dialogPane.add(panel2, BorderLayout.WEST);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JLabel label1;
    private JPanel panel2;
    private JLabel label2;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
