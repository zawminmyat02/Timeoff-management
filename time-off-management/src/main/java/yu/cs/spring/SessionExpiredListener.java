package yu.cs.spring;

import java.io.IOException;

import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class SessionExpiredListener implements ApplicationListener<SessionInformationExpiredEvent> {

    @Override
    public void onApplicationEvent(SessionInformationExpiredEvent event) {
        HttpServletResponse response = (HttpServletResponse) event.getSource();
        try {
            response.sendRedirect("/login?sessionExpired=true");
            System.out.println("sessionExpired");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

