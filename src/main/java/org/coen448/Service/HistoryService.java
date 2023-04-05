package org.coen448.Service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.coen448.Controller.Command;
import org.coen448.Controller.DisplayController;
import org.coen448.Data.HistoryData;

import java.util.ArrayList;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HistoryService {
    @Inject
    private final HistoryData historyData;
    @Inject
    private final DisplayController displayController;

    public void add(String input, Command command) {
        ArrayList<String> inputs = historyData.getInputs();
        inputs.add(input);
        historyData.setInputs(inputs);

        ArrayList<Command> commands  = historyData.getCommands();
        commands.add(command);
        historyData.setCommands(commands);
    }

    public void replay() {
        ArrayList<String> inputs = historyData.getInputs();
        ArrayList<Command> commands = historyData.getCommands();
        for(int i = 0; i < inputs.size(); i++) {
            System.out.println(inputs.get(i));
            displayController.handleCommand(inputs.get(i), commands.get(i));
        }
    }
}
