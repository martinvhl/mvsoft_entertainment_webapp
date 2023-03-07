package cz.mvsoft.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cz.mvsoft.entity.entertainment.Game;

@Service
public class GamesService implements BaseService<Game> {

	@Override
	public List<Game> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Game findById(int theId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Game theEmployee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(int theId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Game> searchByTitle(String theName) {
		// TODO Auto-generated method stub
		return null;
	}

}
