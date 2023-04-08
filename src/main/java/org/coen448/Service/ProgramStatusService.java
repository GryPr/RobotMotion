package org.coen448.Service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.coen448.Data.Orientation;
import org.coen448.Data.StateData;
import org.coen448.Exception.InitSizeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ProgramStatusService {
    @Inject
    private final StateData stateData;

    @Getter
    @Setter
    private boolean running = true;

    public void initialize(int length) throws InitSizeException {
        if (length < 2) throw new InitSizeException();
        stateData.setMatrix(IntStream.range(0, length)
                .mapToObj(i -> new ArrayList<Integer>(Collections.nCopies(length, 0)))
                .collect(Collectors.toList()));
        stateData.setPenDown(false);
        stateData.setOrientation(Orientation.NORTH);
        stateData.setXPosition(0);
        stateData.setYPosition(0);
    }
}
