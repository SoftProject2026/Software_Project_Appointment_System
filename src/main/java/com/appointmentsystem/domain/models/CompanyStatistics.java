package com.appointmentsystem.domain.models;

public class CompanyStatistics {
    private int totalProperties;
    private int availableProperties;
    private int soldProperties;
    private int totalAppointments;
    private int upcomingAppointments;
    private int completedAppointments;
    private int todayAppointments;
    
    public CompanyStatistics(int totalProperties, int availableProperties, int soldProperties,
                            int totalAppointments, int upcomingAppointments, 
                            int completedAppointments, int todayAppointments) {
        this.totalProperties = totalProperties;
        this.availableProperties = availableProperties;
        this.soldProperties = soldProperties;
        this.totalAppointments = totalAppointments;
        this.upcomingAppointments = upcomingAppointments;
        this.completedAppointments = completedAppointments;
        this.todayAppointments = todayAppointments;
    }
    
    public int getTotalProperties() { return totalProperties; }
    public int getAvailableProperties() { return availableProperties; }
    public int getSoldProperties() { return soldProperties; }
    public int getTotalAppointments() { return totalAppointments; }
    public int getUpcomingAppointments() { return upcomingAppointments; }
    public int getCompletedAppointments() { return completedAppointments; }
    public int getTodayAppointments() { return todayAppointments; }
    
    @Override
    public String toString() {
        return "CompanyStatistics{totalProperties=" + totalProperties + 
               ", availableProperties=" + availableProperties + 
               ", soldProperties=" + soldProperties + 
               ", totalAppointments=" + totalAppointments + 
               ", upcomingAppointments=" + upcomingAppointments + 
               ", completedAppointments=" + completedAppointments + 
               ", todayAppointments=" + todayAppointments + "}";
    }
}