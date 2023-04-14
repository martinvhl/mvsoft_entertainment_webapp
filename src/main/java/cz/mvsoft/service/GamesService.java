package cz.mvsoft.service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cz.mvsoft.dao.entertainmentDao.DeveloperDao;
import cz.mvsoft.dao.entertainmentDao.GameDao;
import cz.mvsoft.entity.entertainment.Game;
import cz.mvsoft.entity.entertainment.GameStudio;

@Service
public class GamesService implements BaseService<Game> {
	
	private GameDao gameDao;
	private DeveloperDao developerDao;
	
	//single constructor, we can omit the @Autowired annotation
	public GamesService(GameDao gameDao, DeveloperDao developerDao) {
		this.gameDao = gameDao;
		this.developerDao = developerDao;
	}

	@Override
	public List<Game> findAll() {
		List<Game> foundGames = gameDao.findAllByOrderByTitleAsc();
		for (Game game : foundGames) {
			game.setBase64Encoded(Base64.getEncoder().encodeToString(game.getImage()));
		}
		return foundGames;
	}

	@Override
	public Game findById(int theId) {
		Optional<Game> foundGame = gameDao.findById(theId);
		if (foundGame.isPresent()) {
			foundGame.get().setBase64Encoded(Base64.getEncoder().encodeToString(foundGame.get().getImage()));
			foundGame.get().setTypedStudio(foundGame.get().getDeveloper().getName());
			return foundGame.get();
		} else {
			return null;
		}
	}

	@Override
	public Game save(Game game, MultipartFile imageFile) {
		GameStudio gameStudio = findOrCreateDeveloper(game.getTypedStudio());
		game.setDeveloper(gameStudio);
		//saving image from multipart file
		try {
			game.setImage(imageFile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		game.setCreatedDateTime(LocalDateTime.now());
		game.setLastModifiedDateTime(LocalDateTime.now());
		gameDao.save(game);
		return game;
	}

	@Override
	public void deleteById(int theId) {
		gameDao.deleteById(theId);
	}

	@Override
	public Game searchByTitle(String theName) {
		Optional<Game> foundGame = gameDao.findByTitle(theName.toLowerCase().trim());
		if (foundGame.isPresent()) {
			return foundGame.get();
		}
		return null;
	}
	
	@Override
	public List<Game> filter(String gameName) {
		List<Game> filteredGames = gameDao.filterByName(gameName);
		for (Game game : filteredGames) {
			game.setBase64Encoded(Base64.getEncoder().encodeToString(game.getImage()));
		}
		return filteredGames;
	}
	
	private GameStudio findOrCreateDeveloper(String name) {
		GameStudio gameStudio = developerDao.findByName(name);
		if (gameStudio == null) {
			gameStudio = new GameStudio(name);
			developerDao.save(gameStudio);
		}
		return gameStudio;
	}

}
