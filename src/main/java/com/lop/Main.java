package com.lop;


import com.formdev.flatlaf.intellijthemes.FlatXcodeDarkIJTheme;


import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubIJTheme;
import com.jthemedetecor.OsThemeDetector;
import com.lop.controller.ConfigController;
import com.lop.controller.Controller;
import com.lop.model.BaseLogin;
import com.lop.model.dao.Factory;
import com.lop.view.src.source.ConnectionWindow;
import com.lop.view.src.source.InitialisationBase;
import org.apache.log4j.BasicConfigurator;

import javax.swing.*;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Morel Kouhossounon
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {

        try {
            BasicConfigurator.configure();
            final OsThemeDetector detector = OsThemeDetector.getDetector();
            final boolean isDarkThemeUsed = detector.isDark();
            if (isDarkThemeUsed) {
                UIManager.setLookAndFeel(new FlatXcodeDarkIJTheme());
            } else {
                UIManager.setLookAndFeel(new FlatGitHubIJTheme());
            }

        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        SwingUtilities.invokeAndWait(() -> {
            try {
                if (ConfigController.firstTime()) {
                    new InitialisationBase();
                } else {
                    FileInputStream fichier = new FileInputStream("src/main/java/com/lop/controller/dataBase.dot");
                    BaseLogin data = (BaseLogin) new ObjectInputStream(fichier).readObject();
                    new ConnectionWindow(new Controller(new Factory(
                            data.localhost(), data.username(),
                            data.password())));

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });

    }
}
