package org.coen448.Controller;

import com.google.inject.Singleton;
import org.coen448.Configuration.DisplayConfiguration;

import java.util.NoSuchElementException;
import java.util.Scanner;

@Singleton
public class DisplayController {
    public boolean running;
    public void loopMenu() {
        System.out.println(DisplayConfiguration.commandMenu);
        running = true;
        while (running) {
            menu();
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        String input;

        try {
            input = sc.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println(DisplayConfiguration.inputErrorMessage);
            return;
        }

        final Character commandChar = Character.toLowerCase(input.charAt(0));
        final Command command = DisplayConfiguration.commandInputMap.get(commandChar);

        // Validate the command input is in the correct format
        if (!validateInput(input, command)) {
            System.out.println(DisplayConfiguration.inputErrorMessage);
            return;
        }

        handleCommand(input, command);
    }

    private void handleCommand(final String input, final Command command) {
        switch (command) {
            case PEN_UP -> {}
            case PEN_DOWN -> {}
            case TURN_RIGHT -> {}
            case TURN_LEFT -> {}
            case MOVE_FORWARD -> {}
            case PRINT_ARRAY -> {}
            case PRINT_POSITION -> {}
            case QUIT -> {
                running = false;
            }
            case INITIALIZE -> {}
        }
    }

    private int extractIntArgument(final String input) {
        final String[] splitInput = input.split(" ");
        return Integer.parseInt(splitInput[splitInput.length - 1]);
    }

    private boolean validateInput(final String input, final Command command) {

        // Validates that the command exists
        if (command == null) {
            return false;
        }

        // Validates the input based on whether it's a single input or double input command
        if (DisplayConfiguration.singleInputCommandSet.contains(command)){
            // Check that the input has a length of 1
            if (input.length() > 1) {
                return false;
            }
        } else if (DisplayConfiguration.doubleInputCommandSet.contains(command)) {
            // Check that the input has a length larger than 2
            if (input.length() < 2) {
                return false;
            }
            // Check that the separator between the command and number is a whitespace
            if (input.charAt(1) != ' ') {
                return false;
            }
            // Check that the second input is a number
            try {
                Integer.parseInt(input.substring(2));
            } catch (NumberFormatException nfe) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

}
