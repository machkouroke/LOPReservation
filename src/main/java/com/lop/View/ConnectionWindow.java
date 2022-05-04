package com.lop.View;


/*
 * Created by JFormDesigner on Fri Apr 22 18:04:36 WET 2022
 */


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author unknown
 */
public class ConnectionWindow extends JFrame {
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel welcome;
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel userNameLabel;
    private JTextField usernameInput;
    private JLabel passwordLabel;
    private JTextField passwordInput;
    private JPanel buttonBar;
    private JButton connectButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    public ConnectionWindow() {
        try {
            initComponents();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myInit();
    }

    private void initComponents() throws IOException {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        welcome = new JLabel();
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        userNameLabel = new JLabel();
        usernameInput = new JTextField();
        passwordLabel = new JLabel();
        passwordInput = new JTextField();
        buttonBar = new JPanel();
        connectButton = new JButton();

        //======== this ========
        setMinimumSize(null);
        setMaximizedBounds(null);
        File file =  new File("test");
        System.out.println(file.getAbsolutePath());
        setIconImage(ImageIO.read(new File("src/main/java/com/lop/View/picture/lop.png")));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setBackground(Color.black);
            panel1.setLayout(new CardLayout());

            //---- welcome ----
            welcome.setText("Bienvenue \u00e0 LOPReservation");
            welcome.setHorizontalAlignment(SwingConstants.CENTER);
            welcome.setAlignmentY(-2.5F);
            panel1.add(welcome, "card1");
        }
        contentPane.add(panel1, BorderLayout.PAGE_START);

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout(10, 0));

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridLayout(2, 2, 0, 20));

                //---- userNameLabel ----
                userNameLabel.setText("Nom d'utilisateur");
                userNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
                contentPanel.add(userNameLabel);
                contentPanel.add(usernameInput);

                //---- passwordLabel ----
                passwordLabel.setText("Mot de Passe:");
                passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
                contentPanel.add(passwordLabel);
                contentPanel.add(passwordInput);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridLayout());

                //---- connectButton ----
                connectButton.setText("Connecter");
                buttonBar.add(connectButton);
            }
            dialogPane.add(buttonBar, BorderLayout.PAGE_END);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    public void myInit() {
        setSize(400, 200);
        setResizable(false);
        setVisible(true);
    }


}
