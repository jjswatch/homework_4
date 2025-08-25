package service;

import java.util.List;

import model.Expense;

public interface ExpenseService {
	// Create
	void addExpense(Expense expense);
	
	// Read
	List<Expense> getAllExpense();
	
	// Update
	void updateExpense(Expense expense);
	
	// Delete
	void deleteExpense(int id);
}
