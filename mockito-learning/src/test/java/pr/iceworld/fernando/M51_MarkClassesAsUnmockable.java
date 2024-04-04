package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.DoNotMock;

public class M51_MarkClassesAsUnmockable {

    @DoNotMock
    class Person {
        public String getName() {
            return "name";
        }
    }

    @Test
    void action051() {
        // org.mockito.exceptions.misusing.DoNotMockException:
        // class pr.iceworld.fernando.M51_MarkClassesAsUnmockable$Person
        // is annotated with @org.mockito.DoNotMock and can't be mocked.
        // Create a real instance instead.
        // Person person = mock(Person.class);
        // Person person = spy(Person.class);
        Person person = new Person();
    }
}
