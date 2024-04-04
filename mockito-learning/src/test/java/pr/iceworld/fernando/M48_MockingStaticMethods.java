package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

public class M48_MockingStaticMethods {

    class Dog {
        public static String bark() {
            return "wang wang";
        }
    }

    @Test
    void action048() {
        assertEquals("wang wang", Dog.bark());

        try (MockedStatic mockedStatic = mockStatic(Dog.class)) {
            mockedStatic.when(Dog::bark).thenReturn("miao");
            assertEquals("miao", Dog.bark());
            mockedStatic.verify(Dog::bark);
        }

        assertEquals("wang wang", Dog.bark());
    }


}
