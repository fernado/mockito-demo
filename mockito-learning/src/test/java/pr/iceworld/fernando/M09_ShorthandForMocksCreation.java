package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class M09_ShorthandForMocksCreation {

    @Test
    void action009() {
        class Order {
            public String getOrderDetail(String orderNo) {
                return "order detail is searched by orderNo " + orderNo;
            }
        }

        Order order = mock(Order.class);

        when(order.getOrderDetail("O00001"))
                .thenThrow(new RuntimeException())
                .thenReturn("foo");

        // First call: throws runtime exception:
        try {
            order.getOrderDetail("O00001");
            fail("RuntimeException must be thrown above line code");
        } catch (RuntimeException re) {
            System.out.println("order#getOrderDetail() exception has been caught.");
        }

        // Second call: prints "foo"
        System.out.println(order.getOrderDetail("O00001"));

        // Any consecutive call: prints "foo" as well (last stubbing wins).
        System.out.println(order.getOrderDetail("O00001"));
    }

}
