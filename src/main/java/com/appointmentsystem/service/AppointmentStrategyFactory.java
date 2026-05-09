package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.enums.AppointmentType;

public class AppointmentStrategyFactory {
	


    public static AppointmentStrategy
    getStrategy(AppointmentType type) {

        switch(type) {

            case URGENT:
                return new UrgentStrategy();

            case FOLLOW_UP:
                return new FollowUpStrategy();

            case VIRTUAL:
                return new VirtualStrategy();

            case IN_PERSON:
                return new InPersonStrategy();

            case GROUP:
                return new GroupStrategy();

            default:
                throw new IllegalArgumentException("Invalid type");
        }
    }
}


