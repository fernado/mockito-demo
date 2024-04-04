package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class M15_CapturingArguments {

    @Test
    void action015() {
        class Person {
            private String name;
            public Person(String name) {
                this.name = name;
            }
            public String getName() {
                return this.name;
            }
        }

        class Group {
            private List<Person> persons = new ArrayList<>();
            public void addPerson(Person person) {
                persons.add(person);
            }
        }
        // Assuming you have imported necessary classes including Person and Mockito

        // Create a mock object
        Group mock = mock(Group.class);

        // Perform some action on the mock object that you want to verify
        mock.addPerson(new Person("John"));

        // Create an ArgumentCaptor for the Person class
        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);

        // Verify that the doSomething method was called with the captured argument
        verify(mock).addPerson(argument.capture());

        // Retrieve the captured argument and make assertions on its properties
        assertEquals("John", argument.getValue().getName());
    }

}
