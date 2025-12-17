package pl.pwr.paczkomaty.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pwr.paczkomaty.model.entity.Sortownia;
import pl.pwr.paczkomaty.repository.SortowniaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SortowniaServiceTest {

    @Mock
    private SortowniaRepository sortowniaRepository;

    @InjectMocks
    private SortowniaService sortowniaService;

    private Sortownia sortownia;

    @BeforeEach
    void setUp() {
        sortownia = new Sortownia();
        sortownia.setId(1);
        sortownia.setNazwa("Sortownia Centralna");
        sortownia.setAdres("ul. Testowa 1, Warszawa");
    }

    @Test
    void testPobierzWszystkieSortownie() {
        // Given
        List<Sortownia> sortownie = Arrays.asList(sortownia);
        when(sortowniaRepository.findAll()).thenReturn(sortownie);

        // When
        List<Sortownia> result = sortowniaService.pobierzWszystkieSortownie();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sortownia Centralna", result.get(0).getNazwa());
        verify(sortowniaRepository, times(1)).findAll();
    }

    @Test
    void testZnajdzSortownie() {
        // Given
        when(sortowniaRepository.findById(1)).thenReturn(Optional.of(sortownia));

        // When
        Optional<Sortownia> result = sortowniaService.znajdzSortownie(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Sortownia Centralna", result.get().getNazwa());
        verify(sortowniaRepository, times(1)).findById(1);
    }

    @Test
    void testZapiszSortownie() {
        // Given
        when(sortowniaRepository.save(any(Sortownia.class))).thenReturn(sortownia);

        // When
        Sortownia result = sortowniaService.zapiszSortownie(sortownia);

        // Then
        assertNotNull(result);
        assertEquals("Sortownia Centralna", result.getNazwa());
        verify(sortowniaRepository, times(1)).save(sortownia);
    }

    @Test
    void testUsunSortownie() {
        // Given
        doNothing().when(sortowniaRepository).deleteById(1);

        // When
        sortowniaService.usunSortownie(1);

        // Then
        verify(sortowniaRepository, times(1)).deleteById(1);
    }
}

