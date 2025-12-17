package pl.pwr.paczkomaty.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;
import pl.pwr.paczkomaty.repository.UzytkownikRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Implementacja UserDetailsService dla Spring Security
 * Ładuje dane użytkownika z bazy danych
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Uzytkownik> uzytkownikOpt = uzytkownikRepository.findByLogin(login);

        if (uzytkownikOpt.isEmpty()) {
            throw new UsernameNotFoundException("Użytkownik nie znaleziony: " + login);
        }

        Uzytkownik uzytkownik = uzytkownikOpt.get();
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // Dodaj rolę z prefiksem ROLE_
        String role = uzytkownik.getRola();
        if (role != null && !role.isEmpty()) {
            if (!role.startsWith("ROLE_")) {
                role = "ROLE_" + role;
            }
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return new org.springframework.security.core.userdetails.User(
            uzytkownik.getLogin(),
            uzytkownik.getHasloHash(),
            true,
            true,
            true,
            true,
            authorities
        );
    }
}

