package yu.cs.spring;

import java.io.IOException;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

public class CustomSessionExpiredStrategy implements SessionInformationExpiredStrategy {

	private final String redirectUrl;

	public CustomSessionExpiredStrategy(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		HttpServletResponse response = event.getResponse();
		response.sendRedirect(redirectUrl);
	}
}