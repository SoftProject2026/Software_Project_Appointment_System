package com.appointmentsystem.service;

public class FollowUpStrategy extends AppointmentStrategy {
    @Override
    public String getConfirmationMessage() {

        return "Your follow-up appointment has been booked successfully.";
    }
}



