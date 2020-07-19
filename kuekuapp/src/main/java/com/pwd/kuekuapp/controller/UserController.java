package com.pwd.kuekuapp.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwd.kuekuapp.dao.UserRepo;
import com.pwd.kuekuapp.entity.Users;
import com.pwd.kuekuapp.service.UserService;
import com.pwd.kuekuapp.util.EmailUtil;

@RestController
@RequestMapping("/pengguna")
@CrossOrigin
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;

	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();

	@Autowired
	private EmailUtil emailUtil;
	
	private String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";

	@PostMapping("/daftar")
	public Users signupUser(@RequestBody Users user) {
		//return userService.signupUser(user);
		String encodedPassword = pwEncoder.encode(user.getPassword());
		String verifyToken = pwEncoder.encode(user.getUsername() + user.getEmail());

		user.setPassword(encodedPassword);
		user.setVerified(false);
		user.setVerifyToken(verifyToken);
		user.setRole("pengguna");
		user.setMembership("non premium");

		Users savedUser = userRepo.save(user);
		savedUser.setPassword(null);

		String linkVerify = "http://localhost:3000/pengguna/verifikasi/" + user.getUsername() + "?token=" + verifyToken;

		String bodyMessage = "<h1>Selamat, Registrasi Anda Berhasil!</h1>\n";
		bodyMessage += "Akun dengan username " + user.getUsername() + " telah terdaftar.\n";
		bodyMessage += "Silahkan klik <a href=\"" + linkVerify + "\">link</a> untuk verifikasi email anda.\n";
		bodyMessage += "\n<p>Kueku Team</p>";

		emailUtil.sendEmail(user.getEmail(), "Verifikasi Akun", bodyMessage);
		return savedUser;
	}

	@GetMapping("/verifikasi/{username}")
	public String confirmUserAccount(@RequestParam String token, @PathVariable String username) {
		Users findUser = userRepo.findByUsername(username).get();

		if (findUser.getVerifyToken().equals(token)) {
			findUser.setVerified(true);
			userRepo.save(findUser);
			return "Akun Terverifikasi";
		} else {
			return "Link Sudah Kadaluwarsa";
		}

	}

	@GetMapping("/masuk")
	public Users loginUser(@RequestParam String username, @RequestParam String password) {
		//return userService.loginUser(username, password);
		Users findUser = userRepo.findByUsername(username).get();

		if (pwEncoder.matches(password, findUser.getPassword())) {
			findUser.setPassword(null);
			return findUser;
		}

//		return null;
		throw new RuntimeException("Password Salah");
	}

	@GetMapping("/{username}")
	public Optional<Users> findByUsername(@PathVariable String username) {
		return userService.findByUsername(username);
	}
	
	@GetMapping("/email")
	public Optional<Users> findByEmail(@RequestParam String email){
		return userService.findByEmail(email);
	}
	
	@GetMapping("/{username}/{email}")
	public Iterable<Users> getUniqueUser(@PathVariable String username, @PathVariable String email){
		return userService.getUniqueUser(username, email);
	}
	
	
	@GetMapping("/id")
	public Optional<Users> findUserById(@RequestParam int id) {
		return userService.findUserById(id);
	}
	
	@PatchMapping("/ubah/{id}")
	public String updateUsers(@RequestParam("file") Optional<MultipartFile> file, @RequestParam("userData") String stringUser, @PathVariable int id) throws JsonMappingException, JsonProcessingException {
		Users findUser = userRepo.findById(id).get();
		Date date = new Date();
		
		findUser = new ObjectMapper().readValue(stringUser, Users.class);
		
		String fileDownloadUri = findUser.getProfilePicture();
		
		if(file.toString() != "Optional.empty") {
			String fileExtension = file.get().getContentType().split("/")[1];
			String newFileName = "USERS-" + date.getTime() + "." + fileExtension;
			String fileName = StringUtils.cleanPath(newFileName);

			Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);
			
			try {
				Files.copy(file.get().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/pengguna/download/")
					.path(fileName).toUriString();
		}

		findUser.setProfilePicture(fileDownloadUri);
		
		userRepo.save(findUser);
		
		return fileDownloadUri;
	}
	
	@GetMapping("/download/{fileName:.+}") //.+ extension .jpg/png
	public ResponseEntity<Object> downloadFile(@PathVariable String fileName){
		Path path = Paths.get(uploadPath + fileName);
		Resource resource = null;
		
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		//System.out.println("Download");
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
		
	}
	
	@PutMapping("/ubah-password/{id}")
	public Users updatePasswordUser(@PathVariable int id, @RequestParam String oldPassword, @RequestParam String newPassword) {
		Users findUser = userRepo.findById(id).get();
		
		if (pwEncoder.matches(oldPassword, findUser.getPassword())) {
			String newEncodedPassword = pwEncoder.encode(newPassword);
			findUser.setPassword(newEncodedPassword);
			Users newUser = userRepo.save(findUser);
			newUser.setPassword(null);
			return newUser;
		}
		
		throw new RuntimeException("Password Salah");
		
	}
	
	@PostMapping("/lupa-password")
	public Users forgotPassword(@RequestBody Users user) {
		return userService.forgotPassword(user);
	}
	
	@GetMapping("/lupa-password/{username}")
	public Users verifyResetPassword(@PathVariable String username, @RequestParam String token) {
		return userService.verifyResetPassword(username, token);
	}
	
	@PutMapping("/ganti-password/{username}")
	public Users resetPassword(@PathVariable String username,  @RequestParam String newPassword) {
		Users findUser = userRepo.findByUsername(username).get();
		if (findUser.getPassword() != null) {
			String newEncodedPassword = pwEncoder.encode(newPassword);
			findUser.setPassword(newEncodedPassword);
			Users newUser = userRepo.save(findUser);
			return newUser;
		}
		throw new RuntimeException("Password Tidak Valid");
	}
	
	@GetMapping("/premium/{sort}")
	public Iterable<Users> getAllPremiumUser(@PathVariable String sort) {
		return userService.adminGetAllUser(sort);
	}
	
	@GetMapping("/membership/{sort}")
	public Iterable<Users> getMembershipData(@PathVariable String sort, @RequestParam String membership) {
		return userService.adminGetByMembership(membership, sort);
	}
}
