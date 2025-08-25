package service;

import java.util.List;

import model.Manager;

public interface ManagerService {
	// Create
	void addManager(Manager manager);
	
	// Read
	Manager getManagerByUsernameAndPassword(String username, String password);
	Manager getManagerByUsername(String username);
	List<Manager> getAllManager();
	Manager getManagerById(int id);
	
	// Update
	void updateManager(Manager manager);
	
	// Delete
	void deleteManager(int id);
}
