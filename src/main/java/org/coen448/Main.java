package org.coen448;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.coen448.Controller.DisplayController;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector();
        try {
            DisplayController displayController = injector.getInstance(DisplayController.class);
            displayController.loopMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}