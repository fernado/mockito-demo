package pr.iceworld.fernando.springbootmockito.service;

import pr.iceworld.fernando.springbootmockito.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<Order> update(Long id, Order order);
    Order save(Order order);

    List<Order> getOrders();

    Optional<Order> getOrderById(Long id);

    Boolean delete(Long id);
}