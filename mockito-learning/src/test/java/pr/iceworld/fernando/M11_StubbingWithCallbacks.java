package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class M11_StubbingWithCallbacks {

    @Test
    void action011() {
        class Order {
            public String getOrderDetail(String orderNo) {
                return "order detail is searched by orderNo " + orderNo;
            }
        }

        Order order = mock(Order.class);
        // org.mockito.stubbing.OngoingStubbing.thenAnswer()
        // org.mockito.invocation.InvocationOnMock
        when(order.getOrderDetail(anyString())).thenAnswer(
                new Answer() {
                    public Object answer(InvocationOnMock invocation) {
                        Object[] args = invocation.getArguments();
                        Object mock = invocation.getMock();
                        return "called with arguments: " + Arrays.toString(args);
                    }
                });

        // Following prints "called with arguments: [O00002]"
        System.out.println(order.getOrderDetail("O00002"));
    }

}
