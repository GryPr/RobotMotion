package Controller;

import org.coen448.Configuration.DisplayConfiguration;
import org.coen448.Controller.DisplayController;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class DisplayControllerTest {

    private final DisplayController displayController = new DisplayController();

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

    @ParameterizedTest
    @ValueSource(strings = {
            "", // no command
            "someNonExistentCommand", // non-existent command
            "u 123451", // single input command that's too long
            "m awdawdw", // double input command with non-numerical second input
            "m", // double input command with no second input
            "m|12" // double input command with invalid separator
    })
    public void GIVEN_invalidCommand_WHEN_showMenu_THEN_outputErrorMessage(final String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        displayController.showMenu();

        String output = outputContent.toString();
        String errorMessage = output.split(System.getProperty("line.separator"))[1];

        Assertions.assertEquals(DisplayConfiguration.inputErrorMessage, errorMessage);
    }
}
