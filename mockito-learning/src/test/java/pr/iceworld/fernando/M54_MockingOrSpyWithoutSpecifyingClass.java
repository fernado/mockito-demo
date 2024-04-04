package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.*;

public class M54_MockingOrSpyWithoutSpecifyingClass {

    @Test
    void action054_001() {
        ArrayList list = spy();
        list.add("1");
        assertEquals(1, list.size());
    }

    @Test
    void action054_002() {
        // spy issue here
        // Unable to create mock instance of type 'Person', if Person class is an inner class
        // otherwise it will successfully
        Person personSpy = spy();
        assertEquals("Shui", personSpy.getLastName());
    }

    @Test
    void action054_003() {
        Person person = mock();
        when(person.getFirstName()).thenReturn("X");
        assertEquals("X", person.getFirstName());
        // spy issue here

        Person instance = new Person();
        Person personSpy = spy(instance);
        assertEquals("Shui", personSpy.getLastName());

        AbsPerson absPersonSpy = spy();
        assertNull(absPersonSpy.getLastName());
        absPersonSpy = spy(AbsPerson.class);
        assertNull(absPersonSpy.getLastName());

        personSpy = spy();
        assertEquals("Shui", personSpy.getLastName());
        personSpy = spy(Person.class);
        assertEquals("Shui", personSpy.getLastName());
    }
}
