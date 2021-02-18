package sg.edu.iss.mindmatters.controller;



import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.iss.mindmatters.model.User;
import sg.edu.iss.mindmatters.service.UserService;
import sg.edu.iss.mindmatters.validation.EmailValidation;
import sg.edu.iss.mindmatters.validation.ValidationSequence;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping("register")
	public String registerUser(@Validated(ValidationSequence.class) @RequestBody User newUser) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newUser.getPassword());
		newUser.setPassword(encodedPassword);
		return userService.registerUser(newUser);
	}
	
	@GetMapping("getInfo")
	public ResponseEntity<User> getInfo(Principal principal) {
		return userService.getInfo(principal);
	}

	@PostMapping("edit")
	public String updateUser(@Validated(ValidationSequence.class) @RequestBody User newUser) {
		return userService.updateUser(newUser);
	}

	@PostMapping("forgot")
	public String sendResetLink(@Validated(EmailValidation.class) @RequestBody User user) {
		return userService.sendResetLink(user);
	}
	
	@PostMapping("social")
	public ResponseEntity<User> loginFacebook(@RequestBody User user) {
		return userService.socialLogin(user);
	}
	
	@PostMapping("delete")
	public ResponseEntity<String> deleteAccount(@RequestBody User user,Principal principal) {
		return userService.deleteAccount(user,principal);
	}
	
	@GetMapping("verifyUser")
	public ResponseEntity<String> verify(Principal principal) {
		if(principal!=null) {
			return new ResponseEntity<String>("Success",null,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Failure",null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
