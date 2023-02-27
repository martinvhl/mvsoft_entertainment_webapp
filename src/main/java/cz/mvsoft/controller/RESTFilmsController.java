package cz.mvsoft.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/films")
public class RESTFilmsController {
	
	@GetMapping("/helloworld")
	public ResponseEntity<String> helloWorld(){
		return ResponseEntity.ok("Hello World");
	}
}
