package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class M07_MakingSureInteractionNeverHappen {

    @Test
    void action007() {
        List mockOne = mock(List.class);
        // using mocks - only mockOne is interacted
        mockOne.add("one");

        // ordinary verification
        verify(mockOne).add("one");

        // verify that method was never called on a mock
        verify(mockOne, never()).add("two");
    }
}
