package com.appointmentsystem.domain.models;

import com.appointmentsystem.domain.models.enums.PropertyType;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents a property listed by a company.
 * 
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
    
    /**
     * Constructor with basic property information.
     * 
     * @param id property ID
     * @param companyId owning company ID
     * @param type property type
     * @param price property price
     */
    public Property(String id, String companyId, PropertyType type, double price) {
        this.id = id;
        this.companyId = companyId;
        this.type = type;
        this.price = price;
    }
    
    /**
     * Constructor with complete property information.
     */
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
    
    /**
     * Default constructor for testing.
     */
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

    /**
     * Adds a time slot to the property.
     * 
     * @param slot the time slot to add
     */
    public void addTimeSlot(TimeSlot slot) {
        if (slot != null) {
            timeSlots.add(slot);
        }
    }

    /**
     * @return list of available time slots
     */
    public List<TimeSlot> getAvailableSlots() {
        return timeSlots.stream().filter(TimeSlot::isAvailable).toList();
    }
    
    /**
     * @return copy of all time slots
     */
    public List<TimeSlot> getTimeSlots() {
        return new ArrayList<>(timeSlots);
    }
    
    /**
     * @param timeSlots the new time slots list
     */
    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }
    
    /**
     * @return property ID
     */
    public String getId() { return id; }
    
    /**
     * @param id new property ID
     */
    public void setId(String id) { this.id = id; }
    
    /**
     * @return company ID
     */
    public String getCompanyId() { return companyId; }
    
    /**
     * @param companyId new company ID
     */
    public void setCompanyId(String companyId) { this.companyId = companyId; }
    
    /**
     * @return property type
     */
    public PropertyType getType() { return type; }
    
    /**
     * @param type new property type
     */
    public void setType(PropertyType type) { this.type = type; }
    
    /**
     * @return property price
     */
    public double getPrice() { return price; }
    
    /**
     * @param price new price
     */
    public void setPrice(double price) { this.price = price; }
    
    /**
     * @return property area
     */
    public double getArea() { return area; }
    
    /**
     * @param area new area
     */
    public void setArea(double area) { this.area = area; }
    
    /**
     * @return number of rooms
     */
    public int getRoomsNumber() { return roomsNumber; }
    
    /**
     * @param bedrooms new number of rooms
     */
    public void setRoomsNumber(int bedrooms) { this.roomsNumber = bedrooms; }
    
    /**
     * @return property address
     */
    public String getAddress() { return address; }
    
    /**
     * @param address new address
     */
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "Property{id='" + id + "', companyId='" + companyId + "', Type ='" + type + 
               "', price=" + price + "', area=" + area + "', Rooms Number='" + roomsNumber + 
               "', Address='" + address + "'}";
    }
}