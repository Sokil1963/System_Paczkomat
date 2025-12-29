package pl.pwr.paczkomaty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;
import pl.pwr.paczkomaty.repository.UzytkownikRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public List<Uzytkownik> pobierzUzytkownikowPoRoli(String rola) {
        return uzytkownikRepository.findByRola(rola);
    }

    public Optional<Uzytkownik> znajdzUzytkownika(Integer id) {
        return uzytkownikRepository.findById(id);
    }


    public Uzytkownik zapiszUzytkownika(Uzytkownik uzytkownik) {
        if (uzytkownik.getHasloHash() != null &&
            !uzytkownik.getHasloHash().startsWith("$2a$") &&
            !uzytkownik.getHasloHash().startsWith("$2b$")) {
            uzytkownik.setHasloHash(passwordEncoder.encode(uzytkownik.getHasloHash()));
        }
        return uzytkownikRepository.save(uzytkownik);
    }

    public void usunUzytkownika(Integer id) {
        uzytkownikRepository.deleteById(id);
    }




    /**
     * @deprecated Używaj passwordEncoder.encode() zamiast tego
     * Ta metoda jest zachowana dla wstecznej kompatybilności
     */
    @Deprecated
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Błąd podczas hashowania hasła", e);
        }
    }
}

