package org.coen448.Service;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import com.google.inject.Singleton;
import org.coen448.Data.StateData;
import org.coen448.Exception.MaxDistanceException;
import org.coen448.Exception.MinDistanceException;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MoveService {
    @Inject
    private final StateData stateData;

    public void move(int distance) throws MaxDistanceException, MinDistanceException {
        
    }
}
