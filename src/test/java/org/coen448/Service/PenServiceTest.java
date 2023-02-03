package org.coen448.Service;

import org.coen448.Data.StateData;
import org.coen448.Exception.NoInitException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ExtendWith(MockitoExtension.class)
public class PenServiceTest {
    private StateData stateData;
    private PenService penService;

    @BeforeEach
    public void setUp() {
        stateData = new StateData();
        penService = new PenService(stateData);
        stateData.setMatrix(IntStream.range(0, 9)
                .mapToObj(i -> new ArrayList<Integer>(Collections.nCopies(9, 0)))
                .collect(Collectors.toList()));
    }

    @Test
    public void GIVEN_gridNotInitialized_WHEN_penUp_THEN_outputErrorMessage() {
        stateData = new StateData();
        penService = new PenService(stateData);
        Assertions.assertThrows(NoInitException.class, () -> penService.penUp(), "NoInitException expected to be thrown");
    }

    @Test
    public void GIVEN_gridNotInitialized_WHEN_penDown_THEN_outputErrorMessage() {
        stateData = new StateData();
        penService = new PenService(stateData);
        Assertions.assertThrows(NoInitException.class, () -> penService.penDown(), "NoInitException expected to be thrown");
    }

    @Test
    public void GIVEN_gridInitialized_WHEN_penUp_THEN_success() {
        stateData.setPenDown(true);
        Assertions.assertDoesNotThrow(() -> penService.penUp(), "Exception should not be thrown");
        Assertions.assertFalse(stateData.isPenDown(), "Pen should be up");
    }

    @Test
    public void GIVEN_gridInitialized_WHEN_penDown_THEN_success() {
        stateData.setPenDown(false);
        Assertions.assertDoesNotThrow(() -> penService.penDown(), "Exception should not be thrown");
        Assertions.assertTrue(stateData.isPenDown(), "Pen should be down");
    }
}
