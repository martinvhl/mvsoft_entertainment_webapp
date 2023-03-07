package cz.mvsoft.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.mvsoft.dao.entertainmentDao.FilmDao;
import cz.mvsoft.entity.entertainment.Actor;
import cz.mvsoft.entity.entertainment.Film;

@Service
public class FilmsService implements BaseService<Film> {

	@Autowired
	private FilmDao filmDao;
	
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
		List<Actor> actors = Arrays.asList(film.getActorsInput().split(",")).stream()
								.map(Actor::new)
								.collect(Collectors.toList());
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
	public List<Film> searchByTitle(String theName) {
		return filmDao.findByTitleContainsAllIgnoreCase(theName);
	}

}
