package org.coen448.Service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.coen448.Configuration.DisplayConfiguration;
import org.coen448.Controller.Command;

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

    public void handleCommand(final String input, final Command command) {
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
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private int extractIntArgument(final String input) {
        final String[] splitInput = input.split(" ");
        return Integer.parseInt(splitInput[splitInput.length - 1]);
    }
}
