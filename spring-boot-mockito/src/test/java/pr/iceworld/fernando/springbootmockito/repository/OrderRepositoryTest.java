package pr.iceworld.fernando.springbootmockito.repository;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import pr.iceworld.fernando.springbootmockito.bootstrap.InitData;
import pr.iceworld.fernando.springbootmockito.entity.Order;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(InitData.class)
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void test_findAllByNameLike() {
        List<Order> list = orderRepository.findAllByNameLike("%Outdoor%");
        assertEquals(2, list.size());
    }

    @Test
    void test_findAllByNameLike_Empty() {
        List<Order> list = orderRepository.findAllByNameLike("%Outdoor folding chair%");
        assertEquals(0, list.size());
    }

    @Test
    void test_findAllByPriceLessThan() {
        List<Order> list = orderRepository.findAllByPriceLessThan(50d);
        assertEquals(1, list.size());
    }

    @Test
    void test_findAllByPriceLessThan_Empty() {
        List<Order> list = orderRepository.findAllByPriceLessThan(10d);
        assertEquals(0, list.size());
    }

    @Test
    void test_saveOrder() {
        Order order4 = Order.builder()
                .id(1004L).name("Outdoor folding chair")
                .price(28.0)
                .createTime(LocalDateTime.of(2024, 04, 01, 22, 10, 10))
                .build();
         orderRepository.save(order4);
        long count = orderRepository.count();
        assertEquals(4, count);
    }

    @Test
    void test_saveOrder_NameIsTooLong() {
        Order order4 = Order.builder()
                .id(1004L).name("Outdoor folding chair Outdoor folding chair Outdoor folding chair Outdoor folding chair Outdoor folding chair")
                .price(28.0)
                .createTime(LocalDateTime.of(2024, 04, 01, 22, 10, 10))
                .build();
        order4 = orderRepository.save(order4);
        try {
            orderRepository.flush();
            fail("Name is null");
        } catch (ConstraintViolationException cve) {
            assertTrue(validate(cve, "size must be between 0 and 50", "name"));
        }
    }

    @Test
    void test_saveOrder_NameIsNull() {
        Order order4 = Order.builder()
                .id(1004L)
                .price(28.0)
                .createTime(LocalDateTime.of(2024, 04, 01, 22, 10, 10))
                .build();
        try {
            order4 = orderRepository.save(order4);
            orderRepository.flush();
            fail("Name is null");
        } catch (ConstraintViolationException cve) {
            assertTrue(validate(cve, "must not be null", "name"));
        }
    }

    private boolean validate(ConstraintViolationException cve, String message, String fieldName) {
        return cve.getConstraintViolations().stream()
                .anyMatch(e -> {
                    boolean hasMessage = e.getMessage().contains(message);
                    Iterator<Path.Node> itr = e.getPropertyPath().iterator();
                    boolean matchedFieldName = false;
                    while (itr.hasNext()) {
                        Path.Node pNode = itr.next();
                        matchedFieldName = pNode.getName().equals(fieldName);
                        if (matchedFieldName) {
                            break;
                        }
                    }
                    return hasMessage && matchedFieldName;
                });
    }
}
