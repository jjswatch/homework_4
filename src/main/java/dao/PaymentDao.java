package dao;

import java.util.List;

import model.Payment;

public interface PaymentDao {
	// Create
	void insert(Payment payment);
	
	// Read
	List<Payment> findAllJoined(); // with resident name
    List<Payment> findByResidentId(int residentId);
    
    // Update
    void update(Payment payment);
    
    // Delete
    void delete(int id);
}
