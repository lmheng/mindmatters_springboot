package sg.edu.iss.mindmatters.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.mindmatters.model.ConfirmationToken;
import sg.edu.iss.mindmatters.repo.ConfirmationTokenRepository;

@Service
public class ConfirmationTokenService {

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	private UserService userService;

	public ConfirmationToken findConfirmationToken(String token, String type) {
		ConfirmationToken ctToken = confirmationTokenRepository.findByConfirmationToken(token);

		long expiryDuration = 0l;

		if (type.equals("verify")) {
			expiryDuration = 24 * 60 * 60;
		} else if (type.equals("reset")) {
			expiryDuration = 15 * 60;
		}

		if (ctToken != null) {
			ZonedDateTime currentTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Singapore"));
			if ((currentTime.toEpochSecond() - ctToken.getCreatedDate().toEpochSecond()) < expiryDuration) {
				return ctToken;
			} else {
				confirmationTokenRepository.delete(ctToken);
				if (type.equals("verify")) {
					userService.delete(ctToken.getUser());
				}
			}
		}
		return null;
	}

	public void save(ConfirmationToken confirmationToken) {

		confirmationTokenRepository.save(confirmationToken);

	}

	public void delete(ConfirmationToken token) {

		confirmationTokenRepository.delete(token);

	}
}
