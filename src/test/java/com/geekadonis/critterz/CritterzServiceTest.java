package com.geekadonis.critterz;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CritterzServiceTest {

    @Mock private CritterzRepository critterzRepository;
    @Mock private RestClient.Builder restClientBuilder;

    @InjectMocks private CritterzService service;

    @Test
    public void testGet() {
        var list = List.of(Critter.builder().id(1L).build());
        when(critterzRepository.findAll()).thenReturn(list);

        var critters = service.findAll();

        assertThat(critters.spliterator().estimateSize()).isEqualTo(1 );

        verify(critterzRepository, times(1)).findAll();
    }

    @Test
    public void getUriOfCritterApi() {
        assertEquals("https://placebear.com/100/200",
            service.getUriOfCritterApi(CritterType.BEAR, 100, 200));

        assertEquals("https://place.dog/50/100",
            service.getUriOfCritterApi(CritterType.DOG, 50, 100));

        assertEquals("https://placekitten.com/200/100",
            service.getUriOfCritterApi(CritterType.KITTEN, 200, 100));

    }
}