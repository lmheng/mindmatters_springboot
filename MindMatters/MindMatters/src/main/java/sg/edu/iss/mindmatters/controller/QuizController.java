package sg.edu.iss.mindmatters.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import reactor.core.publisher.Mono;
import sg.edu.iss.mindmatters.model.Quiz;
import sg.edu.iss.mindmatters.model.User;
import sg.edu.iss.mindmatters.service.QuizImplementation;
import sg.edu.iss.mindmatters.service.QuizServices;
import sg.edu.iss.mindmatters.service.UserService;

@Controller
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	QuizServices qService;

	@Autowired
	UserService uService;

	@Autowired
	public void setQuizService(QuizImplementation qImpl) {
		this.qService = qImpl;
	}

	@GetMapping("/landing")
	public String quizForm(Model model, Quiz quiz) {

		quiz = new Quiz();
		model.addAttribute(quiz);

		return "Quiz";
	}

	@GetMapping("/submit")
	public String submitForm(@ModelAttribute Quiz quiz, RedirectAttributes redirectAttributes) {
		WebClient webClient = WebClient.create("http://127.0.0.1:5000/");
		String outcome = webClient.post().uri("/results").body(Mono.just(quiz), Quiz.class).retrieve()
				.bodyToMono(String.class).block();

		redirectAttributes.addFlashAttribute("outcome", outcome);

		return "redirect:/quiz/result";
	}

	@GetMapping("/result")
	public String getResult(Model model, Principal principal) {

		String value = (String) model.asMap().get("outcome");
		value = value.substring(1, value.length() - 2);

		if (principal != null) {
			User user = uService.findByEmail(principal.getName());
			qService.saveQuizOutcome(value, user);
		}

		model.addAttribute("outcome", value);

		return "Outcome";
	}

}
