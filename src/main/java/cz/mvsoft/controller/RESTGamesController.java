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

import cz.mvsoft.entity.entertainment.Game;
import cz.mvsoft.service.GamesService;

@RestController
@RequestMapping("/api")
public class RESTGamesController {
	
	private GamesService gamesService;
	
	public RESTGamesController(GamesService gamesService) {
		this.gamesService = gamesService;
	}

	@GetMapping(path = "/games", produces = "application/json")
	public ResponseEntity<List<Game>> findGames() {
		List<Game> games = gamesService.findAll();
		return new ResponseEntity<>(games,HttpStatus.OK);
	}
	
	@GetMapping(path = "/games/{id}", produces = "application/json")
	public ResponseEntity<Object> findGame(@PathVariable("id") int id) {
		Game game = gamesService.findById(id);
		if (game == null) {
			return new ResponseEntity<>("Film not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(game,HttpStatus.FOUND);
	}
	
	@PostMapping("/games")
	public ResponseEntity<Game> saveGame(@RequestBody Game game) {
		return new ResponseEntity<>(gamesService.save(game, null),HttpStatus.CREATED); //TODO imagefile
	}
	
	@PutMapping("/games")
	public ResponseEntity<Object> updateGame(@RequestBody Game game) {
		Game foundGame = gamesService.findById(game.getId());
		if (foundGame == null) {
			return new ResponseEntity<>("Game with id "+game.getId()+" was not found!", HttpStatus.NOT_FOUND);	
		}
		Game updatedGame = gamesService.save(game, null);//TODO imageFile implementation
		return new ResponseEntity<>(updatedGame,HttpStatus.OK);
	}
	
	@DeleteMapping("/games/{id}")
	public ResponseEntity<Object> deleteGame(@PathVariable("id") int id) {
		Game game = gamesService.findById(id);
		if (game == null) {
			return new ResponseEntity<>("Game with id "+id+" was not found!",HttpStatus.NOT_FOUND);
		} else {
			gamesService.deleteById(id);
			return new ResponseEntity<>("Game successfully deleted!",HttpStatus.OK);
		}
	}
}
