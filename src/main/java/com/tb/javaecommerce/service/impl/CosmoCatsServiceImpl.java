package com.tb.javaecommerce.service.impl;

import com.tb.javaecommerce.domain.CosmoCat;
import com.tb.javaecommerce.service.CosmoCatsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CosmoCatsServiceImpl implements CosmoCatsService {

    List<CosmoCat> cosmoCats = new ArrayList<>(List.of(
            CosmoCat.builder().id(1L).name("Cat Bob").countOfPaws(4).price(14.55).build(),
            CosmoCat.builder().id(2L).name("Cat Tom").countOfPaws(5).price(16.49).build()
    ));

    @Override
    public List<CosmoCat> getCosmoCats() {
        return cosmoCats;
    }
}
