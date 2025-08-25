package service;

import java.util.List;

import model.Resident;

public interface ResidentService {
	// Create
    void addResident(Resident resident);
	
	// Read
    Resident getResidentById(int id);
    List<Resident> getAllResident();
    Resident getResidentByUnitId(int unit_id);
    Resident getResidentByName(String name);
    
	
	// Update
    void updateResident(Resident resident);
	
	// Delete
    void deleteResident(int id);
}
