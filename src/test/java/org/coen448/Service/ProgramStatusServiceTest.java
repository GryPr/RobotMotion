package org.coen448.Service;

import org.coen448.Data.Orientation;
import org.coen448.Data.StateData;
import org.coen448.Exception.InitSizeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ExtendWith(MockitoExtension.class)
class ProgramStatusServiceTest {
    private StateData stateData;
    private ProgramStatusService programStatusService;

    @BeforeEach
    public void setUp() {
        stateData = new StateData();
        programStatusService = new ProgramStatusService(stateData);
    }

    @Test
    void GIVEN_invalidLength_WHEN_initialize_THEN_outputErrorMessage() {
        Assertions.assertThrows(InitSizeException.class, () -> {
            programStatusService.initialize(-2);
        }, "InitSizeException expected to be thrown");
    }

    @Test
    void GIVEN_validLength_WHEN_initialize_THEN_success() {
        final int inputLength = 4;

        Assertions.assertDoesNotThrow(() -> {
            programStatusService.initialize(inputLength);
        }, "InitSizeException should not be thrown");

        Assertions.assertFalse(stateData.isPenDown(), "Pen should be up");
        Assertions.assertEquals(Orientation.NORTH, stateData.getOrientation(), "Robot should be facing North");

        for (List<Integer> list : stateData.getMatrix()) {
            Assertions.assertIterableEquals(new ArrayList<Integer>() {{
                for (int i = 0; i < inputLength; i++) {
                    add(0);
                }
            }}, list);
        }
        Assertions.assertEquals(0, stateData.getXPosition(), "Initial X position should be 0");
        Assertions.assertEquals(0, stateData.getYPosition(), "Initial Y position should be 0");
    }

    @Test
    void GIVEN_madeOperationsOnData_WHEN_initialize_THEN_success() {
        final int inputLength = 4;

        stateData.setMatrix(IntStream.range(0, inputLength)
                .mapToObj(i -> new ArrayList<Integer>(Collections.nCopies(inputLength, 0)))
                .collect(Collectors.toList()));
        stateData.setPenDown(true);
        stateData.setOrientation(Orientation.SOUTH);
        stateData.setXPosition(2);
        stateData.setYPosition(3);

        Assertions.assertDoesNotThrow(() -> {
            programStatusService.initialize(inputLength);
        }, "InitSizeException should not be thrown");

        Assertions.assertFalse(stateData.isPenDown(), "Pen should be up");
        Assertions.assertEquals(Orientation.NORTH, stateData.getOrientation(), "Robot should be facing North");

        for (List<Integer> list : stateData.getMatrix()) {
            Assertions.assertIterableEquals(new ArrayList<Integer>() {{
                for (int i = 0; i < inputLength; i++) {
                    add(0);
                }
            }}, list);
        }
        Assertions.assertEquals(0, stateData.getXPosition(), "Initial X position should be 0");
        Assertions.assertEquals(0, stateData.getYPosition(), "Initial Y position should be 0");
    }
}