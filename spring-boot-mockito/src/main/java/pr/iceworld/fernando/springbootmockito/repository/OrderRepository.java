package pr.iceworld.fernando.springbootmockito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pr.iceworld.fernando.springbootmockito.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByNameLike(String name);

    List<Order> findAllByPriceLessThan(Double price);
}