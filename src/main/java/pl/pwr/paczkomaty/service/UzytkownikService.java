package pl.pwr.paczkomaty.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;
import pl.pwr.paczkomaty.repository.UzytkownikRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UzytkownikService {

    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Uzytkownik> pobierzWszystkichUzytkownikow() {
        return uzytkownikRepository.findAll();
    }

    public Optional<Uzytkownik> znajdzUzytkownika(Integer id) {
        return uzytkownikRepository.findById(id);
    }

    public Optional<Uzytkownik> znajdzPoLoginie(String login) {
        return uzytkownikRepository.findByLogin(login);
    }

    public List<Uzytkownik> znajdzKurierow() {
        return uzytkownikRepository.findByRola("KURIER");
    }

    public Uzytkownik zapiszUzytkownika(Uzytkownik uzytkownik) {
        if (uzytkownik.getHasloHash() != null && !uzytkownik.getHasloHash().isEmpty()) {
            if (!uzytkownik.getHasloHash().startsWith("$2a$")) {
                uzytkownik.setHasloHash(passwordEncoder.encode(uzytkownik.getHasloHash()));
            }
        }
        return uzytkownikRepository.save(uzytkownik);
    }

    public void aktualizujUzytkownika(Integer id, String login, String noweHaslo) {
        Uzytkownik user = uzytkownikRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Użytkownik nie istnieje"));

        user.setLogin(login);
        if (noweHaslo != null && !noweHaslo.isBlank()) {
            user.setHasloHash(passwordEncoder.encode(noweHaslo));
        }
        uzytkownikRepository.save(user);
    }

    public void usunUzytkownika(Integer id) {
        uzytkownikRepository.deleteById(id);
    }
}