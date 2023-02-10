package org.coen448.Service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.coen448.Data.Orientation;
import org.coen448.Data.StateData;
import org.coen448.Exception.NoInitException;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TurnService {
    @Inject
    private final StateData stateData;

    public void turnLeft() throws NoInitException {
        stateData.isInitialized();
        switch (stateData.getOrientation()){
            case NORTH -> stateData.setOrientation(Orientation.WEST);
            case WEST -> stateData.setOrientation(Orientation.SOUTH);
            case SOUTH -> stateData.setOrientation(Orientation.EAST);
            case EAST -> stateData.setOrientation(Orientation.NORTH);
        }


    }
    public void turnRight() throws NoInitException {
        stateData.isInitialized();
        switch (stateData.getOrientation()){
            case NORTH -> stateData.setOrientation(Orientation.EAST);
            case EAST -> stateData.setOrientation(Orientation.SOUTH);
            case SOUTH -> stateData.setOrientation(Orientation.WEST);
            case WEST -> stateData.setOrientation(Orientation.NORTH);
        }
    }
}
