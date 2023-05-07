package cz.mvsoft.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cz.mvsoft.dao.entertainmentDao.DeveloperDao;
import cz.mvsoft.dao.entertainmentDao.GameDao;
import cz.mvsoft.dao.entertainmentDao.OwnerDao;
import cz.mvsoft.entity.entertainment.Game;
import cz.mvsoft.entity.entertainment.GameStudio;
import cz.mvsoft.entity.entertainment.Owner;

@Service
public class GamesService implements BaseService<Game> {
	
	private GameDao gameDao;
	private DeveloperDao developerDao;
	private OwnerDao ownerDao;
	
	//single constructor, we can omit the @Autowired annotation
	public GamesService(GameDao gameDao, DeveloperDao developerDao, OwnerDao ownerDao) {
		this.gameDao = gameDao;
		this.developerDao = developerDao;
		this.ownerDao = ownerDao;
	}

	@Override
	public List<Game> findAll(Pageable pageable) {
		Page<Game> pagedGames = gameDao.findAllByOrderByTitleAsc(pageable);
		List<Game> foundGames = pagedGames.getContent();
		encodeCover(foundGames);
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
		//check if game developer already exists in db (create/find)
		GameStudio gameStudio = findOrCreateDeveloper(game.getTypedStudio());
		game.setDeveloper(gameStudio);
		
		//saving image from multipart file
		try (InputStream inputStream = imageFile.getInputStream()){
			game.setImage(inputStream.readAllBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (game.getId() == 0)
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
		encodeCover(filteredGames);
		return filteredGames;
	}
	
	@Override
	public Set<Game> getFavourites(String username, Pageable pageable) {
		Owner owner = ownerDao.findGameOwnerByUsername(username);
		encodeCover(owner.getOwnedGames());
		return owner.getOwnedGames();
	}

	@Override
	public void addToFavourites(int gameId, String username) {
		Owner owner = ownerDao.findGameOwnerByUsername(username);
		Optional<Game> foundGame = gameDao.findById(gameId);
		if (foundGame.isPresent()) {
			owner.getOwnedGames().add(foundGame.get());
		}
		ownerDao.save(owner);
	}
	
	@Override
	public void removeFromFavourites(int id, String username) {
		Owner owner = ownerDao.findGameOwnerByUsername(username);
		Set<Game> games = owner.getOwnedGames();
		Iterator<Game> iter = games.iterator();
		while (iter.hasNext()) {
			Game currGame = iter.next();
			if (currGame.getId() == id) {
				iter.remove();
				break;
			}
		}
		
	}

	private GameStudio findOrCreateDeveloper(String name) {
		GameStudio gameStudio = developerDao.findByName(name);
		if (gameStudio == null) {
			gameStudio = new GameStudio(name);
			developerDao.save(gameStudio);
		}
		return gameStudio;
	}
	
	private void encodeCover(Collection<Game> foundGames) {
		for (Game game : foundGames) {
			game.setBase64Encoded(Base64.getEncoder().encodeToString(game.getImage()));
		}
	}
}
