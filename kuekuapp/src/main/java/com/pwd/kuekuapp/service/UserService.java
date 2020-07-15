package com.pwd.kuekuapp.service;

import java.util.Optional;

import com.pwd.kuekuapp.entity.Users;

public interface UserService {
	public Users signupUser(Users user);
	public Users loginUser(String username, String password);
	public String verifyUniqueUser(String username, String token);
	public Users forgotPassword(Users user);
	public Users verifyResetPassword(String username, String token);
	public Optional<Users> findByUsername(String username);
	public Optional<Users> findByEmail(String email);
	public Optional<Users> findUserById(int id);
	public Users editUsers(Users user, int id);
	public Iterable<Users> getUniqueUser(String username, String email);
	//public Iterable<Users> getOldPasswordUser(String password);
}
