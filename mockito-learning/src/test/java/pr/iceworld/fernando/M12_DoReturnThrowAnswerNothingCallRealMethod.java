package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class M12_DoReturnThrowAnswerNothingCallRealMethod {

    @Test
    void action012() {
        List mockedList = mock(List.class);
        doReturn("hello").when(mockedList).get(0);
        assertEquals("hello", mockedList.get(0));

        doNothing().when(mockedList).clear();
        mockedList.clear();

        doThrow(new RuntimeException()).when(mockedList).clear();

        try {
            // following throws RuntimeException:
            mockedList.clear();
            fail("RuntimeException");
        } catch (RuntimeException re) {
            // runtimeException => java.lang.RuntimeException
            System.out.println("runtimeException => " + re);
        }
    }

    /**
     * doCallRealMethod()
     */
    @Test
    void action012_002() {
        // Create a mock of Calculator
        Calculator mockCalculator = mock(Calculator.class);

        // Set up mock behavior
        when(mockCalculator.add(2, 3)).thenReturn(5); // Mocking add behavior

        // org.mockito.Mockito#doCallRealMethod()
        // Call real method on mock for specific arguments
        doCallRealMethod().when(mockCalculator).add(5, 5);

        // Create instance of CalculatorService with the mock
        CalculatorService calculatorService = new CalculatorService(mockCalculator);

        // Perform the test
        int result1 = calculatorService.performAddition(2, 3);
        int result2 = calculatorService.performAddition(5, 5);

        // Verify the results
        //
        // Assert that mocked behavior works
        assertEquals(5, result1);
        // Assert that real behavior works
        assertEquals(10, result2);
    }

}
