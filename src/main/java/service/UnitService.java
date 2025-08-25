package service;

import java.util.List;

import model.Unit;

public interface UnitService {
	// Create
	void addUnit(Unit unit);
	
	// Read
	Unit getUnitById(int id);
	List<Unit> getAllUnit();
	
	// Update
	void updateUnit(Unit unit);
	
	// Delete
	void deleteUnit(int id);
}
