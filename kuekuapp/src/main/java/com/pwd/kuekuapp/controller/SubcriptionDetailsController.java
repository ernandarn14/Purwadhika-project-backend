package com.pwd.kuekuapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pwd.kuekuapp.dao.SubcriptionDetailsRepo;
import com.pwd.kuekuapp.entity.SubcriptionDetails;

@RestController
@RequestMapping("/view-details")
@CrossOrigin
public class SubcriptionDetailsController {
	@Autowired
	private SubcriptionDetailsRepo subcriptionDetailsRepo;
	
	@GetMapping
	public Iterable<SubcriptionDetails> getAllDataView(){
		return subcriptionDetailsRepo.findAll();
	}
	
	@GetMapping("/{username}")
	public Iterable<SubcriptionDetails> getDataViewUsername(@PathVariable String username){
		return subcriptionDetailsRepo.getUniqueUsername(username);
	}
}
