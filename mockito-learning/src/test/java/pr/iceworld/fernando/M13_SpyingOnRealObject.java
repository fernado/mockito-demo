package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class M13_SpyingOnRealObject {

    @Test
    void action013_001() {
        List list = new LinkedList();
        // org.mockito.Mockito#spy()
        List spy = spy(list);

        // optionally, you can stub out some methods:
        when(spy.size()).thenReturn(100);

        // using the spy calls *real* methods
        spy.add("one");
        spy.add("two");

        // prints "one" - the first element of a list
        System.out.println(spy.get(0));

        // size() method was stubbed - 100 is printed
        System.out.println(spy.size());

        // optionally, you can verify
        verify(spy).add("one");
        verify(spy).add("two");
    }

    @Test
    void action013_002() {
        List list = new LinkedList();
        List spy = spy(list);

        // Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
        try {
            when(spy.get(0)).thenReturn("foo");
            fail("Index Out of Bounds Exception");
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("IndexOutOfBoundsException => " + ioobe);
        }

        // You have to use doReturn() for stubbing
        doReturn("foo").when(spy).get(0);
        // now it prints what we want, foo => foo
        System.out.println("foo => " + spy.get(0));

    }

}
