package org.coen448.Service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.coen448.Data.StateData;
import org.coen448.Exception.NoInitException;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PrintService {
    @Inject
    private final StateData stateData;

    public void printMatrix() throws NoInitException {
        stateData.isInitialized();
        final List<List<Integer>> matrix = stateData.getMatrix();

        // Build the matrix string
        String matrixString = "";
        for (int i = matrix.size() - 1; i >= 0; i--) {
            final List<Integer> row = matrix.get(i);
            String rowString = String.format("%d | ", i);
            for (final Integer integer : row) {
                rowString = String.format("%s%d ", rowString, integer);
            }
            matrixString = String.format("%s%s\n", matrixString, rowString);
        }

        // Build the horizontal indexes on the last rows
        String horizontalIndexes = "    ";
        for (int i = 0; i < matrix.get(matrix.size()-1).size(); i++) {
            horizontalIndexes = String.format("%s- ", horizontalIndexes);
        }
        matrixString = String.format("%s%s\n", matrixString, horizontalIndexes);

        horizontalIndexes = "    ";
        for (int i = 0; i < matrix.get(matrix.size()-1).size(); i++) {
            horizontalIndexes = String.format("%s%d ", horizontalIndexes, i);
        }
        matrixString = String.format("%s%s", matrixString, horizontalIndexes);

        System.out.println(matrixString);
    }

    public void printPosition() throws NoInitException {
        stateData.isInitialized();

        final String positionString = String.format("""
                Position [x,y]: [%d, %d]
                Pen up/down: %s
                Orientation: %s""",
                stateData.getXPosition(),
                stateData.getYPosition(),
                stateData.isPenDown() ? "DOWN" : "UP",
                stateData.getOrientation());
        System.out.println(positionString);
    }
}
