package hayashi.userservice.shared.util;

import hayashi.userservice.config.security.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUtils {

    private static final String SYSTEM_USER = "SYSTEM";

    public static String getCurrentUserId() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(authentication -> {
                    Object principal = authentication.getPrincipal();
                    if (principal instanceof AuthUser authUser) {
                        return authUser.getId();
                    }
                    return SYSTEM_USER;
                })
                .orElse(SYSTEM_USER);
    }

    public static String getCurrentUserName() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(authentication -> {
                    Object principal = authentication.getPrincipal();
                    if (principal instanceof AuthUser authUser) {
                        return authUser.getName();
                    }
                    return SYSTEM_USER;
                })
                .orElse(SYSTEM_USER);
    }

    public static String getCurrentUserEmail() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(authentication -> {
                    Object principal = authentication.getPrincipal();
                    if (principal instanceof AuthUser authUser) {
                        return authUser.getUsername(); // email
                    }
                    return SYSTEM_USER;
                })
                .orElse(SYSTEM_USER);
    }

    public static Optional<AuthUser> getCurrentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(principal -> principal instanceof AuthUser)
                .map(principal -> (AuthUser) principal);
    }
}
