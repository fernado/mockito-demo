package pr.iceworld.fernando.springbootmockito.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pr.iceworld.fernando.springbootmockito.entity.Order;
import pr.iceworld.fernando.springbootmockito.repository.OrderRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        loadOrderData();
    }

    private void loadOrderData() {
        if (orderRepository.count() == 0) {
            Order order1 = Order.builder()
                    .id(1001L).name("Moon chairs")
                    .price(58.0)
                    .createTime(LocalDateTime.of(2024, 04, 01, 22, 10, 10))
                    .build();
            Order order2 = Order.builder()
                    .id(1002L).name("Outdoor canopy black coating")
                    .price(98.0)
                    .createTime(LocalDateTime.of(2024, 04, 01, 22, 10, 10))
                    .build();
            Order order3 = Order.builder()
                    .id(1003L).name("Outdoor canopy silver coating")
                    .price(48.0)
                    .createTime(LocalDateTime.of(2024, 04, 01, 22, 10, 10))
                    .build();
            orderRepository.save(order1);
            orderRepository.save(order2);
            orderRepository.save(order3);
        }
    }
}
