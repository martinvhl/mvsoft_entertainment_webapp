package cz.mvsoft.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cz.mvsoft.dao.entertainmentDao.ActorDao;
import cz.mvsoft.dao.entertainmentDao.FilmDao;
import cz.mvsoft.entity.entertainment.Actor;
import cz.mvsoft.entity.entertainment.Film;

@Service
public class FilmsService implements BaseService<Film> {

	@Autowired
	private FilmDao filmDao;
	
	@Autowired
	private ActorDao actorDao;
	
	@Override
	public List<Film> findAll() {
		List<Film> foundFilms = filmDao.findAllByOrderByTitleAsc();
		for (Film film : foundFilms) {
			film.setBase64Encoded(Base64.getEncoder().encodeToString(film.getImage()));
		}
		return foundFilms;
	}

	@Override
	public Film findById(int theId) {
		Optional<Film> foundFilm = filmDao.findById(theId);
		if (foundFilm.isPresent()) {
			foundFilm.get().setBase64Encoded(Base64.getEncoder().encodeToString(foundFilm.get().getImage()));
			List<Actor> foundActors = foundFilm.get().getActors();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < foundActors.size(); i++) {
				builder.append(foundActors.get(i).getName());
				if (i < foundActors.size()-1) {
					builder.append(", ");
				}
			}
			foundFilm.get().setActorsInput(builder.toString());
			return foundFilm.get();
		} else {
			return null;
		}
	}

	@Override
	public Film save(Film film, MultipartFile imageFile) {
		//mapping String from input to List
		List<Actor> typedActors = Arrays.asList(film.getActorsInput().split(",")).stream()
								.map(Actor::new)
								.collect(Collectors.toList());
		//checking if actors are already present in the db
		List<Actor> actors = new ArrayList<>();
		for (Actor actor : typedActors) {
			actors.add(findOrCreateActor(actor.getName().trim()));
		}
		//saving image from multipart file
		try {
	        byte[] imageData = imageFile.getBytes();
	        film.setImage(imageData);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		//saving film
		film.setActors(actors);
		if (film.getId() == 0)	
			film.setCreatedDateTime(LocalDateTime.now());
		film.setLastModifiedDateTime(LocalDateTime.now());
		filmDao.save(film);
		return film;
	}

	@Override
	public void deleteById(int theId) {
		filmDao.deleteById(theId);
	}

	@Override
	public Film searchByTitle(String theName) {
		Optional<Film> foundFilm = filmDao.findByTitle(theName.toLowerCase().trim());
		if (foundFilm.isPresent()) {
			return foundFilm.get();
		}
		return null;
	}
	
	private Actor findOrCreateActor(String name) {
	    Actor actor = actorDao.findByName(name);
	    if (actor == null) {
	        actor = new Actor(name);
	        actorDao.save(actor);
	    }
	    return actor;
	}

}
