package com.lop;


import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;

import com.lop.controller.ConfigController;
import com.lop.controller.Controller;
import com.lop.model.BaseLogin;
import com.lop.model.dao.Factory;
import com.lop.view.ConnectionWindow;
import com.lop.view.src.source.InitialisationBase;
import com.lop.view.src.source.MainWindow;

import javax.swing.*;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;


public class Main {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {

//        InitialisationBase b = new InitialisationBase();
//        b.setVisible(true);
        try {
            UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        SwingUtilities.invokeAndWait(() -> {
            try {
                if (ConfigController.firstTime()) {
                    InitialisationBase frame = new InitialisationBase();
                } else {
                    FileInputStream fichier = new FileInputStream("src/main/java/com/lop/controller/dataBase.dot");
                    BaseLogin data = (BaseLogin) new ObjectInputStream(fichier).readObject();
                    ConnectionWindow frame = new ConnectionWindow(new Controller(new Factory(
                            data.localhost(), data.username(),
                            data.password())));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
