package com.appointmentsystem.service;

public class VirtualStrategy extends AppointmentStrategy {
    @Override
    public String getConfirmationMessage() {

        return "Your virtual appointment has been booked successfully.";
    }
}


