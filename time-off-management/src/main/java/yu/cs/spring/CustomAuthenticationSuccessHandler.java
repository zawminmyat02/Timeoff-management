package yu.cs.spring;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import yu.cs.spring.model.master.entity.Account;
import yu.cs.spring.model.master.repo.AccountRepo;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AccountRepo accountRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        Account account = accountRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!account.isActivated()) {
            response.sendRedirect("/change-password");
        } else {
            response.sendRedirect("/home");
        }
    }
}
