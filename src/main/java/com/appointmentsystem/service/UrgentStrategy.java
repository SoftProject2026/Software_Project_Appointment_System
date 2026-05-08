package com.appointmentsystem.service;

public class UrgentStrategy extends AppointmentStrategy {
	    @Override
	    public String getConfirmationMessage() {
	        return "Your urgent appointment has been booked successfully.";
	    }
}



