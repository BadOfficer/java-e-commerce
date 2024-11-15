package com.tb.javaecommerce.service;

import com.tb.javaecommerce.domain.KittyProduct;
import com.tb.javaecommerce.service.impl.CosmoCatsServiceImpl;
import com.tb.javaecommerce.service.impl.KittyProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Kitty Product Service Test")
@SpringBootTest(classes = {KittyProductServiceImpl.class})
public class KittyProductServiceTest {
    @Autowired
    @Spy
    private KittyProductService kittyProductService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetKittyProducts() {
        List<KittyProduct> kittyProducts = new ArrayList<>(List.of(
                KittyProduct.builder().id(1L).name("Test kitty product").description("Some description").price(1.22).build()
        ));

        Mockito.when(kittyProductService.getAllKittyProducts()).thenReturn(kittyProducts);
        assertEquals(kittyProducts, kittyProductService.getAllKittyProducts());
    }
}
