package cz.mvsoft.dao.entertainmentDao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//use JpaRepository for future sorting and pagination functionality
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cz.mvsoft.entity.entertainment.Game;

public interface GameDao extends JpaRepository<Game, Integer> {

	public Optional<Game> findByTitle(String title);
	
	@Query("from Game where title like %:name%")
	public List<Game> filterByName(@Param("name") String name);
	
	public Page<Game> findAllByOrderByTitleAsc(Pageable pageable);
	
	//favourite games for owner
	@Query(value = "Select * from games g where g.game_id in (select game_id from users_games  where user_id in (select user_id from users u where u.name = :username))", nativeQuery = true)
	public Page<Game> findFavouritesByUsername(Pageable pageable, @Param("username") String username);
}
