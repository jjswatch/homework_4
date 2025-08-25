package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.PaymentDao;
import model.Payment;
import util.DBUtil;

public class PaymentDaoImpl implements PaymentDao {
	
	public static Connection conn = DBUtil.getDB();

	@Override
	public void insert(Payment payment) {
		String sql = "INSERT INTO payments(resident_id, amount, payment_date, description, type) VALUES (?,?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, payment.getResidentId());
            ps.setDouble(2, payment.getAmount());
            ps.setDate(3, payment.getPaymentDate());
            ps.setString(4, payment.getDescription());
            ps.setString(5, payment.getType());
            ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Payment> findAllJoined() {
		String sql = "SELECT p.id, p.resident_id, r.name AS resident_name, p.amount, p.payment_date, p.description, p.type FROM payments p JOIN residents r ON p.resident_id = r.id ORDER BY p.payment_date ASC, p.id ASC ";
		List<Payment> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Payment p = new Payment();
                p.setId(rs.getInt("id"));
                p.setResidentId(rs.getInt("resident_id"));
                p.setAmount(rs.getInt("amount"));
                p.setPaymentDate(rs.getDate("payment_date"));

                // Optional fields for reports
                p.setResidentName(rs.getString("resident_name"));
                //p.setUnitNumber(rs.getString("unit_number"));
                //p.setApartmentName(rs.getString("apartment_name"));
                p.setDescription(rs.getString("description"));
                p.setType(rs.getString("type"));
                list.add(p);
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Payment> findByResidentId(int residentId) {
		String sql = "SELECT * FROM payments WHERE resident_id=?";
		List<Payment> payments = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, residentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                payments.add(new Payment(
                        rs.getInt("id"),
                        rs.getInt("resident_id"),
                        rs.getInt("amount"),
                        rs.getDate("date")
                ));
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return payments;
	}

	@Override
	public void update(Payment payment) {
		String sql = "UPDATE payments SET resident_id=?, amount=?, payment_date=?, description=?, type=? WHERE id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, payment.getResidentId());
            ps.setInt(2, payment.getAmount());
            ps.setDate(3, payment.getPaymentDate());
            ps.setString(4, payment.getDescription());
            ps.setString(5, payment.getType());
            ps.setInt(6, payment.getId());
            ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM payments WHERE id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
            ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
