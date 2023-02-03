package org.coen448.Service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.coen448.Data.StateData;
import org.coen448.Exception.MinDistanceException;
import org.coen448.Exception.NoInitException;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PenService {
    @Inject
    private final StateData stateData;

    public void penUp() throws NoInitException {
        stateData.isInitialized();
        stateData.setPenDown(false);
    }

    public void penDown() throws NoInitException {
        stateData.isInitialized();
        stateData.setPenDown(true);
    }
}
