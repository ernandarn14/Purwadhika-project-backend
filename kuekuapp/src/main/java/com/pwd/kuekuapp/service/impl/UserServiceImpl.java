package com.pwd.kuekuapp.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pwd.kuekuapp.dao.UserRepo;
import com.pwd.kuekuapp.entity.Users;
import com.pwd.kuekuapp.service.UserService;
import com.pwd.kuekuapp.util.EmailUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmailUtil emailUtil;

	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();

	@Override
	@Transactional
	public Users signupUser(Users user) {
		String encodedPassword = pwEncoder.encode(user.getPassword());
		String verifyToken = pwEncoder.encode(user.getUsername() + user.getEmail());

		if (userRepo.validateUniqueUser(user.getUsername(), user.getEmail()) == null) {
		user.setPassword(encodedPassword);
		user.setVerified(false);
		user.setVerifyToken(verifyToken);
		user.setRole("pengguna");

		Users savedUser = userRepo.save(user);
			savedUser.setPassword(null);

		String linkVerify = "http://localhost:8080/pengguna/verifikasi/" + user.getUsername() + "?token=" + verifyToken;

		String bodyMessage = "<h1>Selamat, Registrasi Anda Berhasil!</h1>\n";
		bodyMessage += "Akun dengan username " + user.getUsername() + " telah terdaftar.\n";
		bodyMessage += "Silahkan klik <a href=\"" + linkVerify + "\">link</a> untuk verifikasi email anda.\n";
		bodyMessage += "Kueku Team";

		emailUtil.sendEmail(user.getEmail(), "Verifikasi Akun", bodyMessage);

		return savedUser;
		} 
		else {
			throw new RuntimeException("Username atau Email sudah ada.");
		}

	}

	@Override
	@Transactional
	public Users loginUser(String username, String password) {
//		Users findUser = userRepo.findByUsername(username).get();
//
//		if (pwEncoder.matches(password, findUser.getPassword())) {
////			findUser.setPassword(null);
//			return findUser;
//		}
//
//		throw new RuntimeException("Username atau Password Salah");
		Users findUser = userRepo.findByUsername(username).get();

		if (pwEncoder.matches(password, findUser.getPassword())) {
			findUser.setPassword(null);
			return findUser;
		}

		return null;
	}

	@Override
	@Transactional
	public String verifyUniqueUser(String username, String token) {
		Users findUser = userRepo.findByUsername(username).get();

		if (findUser.getVerifyToken().equals(token)) {
			findUser.setVerified(true);
		} else {
			throw new RuntimeException("Token sudah kadaluarsa");
		}

		userRepo.save(findUser);

		return "Verifikasi Berhasil";
	}

	@Override
	@Transactional
	public Users forgotPassword(Users user) {
		Users findEmailUser = userRepo.findUserEmail(user.getEmail()).get();
		
		if (userRepo.findUserEmail(user.getEmail()) == null) {
			throw new RuntimeException("Email tidak ditemukan");
		} else {
			String linkVerify = "http://localhost:3000/pengguna/lupa-password/" + findEmailUser.getUsername() + "?token="
					+ findEmailUser.getVerifyToken();

			String bodyMessage = "<h2>Atur Ulang Password Kueku</h2>\n";
			bodyMessage += "Hai " + findEmailUser.getUsername() + "\n";
			bodyMessage += "Kami telah menerima permohonan untuk mengatur ulang password Anda.\n";
			bodyMessage += "Silahkan klik <a href=\"" + linkVerify
					+ "\">link</a> untuk mengatur ulang password Anda.\n";
			bodyMessage += "\n";
			bodyMessage += "\nKueku Team";

			emailUtil.sendEmail(user.getEmail(), "Atur Ulang Password Anda", bodyMessage);
		}
		
		return findEmailUser;
		

//		String verifyToken = pwEncoder.encode(user.getUsername() + user.getEmail());
//
//		if (userRepo.findUserEmail(user.getEmail()) == null) {
//			throw new RuntimeException("Email tidak ditemukan");
//		} else {
//			user.setVerifyToken(verifyToken);
//			userRepo.save(user);
//
//			String linkVerify = "http://localhost:8080/pengguna/lupa-password/" + user.getUsername() + "/"
//					+ verifyToken;
//
//			String bodyMessage = "<h2>Atur Ulang Password Kueku</h2>\n";
//			bodyMessage += "Hai " + user.getUsername() + "\n";
//			bodyMessage += "Kami telah menerima permohonan untuk mengatur ulang password Anda\n";
//			bodyMessage += "Silahkan klik <a href=\"" + linkVerify
//					+ "\">link</a> untuk mengatur ulang password Anda.\n";
//			bodyMessage += "\n";
//			bodyMessage += "\nKueku Team";
//
//			emailUtil.sendEmail(user.getEmail(), "Atur Ulang Password Anda", bodyMessage);
//		}
//
//		return userRepo.save(user);
	}

	@Override
	@Transactional
	public Users verifyResetPassword(String username, String token) {
		Users findUser = userRepo.findByUsername(username).get();
		findUser = userRepo.findByVerifyToken(token).get();
		return findUser;
//		if(findUser.getVerifyToken().equals(token)) {
//			return userRepo.save(findUser);
//		} else {
//			throw new RuntimeException("Token sudah kadaluarsa");
//		}
		
//		findUser = userRepo.findByVerifyToken(token).get();
//		return findUser;
//		String encodedPassword = pwEncoder.encode(findUser.getPassword());
//
//		if (findUser.getVerifyToken().equals(token)) {
//			findUser.setPassword(encodedPassword);
//			;
//		} else {
//			throw new RuntimeException("Token sudah kadaluarsa");
//		}
//
//		userRepo.save(findUser);
//
//		return "Password Berhasil Diubah";

	}

	@Override
	@Transactional
	public Optional<Users> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	@Transactional
	public Optional<Users> findUserById(int id) {
		return userRepo.findById(id);
	}

	@Override
	public Users editUsers(Users user, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Iterable<Users> getUniqueUser(String username, String email) {
		//Users findUser = userRepo.findByUsername(username).get();
		return userRepo.validateUniqueUser(username, email);
	}

	@Override
	@Transactional
	public Optional<Users> findByEmail(String email) {
		return userRepo.findUserEmail(email);
	}

}
