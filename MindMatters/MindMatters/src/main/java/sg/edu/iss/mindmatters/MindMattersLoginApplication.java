package sg.edu.iss.mindmatters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MindMattersLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(MindMattersLoginApplication.class, args);
	}

}
