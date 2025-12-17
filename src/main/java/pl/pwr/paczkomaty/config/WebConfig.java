package pl.pwr.paczkomaty.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Konfiguracja Web MVC
 * Aутентifikacja obsługiwana jest przez Spring Security
 * (AuthInterceptor został usunięty)
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    // Spring Security obsługuje aутentifikację i aутoryzację
}



