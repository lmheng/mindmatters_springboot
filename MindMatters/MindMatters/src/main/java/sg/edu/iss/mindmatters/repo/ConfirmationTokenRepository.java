package sg.edu.iss.mindmatters.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.mindmatters.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}
