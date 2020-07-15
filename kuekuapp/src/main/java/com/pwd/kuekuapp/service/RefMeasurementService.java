package com.pwd.kuekuapp.service;

import com.pwd.kuekuapp.entity.RefMeasurements;

public interface RefMeasurementService {
	public Iterable<RefMeasurements> getAllMeasurement();
	public RefMeasurements addRefMeasurements(RefMeasurements refMeasurements);
}
