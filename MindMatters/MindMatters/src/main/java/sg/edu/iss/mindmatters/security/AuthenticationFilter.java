package sg.edu.iss.mindmatters.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sg.edu.iss.mindmatters.model.MyUserPrincipal;
import sg.edu.iss.mindmatters.model.User;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setFilterProcessesUrl("/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			User creds = new ObjectMapper().readValue(request.getInputStream(), User.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException("Could not read request" + e);
		}
	}

	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication authentication) {
		String token = JWTtoken.getJwtToken(((MyUserPrincipal) authentication.getPrincipal()).getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		User user = new User();
		user.setUserName(principal.getUser().getUserName());

		try {
			String responseToClient = ow.writeValueAsString(user);
			response.getWriter().write(responseToClient);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
