package service.impl;

import java.util.List;

import dao.impl.PaymentDaoImpl;
import model.Payment;
import service.PaymentService;

public class PaymentServiceImpl implements PaymentService {
	
	PaymentDaoImpl pdi = new PaymentDaoImpl();

	@Override
	public void addPayment(Payment payment) {
		pdi.insert(payment);
	}

	@Override
	public List<Payment> getAllPaymentsJoined() {
		return pdi.findAllJoined();
	}

	@Override
	public List<Payment> getByResidentId(int residentId) {
		return pdi.findByResidentId(residentId);
	}

	@Override
	public void updatePayment(Payment payment) {
		pdi.update(payment);
	}

	@Override
	public void deletePayment(int id) {
		pdi.delete(id);
	}
}
