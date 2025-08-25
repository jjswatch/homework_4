package service.impl;

import java.util.List;

import dao.impl.ManagerDaoImpl;
import model.Manager;
import service.ManagerService;

public class ManagerServiceImpl implements ManagerService {
	
	public static ManagerDaoImpl mdi = new ManagerDaoImpl();

	@Override
	public void addManager(Manager manager) {
		mdi.insert(manager);
	}

	@Override
	public Manager getManagerByUsernameAndPassword(String username, String password) {
		
		return mdi.findByUsernameAndPassword(username, password);
	}

	@Override
	public List<Manager> getAllManager() {
		
		return mdi.findAll();
	}

	@Override
	public Manager getManagerById(int id) {
		
		return mdi.findById(id);
	}

	@Override
	public void updateManager(Manager manager) {
		mdi.update(manager);
		
	}

	@Override
	public void deleteManager(int id) {
		mdi.delete(id);
		
	}

	@Override
	public Manager getManagerByUsername(String username) {
		return mdi.findByUsername(username);
	}
}
