package sg.edu.iss.mindmatters.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.mindmatters.validation.EmailValidation;
import sg.edu.iss.mindmatters.validation.Expensive;
import sg.edu.iss.mindmatters.validation.PasswordValidation;
import sg.edu.iss.mindmatters.validation.Registration;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Username cannot be blank",groups= {Registration.class})
	@Pattern(regexp = "^[A-Za-z0-9]*$", message = "Username can contain only  alphabets and numbers",groups= {Registration.class})
	private String userName;

	@Pattern(regexp = "^[+]?[0-9]{8,20}$", message = "Phone number can contain only numbers and + before country codes", groups = Expensive.class)
	private String phone;

	@NotBlank(message = "Email cannot be blank", groups = { Registration.class, EmailValidation.class })
	@Email(message = "Email address should be of format example@example.com", groups = { Expensive.class,
			EmailValidation.class })
	private String email;

	@NotBlank(message = "Password cannot be blank", groups = {Registration.class, PasswordValidation.class })
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&]).{8,}$", message = "At least 8 characters. Should be combination of numbers, uppercase, lowercase and one special "
			+ "character from @$!%*#?&", groups = { Expensive.class, PasswordValidation.class })
	private String password;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private QuizOutcome quizoutcome;
	

    private String loginMethod;

    private String accessToken;

	private boolean isEnabled;

}
