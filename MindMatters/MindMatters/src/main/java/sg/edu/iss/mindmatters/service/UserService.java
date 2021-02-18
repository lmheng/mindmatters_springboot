package sg.edu.iss.mindmatters.service;

import java.security.Principal;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sg.edu.iss.mindmatters.model.AccountFacebook;
import sg.edu.iss.mindmatters.model.AccountGoogle;
import sg.edu.iss.mindmatters.model.ConfirmationToken;
import sg.edu.iss.mindmatters.model.MyUserPrincipal;
import sg.edu.iss.mindmatters.model.User;
import sg.edu.iss.mindmatters.repo.UserRepository;
import sg.edu.iss.mindmatters.security.JWTtoken;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private ConfirmationTokenService ctService;

	public void save(User user) {
		userRepository.save(user);
	}

	public void delete(User user) {
		userRepository.delete(user);
	}

	public User findByEmail(String userEmail) {
		return userRepository.findByEmail(userEmail);
	}

	@Transactional
	public String registerUser(User newUser) {

		User user = userRepository.findByEmail(newUser.getEmail());
		if (user != null) {
			String loginMethod = user.getLoginMethod();
			if (loginMethod.equals("facebook"))
				return "Email address is already registered as a Facebook account.\nPlease login through facebook"
						+ " or create account with a different email address.";
			else if (loginMethod.equals("google"))
				return "Email address is already registered as a Google account.\nPlease login through google"
						+ " or create account with a different email address.";
			else
				return "User with email id already exists. \nPlease login using your credentials or"
						+ " create account with a different email address.";
		}
		newUser.setLoginMethod("email");
		user = userRepository.save(newUser);
		String subject = "Complete Registration!";
		String text = "To confirm your account, please click here : "
				+ "https://mindmatters.azurewebsites.net/users/confirm-account?token=";
		saveTokenAndSendEmail(user, subject, text);
		return "SUCCESS... Confirm email before you can log in";

	}

	@Transactional
	public String confirmUser(String confirmationToken) {

		ConfirmationToken token = ctService.findConfirmationToken(confirmationToken, "verify");
		if (token != null) {
			User user = userRepository.findByEmail(token.getUser().getEmail());
			user.setEnabled(true);
			userRepository.save(user);
			ctService.delete(token);
			return "success";
		}

		return "invalid";
	}

	@Transactional
	public String updateUser(User newUser) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = userRepository.findByEmail(newUser.getEmail());
		String response = "";
		if (user != null) {
			if (!passwordEncoder.matches(newUser.getPassword(), user.getPassword())) {
				String encodedPassword = passwordEncoder.encode(newUser.getPassword());
				user.setPassword(encodedPassword);
				response = "Password updated";
			} else {
				response = "User updated";
			}
			user.setPhone(newUser.getPhone());
			user.setUserName(newUser.getUserName());
			user = userRepository.save(user);
		} else {
			response = "User Not found";
		}
		return response;
	}

	@Transactional
	public String sendResetLink(User existingUser) {
		User user = userRepository.findByEmail(existingUser.getEmail());
		if (user != null && user.getLoginMethod().equals("email")) {
			String subject = "Reset Password!";
			String text = "To reset your password, please click the link below : "
					+ "\n The link is valid for 15 minutes only\n"
					+ "https://mindmatters.azurewebsites.net//users/reset-password?token=";
			saveTokenAndSendEmail(user, subject, text);
			return "SUCCESS... Check your email to reset the password";
		}
		return "User does not exists for the email id entered";

	}

	@Transactional
	public String updatePassword(ConfirmationToken ct) {
		ConfirmationToken newCt = ctService.findConfirmationToken(ct.getConfirmationToken(), "reset");
		if (newCt != null) {
			User user = newCt.getUser();
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(ct.getUser().getPassword());
			user.setPassword(encodedPassword);
			userRepository.save(user);
			ctService.delete(newCt);
			return "Password update Successful";
		}
		return "The link is invalid or broken! Try Again";
	}

	public void saveTokenAndSendEmail(User user, String subject, String text) {

		ConfirmationToken confirmationToken = new ConfirmationToken(user);
		ctService.save(confirmationToken);

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject(subject);
		mailMessage.setFrom("team1.sa51@gmail.com");
		mailMessage.setText(text + confirmationToken.getConfirmationToken());

		emailSenderService.sendEmail(mailMessage);

	}

	public ResponseEntity<User> socialLogin(User user) {
		User response = getSocialDetails(user);
		if (response.getEmail() != null) {
			response.setLoginMethod(user.getLoginMethod());
			User existingUser = userRepository.findByEmail(response.getEmail());
			if (existingUser != null) {
				if (!existingUser.getLoginMethod().equals(response.getLoginMethod())) {
					HttpHeaders responseHeaders = new HttpHeaders();
					responseHeaders.set("Error", "User with email id already exists");
					return new ResponseEntity<User>(null, responseHeaders, HttpStatus.BAD_REQUEST);
				}
			} else {
				response.setEnabled(true);
				userRepository.save(response);
			}

			HttpHeaders responseHeaders = new HttpHeaders();
			String jwtToken = JWTtoken.getJwtToken(response.getEmail());
			response.setEmail(null);
			responseHeaders.set("Authorization", "Bearer " + jwtToken);
			return new ResponseEntity<User>(response, responseHeaders, HttpStatus.OK);
		}

		return new ResponseEntity<User>(null, null, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<User> getInfo(Principal principal) {
		User user = userRepository.findByEmail(principal.getName());
		if (user.getLoginMethod().equals("email")) {
			user.setPassword(null);
			user.setId(null);
			return new ResponseEntity<User>(user, null, HttpStatus.OK);
		}
		return new ResponseEntity<User>(null, null, HttpStatus.BAD_REQUEST);
	}

	@Transactional
	public ResponseEntity<String> deleteAccount(User user, Principal principal) {
		
		User response = null;
		String email = principal.getName();
		
		if (user.getLoginMethod().equals("google")) {
			response = getSocialDetails(user);
			if (!email.equals(response.getEmail())) {
				return new ResponseEntity<String>("Google authentication error", null, HttpStatus.BAD_REQUEST);
			}

		} else if (user.getLoginMethod().equals("facebook")) {
				response = getSocialDetails(user);
				if (!email.equals(response.getEmail())) {
					return new ResponseEntity<String>("Facebook authentication error", null, HttpStatus.BAD_REQUEST);
				}

		} else if (user.getLoginMethod().equals("email")) {
			email = principal.getName();
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			User existingUser = userRepository.findByEmail(email);
			if (user != null) {
				if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
					return new ResponseEntity<String>("Wrong password", null, HttpStatus.BAD_REQUEST);
				}
			}
		}
		User existingUser = userRepository.findByEmail(email);
		userRepository.delete(existingUser);
		return new ResponseEntity<String>("Success", null, HttpStatus.OK);
	}

	private User getSocialDetails(User user) {
		String accessToken = user.getAccessToken();
		UriComponentsBuilder uriBuilder;
		User response = new User();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		RestTemplate restTemplate = new RestTemplate();

		if (user.getLoginMethod().equals("facebook")) {
			try {
				AccountFacebook facebook = null;
				// field names which will be retrieved from facebook
				final String fields = "id,email,first_name,last_name";
				uriBuilder = UriComponentsBuilder.fromUriString("https://graph.facebook.com/me")
						.queryParam("access_token", accessToken).queryParam("fields", fields);
				facebook = restTemplate.getForObject(uriBuilder.toUriString(), AccountFacebook.class);
				response.setEmail(facebook.getEmail());
				response.setUserName(facebook.getFirst_name() + facebook.getLast_name());
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				return response;
			} catch (Exception exp) {
				exp.printStackTrace();
				return response;
			}
		} else if (user.getLoginMethod().equals("google")) {
			try {
				AccountGoogle google = null;
				uriBuilder = UriComponentsBuilder.fromUriString("https://www.googleapis.com/oauth2/v3/tokeninfo")
						.queryParam("id_token", accessToken);
				google = restTemplate.getForObject(uriBuilder.toUriString(), AccountGoogle.class);
				response.setEmail(google.getEmail());
				response.setUserName(google.getName());
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				return response;
			} catch (Exception exp) {
				exp.printStackTrace();
				return response;
			}
		}
		return response;
	}
}
