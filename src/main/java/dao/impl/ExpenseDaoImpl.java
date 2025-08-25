package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ExpenseDao;
import model.Expense;
import util.DBUtil;

public class ExpenseDaoImpl implements ExpenseDao {
	
	public static Connection conn = DBUtil.getDB();

	@Override
	public void insert(Expense expense) {
		String sql = "INSERT INTO expenses(category, amount, expense_date, description) VALUES (?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, expense.getCategory());
            ps.setInt(2, expense.getAmount());
            ps.setDate(3, expense.getExpenseDate());
            ps.setString(4, expense.getDescription());
            ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Expense> findAll() {
		String sql = "SELECT id, category, amount, expense_date, description FROM expenses ORDER BY expense_date ASC, id ASC";
		List<Expense> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Expense e = new Expense();
                e.setId(rs.getInt("id"));
                e.setCategory(rs.getString("category"));
                e.setAmount(rs.getInt("amount"));
                e.setExpenseDate(rs.getDate("expense_date"));
                e.setDescription(rs.getString("description"));
                list.add(e);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void update(Expense expense) {
		String sql = "UPDATE expenses SET category=?, amount=?, expense_date=?, description=? WHERE id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, expense.getCategory());
            ps.setInt(2, expense.getAmount());
            ps.setDate(3, expense.getExpenseDate());
            ps.setString(4, expense.getDescription());
            ps.setInt(5, expense.getId());
            ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM expenses WHERE id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
            ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
