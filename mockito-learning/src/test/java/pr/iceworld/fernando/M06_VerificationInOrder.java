package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.List;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

public class M06_VerificationInOrder {

    @Test
    void action006() {
        // A. Single mock whose methods must be invoked in a particular order
        List singleMock = mock(List.class);

        // using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");

        // org.mockito.InOrder#inOrder()
        // create an inOrder verifier for a single mock
        InOrder inOrder = inOrder(singleMock);

        // following will make sure that add is first called with "was added first", then with "was added second"
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

        // error
        // inOrder.verify(singleMock).add("was added second");
        // inOrder.verify(singleMock).add("was added first");

        // B. Multiple mocks that must be used in a particular order
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        // using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        // create inOrder object passing any mocks that need to be verified in order
        InOrder inOrderWith2Params = inOrder(secondMock, firstMock);
        // correct also
        // InOrder inOrderWith2Params = inOrder(firstMock, secondMock);

        // following will make sure that firstMock was called before secondMock
        inOrderWith2Params.verify(firstMock).add("was called first");
        inOrderWith2Params.verify(secondMock).add("was called second");

        // Oh, and A + B can be mixed together at will
    }
}
