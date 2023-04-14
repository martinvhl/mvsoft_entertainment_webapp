package cz.mvsoft.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cz.mvsoft.entity.entertainment.Film;
import cz.mvsoft.service.FilmsService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/films")
public class FilmsController {
	
	private static final String ADD_FILM_FORM = "films/add-film-form";

	private FilmsService filmService;
	
	public FilmsController(FilmsService filmService) {
		this.filmService = filmService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/list")
	public String getAllFilms(Model theModel) {
		List<Film> filmsInDb = filmService.findAll();
		theModel.addAttribute("films",filmsInDb);
		return "films/films-list";
	}
	
	@GetMapping("/showAddFilmForm")
	public String showAddFilmForm(Model model) {
		log.info("Sending Add Film Form...");
		model.addAttribute("film",new Film());
		return ADD_FILM_FORM; 
	}
	
	@GetMapping("/filmDetail/{id}")
	public String showFilmDetail(@PathVariable("id") int id, Model model) {
		Film foundFilm = filmService.findById(id);
		model.addAttribute("film",foundFilm);
		return "films/film-detail";
	}
	
	@PostMapping("/addFilm")
	public String processFilmAddition(@Valid @ModelAttribute("film") Film film, BindingResult result, Model model, @RequestParam("imageFile") MultipartFile imageFile) {
		
		log.info("Processing new film: "+film.getTitle());
		
		if (result.hasErrors())
			return ADD_FILM_FORM;
		//check if film is already present in db
		if (film.getId() != 0) {
			filmService.save(film, imageFile);
		} else {
			Film existingFilm = filmService.searchByTitle(film.getTitle());
			if (existingFilm != null) {
				model.addAttribute("film", new Film());
				model.addAttribute("filmExistsError", "This film is already in the database!");
				log.warn(String.format("Film with title %s is already in the database!", film.getTitle()));
				return ADD_FILM_FORM;
			}
		}
		//saving film
		filmService.save(film, imageFile);
		
		if (film.getId() != 0) {
			return "redirect:/films/list";
		} else {
			log.info("New film successfully added!");
			return "films/film-addition-successful";
		}
	}
	
	@GetMapping("/showUpdateForm/{id}")
	public String showUpdateForm(@PathVariable("id") int id, Model model) {
		Film filmToUpdate = filmService.findById(id);
		model.addAttribute("film",filmToUpdate);
		return ADD_FILM_FORM;
	}
	
	@PostMapping("/removeFilm/{id}")
	public String removeFilmProcessing(@PathVariable("id") int id) {
		filmService.deleteById(id);
		return "redirect:/films/list";
	}
	
	@GetMapping("/filterFilms")
	public String searchForFilm(@RequestParam(required=false) String filmName, Model theModel) {
		if (filmName == null || filmName.isBlank()) {
			return "redirect:/films/list";
		}
		List<Film> filteredFilms = filmService.filter(filmName);
		theModel.addAttribute("films",filteredFilms);
		return "films/films-list";
	}
}
