package cz.mvsoft.dao.entertainmentDao;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.mvsoft.entity.entertainment.Actor;

public interface ActorDao extends JpaRepository<Actor, Integer> {
	
	public Actor findByName(String name);
}
