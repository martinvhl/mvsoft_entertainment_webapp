package cz.mvsoft.dao.entertainmentDao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.mvsoft.entity.entertainment.Film;

public interface FilmDao extends JpaRepository<Film, Integer> {
	
	public Optional<Film> findByTitle(String title);
}
