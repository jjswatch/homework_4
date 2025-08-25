package model;

import java.sql.Date;

public class Payment {
	private int id;
    private int residentId;
    private String residentName; // for JOIN display
    private int amount;
    private Date paymentDate;
    private String description;
    private String type; // Rent, Maintenance, Parking, Other
	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Payment(int id, int residentId, int amount, Date paymentDate) {
		super();
		this.id = id;
		this.residentId = residentId;
		this.amount = amount;
		this.paymentDate = paymentDate;
	}
	public Payment(int id, int residentId, String residentName, int amount, Date paymentDate, String description,
			String type) {
		super();
		this.id = id;
		this.residentId = residentId;
		this.residentName = residentName;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.description = description;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getResidentId() {
		return residentId;
	}
	public void setResidentId(int residentId) {
		this.residentId = residentId;
	}
	public String getResidentName() {
		return residentName;
	}
	public void setResidentName(String residentName) {
		this.residentName = residentName;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
    
}
