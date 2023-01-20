package org.coen448.Controller;

import org.coen448.Configuration.DisplayConfiguration;
import org.coen448.Exception.BaseException;
import org.coen448.Exception.Error;
import org.coen448.Service.ProgramStatusService;
import org.junit.jupiter.api.*;
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

@ExtendWith(MockitoExtension.class)
public class DisplayControllerTest {

    @InjectMocks
    private DisplayController displayController;

    @Mock
    private ProgramStatusService programStatusServiceMock;

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
    public void GIVEN_invalidCommand_WHEN_menu_THEN_outputErrorMessage(final String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        displayController.menu();

        String output = outputContent.toString();
        String errorMessage = output.split(System.getProperty("line.separator"))[0];

        Assertions.assertEquals(BaseException.errorMessageMap.get(Error.COMMAND_INPUT_ERROR), errorMessage);
    }
}
