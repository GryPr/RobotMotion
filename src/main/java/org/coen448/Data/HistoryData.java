package org.coen448.Data;

import lombok.Data;
import org.coen448.Controller.Command;

import javax.inject.Singleton;
import java.util.ArrayList;


@Data
@Singleton
public class HistoryData {

    ArrayList<String> inputs = new ArrayList<String>();
    ArrayList<Command> commands = new ArrayList<Command>();

}
