package cz.mvsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@GetMapping("/showLoginPage")
	public String showLoginPage() {
		return "login-page"; //todo create view login-page.html
	}

	@GetMapping("/access-denied")
	public String showAccessDenied() {
		
		return "access-denied"; //todo create view access-denied.html
		
	}
}
