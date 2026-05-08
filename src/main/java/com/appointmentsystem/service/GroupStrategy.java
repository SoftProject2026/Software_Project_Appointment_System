package com.appointmentsystem.service;

public class GroupStrategy implements AppointmentStrategy {
    @Override
    public String getConfirmationMessage() {

        return "Your group appointment has been booked successfully.";
    }
}


