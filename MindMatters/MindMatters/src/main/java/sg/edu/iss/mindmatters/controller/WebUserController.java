package sg.edu.iss.mindmatters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.mindmatters.model.ConfirmationToken;
import sg.edu.iss.mindmatters.model.User;
import sg.edu.iss.mindmatters.service.ConfirmationTokenService;
import sg.edu.iss.mindmatters.service.UserService;
import sg.edu.iss.mindmatters.validation.PasswordValidation;

@Controller
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class WebUserController {

	@Autowired
	private ConfirmationTokenService ctservice;

	@Autowired
	private UserService uservice;

	@GetMapping("reset-password")
	public String resetPassword(@RequestParam("token") String token, Model model) {
		ConfirmationToken ct = ctservice.findConfirmationToken(token, "reset");
		if (ct != null) {
			ct.setUser(new User());
			model.addAttribute("token", ct);
			return "resetPassword";
		} else {
			model.addAttribute("message", "The link is no longer valid");
			return "userMessage";
		}
	}

	@PostMapping("submitChange")
	public String submitChange(@Validated(PasswordValidation.class) @ModelAttribute("token") ConfirmationToken ct,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "resetPassword";
		}
		String result = uservice.updatePassword(ct);
		model.addAttribute("message", result);
		return "userMessage";
	}

	@GetMapping("confirm-account")
	public String confirmUserAccount(@RequestParam("token") String confirmationToken, Model model) {
		String status = uservice.confirmUser(confirmationToken);
		if (status.equals("success")) {
			model.addAttribute("message", "Acccount is verified");
		} else {
			model.addAttribute("message", "The link is no longer valid");
		}
		return "userMessage";
	}
}
