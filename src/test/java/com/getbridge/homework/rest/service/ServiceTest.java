package com.getbridge.homework.rest.service;

import com.getbridge.homework.rest.dto.OneOnOneDto;
import com.getbridge.homework.rest.dto.Search1on1Dto;
import com.getbridge.homework.rest.entity.OneOnOne;
import com.getbridge.homework.rest.repository.OneOnOneRepository;
import com.getbridge.homework.rest.service.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceTest {

    private final String VALID_ID = "valid-id";

    @Mock
    private OneOnOneRepository oneOnOneRepository;

    @Mock
    private Util util;

    private Service service;

    OneOnOne validOneOnOne = new OneOnOne();
    OneOnOneDto oneOnOneDto = new OneOnOneDto();


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new Service(oneOnOneRepository, util);
        String UPDATED_TITLE = "updated-title";
        oneOnOneDto.setTitle(UPDATED_TITLE);
        validOneOnOne.setId(VALID_ID);
        when(oneOnOneRepository.findById(eq(VALID_ID))).thenReturn(Optional.of(validOneOnOne));
    }

    @Test
    void testUpdate1on1_ExistingOneOnOne_ReturnsUpdatedOneOnOne() {
        OneOnOne updatedOneOnOne = new OneOnOne();
        when(oneOnOneRepository.findById(validOneOnOne.getId())).thenReturn(Optional.of(validOneOnOne));
        when(util.convertOneOnOneDtoToEntity(oneOnOneDto)).thenReturn(updatedOneOnOne);
        when(oneOnOneRepository.save(updatedOneOnOne)).thenReturn(updatedOneOnOne);

        OneOnOne result = service.update1on1(VALID_ID, oneOnOneDto);

        assertNotNull(result);
        assertEquals(updatedOneOnOne, result);
        verify(oneOnOneRepository).findById(VALID_ID);
        verify(util).convertOneOnOneDtoToEntity(oneOnOneDto);
        verify(oneOnOneRepository).save(updatedOneOnOne);
    }

    @Test
    void testUpdate1on1_NonExistingOneOnOne_ThrowsRuntimeException() {
        when(oneOnOneRepository.findById(VALID_ID)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.update1on1(VALID_ID, oneOnOneDto));
        verify(oneOnOneRepository).findById(VALID_ID);

    }

    @Test
    void testConclude1on1_ExistingOneOnOne_ReturnsUpdatedOneOnOne() {
        when(oneOnOneRepository.findById(VALID_ID)).thenReturn(Optional.of(validOneOnOne));
        when(oneOnOneRepository.save(validOneOnOne)).thenReturn(validOneOnOne);

        OneOnOne result = service.conclude1on1(VALID_ID);

        assertNotNull(result);
        assertTrue(result.isConcluded());
        verify(oneOnOneRepository).findById(VALID_ID);
        verify(oneOnOneRepository).save(validOneOnOne);
    }

    @Test
    void testConclude1on1_NonExistingOneOnOne_ThrowsRuntimeException() {
        when(oneOnOneRepository.findById(VALID_ID)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.conclude1on1(VALID_ID));
        verify(oneOnOneRepository).findById(VALID_ID);
        verifyNoMoreInteractions(util, oneOnOneRepository);
    }

    @Test
    void testSearch_NoMatches_ReturnsEmptyList() {
        Search1on1Dto search1on1Dto = new Search1on1Dto();
        when(oneOnOneRepository.search(search1on1Dto)).thenReturn(Optional.empty());

        List<OneOnOne> result = service.search(search1on1Dto);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(oneOnOneRepository).search(search1on1Dto);
        verifyNoMoreInteractions(util, oneOnOneRepository);
    }

    @Test
    void testSearch_MatchesFound_ReturnsMatchedList() {
        Search1on1Dto search1on1Dto = new Search1on1Dto();
        List<OneOnOne> matches = new ArrayList<>();
        matches.add(new OneOnOne());
        matches.add(new OneOnOne());
        when(oneOnOneRepository.search(search1on1Dto)).thenReturn(Optional.of(matches));

        List<OneOnOne> result = service.search(search1on1Dto);

        assertNotNull(result);
        assertEquals(matches.size(), result.size());
        assertEquals(matches, result);
        verify(oneOnOneRepository).search(search1on1Dto);
        verifyNoMoreInteractions(util, oneOnOneRepository);
    }
}

