package com.tb.javaecommerce.service.impl;

import com.tb.javaecommerce.domain.KittyProduct;
import com.tb.javaecommerce.service.KittyProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KittyProductServiceImpl implements KittyProductService {
    List<KittyProduct> kittyProducts = new ArrayList<>(List.of(
            KittyProduct.builder().id(1L).name("Cat Cup").description("Interesting Cat Cup").price(5.99).build(),
            KittyProduct.builder().id(2L).name("Cat Bag").description("Wonderful Cat Bag").price(3.99).build()
    ));

    @Override
    public List<KittyProduct> getAllKittyProducts() {
        return kittyProducts;
    }
}
