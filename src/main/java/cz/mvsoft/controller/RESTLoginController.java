package cz.mvsoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.mvsoft.entity.users.User;

@RestController
@RequestMapping("/api")
public class RESTLoginController {
	
//	@Autowired
//	private AuthenticationManager authenticationManager;
	
	//for future register purposes
//	@Autowired
//	private PasswordEncoder passwordEncoder;

//	@PostMapping("/login")
//	public ResponseEntity<String> login(@RequestBody User user) {
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		return new ResponseEntity<>("User successfully logged in!", HttpStatus.OK);
//	}
	
}
