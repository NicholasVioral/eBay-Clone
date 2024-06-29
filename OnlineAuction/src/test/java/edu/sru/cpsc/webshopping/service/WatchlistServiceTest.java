package edu.sru.cpsc.webshopping.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.sru.cpsc.webshopping.repository.user.UserRepository;

public class WatchlistServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WatchlistService watchlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCountUsersWithMarketListingInWatchlist() {
        Long marketListingId = 1L;

        when(userRepository.countUsersWithMarketListingInWatchlist(marketListingId)).thenReturn(2L);

        Long count = watchlistService.countUsersWithMarketListingInWatchlist(marketListingId);

        assertEquals(2L, count);
    }
}
