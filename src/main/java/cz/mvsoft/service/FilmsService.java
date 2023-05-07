package cz.mvsoft.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

import cz.mvsoft.dao.entertainmentDao.ActorDao;
import cz.mvsoft.dao.entertainmentDao.FilmDao;
import cz.mvsoft.dao.entertainmentDao.OwnerDao;
import cz.mvsoft.entity.entertainment.Actor;
import cz.mvsoft.entity.entertainment.Film;
import cz.mvsoft.entity.entertainment.Owner;

@Service
public class FilmsService implements BaseService<Film> {

	private FilmDao filmDao;
	private ActorDao actorDao;
	private OwnerDao ownerDao;
	
	public FilmsService(FilmDao filmDao, ActorDao actorDao, OwnerDao ownerDao) {
		this.filmDao = filmDao;
		this.actorDao = actorDao;
		this.ownerDao = ownerDao;
	}

	@Override
	public List<Film> findAll(Pageable pageable) {
		
		Page<Film> pagedFilms = filmDao.findAllByOrderByTitleAsc(pageable);
		List<Film> foundFilms = pagedFilms.getContent();
		encodeFilm(foundFilms);
		return foundFilms;
	}

	@Override
	public Film findById(int theId) {
		Optional<Film> foundFilm = filmDao.findById(theId);
	    return foundFilm.map(film -> {
	        film.setBase64Encoded(Base64.getEncoder().encodeToString(film.getImage()));
	        List<Actor> foundActors = film.getActors();
	        
	        StringBuilder builder = new StringBuilder();
	        for (int i = 0; i < foundActors.size(); i++) {
	            builder.append(foundActors.get(i).getName());
	            if (i < foundActors.size()-1) {
	                builder.append(", ");
	            }
	        }
	        film.setActorsInput(builder.toString());
	        
	        return film;
	    }).orElse(null);
	}

	@Override
	public Film save(Film film, MultipartFile imageFile) {
		//mapping String from input to List
		List<Actor> typedActors = mapActorsFromInput(film.getActorsInput());
		
		//checking if actors are already present in the db
		List<Actor> actors = new ArrayList<>();
		for (Actor actor : typedActors) {
			actors.add(findOrCreateActor(actor.getName().trim()));
		}
		
		//saving image from multipart file
		try (InputStream inputStream = imageFile.getInputStream()) {
	        film.setImage(inputStream.readAllBytes());
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
	
	@Override
	public List<Film> filter(String filmName) {
		List<Film> filteredFilms = filmDao.filterByName(filmName);
		encodeFilm(filteredFilms);
		return filteredFilms;
	}
	
	@Override
	public Set<Film> getFavourites(String username, Pageable pageable) {
		Owner owner = ownerDao.findFilmOwnerByUsername(username);
		encodeFilm(owner.getOwnedFilms());
		return owner.getOwnedFilms();
	}

	@Override
	public void addToFavourites(int filmId, String username) {
		Owner owner = ownerDao.findFilmOwnerByUsername(username);
		Optional<Film> foundFilm = filmDao.findById(filmId);
		if (foundFilm.isPresent()) {
			owner.getOwnedFilms().add(foundFilm.get());
		}
		ownerDao.save(owner);
	}
	
	@Override
	public void removeFromFavourites(int id, String username) {
		Owner owner = ownerDao.findFilmOwnerByUsername(username);
		Set<Film> films = owner.getOwnedFilms();
		Iterator<Film> iter = films.iterator();
		while (iter.hasNext()) {
			Film currFilm = iter.next();
			if (currFilm.getId() == id) {
				iter.remove();
				break;
			}
		}
		ownerDao.save(owner);
	}

	private Actor findOrCreateActor(String name) {
	    Actor actor = actorDao.findByName(name);
	    if (actor == null) {
	        actor = new Actor(name);
	        actorDao.save(actor);
	    }
	    return actor;
	}
	
	private List<Actor> mapActorsFromInput(String filmActorsInput) {
		return Arrays.asList(filmActorsInput.split(",")).stream()
								.map(Actor::new).toList();
	}
	
	private void encodeFilm(Collection<Film> foundFilms) {
		for (Film film : foundFilms) {
			film.setBase64Encoded(Base64.getEncoder().encodeToString(film.getImage()));
		}
	}

	public boolean isFavourite(Film film, String user) {
		Set<Film> favouriteFilms = getFavourites(user, null);
		return favouriteFilms.stream().anyMatch(e -> e.getTitle().equals(film.getTitle()));
	}
}
