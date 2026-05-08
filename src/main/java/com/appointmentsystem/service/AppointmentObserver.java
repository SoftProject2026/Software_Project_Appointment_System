package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Appointment;

public interface AppointmentObserver {
	
	void update(Appointment appointment, String eventType);

}
