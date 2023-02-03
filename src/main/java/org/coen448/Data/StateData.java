package org.coen448.Data;

import com.google.inject.Singleton;
import lombok.Data;
import org.coen448.Exception.NoInitException;


import java.util.List;


@Data
@Singleton
public class StateData {

    private List<List<Integer>> matrix;

    private boolean penDown = false;

    private Orientation orientation = Orientation.NORTH;

    private int xPosition = 0;

    private int yPosition = 0;

    public void isInitialized() throws NoInitException {
        if(matrix == null) throw new NoInitException();
    }
}
