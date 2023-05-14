package cz.mvsoft.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import cz.mvsoft.entity.entertainment.BaseEntity;

public interface BaseService<T extends BaseEntity> {
	
	public Page<T> findAll(Pageable pageable);
	
	public T findById(int theId);
	
	public T save(T theEntity, MultipartFile imageFile);
	
	public void deleteById(int theId);

	public T searchByTitle(String theName);

	public List<T> filter(String theName);
	
	public Set<T> getFavourites(String username, Pageable pageable);
	
	public void addToFavourites(int id, String username);
	
	public void removeFromFavourites(int id, String username);
}
