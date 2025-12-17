/*
 * PLIK USUNIĘTY
 *
 * AuthInterceptor.java został usunięty, ponieważ Spring Security obsługuje
 * aутентifikację i aутoryzację znacznie lepiej niż ręczne interceptory.
 *
 * DLACZEGO?
 * --------
 * 1. Powodował konflikt ze Spring Security
 * 2. Nie obsługiwał poprawnie BCrypt haseł
 * 3. Tworzył duplikat logiki aутentifikacji
 *
 * NOWA ARCHITEKTURA:
 * -----------------
 * - SecurityConfig.java: Konfiguracja filtrów i reguł dostępu
 * - CustomUserDetailsService.java: Ładowanie użytkowników z bazy
 * - BCryptPasswordEncoder: Bezpieczne kodowanie haseł
 *
 * DOSTĘP DO ZASOBÓW JEST TERAZ KONTROLOWANY PRZEZ:
 * ------------------------------------------------
 * 1. SecurityFilterChain w SecurityConfig.java
 * 2. Role-based access control (@PreAuthorize, hasRole())
 * 3. CSRF protection
 * 4. Method-level security
 */


