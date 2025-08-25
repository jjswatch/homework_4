package service;

import java.util.List;

import model.Payment;

public interface PaymentService {
	
	// Create
	void addPayment(Payment payment);
		
	// Read
	List<Payment> getAllPaymentsJoined(); // with resident name
	List<Payment> getByResidentId(int residentId);
	    
	// Update
	void updatePayment(Payment payment);
	    
	// Delete
	void deletePayment(int id);
}
