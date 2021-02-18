package sg.edu.iss.mindmatters.service;

import javax.servlet.http.HttpSession;

import org.springframework.data.repository.query.Param;

import sg.edu.iss.mindmatters.model.QuizOutcome;
import sg.edu.iss.mindmatters.model.User;

public interface QuizServices {
	
	public void saveQuizOutcome(String outcome, User user);
		
	public boolean checkAttempt(User user);
	
	public QuizOutcome findByEmail(String userEmail);
	
}
