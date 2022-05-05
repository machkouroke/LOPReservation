package com.lop;


import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;

import com.lop.Controller.Controller;
import com.lop.Model.dao.Factory;
import com.lop.View.src.source.MainWindow;

import javax.swing.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class Main {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException, IOException {


        try {
            UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                try {
                    MainWindow frame = new MainWindow(new Controller(Factory.getInstance()));
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
/*test*/

    }
}
