package com.appointmentsystem.domain.models.enums;
/**
 * Appointment types with fixed durations.
 * 
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public enum AppointmentType {
	URGENT(20),
    FOLLOW_UP(15),
    VIRTUAL(30),
    IN_PERSON(30),
    GROUP(60);
	
	int durationMinutes;
	
	

AppointmentType(int durationMinutes) {
	this.durationMinutes=durationMinutes;
	
		}

/**
 * @return the duration in minutes
 */
	public int getDurationMinutes() {
		return durationMinutes;
		
	}
	}
