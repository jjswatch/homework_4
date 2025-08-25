package model;

public class Resident {
	private int id;
	private String name;
	private String phone;
	private String email;
	private int unitId;
	
	public Resident() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Resident(int id, String name, String phone, String email, int unitId) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.unitId = unitId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	@Override
	public String toString() {
	    return name + (unitId != 0 ? " (Unit " + unitId + ")" : "");
	}
}
