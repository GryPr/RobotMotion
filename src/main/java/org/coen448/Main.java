package org.coen448;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.coen448.Controller.DisplayController;
import org.coen448.Module.BasicModule;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BasicModule());
        DisplayController displayController = injector.getInstance(DisplayController.class);
        displayController.loopMenu();
    }
}