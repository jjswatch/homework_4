package model;

import java.sql.Date;

public class Expense {
	private int id;
	private String category;
	private int amount;
	private Date expenseDate;
	private String description;
	public Expense() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Expense(int id, String category, int amount, Date expensedate, String description) {
		super();
		this.id = id;
		this.category = category;
		this.amount = amount;
		this.expenseDate = expensedate;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getExpenseDate() {
		return expenseDate;
	}
	public void setExpenseDate(Date expensedate) {
		this.expenseDate = expensedate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
