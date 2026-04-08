package com.appointmentsystem.domain.models.enums;

public enum AppointmentStatus {
    SCHEDULED("Scheduled"),
    CONFIRMED("Confirmed"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    NO_SHOW("No Show");
    
	/*URGENT("urgent"),
	FOLLOW_UP("follow_up"),
	ASSESSMENT("assessment"),
	VIRTUAL("virtual"),
	IN_PERSON("in-person"),
	INDIVIDUAL("individual"),
	GROUP("group")
   ;*/
	
    private String displayName;
    
    AppointmentStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}