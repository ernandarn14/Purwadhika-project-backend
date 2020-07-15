package com.pwd.kuekuapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pwd.kuekuapp.entity.RefMeasurements;
import com.pwd.kuekuapp.service.RefMeasurementService;

@RestController
@RequestMapping("/satuan-ukuran")
public class RefMeasurementRepoController {
	@Autowired
	private RefMeasurementService refMeasurementService;
	
	@GetMapping
	public Iterable<RefMeasurements> getAllMeasurement(){
		return refMeasurementService.getAllMeasurement();
	}
	
	@PostMapping
	public RefMeasurements addRefMeasurements(@RequestBody RefMeasurements refMeasurements) {
		return refMeasurementService.addRefMeasurements(refMeasurements);
	}
}
