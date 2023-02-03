package org.coen448.Service;

import org.coen448.Data.Orientation;
import org.coen448.Data.StateData;
import org.coen448.Exception.NoInitException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class TurnServiceTest {
    private StateData stateData;
    private TurnService turnService;

    @BeforeEach
    public void setUp() {
        stateData = new StateData();
        turnService = new TurnService(stateData);
        stateData.setMatrix(IntStream.range(0, 9)
                .mapToObj(i -> new ArrayList<Integer>(Collections.nCopies(9, 0)))
                .collect(Collectors.toList()));
        stateData.setXPosition(0);
        stateData.setYPosition(0);
    }

    @Test
    public void GIVEN_gridNotInitialized_WHEN_turn_THEN_outputErrorMessage() {
        stateData = new StateData();
        turnService = new TurnService(stateData);
        Assertions.assertThrows(NoInitException.class, () -> turnService.turnLeft(), "NoInitException expected to be thrown");
    }

    private static Stream<Arguments> provideArgumentsForTurnLeft() {
        return Stream.of(
                Arguments.of(Orientation.NORTH, Orientation.WEST),
                Arguments.of(Orientation.SOUTH, Orientation.EAST),
                Arguments.of(Orientation.WEST, Orientation.SOUTH),
                Arguments.of(Orientation.EAST, Orientation.NORTH)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "provideArgumentsForTurnLeft")
    public void GIVEN_griInitialized_WHEN_turnLeft_THEN_success(
            final Orientation initialOrientation,
            final Orientation finalOrientation) {
        stateData.setOrientation(initialOrientation);
        Assertions.assertDoesNotThrow(() -> turnService.turnLeft(), "Exception should not be thrown");
        Assertions.assertEquals(finalOrientation, stateData.getOrientation(), "Invalid final orientation");
    }

    private static Stream<Arguments> provideArgumentsForTurnRight() {
        return Stream.of(
                Arguments.of(Orientation.NORTH, Orientation.EAST),
                Arguments.of(Orientation.SOUTH, Orientation.WEST),
                Arguments.of(Orientation.WEST, Orientation.NORTH),
                Arguments.of(Orientation.EAST, Orientation.SOUTH)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "provideArgumentsForTurnRight")
    public void GIVEN_griInitialized_WHEN_turnRight_THEN_success(
            final Orientation initialOrientation,
            final Orientation finalOrientation) {
        stateData.setOrientation(initialOrientation);
        Assertions.assertDoesNotThrow(() -> turnService.turnRight(), "Exception should not be thrown");
        Assertions.assertEquals(finalOrientation, stateData.getOrientation(), "Invalid final orientation");
    }
}
