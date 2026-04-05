package com.appointmentsystem.domain.models;

import com.appointmentsystem.domain.models.enums.PropertyStatus;
import com.appointmentsystem.domain.models.enums.PropertyType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Property {
    private String id;
    private String companyId;
    private String title;
    private String description;
    private PropertyType type;
    private PropertyStatus status;
    private double price;
    private double area;
    private int bedrooms;
    private int bathrooms;
    private String address;
    private List<String> images;
    private LocalDateTime createdAt;
    private int viewCount;
    private boolean isAvailable;
    
    public Property(String id, String companyId, String title, PropertyType type, double price) {
        this.id = id;
        this.companyId = companyId;
        this.title = title;
        this.type = type;
        this.price = price;
        this.status = PropertyStatus.AVAILABLE;
        this.createdAt = LocalDateTime.now();
        this.images = new ArrayList<>();
        this.viewCount = 0;
        this.isAvailable = true;
    }
    
    public Property(String id, String companyId, String title, String description, PropertyType type, 
                    double price, double area, int bedrooms, int bathrooms, String address) {
        this.id = id;
        this.companyId = companyId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.price = price;
        this.area = area;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.address = address;
        this.status = PropertyStatus.AVAILABLE;
        this.createdAt = LocalDateTime.now();
        this.images = new ArrayList<>();
        this.viewCount = 0;
        this.isAvailable = true;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public PropertyType getType() { return type; }
    public void setType(PropertyType type) { this.type = type; }
    
    public PropertyStatus getStatus() { return status; }
    public void setStatus(PropertyStatus status) { this.status = status; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }
    
    public int getBedrooms() { return bedrooms; }
    public void setBedrooms(int bedrooms) { this.bedrooms = bedrooms; }
    
    public int getBathrooms() { return bathrooms; }
    public void setBathrooms(int bathrooms) { this.bathrooms = bathrooms; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public List<String> getImages() { return images; }
    public void addImage(String image) { this.images.add(image); }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    public int getViewCount() { return viewCount; }
    public void incrementViewCount() { this.viewCount++; }
    
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    
    public void markAsSold() {
        this.status = PropertyStatus.SOLD;
        this.isAvailable = false;
    }
    
    @Override
    public String toString() {
        return "Property{id='" + id + "', title='" + title + "', companyId='" + companyId + "', price=" + price + ", status=" + status + "}";
    }
}