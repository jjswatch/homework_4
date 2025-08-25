package dao;

import java.util.List;

import model.Resident;

public interface ResidentDao {
	// Create
    void insert(Resident resident);
	
	// Read
    Resident findById(int id);
    List<Resident> findAll();
    Resident findByName(String name);
    Resident findByUnitId(int unit_id);
	
	// Update
    void update(Resident resident);
	
	// Delete
    void delete(int id);
}
