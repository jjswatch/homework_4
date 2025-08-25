package dao;

import java.util.List;

import model.Expense;

public interface ExpenseDao {
	// Create
	void insert(Expense expense);
	
	// Read
	List<Expense> findAll();
	
	// Update
	void update(Expense expense);
	
	// Delete
	void delete(int id);
}
