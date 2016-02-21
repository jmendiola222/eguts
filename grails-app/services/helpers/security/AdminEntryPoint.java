package helpers.security;

import grails.plugin.springsecurity.web.authentication.AjaxAwareAuthenticationEntryPoint;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminEntryPoint extends AjaxAwareAuthenticationEntryPoint {
	
	public AdminEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException e)
			throws IOException, ServletException {
		
	}
}
