package com.pwd.kuekuapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pwd.kuekuapp.entity.Subscriptions;
import com.pwd.kuekuapp.service.SubcriptionService;

@RestController
@RequestMapping("/langgananku")
@CrossOrigin
public class SubscriptionController {
	@Autowired
	private SubcriptionService subcriptionService;
	
	@GetMapping
	public Iterable<Subscriptions> getAllSubscriptions(){
		return subcriptionService.getAllSubscriptions();
	}
	
	@GetMapping("/{id}")
	public Optional<Subscriptions> getSubscriptionsById(@PathVariable int id){
		return subcriptionService.getSubscriptionsById(id);
	}
	
	@PostMapping("/tambah/{transactionId}")
	public Subscriptions addSubscriptions(@RequestBody Subscriptions subscriptions,@PathVariable int transactionId) {
		return subcriptionService.addSubscriptions(subscriptions, transactionId);
	}
	
	@DeleteMapping("/hapus/{id}")
	public void deleteSubscription(@PathVariable int id) {
		subcriptionService.deleteSubscription(id);
	}
}
