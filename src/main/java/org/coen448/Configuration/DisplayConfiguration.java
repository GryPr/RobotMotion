package org.coen448.Configuration;

import org.coen448.Controller.Command;
import org.coen448.Exception.Error;

import java.util.*;

public class DisplayConfiguration {

    public static Map<Character, Command> commandInputMap = new HashMap<>()
    {{
        put('u', Command.PEN_UP);
        put('d', Command.PEN_DOWN);
        put('r', Command.TURN_RIGHT);
        put('l', Command.TURN_LEFT);
        put('m', Command.MOVE_FORWARD);
        put('p', Command.PRINT_ARRAY);
        put('c', Command.PRINT_POSITION);
        put('q', Command.QUIT);
        put('i', Command.INITIALIZE);
        put('h', Command.HELP);
        put('p', Command.REPLAY);
    }};

    public static Set<Command> singleInputCommandSet = new HashSet<>()
    {{
        addAll(Arrays.asList(
                Command.PEN_UP,
                Command.PEN_DOWN,
                Command.TURN_RIGHT,
                Command.TURN_LEFT,
                Command.PRINT_ARRAY,
                Command.PRINT_POSITION,
                Command.QUIT,
                Command.HELP,
                Command.REPLAY
        ));
    }};

    public static Set<Command> doubleInputCommandSet = new HashSet<>()
    {{
        addAll(Arrays.asList(
                Command.MOVE_FORWARD,
                Command.INITIALIZE
        ));
    }};

    public static String commandMenu =
                    """
                    [U|u] Pen up
                    [D|d] Pen down
                    [R|r] Turn right
                    [L|l] Turn left
                    [M s|m s] Move forward s spaces (s is a non-negative integer)
                    [P|p] Print the N by N array and display the indices
                    [C|c] Print current position of the pen and whether it is up or down and its facing direction
                    [Q|q] Stop the program
                    [I n|i n] Initialize the system: The values of the array floor are zeros and the robot is back to [0, 0], pen up and facing north. n size of the array, an integer greater than zero
                    [R|r] Replay all the previously entered commands
                    
                    [H|h] Print that help message again
                    """;
}
