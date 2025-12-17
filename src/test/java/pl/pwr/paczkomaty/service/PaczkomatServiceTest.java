package pl.pwr.paczkomaty.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pwr.paczkomaty.model.entity.Paczkomat;
import pl.pwr.paczkomaty.repository.PaczkomatRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaczkomatServiceTest {

    @Mock
    private PaczkomatRepository paczkomatRepository;

    @InjectMocks
    private PaczkomatService paczkomatService;

    private Paczkomat paczkomat;

    @BeforeEach
    void setUp() {
        paczkomat = new Paczkomat();
        paczkomat.setId(1);
        paczkomat.setKod("PACZ001");
        paczkomat.setAdres("ul. Testowa 1");
        paczkomat.setStatus("AKTYWNY");
    }

    @Test
    void testPobierzWszystkiePaczkomaty() {
        // Given
        List<Paczkomat> paczkomaty = Arrays.asList(paczkomat);
        when(paczkomatRepository.findAll()).thenReturn(paczkomaty);

        // When
        List<Paczkomat> result = paczkomatService.pobierzWszystkiePaczkomaty();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("PACZ001", result.get(0).getKod());
        verify(paczkomatRepository, times(1)).findAll();
    }

    @Test
    void testZnajdzPaczkomat() {
        // Given
        when(paczkomatRepository.findById(1)).thenReturn(Optional.of(paczkomat));

        // When
        Optional<Paczkomat> result = paczkomatService.znajdzPaczkomat(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals("PACZ001", result.get().getKod());
        verify(paczkomatRepository, times(1)).findById(1);
    }

    @Test
    void testZapiszPaczkomat() {
        // Given
        when(paczkomatRepository.save(any(Paczkomat.class))).thenReturn(paczkomat);

        // When
        Paczkomat result = paczkomatService.zapiszPaczkomat(paczkomat);

        // Then
        assertNotNull(result);
        assertEquals("PACZ001", result.getKod());
        verify(paczkomatRepository, times(1)).save(paczkomat);
    }
}

