package service.impl;

import java.util.List;

import dao.impl.ExpenseDaoImpl;
import model.Expense;
import service.ExpenseService;

public class ExpenseServiceImpl implements ExpenseService {
	
	public static ExpenseDaoImpl edi = new ExpenseDaoImpl();

	@Override
	public void addExpense(Expense expense) {
		edi.insert(expense);
		
	}

	@Override
	public List<Expense> getAllExpense() {
		return edi.findAll();
	}

	@Override
	public void updateExpense(Expense expense) {
		edi.update(expense);
		
	}

	@Override
	public void deleteExpense(int id) {
		edi.delete(id);
		
	}

}
