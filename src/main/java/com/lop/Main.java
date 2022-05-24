package com.lop;


import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;

import com.lop.controller.Controller;
import com.lop.model.dao.Factory;
import com.lop.view.ConnectionWindow;
import com.lop.view.src.source.InitialisationBase;
import com.lop.view.src.source.MainWindow;

import javax.swing.*;

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
                ConnectionWindow frame = new ConnectionWindow(new Controller(new Factory(
                        "jdbc:mysql://localhost:3306/manager", "root",
                        "momo")));
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
