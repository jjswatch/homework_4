package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.ResidentDao;
import model.Resident;
import util.DBUtil;

public class ResidentDaoImpl implements ResidentDao {
	
	private Connection conn = DBUtil.getDB();

	@Override
	public void insert(Resident resident) {
		String sql = "insert into residents(name, phone, email, unit_id) values (?, ?, ?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, resident.getName());
			ps.setString(2, resident.getPhone());
			ps.setString(3, resident.getEmail());
			ps.setInt(4, resident.getUnitId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Resident findById(int id) {
		String sql = "select id, name, phone, email, unit_id from residents where id= ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Resident resident = new Resident();
				resident.setId(rs.getInt("id"));
				resident.setName(rs.getString("name"));
				resident.setPhone(rs.getString("phone"));
				resident.setEmail(rs.getString("email"));
				resident.setUnitId(rs.getInt("unit_id"));
				return resident;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Resident> findAll() {
        String sql = "select id, name, phone, email, unit_id from residents order by name";
        List<Resident> list = new ArrayList<>();
        try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Resident resident = new Resident();
				resident.setId(rs.getInt("id"));
				resident.setName(rs.getString("name"));
				resident.setPhone(rs.getString("phone"));
				resident.setEmail(rs.getString("email"));
				resident.setUnitId(rs.getInt("unit_id"));
				list.add(resident);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public void update(Resident resident) {
		String sql = "UPDATE residents SET name=?, phone=?, email=?, unit_id=? WHERE id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, resident.getName());
            ps.setString(2, resident.getPhone());
            ps.setString(3, resident.getEmail());
            if (resident.getUnitId() == 0) ps.setNull(4, Types.INTEGER); else ps.setInt(4, resident.getUnitId());
            ps.setInt(5, resident.getId());
            ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		String sql = "delete from residents where id = ?";
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
	public Resident findByUnitId(int unit_id) {
		String sql = "select * from residents where unit_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, unit_id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Resident resident = new Resident();
				resident.setId(rs.getInt("id"));
				resident.setName(rs.getString("name"));
				resident.setPhone(rs.getString("phone"));
				resident.setEmail(rs.getString("email"));
				resident.setUnitId(rs.getInt("unit_id"));
				return resident;
			}
 		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Resident findByName(String name) {
		String sql = "select * from residents where name = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Resident resident = new Resident();
				resident.setId(rs.getInt("id"));
				resident.setName(rs.getString("name"));
				resident.setPhone(rs.getString("phone"));
				resident.setEmail(rs.getString("email"));
				resident.setUnitId(rs.getInt("unit_id"));
				return resident;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
