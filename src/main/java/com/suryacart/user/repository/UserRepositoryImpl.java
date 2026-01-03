package com.suryacart.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.suryacart.user.model.entity.User;

public interface UserRepositoryImpl extends JpaRepository<User, UUID> {

	@Query("select u from User u where u.email = :email")
	public User getUserByUserName(@Param("email") String email);

}
