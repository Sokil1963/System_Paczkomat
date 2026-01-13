package pl.pwr.paczkomaty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;
import pl.pwr.paczkomaty.repository.UzytkownikRepository;

import java.util.Optional;


public abstract class BaseController {

    @Autowired
    protected UzytkownikRepository uzytkownikRepository;


    protected Optional<Uzytkownik> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        String username = authentication.getName();
        return uzytkownikRepository.findByLogin(username);
    }



}

