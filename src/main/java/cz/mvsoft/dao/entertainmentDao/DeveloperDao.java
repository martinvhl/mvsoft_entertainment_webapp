package cz.mvsoft.dao.entertainmentDao;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.mvsoft.entity.entertainment.GameStudio;

public interface DeveloperDao extends JpaRepository<GameStudio, Integer> {
	
	public GameStudio findByName(String name);
}
