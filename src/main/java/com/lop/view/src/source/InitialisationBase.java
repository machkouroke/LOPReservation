package com.lop.view.src.source;

import com.lop.controller.ConfigController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class InitialisationBase extends JFrame {
    private JPanel contentPane;
    private JTextField textHost;
    private JTextField textUserName;
    private JTextField textPwd;

    public InitialisationBase() throws IOException, ClassNotFoundException {
        init();
        setVisible(true);
    }
    public void init() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 438, 247);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Host");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel.setBounds(24, 68, 57, 26);
        contentPane.add(lblNewLabel);

        textHost = new JTextField();
        textF(textHost,69);

        JLabel lblNewLabel_1 = new JLabel("Nom d'utilsateur");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_1.setBounds(24, 99, 104, 39);
        contentPane.add(lblNewLabel_1);

        textUserName = new JTextField();
        textF(textUserName,106);

        JLabel lblNewLabel_2 = new JLabel("Mot de passe");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_2.setBounds(24, 133, 92, 32);
        contentPane.add(lblNewLabel_2);

        textPwd = new JTextField();
        textF(textPwd,137);

        JButton buttEnter = new JButton("Entrer");
        buttEnter.setBorder(null);
        buttEnter.setBounds(24, 176, 370, 23);
        contentPane.add(buttEnter);

        JLabel lblNewLabel_3 = new JLabel("Nouveau ici ??\r\n");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_3.setBounds(148, 11, 92, 14);
        contentPane.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Entrez les informations de votre base MySQL.");
        lblNewLabel_4.setBounds(55, 36, 288, 14);
        contentPane.add(lblNewLabel_4);
    }
    public void textF(JTextField tf,int y){
        tf.setBounds(167, y, 227, 26);
        contentPane.add(tf);
        tf.setColumns(10);
    }
}
