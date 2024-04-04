package pr.iceworld.fernando.springbootmockito;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pr.iceworld.fernando.springbootmockito.controller.OrderController;
import pr.iceworld.fernando.springbootmockito.entity.Order;
import pr.iceworld.fernando.springbootmockito.repository.OrderRepository;
import pr.iceworld.fernando.springbootmockito.service.OrderService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pr.iceworld.fernando.springbootmockito.controller.OrderController.ORDER_PATH;

@Tag("Integration")
@SpringBootTest
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class SpringBootMockitoApplicationTests {

    @Autowired
    OrderController orderController;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    ObjectMapper objectMapper;
    // @Autowired
    // WebApplicationContext wac;
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void test_ListAllOrders() throws Exception {
        long count = orderRepository.count();
        ResultActions resultActions = mockMvc.perform(get(ORDER_PATH))
                .andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.size()").value(count));
        //System.out.println(resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8")));
    }

    @org.junit.jupiter.api.Order(2)
    @Test
    void test_GetOrderById() throws Exception {
        Order order = orderRepository.findAll().get(0);
        ResultActions resultActions = mockMvc.perform(get(ORDER_PATH + "/{id}", order.getId()))
                .andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.name").value(order.getName()))
                .andExpect(jsonPath("$.price").value(order.getPrice()));
    }

    @org.junit.jupiter.api.Order(3)
    @Test
    void test_GetOrderById_404() throws Exception {
        Long orderId = orderRepository.findAll().stream().mapToLong(e -> e.getId()).max().orElse(0) + 10;
        ResultActions resultActions = mockMvc.perform(get(ORDER_PATH + "/{id}", orderId))
                .andExpect(status().isNotFound());
    }

    @org.junit.jupiter.api.Order(4)
    @Test
    void test_UpdateOrderById() throws Exception {
        Order order = orderRepository.findAll().get(0);
        Order updatedOrder = order;
        updatedOrder.setName("Picnic pot");
        updatedOrder.setPrice(95.5d);
        mockMvc.perform(put(ORDER_PATH + "/{id}", order.getId())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updatedOrder)))
                .andExpect(status().isNoContent());

        order = orderRepository.findById(order.getId()).get();
        assertEquals(order.getId(), updatedOrder.getId());
        assertEquals(order.getName(), updatedOrder.getName());
        assertEquals(order.getPrice(), updatedOrder.getPrice());
    }

    @org.junit.jupiter.api.Order(5)
    @Test
    void test_UpdateOrderById_404() throws Exception {
        Order order = orderRepository.findAll().get(0);
        Long orderId = orderRepository.findAll().stream().mapToLong(e -> e.getId()).max().orElse(0) + 10;
        Order updatedOrder = order;
        updatedOrder.setId(orderId + 10);
        updatedOrder.setName("Picnic pot");
        updatedOrder.setPrice(95.5d);
        mockMvc.perform(put(ORDER_PATH + "/{id}", updatedOrder.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOrder)))
                .andExpect(status().isNotFound());
    }

    @org.junit.jupiter.api.Order(6)
    @Test
    void test_CreateNewOrder() throws Exception {
        Order order = Order.builder()
                .name("Picnic pot")
                .price(95.5d)
                .build();
        ResultActions resultActions = mockMvc.perform(post(ORDER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated());

        Order createdOrder = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), Order.class);

        order = orderRepository.findById(createdOrder.getId()).get();
        assertNotNull(order);
    }

    @org.junit.jupiter.api.Order(7)
    @Test
    void test_DeleteOrderById() throws Exception {
        Order order = orderRepository.findAll().get(0);
        mockMvc.perform(delete(ORDER_PATH + "/{id}", order.getId()))
                .andExpect(status().isNoContent());

        Optional<Order> existed = orderRepository.findById(order.getId());
        assertFalse(existed.isPresent());
    }

    @org.junit.jupiter.api.Order(8)
    @Test
    void test_DeleteOrderById_404() throws Exception {
        Long orderId = orderRepository.findAll().stream().mapToLong(e -> e.getId()).max().orElse(0) + 10;
        mockMvc.perform(delete(ORDER_PATH + "/{id}", orderId))
                .andExpect(status().isNotFound());
    }
}
