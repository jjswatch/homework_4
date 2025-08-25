package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.UnitDao;
import model.Unit;
import util.DBUtil;

public class UnitDaoImpl implements UnitDao {
	
	public static void main(String[] args) {
		/*
		List<Unit> l=new UnitDaoImpl().findUnit();
		
		for(Unit u:l)
		{
			System.out.println("building:"+u.getBuilding()+"\tfloor:"+u.getFloor()+"\tnumber:"+u.getNumber()+"\tunit:"+u.getUnit());
		}
		*/
	}
	
	private Connection conn = DBUtil.getDB();

	@Override
	public void insert(Unit unit) {
		String sql = "insert into units(building, floor, number) values (?, ?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, unit.getBuilding());
			ps.setInt(2, unit.getFloor());
			ps.setString(3, unit.getNumber());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Unit findById(int id) {
		String sql = "select id, building, floor, number from units where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Unit unit = new Unit();
				unit.setId(rs.getInt("id"));
				unit.setBuilding(rs.getString("building"));
				unit.setFloor(rs.getInt("floor"));
				unit.setNumber(rs.getString("number"));
				return unit;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Unit> findAll() {
		String sql = "select id, building, floor, number, concat(building,'-',floor,'-',number) as unit from units order by building, floor, number";
		//String sql = "select id, building, floor, number from units order by building, floor, number";
		List<Unit> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Unit unit = new Unit();
				unit.setId(rs.getInt("id"));
				unit.setBuilding(rs.getString("building"));
				unit.setFloor(rs.getInt("floor"));
				unit.setNumber(rs.getString("number"));
				list.add(unit);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void update(Unit unit) {
		String sql = "update units set building = ?, floor = ?, number = ? where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, unit.getBuilding());
			ps.setInt(2, unit.getFloor());
			ps.setString(3, unit.getNumber());
			ps.setInt(4, unit.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		String sql = "delete from units where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Unit> findUnit() {
		String sql = "select * from unit";
		List<Unit> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Unit unit = new Unit();
				unit.setId(rs.getInt("id"));
				unit.setBuilding(rs.getString("building"));
				unit.setFloor(rs.getInt("floor"));
				unit.setNumber(rs.getString("number"));
				unit.setUnit(rs.getString("unit"));
				list.add(unit);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
