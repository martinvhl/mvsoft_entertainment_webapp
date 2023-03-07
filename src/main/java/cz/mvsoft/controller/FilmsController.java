package cz.mvsoft.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.mvsoft.entity.entertainment.Film;
import cz.mvsoft.service.FilmsService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/films")
public class FilmsController {
	
	private static final String ADD_FILM_FORM = "add-film-form";

	@Autowired
	private FilmsService filmService;
	
	@GetMapping("/list")
	public String getAllFilms() {
		return "films-list";
	}
	
	@GetMapping("/showAddFilmForm")
	public String showAddFilmForm(Model model) {
		log.info("Sending Add Film Form...");
		model.addAttribute("film",new Film());
		return ADD_FILM_FORM; 
	}
	
	@PostMapping("/addFilm")
	public String processFilmAddition(@Valid @ModelAttribute("film") Film film, BindingResult result, Model model) {
		log.info("Processing new film: "+film.getTitle());
		
		if (result.hasErrors())
			return ADD_FILM_FORM;
		//TODO opravit hledání existujících filmů z DB a implementovat
//		List<Film> existingFilms = filmService.searchByTitle(film.getTitle());
//		if (existingFilms != null) {
//			model.addAttribute("film", new Film());
//			model.addAttribute("filmExistsError", "This film is already in the database!");
//			log.warn(String.format("Film with title %s is already in the database!", film.getTitle()));
//			return ADD_FILM_FORM;
//		}
		
		filmService.save(film);
		log.info("New film successfully added!");
		return "film-addition-successful";
	}
}
