package service.impl;

import java.util.List;

import dao.impl.ResidentDaoImpl;
import model.Resident;
import service.ResidentService;

public class ResidentServiceImpl implements ResidentService {
	
	private static ResidentDaoImpl rdi = new ResidentDaoImpl();

	@Override
	public void addResident(Resident resident) {
		rdi.insert(resident);
	}

	@Override
	public Resident getResidentById(int id) {
		return rdi.findById(id);
	}

	@Override
	public List<Resident> getAllResident() {
		return rdi.findAll();
	}

	@Override
	public void updateResident(Resident resident) {
		rdi.update(resident);
	}

	@Override
	public void deleteResident(int id) {
		rdi.delete(id);
	}

	@Override
	public Resident getResidentByUnitId(int unit_id) {
		
		return rdi.findByUnitId(unit_id);
	}

	@Override
	public Resident getResidentByName(String name) {

		return rdi.findByName(name);
	}

}
