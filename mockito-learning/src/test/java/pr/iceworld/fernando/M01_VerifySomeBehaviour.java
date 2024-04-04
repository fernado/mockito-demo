package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class M01_VerifySomeBehaviour {
    @Test
    void action001() {
        // org.mockito.Mockito#mock()
        // mock creation
        List mockedList = mock(List.class);

        // using mock object
        mockedList.add("one");
        mockedList.clear();

        // org.mockito.Mockito#verify()
        // verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

}
