package com.lop.view.src.source;



import com.lop.communication.Request;
import com.lop.controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author Machkour Oke
 */
public class ConnectionWindow extends JFrame {
    private final transient Controller controller;

    private JPanel welcomePanel;
    private JLabel welcome;
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel userNameLabel;
    private JTextField usernameInput;
    private JLabel passwordLabel;
    private JTextField passwordInput;
    private JPanel buttonBar;
    private JButton connectButton;

    public ConnectionWindow(Controller controller) {
        try {
            initComponents();
            setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.controller = controller;
        myInit();
    }

    private void initComponents() throws IOException {

        welcomePanel = new JPanel();
        welcome = new JLabel();
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        userNameLabel = new JLabel();
        usernameInput = new JTextField();
        passwordLabel = new JLabel();
        passwordInput = new JPasswordField();
        buttonBar = new JPanel();
        connectButton = new JButton();

        //======== this ========
        setMinimumSize(null);
        setMaximizedBounds(null);
        setIconImage(ImageIO.read(new File("src/main/java/com/lop/View/picture/lop.png")));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== welcomePanel ========
        welcomePanel();
        contentPane.add(welcomePanel, BorderLayout.PAGE_START);

        //======== dialogPane ========
        dialogPane();
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

    }

    private void dialogPane() {
        dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
        dialogPane.setLayout(new BorderLayout(10, 0));

        //======== contentPanel ========
        contentPanel();
        dialogPane.add(contentPanel, BorderLayout.CENTER);

        //======== buttonBar ========
        buttonBar();
        dialogPane.add(buttonBar, BorderLayout.PAGE_END);
    }

    private void buttonBar() {
        buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
        buttonBar.setLayout(new GridLayout());

        //---- connectButton ----
        connectButton.setText("Connecter");
        connectButton.addActionListener(this::authenticate);
        buttonBar.add(connectButton);
    }

    private void contentPanel() {
        contentPanel.setLayout(new GridLayout(2, 2, 0, 20));

        //---- userNameLabel ----
        userNameLabel.setText("Nom d'utilisateur:");
        userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(userNameLabel);
        contentPanel.add(usernameInput);

        //---- passwordLabel ----
        passwordLabel.setText("Mot de Passe:");
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(passwordLabel);
        contentPanel.add(passwordInput);
    }

    private void welcomePanel() {

        welcomePanel.setLayout(new CardLayout());

        //---- welcome ----
        welcome.setText("Bienvenue sur LOPReservation");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setAlignmentY(-2.5F);
        welcomePanel.add(welcome, "card1");
    }

    public void myInit() {
        setSize(400, 200);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Authentifie l'utilisateur puis ouvre la fenêtre principale si l'utilisateur est vérifié
     */
    public void authenticate(ActionEvent action) {
        Map<String, String> data = Map.of("userName", usernameInput.getText(),
                "password", passwordInput.getText());
        Request request = new Request("Connection", data);
        if (controller.authenticate(request).getMessage().equals("allowed")) {
                try {
                    MainWindow frame = new MainWindow(this.controller);
                    frame.setVisible(true);
                    this.setVisible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        else {
            JOptionPane.showMessageDialog(null, "Informations de connections non valide");
        }
    }


}
