package pl.pwr.paczkomaty.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pwr.paczkomaty.model.entity.Przesylka;
import pl.pwr.paczkomaty.model.entity.StatusPrzesylki;
import pl.pwr.paczkomaty.repository.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrzesylkaServiceTest {

    @Mock
    private PrzesylkaRepository przesylkaRepository;

    @Mock
    private StatusPrzesylkiRepository statusPrzesylkiRepository;

    @Mock
    private HistoriaStatusuRepository historiaStatusuRepository;

    @Mock
    private PaczkomatRepository paczkomatRepository;

    @Mock
    private UzytkownikRepository uzytkownikRepository;

    @InjectMocks
    private PrzesylkaService przesylkaService;

    private Przesylka przesylka;
    private StatusPrzesylki status;

    @BeforeEach
    void setUp() {
        przesylka = new Przesylka();
        przesylka.setId(1);
        przesylka.setNumer(1001L);
        przesylka.setOpis("Testowa przesyłka");

        status = new StatusPrzesylki();
        status.setId(1);
        status.setKod("NADANA");
        status.setOpis("Przesyłka nadana");
    }

    @Test
    void testZnajdzPrzesylke() {
        // Given
        when(przesylkaRepository.findById(1)).thenReturn(Optional.of(przesylka));

        // When
        Optional<Przesylka> result = przesylkaService.znajdzPrzesylke(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals("PRZ001", result.get().getNumer());
        verify(przesylkaRepository, times(1)).findById(1);
    }

    @Test
    void testZnajdzPrzesylkePoNumerze() {
        // Given
        when(przesylkaRepository.findByNumer(1001L)).thenReturn(Optional.of(przesylka));

        // When
        Optional<Przesylka> result = przesylkaService.znajdzPrzesylkePoNumerze(1001L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(1001L, result.get().getNumer());
        verify(przesylkaRepository, times(1)).findByNumer(1001L);
    }

    @Test
    void testZapiszPrzesylke() {
        // Given
        when(przesylkaRepository.save(any(Przesylka.class))).thenReturn(przesylka);

        // When
        Przesylka result = przesylkaService.zapiszPrzesylke(przesylka);

        // Then
        assertNotNull(result);
        assertEquals("PRZ001", result.getNumer());
        verify(przesylkaRepository, times(1)).save(przesylka);
    }
}

