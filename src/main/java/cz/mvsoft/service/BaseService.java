package cz.mvsoft.service;

import java.util.List;

import cz.mvsoft.entity.entertainment.BaseEntity;

public interface BaseService<T extends BaseEntity> {
	
	public List<T> findAll();
	
	public T findById(int theId);
	
	public void save(T theEntity);
	
	public void deleteById(int theId);

	public List<T> searchByTitle(String theName);
}
