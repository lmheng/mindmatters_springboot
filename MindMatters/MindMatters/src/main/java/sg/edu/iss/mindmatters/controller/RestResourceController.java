package sg.edu.iss.mindmatters.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.MalformedJwtException;
import sg.edu.iss.mindmatters.model.DailyTips;
import sg.edu.iss.mindmatters.model.QuizOutcome;
import sg.edu.iss.mindmatters.model.Resource;
import sg.edu.iss.mindmatters.model.User;
import sg.edu.iss.mindmatters.service.DailyTipsService;
import sg.edu.iss.mindmatters.service.QuizServices;
import sg.edu.iss.mindmatters.service.ResourceImplementation;
import sg.edu.iss.mindmatters.service.ResourceServices;
import sg.edu.iss.mindmatters.service.UserService;

@RestController
@RequestMapping("rest")
public class RestResourceController {

	@Autowired
	ResourceServices rService;

	@Autowired
	QuizServices qService;

	@Autowired
	DailyTipsService dtService;

	@Autowired
	UserService uService;

	@GetMapping("/list")
	public List<Resource> getAllResources() {
		return rService.findAll();
	}

	@GetMapping("/tip")
	public List<DailyTips> findTip() {
		return dtService.findTip();
	}

	@GetMapping("profile")
	QuizOutcome getOutcome(Principal principal) {
		try {
			if(principal != null)
	        {
				User user = uService.findByEmail(principal.getName());
	            String userEmail = user.getEmail();
	            if(userEmail != null)
	            {
	            	QuizOutcome qo=new QuizOutcome(null,null,null);
	            	System.out.println(qService.findByEmail(userEmail));
	            	if(qService.findByEmail(userEmail)!=null) {
	            		qo=qService.findByEmail(userEmail);
	            	}
	        		return qo; 
	            }
	        }
		}
		catch(MalformedJwtException e) {
			e.printStackTrace();
		}

		return null;
	}

}
