package pr.iceworld.fernando.springbootmockito.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pr.iceworld.fernando.springbootmockito.entity.Order;
import pr.iceworld.fernando.springbootmockito.exception.NotFoundException;
import pr.iceworld.fernando.springbootmockito.service.OrderService;

import java.util.List;

import static pr.iceworld.fernando.springbootmockito.controller.OrderController.ORDER_PATH;

@RestController
@RequestMapping(ORDER_PATH)
public class OrderController {

    public static final String ORDER_PATH = "/api/v1/orders";

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order savedOrder = orderService.save(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") long orderId) {
        return orderService.getOrderById(orderId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable("id") long orderId,
                                             @RequestBody Order order) {
        if (orderService.update(orderId, order).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") long orderId) {
        if (orderService.delete(orderId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        throw new NotFoundException();
    }
}