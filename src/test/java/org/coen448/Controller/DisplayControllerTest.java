package org.coen448.Controller;

import org.coen448.Configuration.DisplayConfiguration;
import org.coen448.Exception.Error;
import org.coen448.Exception.*;
import org.coen448.Service.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class DisplayControllerTest {

    @Mock
    ProgramStatusService programStatusService;
    @Mock
    MoveService moveService;
    @Mock
    PenService penService;
    @Mock
    TurnService turnService;
    @Mock
    PrintService printService;
    @InjectMocks
    DisplayController displayController;

    private final ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errorContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outputContent));
        System.setErr(new PrintStream(errorContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void GIVEN_quit_WHEN_loopMenu_THEN_outputMenuAndBreakLoop() {
        InputStream in = new ByteArrayInputStream("q".getBytes());
        System.setIn(in);

        displayController.loopMenu();

        String output = outputContent.toString().replaceAll("\\r", "");
        String expected = DisplayConfiguration.commandMenu + "\n";

        Assertions.assertEquals(expected, output);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "", // no command
            "someNonExistentCommand", // non-existent command
            "u 123451", // single input command that's too long
            "m awdawdw", // double input command with non-numerical second input
            "m", // double input command with no second input
            "m|12" // double input command with invalid separator
    })
    public void GIVEN_invalidCommand_WHEN_menu_THEN_outputErrorMessage(final String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        displayController.menu();

        String output = outputContent.toString().replaceAll("\\r\\n", "");
        String errorMessage = output.split(System.getProperty("line.separator"))[0];

        Assertions.assertEquals(BaseException.errorMessageMap.get(Error.COMMAND_INPUT_ERROR), errorMessage);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "u", // pen up
            "d", // pen down
            "r", // turn right
            "l", // turn left
            "m 5", // move
            "p", // print array
            "c", // print current position
            "q", // stop program
            "h", //help
            "i 5" // initialize
    })
    public void GIVEN_validCommand_WHEN_menu_THEN_success(final String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> displayController.menu());
    }

    @Test
    public void GIVEN_penUpWithNoInitException_WHEN_menu_THEN_printException() throws NoInitException {
        final String input = "u";
        final String expected = BaseException.errorMessageMap.get(Error.NO_INIT_ERROR);
        doThrow(new NoInitException()).when(penService).penUp();

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> displayController.menu());

        String output = outputContent.toString().replaceAll("\\r\\n", "");
        String errorMessage = output.split(System.getProperty("line.separator"))[0];

        Assertions.assertEquals(expected, errorMessage);
    }

    @Test
    public void GIVEN_penDownWithNoInitException_WHEN_menu_THEN_printException() throws NoInitException {
        final String input = "d";
        final String expected = BaseException.errorMessageMap.get(Error.NO_INIT_ERROR);
        doThrow(new NoInitException()).when(penService).penDown();

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> displayController.menu());

        String output = outputContent.toString().replaceAll("\\r\\n", "");
        String errorMessage = output.split(System.getProperty("line.separator"))[0];

        Assertions.assertEquals(expected, errorMessage);
    }
    @Test
    public void GIVEN_turnLeftWithNoInitException_WHEN_menu_THEN_printException() throws NoInitException {
        final String input = "l";
        final String expected = BaseException.errorMessageMap.get(Error.NO_INIT_ERROR);
        doThrow(new NoInitException()).when(turnService).turnLeft();

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> displayController.menu());

        String output = outputContent.toString().replaceAll("\\r\\n", "");
        String errorMessage = output.split(System.getProperty("line.separator"))[0];

        Assertions.assertEquals(expected, errorMessage);
    }

    @Test
    public void GIVEN_turnRightWithNoInitException_WHEN_menu_THEN_printException() throws NoInitException {
        final String input = "r";
        final String expected = BaseException.errorMessageMap.get(Error.NO_INIT_ERROR);
        doThrow(new NoInitException()).when(turnService).turnRight();

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> displayController.menu());

        String output = outputContent.toString().replaceAll("\\r\\n", "");
        String errorMessage = output.split(System.getProperty("line.separator"))[0];

        Assertions.assertEquals(expected, errorMessage);
    }
    @Test
    public void GIVEN_moveWithNoInitException_WHEN_menu_THEN_printException() throws NoInitException, MinDistanceException, MaxDistanceException {
        final String input = "m 5";
        final String expected = BaseException.errorMessageMap.get(Error.NO_INIT_ERROR);
        doThrow(new NoInitException()).when(moveService).move(anyInt());

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> displayController.menu());

        String output = outputContent.toString().replaceAll("\\r\\n", "");
        String errorMessage = output.split(System.getProperty("line.separator"))[0];

        Assertions.assertEquals(expected, errorMessage);
    }

    @Test
    public void GIVEN_moveWithMinDistanceException_WHEN_menu_THEN_printException() throws NoInitException, MinDistanceException, MaxDistanceException {
        final String input = "m 5";
        final String expected = BaseException.errorMessageMap.get(Error.MIN_DISTANCE_ERROR);
        doThrow(new MinDistanceException()).when(moveService).move(anyInt());

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> displayController.menu());

        String output = outputContent.toString().replaceAll("\\r\\n", "");
        String errorMessage = output.split(System.getProperty("line.separator"))[0];

        Assertions.assertEquals(expected, errorMessage);
    }

    @Test
    public void GIVEN_moveWithMaxDistanceException_WHEN_menu_THEN_printException() throws NoInitException, MinDistanceException, MaxDistanceException {
        final String input = "m 5";
        final String expected = BaseException.errorMessageMap.get(Error.MAX_DISTANCE_ERROR);
        doThrow(new MaxDistanceException()).when(moveService).move(anyInt());

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> displayController.menu());

        String output = outputContent.toString().replaceAll("\\r\\n", "");
        String errorMessage = output.split(System.getProperty("line.separator"))[0];

        Assertions.assertEquals(expected, errorMessage);
    }

    @Test
    public void GIVEN_printMatrixWithNoInitException_WHEN_menu_THEN_printException() throws NoInitException {
        final String input = "p";
        final String expected = BaseException.errorMessageMap.get(Error.NO_INIT_ERROR);
        doThrow(new NoInitException()).when(printService).printMatrix();

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> displayController.menu());

        String output = outputContent.toString().replaceAll("\\r\\n", "");
        String errorMessage = output.split(System.getProperty("line.separator"))[0];

        Assertions.assertEquals(expected, errorMessage);
    }

    @Test
    public void GIVEN_printPositionWithNoInitException_WHEN_menu_THEN_printException() throws NoInitException {
        final String input = "c";
        final String expected = BaseException.errorMessageMap.get(Error.NO_INIT_ERROR);
        doThrow(new NoInitException()).when(printService).printPosition();

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> displayController.menu());

        String output = outputContent.toString().replaceAll("\\r\\n", "");
        String errorMessage = output.split(System.getProperty("line.separator"))[0];

        Assertions.assertEquals(expected, errorMessage);
    }

    @Test
    public void GIVEN_initializeWithInitSizeException_WHEN_menu_THEN_printException() throws InitSizeException {
        final String input = "i 5";
        final String expected = BaseException.errorMessageMap.get(Error.INIT_SIZE_ERROR);
        doThrow(new InitSizeException()).when(programStatusService).initialize(anyInt());

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> displayController.menu());

        String output = outputContent.toString().replaceAll("\\r\\n", "");
        String errorMessage = output.split(System.getProperty("line.separator"))[0];

        Assertions.assertEquals(expected, errorMessage);
    }

}
