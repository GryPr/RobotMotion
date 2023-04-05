package org.coen448.Controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.coen448.Configuration.DisplayConfiguration;
import org.coen448.Exception.BaseException;
import org.coen448.Exception.Error;
import org.coen448.Exception.NoHistoryException;
import org.coen448.Service.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DisplayController {
    @Inject
    private final CommandService commandService;
    @Inject
    private final ProgramStatusService programStatusService;
    @Inject
    private final HistoryService historyService;

    public void loopMenu() {
        System.out.println(DisplayConfiguration.commandMenu);
        while (programStatusService.isRunning()) {
            menu();
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        String input;
        try {
            input = sc.nextLine();
            if(input.isEmpty()) throw new NoSuchElementException();
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

        if(command == Command.REPLAY) {
            try {
                historyService.replay();
            } catch (NoHistoryException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            commandService.handleCommand(input, command);
            historyService.add(input, command);
        }
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
