package cz.mvsoft.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/films")
public class FilmsController {
	
	private static final String LIST = "films/films-list";
	private static final String ADD_FILM_FORM = "films/add-film-form";
	private static final String REDIRECTED = "redirect:/films/list";
	private static final String FILMS_ATTRIBUTE = "films";

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
	public String getAllFilms(Model theModel, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size) {
		theModel.addAttribute(FILMS_ATTRIBUTE,filmService.findAll(PageRequest.of(page, size)));
		
		theModel.addAttribute("currentPage",1);
		theModel.addAttribute("totalItems", 300);
	    theModel.addAttribute("totalPages", 12);
	    theModel.addAttribute("pageSize", size);
	    
		return LIST;
	}
	
	@GetMapping("/favourite/{username}")
	public String getFavouriteFilms(Model theModel, @PathVariable(name = "username") String username, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size) {
		Pageable pageable = PageRequest.of(page,size);
		if (username.equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			theModel.addAttribute(FILMS_ATTRIBUTE,filmService.getFavourites(username,pageable));
			return LIST;
		} else {
			return "access-denied";
		}
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
		if (foundFilm == null) {
			return "films/film-not-found";
		}
		model.addAttribute("film",foundFilm);
		model.addAttribute("isFavourite",filmService.isFavourite(foundFilm,SecurityContextHolder.getContext().getAuthentication().getName()));
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
		} else if (filmService.searchByTitle(film.getTitle()) != null) {
			model.addAttribute("film", new Film());
			model.addAttribute("filmExistsError", "This film is already in the database!");
			log.warn(String.format("Film with title %s is already in the database!", film.getTitle()));
			return ADD_FILM_FORM;
		}
		//saving film
		filmService.save(film, imageFile);
		
		if (film.getId() != 0) {
			return REDIRECTED;
		} else {
			log.info("New film successfully added!");
			return "films/film-addition-successful";
		}
	}
	
	@GetMapping("/addToFavourites/{id}")
	public String addToFavourite(@PathVariable(name = "id") int id) {
		String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		filmService.addToFavourites(id,loggedUser);
		return "redirect:/films/favourite/"+loggedUser;
	}
	
	@GetMapping("/removeFromFavourites/{id}")
	public String removeFromFavourites(@PathVariable(name = "id") int id) {
		String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		filmService.removeFromFavourites(id, loggedUser);
		return "redirect:/films/favourite/"+loggedUser;
	}
	
	@GetMapping("/showUpdateForm/{id}")
	public String showUpdateForm(@PathVariable("id") int id, Model model) {
		model.addAttribute("film",filmService.findById(id));
		return ADD_FILM_FORM;
	}
	
	@PostMapping("/removeFilm/{id}")
	public String removeFilmProcessing(@PathVariable("id") int id) {
		filmService.deleteById(id);
		return REDIRECTED;
	}
	
	@GetMapping("/filterFilms")
	public String searchForFilm(@RequestParam(required=false) String filmName, Model theModel) {
		if (filmName == null || filmName.isBlank()) {
			return REDIRECTED;
		}
		theModel.addAttribute(FILMS_ATTRIBUTE,filmService.filter(filmName));
		return LIST;
	}
}
