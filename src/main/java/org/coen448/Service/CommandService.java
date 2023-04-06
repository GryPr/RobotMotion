package org.coen448.Service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.coen448.Configuration.DisplayConfiguration;
import org.coen448.Controller.Command;
import org.coen448.Data.HistoryData;
import org.coen448.Exception.NoHistoryException;

import java.util.ArrayList;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CommandService {
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
    @Inject
    private final HistoryData historyData;

    public void handleCommand(final String input, final Command command, boolean addToHistory) {
        try {
            switch (command) {

                case PEN_UP -> penService.penUp();
                case PEN_DOWN -> penService.penDown();
                case TURN_RIGHT -> turnService.turnRight();
                case TURN_LEFT -> turnService.turnLeft();
                case MOVE_FORWARD -> moveService.move(extractIntArgument(input));
                case PRINT_ARRAY -> printService.printMatrix();
                case PRINT_POSITION -> printService.printPosition();
                case QUIT -> programStatusService.setRunning(false);
                case INITIALIZE -> programStatusService.initialize(extractIntArgument(input));
                case HELP -> System.out.println(DisplayConfiguration.commandMenu);
                case REPLAY -> replay();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if(command != Command.REPLAY && addToHistory) addToHistory(input, command);
    }

    private int extractIntArgument(final String input) {
        final String[] splitInput = input.split(" ");
        return Integer.parseInt(splitInput[splitInput.length - 1]);
    }

    private void addToHistory(String input, Command command) {
        ArrayList<String> inputs = historyData.getInputs();
        inputs.add(input);
        historyData.setInputs(inputs);

        ArrayList<Command> commands  = historyData.getCommands();
        commands.add(command);
        historyData.setCommands(commands);
    }

    private void replay() throws NoHistoryException {
        ArrayList<String> inputs = historyData.getInputs();
        if(inputs.isEmpty()) throw new NoHistoryException();

        ArrayList<Command> commands = historyData.getCommands();
        for(int i = 0; i < inputs.size(); i++) {
            System.out.println(inputs.get(i));
            handleCommand(inputs.get(i), commands.get(i), false);
        }
    }
}
