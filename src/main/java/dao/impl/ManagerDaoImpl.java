package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ManagerDao;
import model.Manager;
import util.DBUtil;

public class ManagerDaoImpl implements ManagerDao {
	
	public static Connection conn = DBUtil.getDB();

	@Override
	public void insert(Manager manager) {
		String sql = "INSERT INTO managers (username, password, full_name, role) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, manager.getUsername());
            ps.setString(2, manager.getPassword());
            ps.setString(3, manager.getFullName());
            ps.setString(4, manager.getRole());
            ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Manager findByUsernameAndPassword(String username, String password) {
		String sql = "SELECT * FROM managers WHERE username=? AND password=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Manager(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("role")
                );
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Manager> findAll() {
		String sql = "SELECT * FROM managers";
		List<Manager> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
                list.add(new Manager(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("role")
                ));
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Manager findById(int id) {
		String sql = "SELECT * FROM managers WHERE id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Manager(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("role")
                );
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Manager manager) {
		String sql = "UPDATE managers SET username=?, password=?, full_name=?, role=? WHERE id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, manager.getUsername());
            ps.setString(2, manager.getPassword());
            ps.setString(3, manager.getFullName());
            ps.setString(4, manager.getRole());
            ps.setInt(5, manager.getId());
            ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM managers WHERE id=?";
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
	public Manager findByUsername(String username) {
		String sql = "select * from managers where username = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Manager manager = new Manager();
				manager.setUsername(rs.getString("username"));
				manager.setPassword(rs.getString("password"));
				manager.setFullName(rs.getString("full_name"));
				manager.setRole(rs.getString("role"));
				return manager;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
