package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.UsersDao;
import model.Users;
import util.DBUtil;

public class UsersDaoImpl implements UsersDao {
	
	public static Connection conn = DBUtil.getDB();

	@Override
	public boolean insert(Users users) {
		String sql = "insert into users (username, password, fullname, unit_id, role) values (?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, users.getUsername());
			ps.setString(2, users.getPassword());
			ps.setString(3, users.getFullname());
			ps.setInt(4, users.getUnitId());
			ps.setString(5, users.getRole());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}

	@Override
	public boolean findByUsername(String username) {
		String sql = "select * from users where username = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			return rs.next();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true; // 出錯時當作已存在,避免重複
	}

	@Override
	public List<Users> findAll() {
		String sql = "select * from users";
		List<Users> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Users users = new Users();
				users.setId(rs.getInt("id"));
				users.setUsername(rs.getString("username"));
		        users.setPassword(rs.getString("password"));
		        users.setFullname(rs.getString("fullname"));
		        users.setUnitId(rs.getInt("unit_id"));
		        users.setRole(rs.getString("role"));
		        list.add(users);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void update(Users users) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean findByNameAndUnit(String name, int unitId) {
		String sql = "select * from residents where name = ? AND unit_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
            ps.setInt(2, unitId);
            ResultSet rs = ps.executeQuery();
			return rs.next(); // 如果有資料,表示存在
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Users findByUsernameAndPassword(String username, String password) {
		String sql = "select * from users where username = ? and password = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Users(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("fullname"),
                        rs.getInt("unit_id"),
                        rs.getString("role")
                );
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
