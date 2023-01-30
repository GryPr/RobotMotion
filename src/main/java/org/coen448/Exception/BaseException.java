package org.coen448.Exception;

import java.util.HashMap;
import java.util.Map;

public class BaseException extends Exception{
    public static Map<Error, String> errorMessageMap = new HashMap<>() {{
        put(Error.COMMAND_INPUT_ERROR, "Command input not recognized, please try again");
        put(Error.INIT_SIZE_ERROR, "n (size of the array) needs to be greater than 1");
        put(Error.DISTANCE_ERROR, "Distance s is over boundaries of matrix, please try again with a smaller distance s");
    }};

    public BaseException(Error error) {
      super(errorMessageMap.get(error));
    };
}
