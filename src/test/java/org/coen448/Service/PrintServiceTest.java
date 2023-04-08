package org.coen448.Service;

import org.coen448.Data.Orientation;
import org.coen448.Data.StateData;
import org.coen448.Exception.NoInitException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Stream;

class PrintServiceTest {
    StateData stateData;
    PrintService printService;

    private final ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errorContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    List<List<Integer>> matrix = List.of(
            List.of(1, 1, 1),
            List.of(1, 0, 0),
            List.of(1, 1, 1)
    );

    String expectedMatrixString = """
            2 | * * *\s
            1 | *    \s
            0 | * * *\s
                - - -\s
                0 1 2\s
              """;

    @BeforeEach
    public void setUp() {
        stateData = new StateData();
        printService = new PrintService(stateData);
        stateData.setMatrix(matrix);
        System.setOut(new PrintStream(outputContent));
        System.setErr(new PrintStream(errorContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }


    @Test
    void GIVEN_initializedStateData_WHEN_buildMatrixString_THEN_returnFormattedMatrixString() {
        Assertions.assertDoesNotThrow(() -> printService.printMatrix());
        String output = outputContent.toString().replaceAll("\\r", "");
        Assertions.assertEquals(expectedMatrixString, output);
    }

    @Test
    void GIVEN_uninitializedStateData_WHEN_printMatrix_THEN_throwNoInitException() {
        stateData.setMatrix(null);
        Assertions.assertThrows(NoInitException.class, () -> printService.printMatrix());
    }

    private static Stream<Arguments> positionDataset() {
        return Stream.of(
                Arguments.of(5, 4, true, Orientation.NORTH,
                        """
                                Position [x,y]: [5, 4]
                                Pen up/down: DOWN
                                Orientation: NORTH"""),
                Arguments.of(3, 2, false, Orientation.SOUTH,
                        """
                                Position [x,y]: [3, 2]
                                Pen up/down: UP
                                Orientation: SOUTH"""),
                Arguments.of(10, 26, true, Orientation.WEST,
                        """
                                Position [x,y]: [10, 26]
                                Pen up/down: DOWN
                                Orientation: WEST"""),
                Arguments.of(72, 93, false, Orientation.EAST,
                        """
                                Position [x,y]: [72, 93]
                                Pen up/down: UP
                                Orientation: EAST""")
        );
    }

    @ParameterizedTest
    @MethodSource(value = "positionDataset")
    void GIVEN_initializedStateData_WHEN_printPosition_THEN_printPositionToConsole(
            final int xPosition,
            final int yPosition,
            final boolean penDown,
            final Orientation orientation,
            final String positionString) {

        stateData.setXPosition(xPosition);
        stateData.setYPosition(yPosition);
        stateData.setPenDown(penDown);
        stateData.setOrientation(orientation);

        Assertions.assertDoesNotThrow(() -> printService.printPosition());

        String output = outputContent.toString().replaceAll("\\r", "");
        String expected = positionString + "\n";
        Assertions.assertEquals(expected, output);
    }

    @Test
    void GIVEN_uninitializedStateData_WHEN_printPosition_THEN_throwNoInitException() {
        stateData.setMatrix(null);

        Assertions.assertThrows(NoInitException.class, () -> printService.printPosition());
    }
}