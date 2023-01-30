package org.coen448.Exception;

import java.util.HashMap;
import java.util.Map;

public class BaseException extends Exception{
    public static Map<Error, String> errorMessageMap = new HashMap<>() {{
        put(Error.COMMAND_INPUT_ERROR, "Command input not recognized, please try again");
        put(Error.INIT_SIZE_ERROR, "n (size of the array) needs to be greater than 1");
        put(Error.MAX_DISTANCE_ERROR, "Distance s is over boundaries of grid, please try again with a smaller distance s");
        put(Error.MIN_DISTANCE_ERROR, "Distance s less than 1, please enter a positive distance higher than 0");
        put(Error.NO_INIT_ERROR, "Grid has not been initialized, please initialize it before making an operation on the robot");
    }};

    public BaseException(Error error) {
      super(errorMessageMap.get(error));
    };
}
