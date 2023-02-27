package cz.mvsoft.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.mvsoft.entity.users.User;
import cz.mvsoft.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/register")
public class RegistrationController {
	
	private static final String REGISTRATION_FORM = "registration-form";
	
	@Autowired
	UserService userService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
//	@PreAuthorize("hasAuthority(ROLE_ADMIN)") můžeme použít pro zamezení přístupu někoho bez role ADMIN
	@GetMapping("/showRegistrationForm")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user",new User());
		return REGISTRATION_FORM;
	}
	
	@PostMapping("/processRegistration")
	public String processRegistration(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
		
		String userLogin = user.getName();
		log.info(String.format("Processing registration form for: %s.", userLogin));
		
		//check for errors
		if (result.hasErrors())
			return REGISTRATION_FORM;
		
		//check if user is not already existing
		User existingUser = userService.findByUserName(userLogin);
		if (existingUser != null) {
			model.addAttribute("user",new User());
			model.addAttribute("userExistsError","This user already exists!");
			log.warn(String.format("User name %s already exists!", userLogin));
			return REGISTRATION_FORM;
		}
		userService.save(user);
		log.info(String.format("User with name %s successfully created!", userLogin));
		return "redirect:/login/showLoginPage";
	}
	
}
