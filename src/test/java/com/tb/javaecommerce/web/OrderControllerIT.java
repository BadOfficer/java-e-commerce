package com.tb.javaecommerce.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.javaecommerce.AbstractIt;
import com.tb.javaecommerce.common.OrderStatus;
import com.tb.javaecommerce.common.ProductStatus;
import com.tb.javaecommerce.domain.Order;
import com.tb.javaecommerce.dto.order.OrderItemRequestDto;
import com.tb.javaecommerce.dto.order.OrderRequestDto;
import com.tb.javaecommerce.dto.order.OrderResponseDto;
import com.tb.javaecommerce.repository.CategoryRepository;
import com.tb.javaecommerce.repository.OrderRepository;
import com.tb.javaecommerce.repository.ProductRepository;
import com.tb.javaecommerce.repository.entity.CategoryEntity;
import com.tb.javaecommerce.repository.entity.OrderEntity;
import com.tb.javaecommerce.repository.entity.OrderItemEntity;
import com.tb.javaecommerce.repository.entity.ProductEntity;
import com.tb.javaecommerce.service.OrderService;
import com.tb.javaecommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AutoConfigureMockMvc
@DisplayName("Order Controller IT")
@SpringBootTest
public class OrderControllerIT extends AbstractIt {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @SpyBean
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        Mockito.reset(orderService);
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    void shouldCreateOrder() throws Exception {
        ProductEntity productEntity = productEntity();
        OrderRequestDto orderRequestDto = orderRequestDto(productEntity.getProduct_reference().toString());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldCreateOrderProductNotFound() throws Exception {
        OrderRequestDto orderRequestDto = orderRequestDto(UUID.randomUUID().toString());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void shouldCreateOrderFailed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                .contentType("application/json")
                .content("{}")).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldGetAllOrders() throws Exception {
        ProductEntity product = productEntity();
        orderService.createOrder(orderRequestDto(product.getProduct_reference().toString()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateOrderStatus() throws Exception {
        ProductEntity product = productEntity();
        Order order = orderService.createOrder(orderRequestDto(product.getProduct_reference().toString()));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/orders/" + order.getId())
                        .param("status", String.valueOf(OrderStatus.COMPLETED))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteOrder() throws Exception {
        ProductEntity product = productEntity();
        Order order = orderService.createOrder(orderRequestDto(product.getProduct_reference().toString()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/orders/" + order.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteOrderNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/orders/" + UUID.randomUUID().toString()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private List<OrderItemRequestDto> orderItemRequestDtoList (String productId) {
        List<OrderItemRequestDto> orderItemRequestDtoList = new ArrayList<>();

        orderItemRequestDtoList.add(OrderItemRequestDto.builder()
                .productId(productId)
                .quantity(2).build());

        return orderItemRequestDtoList;
    }

    private OrderRequestDto orderRequestDto (String productId) {
        return OrderRequestDto.builder()
                .orderItems(orderItemRequestDtoList(productId))
                .email("test@example.com")
                .address("address 1")
                .consumerName("Taras").build();
    }

    private CategoryEntity categoryEntity () {
        return categoryRepository.save(CategoryEntity.builder()
                .title("Cat 1")
                .description("Desc Cat 1")
                .build());
    }

    private ProductEntity productEntity () {
        return productRepository.save(ProductEntity.builder()
                .category(categoryEntity())
                .product_reference(UUID.randomUUID())
                .status(ProductStatus.IN_STOCK)
                .title("Prod 1 galaxy")
                .description("Descr Prod 1")
                .price(14.99)
                .build());
    }
}
