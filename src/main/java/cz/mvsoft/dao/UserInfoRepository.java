package cz.mvsoft.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.mvsoft.entity.users.User;

public interface UserInfoRepository extends JpaRepository<User, Integer> {

	Optional<User> findByName(String username);

}
