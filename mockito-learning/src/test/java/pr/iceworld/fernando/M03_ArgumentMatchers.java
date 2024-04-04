package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;

import java.util.LinkedList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class M03_ArgumentMatchers {


    @Test
    void action003_001() {
        LinkedList<String> mockedList = mock(LinkedList.class);
        // org.mockito.ArgumentMatchers#anyInt()
        // stubbing using built-in anyInt() argument matcher
        when(mockedList.get(anyInt())).thenReturn("element");

        // org.mockito.ArgumentMatchers#argThat()
        // stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
        when(mockedList.contains(argThat(isValid()))).thenReturn(true);

        // following prints "element"
        System.out.println(mockedList.get(999));

        // you can also verify using an argument matcher
        verify(mockedList).get(anyInt());
        System.out.println(mockedList.get(anyInt()));

        mockedList.add("12345");
        mockedList.add("3456789");

        // argument matchers can also be written as Java 8 Lambdas
        verify(mockedList).add(argThat(someString -> someString.length() > 5));
    }

    private ArgumentMatcher isValid() {
        return obj -> {
            System.out.println("obj -> " + obj);
            return obj != null;
        };
    }

    @Test
    void action003_002() {
        class Person {
            private int age;
            private String firstName;
            private String lastName;

            public String updatePerson(int age, String firstName, String lastName) {
                this.age = age;
                this.firstName = firstName;
                this.lastName = lastName;
                return "updated";
            }
        }
        Person mock = mock(Person.class);
        String result = mock.updatePerson(30, "John", "Doe");
        System.out.println(result);
        // org.mockito.ArgumentMatchers#anyString()
        // org.mockito.ArgumentMatchers#eq()
        // correct - eq() is also an argument matcher
        verify(mock).updatePerson(anyInt(), anyString(), eq("Doe"));

        // incorrect - exception will be thrown because third argument is given without an argument matcher.
        // verify(mock).updatePerson(anyInt(), anyString(), "third argument");
    }
}
