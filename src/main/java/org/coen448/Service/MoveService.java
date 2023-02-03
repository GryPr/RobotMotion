package org.coen448.Service;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import com.google.inject.Singleton;
import org.coen448.Data.Orientation;
import org.coen448.Data.StateData;
import org.coen448.Exception.MaxDistanceException;
import org.coen448.Exception.MinDistanceException;
import org.coen448.Exception.NoInitException;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MoveService {
    @Inject
    private final StateData stateData;

    public void move(int distance) throws MaxDistanceException, MinDistanceException, NoInitException {
        stateData.isInitialized();
        if (distance < 1) throw new MinDistanceException();

        final List<List<Integer>> matrix = stateData.getMatrix();
        final int matrixLength = matrix.size();
        final int xPosition = stateData.getXPosition();
        final int yPosition = stateData.getYPosition();
        final boolean isPenDown = stateData.isPenDown();
        final Orientation orientation = stateData.getOrientation();

        int tempPosition;
        switch (orientation) {
            case NORTH -> {
                if (yPosition + distance >= matrixLength) throw new MaxDistanceException();
                if (isPenDown) {
                    tempPosition = yPosition;
                    while (tempPosition <= yPosition + distance) {
                        matrix.get(tempPosition++).set(xPosition, 1);
                    }
                }
                stateData.setYPosition(yPosition + distance);
            }
            case SOUTH -> {
                if (yPosition - distance < 0) throw new MaxDistanceException();
                if (isPenDown) {
                    tempPosition = yPosition;
                    while (tempPosition >= yPosition - distance) {
                        matrix.get(tempPosition--).set(xPosition, 1);
                    }
                }
                stateData.setYPosition(yPosition - distance);
            }

            case EAST -> {
                if (xPosition + distance >= matrixLength) throw new MaxDistanceException();
                if (isPenDown) {
                    tempPosition = xPosition;
                    while (tempPosition <= xPosition + distance) {
                        matrix.get(yPosition).set(tempPosition++, 1);
                    }
                }
                stateData.setXPosition(xPosition + distance);
            }

            case WEST -> {
                if (xPosition - distance < 0) throw new MaxDistanceException();
                if (isPenDown) {
                    tempPosition = xPosition;
                    while (tempPosition >= xPosition - distance) {
                        matrix.get(yPosition).set(tempPosition--, 1);
                    }
                }
                stateData.setXPosition(xPosition - distance);
            }
        }
    }
}
