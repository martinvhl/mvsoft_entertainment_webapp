package cz.mvsoft.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import cz.mvsoft.entity.entertainment.Game;
import cz.mvsoft.service.GamesService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/games")
public class GamesController {

	private static final String ADD_GAME_FORM = "games/add-game-form";
	
	@Autowired
	private GamesService gamesService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/list")
	public String getAllGames(Model model) {
		List<Game> games = gamesService.findAll();
		model.addAttribute("games", games);
		return "games/games-list";
	}
	
	@GetMapping("/showAddGameForm")
	public String showAddGameForm(Model model) {
		log.info("Sending Add new Game Form...");
		model.addAttribute("game",new Game());
		return ADD_GAME_FORM;
	}
	
	@GetMapping("/gameDetail/{id}")
	public String showGameDetail(@PathVariable("id") int id, Model model) {
		Game foundGame = gamesService.findById(id);
		model.addAttribute("game",foundGame);
		return "games/game-detail";
	}
	
	@PostMapping("/addGame")
	public String processGameAddition(@Valid @ModelAttribute("game") Game game, BindingResult result, Model model, @RequestParam("imageFile") MultipartFile imageFile) {
		log.info("Processing new game "+game.getTitle());
		
		System.out.println(game.toString());
		
		if (result.hasErrors())
			return ADD_GAME_FORM;
		
		Game existingGame = gamesService.searchByTitle(game.getTitle());
		if (existingGame != null) {
			model.addAttribute("game", new Game());
			model.addAttribute("gameExistsError", "This game is already in the database!");
			log.warn(String.format("Game with title %s is already in the database!", game.getTitle()));
			return ADD_GAME_FORM;
		}
		
		gamesService.save(game, imageFile);
		log.info("Game with title "+game.getTitle()+" was successfully added to the database.");
		return "games/game-addition-successful";
	}
	
	@PostMapping("/removeGame/{id}")
	public String removeGame(@PathVariable("id") int id) {
		gamesService.deleteById(id);
		return "redirect:/games/list";
	}
}
