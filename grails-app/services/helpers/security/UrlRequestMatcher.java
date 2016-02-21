package helpers.security;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

public class UrlRequestMatcher implements RequestMatcher {

	private String pattern;
	private AntPathMatcher antPathMatcher;

	public UrlRequestMatcher(String pattern) {
		antPathMatcher = new AntPathMatcher();
		this.pattern = pattern;
    }
	
	@Override
	public boolean matches(HttpServletRequest request) {
		String path = request.getServletPath();
		return antPathMatcher.match(pattern, path);
	}

}
