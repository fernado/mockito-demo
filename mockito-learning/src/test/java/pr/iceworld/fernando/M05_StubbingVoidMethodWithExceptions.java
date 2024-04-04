package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class M05_StubbingVoidMethodWithExceptions {

    @Test
    void action005() {
        LinkedList<String> mockedList = mock(LinkedList.class);
        // org.mockito.Mockito#doThrow()
        doThrow(new RuntimeException()).when(mockedList).clear();

        try {
            // following throws RuntimeException:
            // org.junit.jupiter.api.Assertions#fail()
            mockedList.clear();
            fail("RuntimeException");
        } catch (RuntimeException re) {
            // runtimeException => java.lang.RuntimeException
            System.out.println("runtimeException => " + re);
        }
    }
}
