package service.impl;

import java.util.List;

import dao.impl.UnitDaoImpl;
import model.Unit;
import service.UnitService;

public class UnitServiceImpl implements UnitService {
	
	private static UnitDaoImpl udi = new UnitDaoImpl();

	@Override
	public void addUnit(Unit unit) {
		udi.insert(unit);
	}

	@Override
	public Unit getUnitById(int id) {
		return udi.findById(id);
	}

	@Override
	public List<Unit> getAllUnit() {
		return udi.findUnit();
	}

	@Override
	public void updateUnit(Unit unit) {
		udi.update(unit);
	}

	@Override
	public void deleteUnit(int id) {
		udi.delete(id);
	}
}
