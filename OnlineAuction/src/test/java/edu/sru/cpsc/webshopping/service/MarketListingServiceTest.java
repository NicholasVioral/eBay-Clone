package edu.sru.cpsc.webshopping.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;

public class MarketListingServiceTest {

    @Mock
    private MarketListingRepository marketListingRepository;

    @InjectMocks
    private MarketListingService marketListingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindPage() {
        Pageable pageable = PageRequest.of(0, 4);
        List<MarketListing> listings = Arrays.asList(new MarketListing(), new MarketListing());

        when(marketListingRepository.findAllByIsDeletedNotInAndQtyAvailableNotIn(any(), any(), any())).thenReturn(new PageImpl<>(listings, pageable, 2));

        Page<MarketListing> result = marketListingService.findPage(1);

        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
    }
}
