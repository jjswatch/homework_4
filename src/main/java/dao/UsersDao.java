package dao;

import java.util.List;

import model.Users;

public interface UsersDao {
	// Create
	boolean insert(Users users);
	
	// Read
	boolean findByUsername(String username);
	boolean findByNameAndUnit(String name, int unitId);
	Users findByUsernameAndPassword(String username, String password);
	List<Users> findAll();
	
	// Update
	void update(Users users);
	
	// Delete
	void delete(int id);
}
