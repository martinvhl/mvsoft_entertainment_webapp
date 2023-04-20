package cz.mvsoft.dao.entertainmentDao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cz.mvsoft.entity.entertainment.Film;

public interface FilmDao extends JpaRepository<Film, Integer> {
	
	public Optional<Film> findByTitle(String title);
	
//	public List<Film> findAllByOrderByTitleAsc();
	
	@Query("from Film where title like %:name%")
	public List<Film> filterByName(@Param("name") String name);
	
	public Page<Film> findAllByOrderByTitleAsc(Pageable pageable);
}
