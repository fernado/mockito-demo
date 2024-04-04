package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class M36_Java8LambdaMatcher {

    @Test
    void action036_001() {
        List<String> list = mock(ArrayList.class);

        list.add("1234");
        list.add("2345");
        list.add("34567");

        // org.mockito.ArgumentMatchers#argThat()
        // verify a list only had strings of a certain length added to it
        // note - this will only compile under Java 8
        verify(list, times(2)).add(argThat(string -> string.length() < 5));

        // Java 7 equivalent - not as neat
        verify(list, times(2)).add(argThat(new ArgumentMatcher<String>() {
            public boolean matches(String arg) {
                return arg.length() < 5;
            }
        }));
    }

    public class ComplexObject {
        private List<String> subObject;

        public ComplexObject(List<String> subObject) {
            this.subObject = subObject;
        }

        public List<String> getSubObject() {
            return subObject;
        }
    }

    interface Target {
        void receiveComplexObject(ComplexObject obj);
    }

    public class MockClass {
        public String someMethod(List<?> list) {
            return list.size() < 3 ? "Mocked value" : null;
        }
    }

    @Test
    void action036_002() {
        Target target = mock(Target.class);
        ComplexObject complexObject = new ComplexObject(Arrays.asList("expected", "other"));

        // Call some method on the target with the complex object
        target.receiveComplexObject(complexObject);

        // more complex Java 8 example - where you can specify complex verification behaviour functionally
        verify(target, times(1)).receiveComplexObject(argThat(obj -> obj.getSubObject().get(0).equals("expected")));

        MockClass mock = mock(MockClass.class);

        // Define the behavior of the mock 'mock' for the 'someMethod' method
        // If the input list has fewer than 3 items, return "Mocked value"
        when(mock.someMethod(argThat(list -> list.size() < 3))).thenReturn("Mocked value");


        // Test the behavior of the mock
        List<String> inputList1 = Arrays.asList("item1", "item2");
        List<String> inputList2 = Arrays.asList("item1", "item2", "item3");

        // org.junit.jupiter.api.Assertions#assertEquals
        // org.junit.jupiter.api.Assertions#assertNull
        assertEquals("Mocked value", mock.someMethod(inputList1));
        assertNull(mock.someMethod(inputList2));
    }
}
