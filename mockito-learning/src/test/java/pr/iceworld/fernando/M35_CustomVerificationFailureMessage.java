package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class M35_CustomVerificationFailureMessage {

    @Test
    void action035_001() {
        Person person = mock(Person.class);
        when(person.getIdCard()).thenReturn("1234");

        // will print a custom message on verification failure
        verify(person, description("This will print on failure")).getIdCard();
    }

    @Test
    void action035_002() {
        Person person = mock(Person.class);
        person.getIdCard();
        // will work with any verification mode
        verify(person, times(2).description("getIdCard should be called twice")).getIdCard();
    }
}
