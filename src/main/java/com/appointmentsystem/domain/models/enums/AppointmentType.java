package com.appointmentsystem.domain.models.enums;
/**
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
	boolean hasTimeRestriction;
	String startAfte;

AppointmentType(int durationMinutes) {
	this.durationMinutes=durationMinutes;
	
		}
	public int getDurationMinutes() {
		return durationMinutes;
		
	}
	



	}
