package cz.mvsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/films")
public class FilmsController {

	@GetMapping("/list")
	public String getAllFilms() {
		return "films-list";
	}
	
}
