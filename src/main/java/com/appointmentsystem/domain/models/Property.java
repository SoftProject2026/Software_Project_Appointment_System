package com.appointmentsystem.domain.models;

import com.appointmentsystem.domain.models.enums.PropertyType;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public class Property {
    private String id;
    private String companyId;
    private PropertyType type;
    private double price;
    private double area;
    private int roomsNumber;
    private String address;
    
    private List<TimeSlot> timeSlots = new ArrayList<>();
    
    public Property(String id, String companyId, PropertyType type, double price) {
        this.id = id;
        this.companyId = companyId;
        this.type = type;
        this.price = price;
    }
    
    public Property(String id, String companyId, PropertyType type, 
                    double price, double area, int roomsNumber, String address) {
        this.id = id;
        this.companyId = companyId;
        this.type = type;
        this.price = price;
        this.area = area;
        this.roomsNumber = roomsNumber;
        this.address = address;
    }
    
    public Property() {
    	this.id = "0";
        this.companyId = "0";
        this.type = PropertyType.APARTMENT;
        this.price = 0;
        this.area = 0;
        this.roomsNumber = 0;
        this.address = "address";
    	    this.timeSlots = new ArrayList<>();
    	
	}

	public void addTimeSlot(TimeSlot slot) {
    	if (slot != null) {
            timeSlots.add(slot);
        }
    }

    public List<TimeSlot> getAvailableSlots() {
        return timeSlots.stream().filter(TimeSlot::isAvailable).toList();
    }
    
    public List<TimeSlot> getTimeSlots() {
        return new ArrayList<>(timeSlots);
    }
    
    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }
    
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }
    
    public PropertyType getType() { return type; }
    public void setType(PropertyType type) { this.type = type; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }
    
    public int getRoomsNumber() { return roomsNumber; }
    public void setRoomsNumber(int bedrooms) { this.roomsNumber = bedrooms; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "Property{ Type ='" + type + "', price=" + price + "', area=" + area +"', Rooms Number='" + roomsNumber+"', Address='" + address+"}";
    }
}