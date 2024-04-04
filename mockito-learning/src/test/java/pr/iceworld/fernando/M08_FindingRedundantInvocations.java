package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class M08_FindingRedundantInvocations {

    @Test
    void action008_001() {
        List mockedList = mock(List.class);
        // using mocks
        mockedList.add("one");
        mockedList.add("two");

        // comment one of below line code, will fail when run at verifyNoMoreInteractions()
        verify(mockedList).add("one");
        verify(mockedList).add("two");

        // org.mockito.Mockito#verifyNoMoreInteractions()
        verifyNoMoreInteractions(mockedList);
    }

    @Test
    void action008_002() {
        List mockedList = mock(List.class);
        // using mocks
        mockedList.add("one");
        mockedList.add("two");

        // comment one of below line code, will fail when run at verifyNoMoreInteractions()
        verify(mockedList).add("one");
        verify(mockedList).add("two");

        verify(mockedList, never()).add("never add element");
    }
}
