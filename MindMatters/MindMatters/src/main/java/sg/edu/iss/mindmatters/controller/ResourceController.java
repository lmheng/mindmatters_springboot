package sg.edu.iss.mindmatters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sg.edu.iss.mindmatters.model.Resource;
import sg.edu.iss.mindmatters.service.ResourceImplementation;
import sg.edu.iss.mindmatters.service.ResourceServices;

@Controller
@RequestMapping("/resource")
public class ResourceController {

	@Autowired
	ResourceServices rService;

	@Autowired
	public void setResourceImplementation(ResourceImplementation rimpl) {
		this.rService = rimpl;
	}

	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
	public String Resources(Model model) {

		return "resourcelanding";
	}

	@RequestMapping(value = "/list/{type}", method = { RequestMethod.GET, RequestMethod.POST })
	public String listResources(Model model, @PathVariable("type") String type) {

		if (type.equals("all"))
			model.addAttribute("rList", rService.findAll());
		else
			model.addAttribute("rList", rService.findByType(type));

		return "mindfullist";
	}

	@RequestMapping(value = "/view/{urlCode}", method = { RequestMethod.GET, RequestMethod.POST })
	public String view(Model model, @PathVariable("urlCode") String urlcode) {
		model.addAttribute("resource", rService.findByUrlCode(urlcode));
		return "mindfulview";
	}

	@RequestMapping(value = "/edulist/{type}", method = { RequestMethod.GET, RequestMethod.POST })
	public String listeduResources(Model model, @PathVariable("type") String type) {

		model.addAttribute("rList", rService.findByType(type));
		return "educationlist";
	}

	@RequestMapping(value = "/eduview/{urlCode}", method = { RequestMethod.GET, RequestMethod.POST })
	public String eduview(Model model, @PathVariable("urlCode") String urlcode) {
		model.addAttribute("resource", rService.findByUrlCode(urlcode));
		return "educationview";
	}

}
