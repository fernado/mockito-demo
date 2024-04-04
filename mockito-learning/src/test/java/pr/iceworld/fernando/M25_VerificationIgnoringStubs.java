package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.List;

import static org.mockito.Mockito.*;

public class M25_VerificationIgnoringStubs {
    class Foo {
        public String foo() {
            return "foo";
        }
    }
    class Bar {
        public String bar() {
            return "bar";
        }
    }

    @Test
    void action025_001() {

        Foo mock = mock(Foo.class);
        Bar mockTwo = mock(Bar.class);
        when(mock.foo()).thenReturn("fooX");
        when(mockTwo.bar()).thenReturn("barX");

        mock.foo();
        mockTwo.bar();
        verify(mock).foo();

        // org.mockito.Mockito#verifyNoMoreInteractions()
        // verifyNoMoreInteractions() fails because mockTwo.bar() methods were not accounted for.
        // verifyNoMoreInteractions(mock, mockTwo);

        // org.mockito.Mockito#ignoreStubs()
        // successfully as ignore Stubbing mockTwo.bar()
        verifyNoMoreInteractions(ignoreStubs(mock, mockTwo));
    }

    @Test
    void action025_002() {
        Foo mock = mock(Foo.class);
        Bar mockTwo = mock(Bar.class);
        when(mock.foo()).thenReturn("fooX");
        when(mockTwo.bar()).thenReturn("barX");
        mock.foo();
        mockTwo.bar();
        verify(mockTwo).bar();
        // creates InOrder that will ignore stubbed
        InOrder inOrder = inOrder(ignoreStubs(mock, mockTwo));
        mockTwo.bar();
        mock.foo();
        inOrder.verify(mockTwo).bar();
        inOrder.verify(mock).foo();

        inOrder.verifyNoMoreInteractions();
    }


    @Test
    void action025_003() {
        List list = mock(List.class);
        when(list.get(0)).thenReturn("foo");

        list.add(0);
        list.clear();
        System.out.println(list.get(0)); //we don't want to verify this

        InOrder inOrder = inOrder(ignoreStubs(list));
        inOrder.verify(list).add(0);
        inOrder.verify(list).clear();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void action025_004() {
        // mocking lists for the sake of the example (if you mock List in real you will burn in hell)
        List mock1 = mock(List.class);
        List mock2 = mock(List.class);

        // stubbing mocks:
        when(mock1.get(0)).thenReturn(10);
        when(mock2.get(0)).thenReturn(20);

        // using mocks by calling stubbed get(0) methods:
        System.out.println(mock1.get(0)); // prints 10
        System.out.println(mock2.get(0)); // prints 20

        // using mocks by calling clear() methods:
        mock1.clear();
        mock2.clear();

        // verification:
        verify(mock1).clear();
        verify(mock2).clear();

        // verifyNoMoreInteractions() fails because get() methods were not accounted for.

        // verify(mock1).get(0);
        // verify(mock2).get(0);
        // verifyNoMoreInteractions(mock1, mock2);

        // org.mockito.Mockito#verifyNoMoreInteractions()
        // org.mockito.Mockito#ignoreStubs()
        // However, if we ignore stubbed methods then we can verifyNoMoreInteractions()
        verifyNoMoreInteractions(ignoreStubs(mock1, mock2));

        // Remember that ignoreStubs() *changes* the input mocks and returns them for convenience.
    }

}
