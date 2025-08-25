package model;

public class Unit {
	private int id;
	private String building;
	private int floor;
	private String number;
	private String unit; // 註冊驗證用
	public Unit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Unit(int id, String building, int floor, String number) {
		super();
		this.id = id;
		this.building = building;
		this.floor = floor;
		this.number = number;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Override
	public String toString() {
		return building + "-" + floor + "-" + number;
	}
}
