package pr.iceworld.fernando.springbootmockito.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pr.iceworld.fernando.springbootmockito.entity.Order;
import pr.iceworld.fernando.springbootmockito.exception.NotFoundException;
import pr.iceworld.fernando.springbootmockito.service.OrderService;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pr.iceworld.fernando.springbootmockito.controller.OrderController.ORDER_PATH;

@WebMvcTest
class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;
    @Captor
    ArgumentCaptor<Order> orderArgumentCaptor;
    @Captor
    ArgumentCaptor<Long> idArgumentCaptor;

    final Order order1 = Order.builder().id(1001L).name("Moon chairs").price(58.0).createTime(LocalDateTime.of(2024, 04, 01, 22, 10, 10)).build();
    final Order order2 = Order.builder().id(1002L).name("Outdoor canopy").price(158.0).createTime(LocalDateTime.of(2024, 04, 01, 22, 10, 10)).build();

    @Test
    void givenOrder_whenCreateOrder_thenReturn_CreatedOrder() throws Exception {
        given(orderService.save(any(Order.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
        Order requestOrder = order1;
        requestOrder.setId(null);
        ResultActions response = mockMvc.perform(post(ORDER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestOrder)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(requestOrder.getName()))
                .andExpect(jsonPath("$.price").value(requestOrder.getPrice()));

        verify(orderService).save(any(Order.class));
    }

    @Test
    void givenOrders_whenGetOrders_thenReturnOrders() throws Exception {
        List<Order> listOfOrders = new ArrayList<>();
        listOfOrders.add(order1);
        listOfOrders.add(order2);

        given(orderService.getOrders()).willReturn(listOfOrders);

        ResultActions response = mockMvc.perform(get(ORDER_PATH));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(listOfOrders.size()));
        verify(orderService, times(1)).getOrders();
    }

    @Test
    void givenOrderId_whenGetOrderById_thenReturnOrder() throws Exception {

        given(orderService.getOrderById(order1.getId())).willReturn(Optional.of(order1));

        ResultActions response = mockMvc.perform(get(ORDER_PATH + "/{id}", order1.getId()));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value(order1.getName()))
                .andExpect(jsonPath("$.price").value(order1.getPrice()));
        verify(orderService).getOrderById(anyLong());
    }

    @Test
    void givenInvalidOrderId_whenGetOrderById_thenReturnEmpty() throws Exception {

        given(orderService.getOrderById(anyLong())).willThrow(NotFoundException.class);

        ResultActions response = mockMvc.perform(get(ORDER_PATH + "/{id}", new Random().nextInt()));

        response.andExpect(status().isNotFound()).andDo(print());
        verify(orderService).getOrderById(anyLong());
    }

    @Test
    void givenUpdatedOrder_whenUpdateOrder_thenReturn204() throws Exception {
        Order requestOrder = order2;
        requestOrder.setId(order1.getId());

        given(orderService.getOrderById(order1.getId())).willReturn(Optional.of(order1));
        given(orderService.update(anyLong(), any(Order.class))).willReturn(Optional.of(requestOrder));

        ResultActions response = mockMvc.perform(put(ORDER_PATH + "/{id}", order1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order2)));
        response.andExpect(status().isNoContent());

        verify(orderService).update(anyLong(), any(Order.class));
    }

    @Test
    void givenUpdatedOrder_whenUpdateOrder_thenReturn404() throws Exception {
        long orderId = order1.getId();
        // given(orderService.getOrderById(orderId)).willReturn(Optional.empty());
        given(orderService.update(anyLong(), any(Order.class))).willThrow(NotFoundException.class);
        ResultActions response = mockMvc.perform(put(ORDER_PATH + "/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order2)));

        response.andExpect(status().isNotFound());

        // verify(orderService).getOrderById(anyLong());
        verify(orderService).update(anyLong(), any(Order.class));
    }

    @Test
    void givenOrderId_whenDeleteOrderById_thenReturn200() throws Exception {
        long OrderId = 1L;
        given(orderService.delete(OrderId)).willReturn(true);
        ResultActions response = mockMvc.perform(delete(ORDER_PATH + "/{id}", OrderId));
        response.andExpect(status().isNoContent());
        verify(orderService).delete(anyLong());
    }
}