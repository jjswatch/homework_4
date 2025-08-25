package service.impl;

import java.util.List;

import dao.impl.UsersDaoImpl;
import model.Users;
import service.UsersService;

public class UsersServiceImpl implements UsersService{
	public static UsersDaoImpl udi = new UsersDaoImpl();

	@Override
	public boolean addUser(Users users) {
		return udi.insert(users);
		
	}


	@Override
	public boolean getUserByUsername(String username) {
		
		return udi.findByUsername(username);
	}

	@Override
	public List<Users> getAllUsers() {
		
		return udi.findAll();
	}

	@Override
	public void updateUser(Users users) {
		udi.update(users);
		
	}

	@Override
	public void deleteUser(int id) {
		udi.delete(id);
		
	}

	@Override
	public boolean getUserByNameAndUnit(String name, int unitId) {
		
		return udi.findByNameAndUnit(name, unitId);
	}


	@Override
	public Users getUserByUsernameAndPassword(String username, String password) {
		
		return udi.findByUsernameAndPassword(username, password);
	}

}
