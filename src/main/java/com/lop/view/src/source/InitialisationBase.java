package com.lop.view.src.source;


import com.lop.controller.Controller;
import com.lop.model.dao.Factory;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Morel Kouhossounon
 */
public class InitialisationBase extends JFrame {
    private JPanel contentPane;
    private JTextField textHost;
    private JTextField textUserName;
    private JTextField textPwd;

    public InitialisationBase() throws IOException {

        init();
        setVisible(true);
    }
    public void init() throws IOException {
        setResizable(false);
        setIconImage(ImageIO.read(new File("src/main/java/com/lop/View/picture/lop.png")));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 438, 247);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel host = new JLabel("Host");
        host.setBounds(24, 68, 57, 26);
        contentPane.add(host);

        textHost = new JTextField();
        textF(textHost,69);

        JLabel userName = new JLabel("Nom d'utilisateur");
        userName.setBounds(24, 99, 104, 26);
        contentPane.add(userName);

        textUserName = new JTextField();
        textF(textUserName,106);

        JLabel password = new JLabel("Mot de passe");
        password.setBounds(24, 133, 92, 26);
        contentPane.add(password);

        textPwd = new JTextField();
        textF(textPwd,137);

        JButton buttEnter = new JButton("Connexion");
        buttEnter.setBorder(null);
        buttEnter.setBounds(24, 176, 370, 23);
        contentPane.add(buttEnter);
        buttEnter.addActionListener(this::authenticate);

        JLabel configuration = new JLabel("<html><b>Configuration</b></html>", SwingConstants.CENTER);
        configuration.setBounds(148, 11, 100, 14);
        contentPane.add(configuration);

        JLabel instruction = new JLabel("Entrez les informations de votre base MySQL", SwingConstants.CENTER);
        instruction.setBounds(55, 36, 288, 14);
        contentPane.add(instruction);
    }

    public void authenticate(ActionEvent e) {
        String host = textHost.getText();
        String username = textUserName.getText();
        String pwd = textPwd.getText();

        try {
            Factory factory = new Factory(host, username, pwd);
            Controller controller = new Controller(factory);
            ConnectionWindow frame = new ConnectionWindow(controller);
            frame.setVisible(true);
            this.setVisible(false);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(this,exception.getMessage());
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void textF(JTextField tf,int y){
        tf.setBounds(167, y, 227, 26);
        contentPane.add(tf);
        tf.setColumns(10);

    }

}
