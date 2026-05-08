package com.appointmentsystem.service;

public class InPersonStrategy extends AppointmentStrategy {
    @Override
    public String getConfirmationMessage() {

        return "Your in Person appointment has been booked successfully.";
    }
}


