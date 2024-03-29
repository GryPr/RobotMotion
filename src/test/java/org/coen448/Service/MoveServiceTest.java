package org.coen448.Service;

import org.coen448.Data.Orientation;
import org.coen448.Data.StateData;
import org.coen448.Exception.MaxDistanceException;
import org.coen448.Exception.MinDistanceException;
import org.coen448.Exception.NoInitException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class MoveServiceTest {
    private StateData stateData;
    private MoveService moveService;

    private final int initialPosition = 4;
    private final int validDistance = 3;

    @BeforeEach
    void setUp() {
        // Set pen on center of 9x9 matrix
        stateData = new StateData();

        moveService = new MoveService(stateData);
        stateData.setMatrix(IntStream.range(0, 9)
                .mapToObj(i -> new ArrayList<Integer>(Collections.nCopies(9, 0)))
                .collect(Collectors.toList()));
        stateData.setXPosition(initialPosition);
        stateData.setYPosition(initialPosition);
    }

    @Test
    void GIVEN_gridNotInitialized_WHEN_move_THEN_outputErrorMessage() {
        stateData = new StateData();
        moveService = new MoveService(stateData);
        Assertions.assertThrows(NoInitException.class, () -> moveService.move(5), "NoInitException expected to be thrown");
    }

    @ParameterizedTest
    @EnumSource(value = Orientation.class)
    void GIVEN_invalidMaxDistance_WHEN_move_THEN_outputErrorMessage(final Orientation orientation) {
        final int inputDistance = 6;
        stateData.setOrientation(orientation);
        Assertions.assertThrows(MaxDistanceException.class, () -> moveService.move(inputDistance), "MaxDistanceException expected to be thrown");
    }

    @Test
    void GIVEN_invalidMinDistance_WHEN_move_THEN_outputErrorMessage() {
        final int inputDistance = 0;
        Assertions.assertThrows(MinDistanceException.class, () -> moveService.move(inputDistance), "MinDistanceException expected to be thrown");
    }

    private static Stream<Arguments> provideArgumentsForMove() {
        return Stream.of(
                Arguments.of(Orientation.NORTH, 0, 1),
                Arguments.of(Orientation.SOUTH, 0, -1),
                Arguments.of(Orientation.WEST, -1, 0),
                Arguments.of(Orientation.EAST, 1, 0)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "provideArgumentsForMove")
    void GIVEN_validDistance_WHEN_move_THEN_success(final Orientation orientation, final int xMultiplier, final int yMultiplier) {
        stateData.setOrientation(orientation);
        int initialXPosition = stateData.getXPosition(), initialYPosition = stateData.getYPosition();
        Assertions.assertDoesNotThrow(() -> moveService.move(validDistance), "Exception should not be thrown");
        Assertions.assertEquals(initialXPosition + (validDistance * xMultiplier), stateData.getXPosition(), "Invalid final X position");
        Assertions.assertEquals(initialYPosition + (validDistance * yMultiplier), stateData.getYPosition(), "Invalid final Y position");
    }

    @ParameterizedTest()
    @MethodSource(value = "provideArgumentsForMove")
    void GIVEN_penIsDown_WHEN_move_THEN_success(final Orientation orientation, final int xMultiplier, final int yMultiplier) {
        stateData.setOrientation(orientation);
        stateData.setPenDown(true);
        Assertions.assertDoesNotThrow(() -> moveService.move(validDistance), "Exception should not be thrown");
        for (int i = 0; i <= validDistance; i++) {
            Assertions.assertEquals(1, stateData.getMatrix().get(initialPosition + (i * yMultiplier)).get(initialPosition + (i * xMultiplier)), "Robot is not writing to grid");
        }
    }

    @ParameterizedTest()
    @MethodSource(value = "provideArgumentsForMove")
    void GIVEN_penIsUp_WHEN_move_THEN_success(final Orientation orientation, final int xMultiplier, final int yMultiplier) {
        stateData.setOrientation(orientation);
        stateData.setPenDown(false);
        Assertions.assertDoesNotThrow(() -> moveService.move(validDistance), "Exception should not be thrown");
        for (int i = 0; i <= validDistance; i++) {
            Assertions.assertEquals(0, stateData.getMatrix().get(initialPosition + (i * yMultiplier)).get(initialPosition + (i * xMultiplier)), "Robot is writing to grid");
        }
    }
}