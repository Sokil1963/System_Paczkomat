package pl.pwr.paczkomaty.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import pl.pwr.paczkomaty.annotation.RequireRole;

@Aspect
@Component
public class RoleCheckAspect {

    @Around("@annotation(requireRole)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RequireRole requireRole) throws Throwable {
        // W prawdziwej aplikacji pobierz użytkownika z sesji/Spring Security
        // Tutaj kontrola dostępu jest realizowana w kontrolerach przez BaseController
        
        // Sprawdź uprawnienia
        if (requireRole.requireAdmin()) {
            // W prawdziwej aplikacji sprawdź rolę użytkownika
            // Na razie pozwalamy wszystkim (w produkcji to musi być zaimplementowane)
        }

        return joinPoint.proceed();
    }
}

