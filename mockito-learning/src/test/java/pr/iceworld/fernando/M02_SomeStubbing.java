package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.*;

public class M02_SomeStubbing {

    @Test
    void action002() {
        // You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);
        // Mockito does not directly support simulation of raw data types
        // such as int, double, etc.
        // However, we can use some of the methods provided by Mockito
        // to simulate the method parameters of the original data type
        // error
        // int iValue = mock(int.class);
        // Integer intValue = mock(Integer.class);

        // org.mockito.ArgumentMatchers#intThat()
        // correct
        // int iValue = anyInt();
        // correct
        int iValue = intThat(e -> e > 100);

        // org.mockito.Mockito#when()
        // org.mockito.stubbing.OngoingStubbing#thenReturn()
        // org.mockito.stubbing.OngoingStubbing#thenThrow()
        // stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        // org.junit.jupiter.api.Assertions#assertEquals()
        // following prints "first"
        assertNull(mockedList.get(0));

        // org.junit.jupiter.api.Assertions#assertNull()
        // following prints "null" because get(999) was not stubbed
        System.out.println("mockedList.get(999) = " + mockedList.get(999));
        // assertNull(mockedList.get(999));

        System.out.println("iValue = " + iValue);

        // System.out.println(intValue);

        // Although it is possible to verify a stubbed invocation, usually it's just redundant
        // If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
        // If your code doesn't care what get(0) returns, then it should not be stubbed.
        verify(mockedList).get(0);

        try {
            // following throws runtime exception
            System.out.println("mockedList.get(1) = " + mockedList.get(1));
            fail("RuntimeException must be thrown above line code");
        } catch (RuntimeException re) {
            System.out.println("mockedList.get(1) exception has been caught.");
        }
    }
}
