package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class M10_StubbingConsecutiveCalls {
    @Test
    void action010() {
        class Order {
            public String getOrderDetail(String orderNo) {
                return "order detail is searched by orderNo " + orderNo;
            }
        }

        Order order = mock(Order.class);

        when(order.getOrderDetail("O00001"))
                .thenThrow(new RuntimeException())
                .thenReturn("O0000x");

        //First call: throws runtime exception:
        try {
            order.getOrderDetail("O00001");
            fail("RuntimeException must be thrown above line code");
        } catch (RuntimeException re) {
            System.out.println("order#getOrderDetail() exception has been caught.");
        }

        //Second call: prints "O0000x"
        assertEquals("O0000x", order.getOrderDetail("O00001"));

        //Any consecutive call: prints "O0000x" as well (last stubbing wins).
        assertEquals("O0000x", order.getOrderDetail("O00001"));
    }
}
