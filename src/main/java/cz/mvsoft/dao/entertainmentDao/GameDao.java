package cz.mvsoft.dao.entertainmentDao;

import java.util.List;
import java.util.Optional;

//use JpaRepository for future sorting and pagination functionality
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cz.mvsoft.entity.entertainment.Game;

public interface GameDao extends JpaRepository<Game, Integer> {

	public Optional<Game> findByTitle(String title);
	
	public List<Game> findAllByOrderByTitleAsc();
	
	@Query("from Game where title like %:name%")
	public List<Game> filterByName(@Param("name") String name);
}
