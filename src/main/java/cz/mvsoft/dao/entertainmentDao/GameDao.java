package cz.mvsoft.dao.entertainmentDao;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.mvsoft.entity.entertainment.Game;

public interface GameDao extends JpaRepository<Game, Integer> {

}
