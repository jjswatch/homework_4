package dao;

import java.util.List;

import model.Manager;

public interface ManagerDao {
	// Create
	void insert(Manager manager);
	
	// Read
	Manager findByUsernameAndPassword(String username, String password);
	Manager findByUsername(String username);
	List<Manager> findAll();
	Manager findById(int id);
	
	// Update
	void update(Manager manager);
	
	// Delete
	void delete(int id);
}
