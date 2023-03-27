package cz.mvsoft.dao.entertainmentDao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.mvsoft.entity.entertainment.Film;
//use JpaRepository for future sorting and pagination functionality
public interface FilmDao extends JpaRepository<Film, Integer> {
	
	public Optional<Film> findByTitle(String title);
	
	public List<Film> findAllByOrderByTitleAsc();
}
