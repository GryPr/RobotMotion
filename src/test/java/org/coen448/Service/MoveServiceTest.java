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
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MoveServiceTest {
    private StateData stateData;
    private MoveService moveService;

    private final int initialPosition = 4;

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
    void GIVEN_invalidMaxDistance_WHEN_move_THEN_outputErrorMessage() {
        final int inputDistance = 6;

        stateData.setOrientation(Orientation.NORTH);
        Assertions.assertThrows(MaxDistanceException.class, () -> moveService.move(inputDistance), "MaxDistanceException expected to be thrown");

        stateData.setOrientation(Orientation.SOUTH);
        Assertions.assertThrows(MaxDistanceException.class, () -> moveService.move(inputDistance), "MaxDistanceException expected to be thrown");

        stateData.setOrientation(Orientation.WEST);
        Assertions.assertThrows(MaxDistanceException.class, () -> moveService.move(inputDistance), "MaxDistanceException expected to be thrown");

        stateData.setOrientation(Orientation.EAST);
        Assertions.assertThrows(MaxDistanceException.class, () -> moveService.move(inputDistance), "MaxDistanceException expected to be thrown");
    }

    @Test
    void GIVEN_invalidMinDistance_WHEN_move_THEN_outputErrorMessage() {
        final int inputDistance = 0;
        Assertions.assertThrows(MinDistanceException.class, () -> moveService.move(inputDistance), "MinDistanceException expected to be thrown");
    }

    @ParameterizedTest()
    @EnumSource(value = Orientation.class)
    void GIVEN_validDistance_WHEN_move_THEN_success(final Orientation orientation) {
        final int inputDistance = 3;
        stateData.setOrientation(orientation);
        Assertions.assertDoesNotThrow(() -> moveService.move(inputDistance), "Exception should not be thrown");
        switch (orientation) {
            case NORTH -> {
                Assertions.assertEquals(initialPosition + inputDistance, stateData.getYPosition(), "Final Y position should be 7");
            }
            case SOUTH -> {
                Assertions.assertEquals(initialPosition - inputDistance, stateData.getYPosition(), "Final Y position should be 1");
            }
            case WEST -> {
                Assertions.assertEquals(initialPosition - inputDistance, stateData.getXPosition(), "Final X position should be 1");
            }
            case EAST -> {
                Assertions.assertEquals(initialPosition + inputDistance, stateData.getXPosition(), "Final X position should be 7");
            }
        }
    }

    @ParameterizedTest()
    @EnumSource(value = Orientation.class)
    void GIVEN_penIsDown_WHEN_move_THEN_success(final Orientation orientation) {
        final int inputDistance = 3;
        stateData.setOrientation(orientation);
        stateData.setPenDown(true);
        Assertions.assertDoesNotThrow(() -> moveService.move(inputDistance), "Exception should not be thrown");
        switch (orientation) {
            case NORTH -> {
                for (int i = 0; i < inputDistance; i++) {
                    Assertions.assertEquals(1, stateData.getMatrix().get(initialPosition).get(initialPosition + i), "Square on grid should have value of 1");
                }
            }
            case SOUTH -> {
                for (int i = 0; i < inputDistance; i++) {
                    Assertions.assertEquals(1, stateData.getMatrix().get(initialPosition).get(initialPosition - i), "Square on grid should have value of 1");
                }
            }
            case WEST -> {
                for (int i = 0; i < inputDistance; i++) {
                    Assertions.assertEquals(1, stateData.getMatrix().get(initialPosition - i).get(initialPosition), "Square on grid should have value of 1");
                }
            }
            case EAST -> {
                for (int i = 0; i < inputDistance; i++) {
                    Assertions.assertEquals(1, stateData.getMatrix().get(initialPosition + i).get(initialPosition), "Square on grid should have value of 1");
                }
            }
        }
    }

    @ParameterizedTest()
    @EnumSource(value = Orientation.class)
    void GIVEN_penIsUp_WHEN_move_THEN_success(final Orientation orientation) {
        final int inputDistance = 3;
        stateData.setOrientation(orientation);
        stateData.setPenDown(false);
        Assertions.assertDoesNotThrow(() -> moveService.move(inputDistance), "Exception should not be thrown");
        switch (orientation) {
            case NORTH -> {
                for (int i = 0; i < inputDistance; i++) {
                    Assertions.assertEquals(0, stateData.getMatrix().get(initialPosition).get(initialPosition + i), "Square on grid should have value of 1");
                }
            }
            case SOUTH -> {
                for (int i = 0; i < inputDistance; i++) {
                    Assertions.assertEquals(0, stateData.getMatrix().get(initialPosition).get(initialPosition - i), "Square on grid should have value of 1");
                }
            }
            case WEST -> {
                for (int i = 0; i < inputDistance; i++) {
                    Assertions.assertEquals(0, stateData.getMatrix().get(initialPosition - i).get(initialPosition), "Square on grid should have value of 1");
                }
            }
            case EAST -> {
                for (int i = 0; i < inputDistance; i++) {
                    Assertions.assertEquals(0, stateData.getMatrix().get(initialPosition + i).get(initialPosition), "Square on grid should have value of 1");
                }
            }
        }
    }

    @Test
    void GIVEN_gridNotInitialized_WHEN_move_THEN_outputErrorMessage() {
        stateData = new StateData();
        moveService = new MoveService(stateData);
        Assertions.assertThrows(NoInitException.class, () -> moveService.move(5), "NoInitException expected to be thrown");
    }
}