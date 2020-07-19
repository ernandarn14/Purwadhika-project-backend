package com.pwd.kuekuapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pwd.kuekuapp.entity.Plans;
import com.pwd.kuekuapp.service.PlanService;

@RestController
@RequestMapping("/langganan")
@CrossOrigin
public class PlanController {
	@Autowired
	private PlanService planService;
	
	@GetMapping
	public Iterable<Plans> getAllPlans(){
		return planService.getAllPlans();
	}
	
	@GetMapping("/{id}")
	public Optional<Plans> getPlansById(@PathVariable int id){
		return planService.getPlansById(id);
	}
	
	@GetMapping("/admin/{sort}/{order}/{minPrice}/{maxPrice}")
	public Iterable<Plans> adminGetAllPlans(@PathVariable String sort, @PathVariable String order, @PathVariable double minPrice, @PathVariable double maxPrice){
		return planService.adminGetAllPlans(sort, order, minPrice, maxPrice);
	}
	
	@GetMapping("/admin/periode/{sort}/{order}/{minPrice}/{maxPrice}")
	public Iterable<Plans> adminGetAllPlansByPeriod(@RequestParam String planPeriod, @PathVariable String sort, @PathVariable String order, @PathVariable double minPrice, @PathVariable double maxPrice){
		return planService.adminGetAllPlansByPeriod(planPeriod, sort, order, minPrice, maxPrice);
	}
	
	
	@PostMapping("/tambah")
	public Plans addNewPlans(@RequestBody Plans plan) {
		return planService.addNewPlans(plan);
	}
	
	@PutMapping("/ubah")
	public Plans editPlans(@RequestBody Plans plan) {
		return planService.editPlans(plan);
	}
	
	@DeleteMapping("/hapus/{id}")
	public void deletePlans(@PathVariable int id) {
		planService.deletePlans(id);
	}

}
