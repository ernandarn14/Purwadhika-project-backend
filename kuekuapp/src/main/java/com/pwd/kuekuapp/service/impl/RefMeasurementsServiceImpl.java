package com.pwd.kuekuapp.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pwd.kuekuapp.dao.RefMeasurementRepo;
import com.pwd.kuekuapp.entity.RefMeasurements;
import com.pwd.kuekuapp.service.RefMeasurementService;

@Service
public class RefMeasurementsServiceImpl implements RefMeasurementService {
	@Autowired
	private RefMeasurementRepo refMeasurementRepo;

	@Override
	@Transactional
	public Iterable<RefMeasurements> getAllMeasurement() {
		return refMeasurementRepo.findAll();
	}

	@Override
	@Transactional
	public RefMeasurements addRefMeasurements(RefMeasurements refMeasurements) {
		refMeasurements.setId(0);
		return refMeasurementRepo.save(refMeasurements);
	}

}
