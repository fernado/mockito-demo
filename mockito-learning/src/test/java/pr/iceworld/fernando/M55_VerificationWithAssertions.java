package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.*;

public class M55_VerificationWithAssertions {

    class PersonService {
        public void updateName(Person person) {
            // some implementation
        }
    }

    @Test
    void action055() {
        PersonService personService = mock(PersonService.class);

        Person person = new Person("Fernando", "Shui");
        personService.updateName(person);

        verify(personService).updateName(assertArg(param -> {
            assertEquals("Fernando", param.getFirstName());
            assertEquals("Shui", param.getLastName());
        }));
    }
}
