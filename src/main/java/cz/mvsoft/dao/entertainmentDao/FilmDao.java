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
		
	@Query("from Film where title like %:name%")
	public List<Film> filterByName(@Param("name") String name);
	
	public Page<Film> findAllByOrderByTitleAsc(Pageable pageable);
	
	//favourite films for owner
	@Query(value = "Select * from films f where f.film_id in (select film_id from users_films  where user_id in (select user_id from users u where u.name = :username))", nativeQuery = true)
	public Page<Film> findFavouritesByUsername(Pageable pageable, @Param("username") String username);
}
