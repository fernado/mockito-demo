package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class M14_ChangingDefaultReturnValues {

    class Person {

        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public String getFullName() {
            return "fullName > ";
        }
    }
    class YourOwnAnswer implements Answer {
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            return "You see me";
        }
    }

    @Test
    void action014() {

        Person mockPerson1 = mock(Person.class, Mockito.RETURNS_SMART_NULLS);
        Person mockPerson2 = mock(Person.class, new YourOwnAnswer());

        //
        System.out.println(mockPerson1.getName());
        // You see me
        System.out.println(mockPerson2.getName());
        //
        System.out.println(mockPerson1.getFullName());
        // You see me
        System.out.println(mockPerson2.getFullName());

        when(mockPerson1.getName()).thenReturn("1mk -> ");
        when(mockPerson2.getName()).thenReturn("2mk -> ");
        // 1mk ->
        System.out.println(mockPerson1.getName());
        // 2mk ->
        System.out.println(mockPerson2.getName());

        when(mockPerson1.getFullName()).thenReturn("3mk -> ");
        when(mockPerson2.getFullName()).thenReturn("4mk -> ");
        // 3mk ->
        System.out.println(mockPerson1.getFullName());
        // 4mk ->
        System.out.println(mockPerson2.getFullName());
    }

}
