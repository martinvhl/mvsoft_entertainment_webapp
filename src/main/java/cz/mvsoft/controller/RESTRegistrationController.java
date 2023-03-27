package cz.mvsoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.mvsoft.entity.users.User;
import cz.mvsoft.service.UserService;

@RestController
@RequestMapping("/api")
public class RESTRegistrationController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(path = "/register", produces = "application/json")
	public ResponseEntity<Object> registerUser(@RequestBody User user){
		User foundUser = userService.findByUserName(user.getUserName());
		if (foundUser != null) {
			return new ResponseEntity<>("User with this username already exists!",HttpStatus.NOT_ACCEPTABLE);
		}
		User savedUser = userService.save(user);
		return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
	}
}
