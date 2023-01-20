package org.coen448.Data;

import com.google.inject.Singleton;
import lombok.Data;


import java.util.List;


@Data
@Singleton
public class StateData {

    private List<List<Integer>> matrix;

    private boolean penDown = false;

    private Orientation orientation = Orientation.NORTH;
}
