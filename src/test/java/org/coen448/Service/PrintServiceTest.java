package org.coen448.Service;

import org.coen448.Data.StateData;
import org.coen448.Exception.NoInitException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PrintServiceTest {
    @Mock
    StateData stateData;
    @InjectMocks
    PrintService printService;

    List<List<Integer>> matrixInput = List.of(
            List.of(1, 2, 3),
            List.of(4, 5, 6),
            List.of(7, 8, 9)
    );

    String expectedMatrixString = """
            2 7 8 9\s
            1 4 5 6\s
            0 1 2 3\s
              0 1 2\s""";

    @Test
    void GIVEN_matrix_WHEN_buildMatrixString_THEN_returnFormattedMatrixString() {
        Assertions.assertDoesNotThrow(() -> {
            final String matrixString = printService.buildMatrixString(matrixInput);
            Assertions.assertEquals(expectedMatrixString, matrixString);
        });
    }

    @Test
    void GIVEN_nullMatrix_WHEN_buildMatrixString_THEN_throwNoInitException() {
        Assertions.assertThrows(NoInitException.class, () -> {
            printService.buildMatrixString(null);
        });
    }
}