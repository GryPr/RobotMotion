package org.coen448.Service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.coen448.Controller.Command;
import org.coen448.Controller.DisplayController;
import org.coen448.Data.HistoryData;
import org.coen448.Exception.NoHistoryException;

import java.util.ArrayList;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HistoryService {
    @Inject
    private final HistoryData historyData;
    @Inject
    private final CommandService commandService;

    public void add(String input, Command command) {
        ArrayList<String> inputs = historyData.getInputs();
        inputs.add(input);
        historyData.setInputs(inputs);

        ArrayList<Command> commands  = historyData.getCommands();
        commands.add(command);
        historyData.setCommands(commands);
    }

    public void replay() throws NoHistoryException {
        ArrayList<String> inputs = historyData.getInputs();
        if(inputs.isEmpty()) throw new NoHistoryException();

        ArrayList<Command> commands = historyData.getCommands();
        for(int i = 0; i < inputs.size(); i++) {
            System.out.println(inputs.get(i));
            commandService.handleCommand(inputs.get(i), commands.get(i));
        }
    }
}
