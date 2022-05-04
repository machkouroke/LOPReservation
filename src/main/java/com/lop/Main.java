package com.lop;


import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatLightOwlIJTheme;
import com.lop.View.ConnectionWindow;

import javax.swing.*;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {

         try {
             UIManager.setLookAndFeel( new FlatLightOwlIJTheme() );
         } catch( Exception ex ) {
             System.err.println( "Failed to initialize LaF" );
         }
        Runnable initFrame = ConnectionWindow::new;
        SwingUtilities.invokeAndWait(initFrame);

    }
}
