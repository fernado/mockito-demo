package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import javax.print.Doc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class M49_MockingObjectContruction {

    class Dog {
        public String bark() {
            return "wang wang";
        }
    }

    @Test
    void action048() {
        assertEquals("wang wang", new Dog().bark());

        try (MockedConstruction mockedConstruction = mockConstruction(Dog.class)) {
            Dog dog = new Dog();
            when(dog.bark()).thenReturn("miao");
            assertEquals("miao", dog.bark());
            verify(dog).bark();
        }

        assertEquals("wang wang", new Dog().bark());
    }


}
