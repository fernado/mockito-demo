package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class M39_MockingFinalTypesAndEnumAndFinalMethods {
    final class FinalClass {
        public String finalMethod() {
            return "Original result";
        }
        Status status = Status.DEFAULT;
        public Status getStatus() {
            return status;
        }
    }

    enum Status {
        SUCCESS,
        FAILURE,
        DEFAULT;

        public String getName() {
            return name();
        }
    }

    @Test
    void action039() {
        FinalClass mockFinalClass = mock(FinalClass.class);
        when(mockFinalClass.finalMethod()).thenReturn("Mocked result");
        assertEquals("Mocked result", mockFinalClass.finalMethod());

        when(mockFinalClass.getStatus()).thenReturn(Status.FAILURE);
        assertEquals(Status.FAILURE, mockFinalClass.getStatus());

        Status status = mock(Status.class);
        when (status.getName()).thenReturn("UNKNOWN");
        assertEquals("UNKNOWN", status.getName());
    }
}
