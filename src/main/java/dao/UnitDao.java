package dao;

import java.util.List;

import model.Unit;

public interface UnitDao {
	// Create
	void insert(Unit unit);
	
	// Read
	Unit findById(int id);
	List<Unit> findAll();
	List<Unit> findUnit();
	
	// Update
	void update(Unit unit);
	
	// Delete
	void delete(int id);
}
