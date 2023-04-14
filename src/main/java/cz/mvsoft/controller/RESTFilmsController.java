package cz.mvsoft.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.mvsoft.entity.entertainment.Film;
import cz.mvsoft.service.FilmsService;

@RestController
@RequestMapping("/api")
public class RESTFilmsController {
	
	private FilmsService filmsService;
	
	public RESTFilmsController(FilmsService filmsService) {
		this.filmsService = filmsService;
	}

	@GetMapping(path = "/films", produces = "application/json")
	public ResponseEntity<List<Film>> findFilms(){
		List<Film> films = filmsService.findAll();
		return new ResponseEntity<>(films,HttpStatus.OK);
	}
	
	@GetMapping(path="/films/{id}", produces = "application/json")
	public ResponseEntity<Object> findFilm(@PathVariable("id") int id) {
		Film film = filmsService.findById(id);
		if (film == null) {
			return new ResponseEntity<>("Film not found",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(film,HttpStatus.FOUND);
	}
	
	@PostMapping("/films")
	public ResponseEntity<Film> saveFilm(@RequestBody Film film) {
		return new ResponseEntity<>(filmsService.save(film, null),HttpStatus.CREATED);
	}
	
	@PutMapping("/films")
	public ResponseEntity<Object> updateFilm(@RequestBody Film film) {
		Film foundFilm = filmsService.findById(film.getId());
		if (foundFilm == null) {
			return new ResponseEntity<>("Film with id "+film.getId()+" was not found!",HttpStatus.NOT_FOUND);
		} else {
			Film updated = filmsService.save(film, null); //TODO vyřešit multipart file posting přes postmana!!!
			return new ResponseEntity<>(updated,HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/films/{id}")
	public ResponseEntity<Object> deleteFilm(@PathVariable int id ) {
		Film film = filmsService.findById(id);
		if (film == null) {
			return new ResponseEntity<>("Film with id "+id+" was not found!",HttpStatus.NOT_FOUND);
		} else {
			filmsService.deleteById(id);
			return new ResponseEntity<>("Film successfully deleted!",HttpStatus.OK);
		}
	}
}
