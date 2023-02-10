package org.coen448.Controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.coen448.Configuration.DisplayConfiguration;
import org.coen448.Exception.BaseException;
import org.coen448.Exception.Error;
import org.coen448.Service.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DisplayController {
    @Inject
    private final ProgramStatusService programStatusService;
    @Inject
    private final MoveService moveService;
    @Inject
    private final PenService penService;
    @Inject
    private final TurnService turnService;
    @Inject
    private final PrintService printService;
    
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
            System.out.println(BaseException.errorMessageMap.get(Error.COMMAND_INPUT_ERROR));
            return;
        }

        final Character commandChar = Character.toLowerCase(input.charAt(0));
        final Command command = DisplayConfiguration.commandInputMap.get(commandChar);

        // Validate the command input is in the correct format
        if (!validateInput(input, command)) {
            System.out.println(BaseException.errorMessageMap.get(Error.COMMAND_INPUT_ERROR));
            return;
        }

        handleCommand(input, command);
    }

    private void handleCommand(final String input, final Command command) {
        try {
            switch (command) {

                case PEN_UP -> penService.penUp();
                case PEN_DOWN -> penService.penDown();
                case TURN_RIGHT -> turnService.turnRight();
                case TURN_LEFT -> turnService.turnLeft();
                case MOVE_FORWARD -> moveService.move(extractIntArgument(input));
                case PRINT_ARRAY -> printService.printMatrix();
                case PRINT_POSITION -> printService.printPosition();
                case QUIT -> running = false;
                case INITIALIZE -> programStatusService.initialize(extractIntArgument(input));
                case HELP -> System.out.println(DisplayConfiguration.commandMenu);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            return input.length() <= 1;
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
        }
        return true;
    }

}
