package admin_user.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ADMIN"))) {
            response.sendRedirect("/admin-page");
        } else if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals("TUTEUR"))) {
            response.sendRedirect("/tuteur-page");
        } else if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ETUDIANT"))) {
            response.sendRedirect("/etudiant-page");
        } else {
            response.sendRedirect("/error");
        }
    }
}
