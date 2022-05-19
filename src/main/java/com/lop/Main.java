package com.lop;


import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;

import com.lop.controller.Controller;
import com.lop.model.dao.Factory;
import com.lop.view.ConnectionWindow;
import com.lop.view.src.source.MainWindow;

import javax.swing.*;

import java.lang.reflect.InvocationTargetException;


public class Main {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {


        try {
            UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        SwingUtilities.invokeAndWait(() -> {
            try {
//                ConnectionWindow connection = new ConnectionWindow();
//                connection.setVisible(true);
                MainWindow frame = new MainWindow(new Controller(Factory.getInstance()));
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
/*test*/
/*tez*/
    }
}
