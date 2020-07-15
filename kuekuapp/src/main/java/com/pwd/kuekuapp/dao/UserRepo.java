package com.pwd.kuekuapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pwd.kuekuapp.entity.Users;

public interface UserRepo extends JpaRepository<Users, Integer>{
	public Optional<Users> findByUsername(String username);
//	public Iterable<Users> findByEmail(String email);
	public Optional<Users> findByVerifyToken(String verifyToken);
	
	@Query(value = "SELECT * FROM Users WHERE username = ?1 and email = ?2", nativeQuery = true)
	public Iterable<Users> validateUniqueUser(String username, String email);
	
	@Query(value = "SELECT * FROM Users WHERE email = ?1", nativeQuery = true)
	public Optional<Users> findUserEmail(String email);
	
	@Query(value = "SELECT * FROM Users WHERE password = ?1 and id= = ?2", nativeQuery = true)
	public Iterable<Users> findUserPassword(String password);
	
	@Query(value="SELECT count(membership) FROM Users where membership='premium'", nativeQuery = true)
	public int getAllPremiumUsers();
	
}