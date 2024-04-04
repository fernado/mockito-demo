package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class M52_NewStrictness {


    @Test
    void action052() {
        // Create a mock with strictness level
        MyInterface mock = mock(MyInterface.class, withSettings().strictness(Strictness.STRICT_STUBS));

        // Define behavior for the mocked method
        when(mock.doSomething()).thenReturn("Mocked value");

        // Call the method on the mock
        assertEquals("Mocked value", mock.doSomething());
        verify(mock).doSomething();

        assertNull(mock.doSomethingElse());
        verify(mock).doSomethingElse();

    }

}
