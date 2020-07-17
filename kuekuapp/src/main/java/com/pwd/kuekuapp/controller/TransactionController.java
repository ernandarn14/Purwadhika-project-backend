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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.pwd.kuekuapp.dao.TransactionRepo;
import com.pwd.kuekuapp.entity.Transactions;
import com.pwd.kuekuapp.service.TransactionService;
import com.pwd.kuekuapp.util.EmailUtil;

@RestController
@RequestMapping("/transaksi")
@CrossOrigin
public class TransactionController {
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	private String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";
	
	@GetMapping
	public Iterable<Transactions> getAllTransactions(){
		return transactionService.getAllTransactions();
	}
	
	@GetMapping("/{id}")
	public Optional<Transactions> getTransactionById(@PathVariable int id){
		return transactionService.getTransactionById(id);
	}
	
	@GetMapping("/semua")
	public Iterable<Transactions> getAllByStatus(@RequestParam String status){
		return transactionService.getAllStatusTransactions(status);
	}
	
	@GetMapping("/status")
	public Iterable<Transactions> getByStatus(@RequestParam String status, @RequestParam int userId){
		return transactionService.getByStatus(status, userId);
	}
	
	@GetMapping("/status/pending")
	public Iterable<Transactions> getByPendingStatus(@RequestParam String status, @RequestParam int userId){
		return transactionService.getByPendingStatus(status, userId);
	}
	
	@GetMapping("/terlaris")
	public Iterable<Transactions> getBestSellerPlans(){
		return transactionRepo.getBestSellerPlans();
	}
	
	@PostMapping("/tambah/pengguna/{userId}/langganan/{planId}")
	public Transactions addTransactions(@RequestBody Transactions transactions, @PathVariable int planId, @PathVariable int userId) {
		return transactionService.addTransactions(transactions, planId, userId);
	}
	
	@PutMapping("/upload/{transaksiId}")
	public String uploadPaymentReceipt(@RequestParam("file") MultipartFile file, @RequestParam("userData") String stringTransaction, @PathVariable int transaksiId) throws JsonMappingException, JsonProcessingException {
		Transactions findTransactions = transactionRepo.findById(transaksiId).get();
		
		Date date = new Date();
		findTransactions = new ObjectMapper().readValue(stringTransaction, Transactions.class);
		
		String fileExtension = file.getContentType().split("/")[1];
		String newFileName = "PAYMENTREC-" + date.getTime() + "." + fileExtension;

		String fileName = StringUtils.cleanPath(newFileName);

		Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);
		
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/transaksi/download/")
				.path(fileName).toUriString();
		
		findTransactions.setPaymentReciept(fileDownloadUri);
		findTransactions.setPaymentDate(date);
		findTransactions.setStatus("pending");
		transactionRepo.save(findTransactions);
		
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
		
		//System.out.println("Download receipt");
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@PutMapping("/konfirmasi/{id}")
	public Transactions confirmPayment(@PathVariable int id){
		return transactionService.confirmPayment(id);
	}
	
	@PutMapping("/tolak/{id}")
	public Transactions rejectPayment(@RequestBody Transactions transactions, @PathVariable int id, @RequestParam String failedNote){
		return transactionService.rejectPayment(transactions, id, failedNote);
	}
	
	

}
