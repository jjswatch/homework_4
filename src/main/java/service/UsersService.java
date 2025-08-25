package service;

import java.util.List;

import model.Users;

public interface UsersService {
	// Create
	boolean addUser(Users users);
	
	// Read
	boolean getUserByUsername(String username);
	boolean getUserByNameAndUnit(String name, int unitId);
	Users getUserByUsernameAndPassword(String username, String password);
	List<Users> getAllUsers();
	
	// Update
	void updateUser(Users users);
	
	// Delete
	void deleteUser(int id);
}
