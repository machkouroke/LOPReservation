package com.lop;



import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;

import com.lop.controller.ConfigController;
import com.lop.controller.Controller;
import com.lop.model.BaseLogin;
import com.lop.model.dao.Factory;
import com.lop.view.src.source.ConnectionWindow;
import com.lop.view.src.source.InitialisationBase;

import javax.swing.*;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;


public class Main {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {

        try {
            UIManager.setLookAndFeel(new FlatGitHubDarkIJTheme());
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
                e.printStackTrace();
            }
        });

    }
}
