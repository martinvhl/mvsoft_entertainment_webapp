package cz.mvsoft.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Film findById(int theId) {
		// TODO Auto-generated method stub
		return null;
	}

	//POZOR - je potřeba vytvořit list ze stringu (hodnoty separované čárkami)
	@Override
	public void save(Film film) {
		//mapping String from input to List
		List<Actor> typedActors = Arrays.asList(film.getActorsInput().split(",")).stream()
								.map(Actor::new)
								.collect(Collectors.toList());
		//checking if actors are already present in the db
		List<Actor> actors = new ArrayList<>();
		for (Actor actor : typedActors) {
			actors.add(findOrCreateActor(actor.getName()));
		}
		//saving film
		film.setActors(actors);
		film.setCreatedDateTime(LocalDateTime.now());
		film.setLastModifiedDateTime(LocalDateTime.now());
		filmDao.save(film);
	}

	@Override
	public void deleteById(int theId) {
		// TODO Auto-generated method stub
		
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
