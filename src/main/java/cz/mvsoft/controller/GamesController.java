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

import cz.mvsoft.entity.entertainment.Game;
import cz.mvsoft.service.GamesService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/games")
public class GamesController {
	
	private static final String LIST = "games/games-list";
	private static final String ADD_GAME_FORM = "games/add-game-form";
	private static final String REDIRECTED = "redirect:/games/list";
	private static final String GAMES_ATTRIBUTE = "games";
	
	private GamesService gamesService;
	
	public GamesController(GamesService gamesService) {
		this.gamesService = gamesService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/list")
	public String getAllGames(Model model , @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size) {
		model.addAttribute(GAMES_ATTRIBUTE, gamesService.findAll(PageRequest.of(page, size)));
		return LIST;
	}
	
	@GetMapping("/favourite/{username}")
	public String getFavouriteGames(Model theModel, @PathVariable(name = "username") String username, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size) {
		Pageable pageable = PageRequest.of(page, size);
		if (username.equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			theModel.addAttribute(GAMES_ATTRIBUTE, gamesService.getFavourites(username, pageable));
			return LIST;
		} else {
			return "access-denied";
		}
	}
	
	@GetMapping("/showAddGameForm")
	public String showAddGameForm(Model model) {
		log.info("Sending Add new Game Form...");
		model.addAttribute("game",new Game());
		return ADD_GAME_FORM;
	}
	
	@GetMapping("/showUpdateForm/{id}")
	public String showUpdateForm(@PathVariable("id") int id, Model model) {
		model.addAttribute("game",gamesService.findById(id));
		return ADD_GAME_FORM;
	}
	
	@GetMapping("/gameDetail/{id}")
	public String showGameDetail(@PathVariable("id") int id, Model model) {
		model.addAttribute("game",gamesService.findById(id));
		return "games/game-detail";
	}
	
	@PostMapping("/addGame")
	public String processGameAddition(@Valid @ModelAttribute("game") Game game, BindingResult result, Model model, @RequestParam("imageFile") MultipartFile imageFile) {
		log.info("Processing new game "+game.getTitle());
		
		if (result.hasErrors())
			return ADD_GAME_FORM;
		//check if game is already present in db
		if (game.getId() != 0) {
			gamesService.save(game, imageFile);
		} else if (gamesService.searchByTitle(game.getTitle()) != null) {
			model.addAttribute("game", new Game());
			model.addAttribute("gameExistsError", "This game is already in the database!");
			log.warn(String.format("Game with title %s is already in the database!", game.getTitle()));
			return ADD_GAME_FORM;
		}
		//saving new game
		gamesService.save(game, imageFile);
		
		if (game.getId() != 0) {
			return REDIRECTED;
		} else {
			log.info("New game successfully added!");
			return "games/game-addition-successful";
		}
	}
	
	@GetMapping("/addToFavourite/{id}")
	public String addToFavourite(@PathVariable(name = "id") int id) {
		gamesService.addToFavourites(id, SecurityContextHolder.getContext().getAuthentication().getName());
		return REDIRECTED;
	}
	
	@PostMapping("/removeGame/{id}")
	public String removeGame(@PathVariable("id") int id) {
		gamesService.deleteById(id);
		return REDIRECTED;
	}
	
	@GetMapping("/filterGames")
	public String searchForGame(@RequestParam(required = false) String gameName, Model theModel) {
		if (gameName == null || gameName.isBlank()) {
			return REDIRECTED;
		}
		theModel.addAttribute(GAMES_ATTRIBUTE,gamesService.filter(gameName));
		return LIST;
	}
}
