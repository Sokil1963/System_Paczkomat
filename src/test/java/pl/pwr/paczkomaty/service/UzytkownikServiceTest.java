package pl.pwr.paczkomaty.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;
import pl.pwr.paczkomaty.repository.UzytkownikRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UzytkownikServiceTest {

    @Mock
    private UzytkownikRepository uzytkownikRepository;

    @InjectMocks
    private UzytkownikService uzytkownikService;

    private Uzytkownik uzytkownik;

    @BeforeEach
    void setUp() {
        uzytkownik = new Uzytkownik();
        uzytkownik.setId(1);
        uzytkownik.setLogin("admin");
        uzytkownik.setHasloHash("haslo123");
        uzytkownik.setRola("ADMIN");
    }

    @Test
    void testPobierzWszystkichUzytkownikow() {
        // Given
        List<Uzytkownik> uzytkownicy = Arrays.asList(uzytkownik);
        when(uzytkownikRepository.findAll()).thenReturn(uzytkownicy);

        // When
        List<Uzytkownik> result = uzytkownikService.pobierzWszystkichUzytkownikow();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("admin", result.get(0).getLogin());
        verify(uzytkownikRepository, times(1)).findAll();
    }

    @Test
    void testPobierzUzytkownikowPoRoli() {
        // Given
        List<Uzytkownik> uzytkownicy = Arrays.asList(uzytkownik);
        when(uzytkownikRepository.findByRola("ADMIN")).thenReturn(uzytkownicy);

        // When
        List<Uzytkownik> result = uzytkownikService.pobierzUzytkownikowPoRoli("ADMIN");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ADMIN", result.get(0).getRola());
        verify(uzytkownikRepository, times(1)).findByRola("ADMIN");
    }

    @Test
    void testZnajdzUzytkownika() {
        // Given
        when(uzytkownikRepository.findById(1)).thenReturn(Optional.of(uzytkownik));

        // When
        Optional<Uzytkownik> result = uzytkownikService.znajdzUzytkownika(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals("admin", result.get().getLogin());
        verify(uzytkownikRepository, times(1)).findById(1);
    }

    @Test
    void testZapiszUzytkownika() {
        // Given
        when(uzytkownikRepository.save(any(Uzytkownik.class))).thenReturn(uzytkownik);

        // When
        Uzytkownik result = uzytkownikService.zapiszUzytkownika(uzytkownik);

        // Then
        assertNotNull(result);
        assertEquals("admin", result.getLogin());
        assertNotNull(result.getHasloHash());
        verify(uzytkownikRepository, times(1)).save(any(Uzytkownik.class));
    }

    @Test
    void testUsunUzytkownika() {
        // Given
        doNothing().when(uzytkownikRepository).deleteById(1);

        // When
        uzytkownikService.usunUzytkownika(1);

        // Then
        verify(uzytkownikRepository, times(1)).deleteById(1);
    }
}

