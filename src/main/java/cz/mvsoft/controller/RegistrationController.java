package cz.mvsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
//	@PreAuthorize("hasAuthority(ROLE_ADMIN)") můžeme použít pro zamezení přístupu někoho bez role ADMIN
	@GetMapping("/registrationForm")
	public String showRegistrationForm() {
		return "registration-form";
	}
	
	@PostMapping("/processRegistration")
	public String processRegistration() {
		return "/home-page";
	}
	
}
