package cz.mvsoft.dao.entertainmentDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cz.mvsoft.entity.entertainment.Owner;

public interface OwnerDao extends JpaRepository<Owner, Integer> {
	
	@Query("SELECT DISTINCT owner FROM Owner owner LEFT JOIN FETCH owner.ownedFilms WHERE owner.username=:username")	
	public Owner findFilmOwnerByUsername(String username);
	
	@Query("SELECT DISTINCT owner FROM Owner owner LEFT JOIN FETCH owner.ownedGames WHERE owner.username=:username")
	public Owner findGameOwnerByUsername(String username);

}
