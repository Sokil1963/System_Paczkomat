package pl.pwr.paczkomaty.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;
import pl.pwr.paczkomaty.repository.UzytkownikRepository;

/**
 * Custom UserDetailsService dla Spring Security
 * Ładuje dane użytkownika z bazy danych
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Uzytkownik uzytkownik = uzytkownikRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new User(
                uzytkownik.getLogin(),
                uzytkownik.getHasloHash(),
                AuthorityUtils.createAuthorityList("ROLE_" + uzytkownik.getRola())
        );
    }
}

