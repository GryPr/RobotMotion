package org.coen448.Service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.coen448.Data.Orientation;
import org.coen448.Data.StateData;
import org.coen448.Exception.MaxDistanceException;
import org.coen448.Exception.NoInitException;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TurnService {
    @Inject
    private final StateData stateData;

    public void turnLeft() throws NoInitException {
        final List<List<Integer>> matrix = stateData.getMatrix();
        if(matrix == null) throw new NoInitException();
        switch (stateData.getOrientation()){
            case NORTH -> {
                stateData.setOrientation(Orientation.WEST);
            }
            case WEST -> {
                stateData.setOrientation(Orientation.SOUTH);
            }
            case SOUTH -> {
                stateData.setOrientation(Orientation.EAST);
            }
            case EAST -> {
                stateData.setOrientation(Orientation.NORTH);
            }
            default -> throw new IllegalStateException("Unexpected value: " + stateData.getOrientation());
        }


    }
    public void turnRight() throws NoInitException {
        final List<List<Integer>> matrix = stateData.getMatrix();
        if(matrix == null) throw new NoInitException();
        switch (stateData.getOrientation()){
            case NORTH -> {
                stateData.setOrientation(Orientation.EAST);
            }
            case EAST -> {
                stateData.setOrientation(Orientation.SOUTH);
            }
            case SOUTH -> {
                stateData.setOrientation(Orientation.WEST);
            }
            case WEST -> {
                stateData.setOrientation(Orientation.NORTH);
            }
            default -> throw new IllegalStateException("Unexpected value: " + stateData.getOrientation());
        }
    }
}
