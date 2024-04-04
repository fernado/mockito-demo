package pr.iceworld.fernando.springbootmockito.service;

import org.springframework.stereotype.Service;
import pr.iceworld.fernando.springbootmockito.entity.Order;
import pr.iceworld.fernando.springbootmockito.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Order> update(Long id, Order order) {
        AtomicReference<Optional<Order>> atomicReference = new AtomicReference<>();
        orderRepository.findById(id).ifPresentOrElse(foundVal -> {
            updateOrder(order, foundVal);
            atomicReference.set(Optional.of(orderRepository.save(foundVal)));
        }, () -> atomicReference.set(Optional.empty()));
        return atomicReference.get();
    }

    public Order save(Order order) {
        if (null == order.getId()) {
            order.setCreateTime(LocalDateTime.now());
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Boolean delete(Long id) {
        if (getOrderById(id).isEmpty()) {
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }

    private void updateOrder(Order current, Order newOrder) {
        if (null != current.getPrice()) {
            newOrder.setPrice(current.getPrice());
            newOrder.setName(current.getName());
            newOrder.setUpdateTime(LocalDateTime.now());
        }
    }
}