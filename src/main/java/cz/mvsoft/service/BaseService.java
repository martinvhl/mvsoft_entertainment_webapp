package cz.mvsoft.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cz.mvsoft.entity.entertainment.BaseEntity;

public interface BaseService<T extends BaseEntity> {
	
	public List<T> findAll();
	
	public T findById(int theId);
	
	public T save(T theEntity, MultipartFile imageFile);
	
	public void deleteById(int theId);

	public T searchByTitle(String theName);
}
