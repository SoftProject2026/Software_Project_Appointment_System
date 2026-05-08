package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.Visitor;
import com.appointmentsystem.persistence.VisitorRepository;

public class EmailNotifier implements AppointmentObserver {

    private EmailService emailService;
    private VisitorRepository visitorRepository;
    public EmailNotifier(EmailService emailService, VisitorRepository visitorRepository) {
        this.emailService = emailService;
        this.visitorRepository = visitorRepository;
    }

    @Override
    public void update(Appointment appointment, String eventType) {

    	String subject = "Appointment " + eventType;
        
        AppointmentStrategy strategy = AppointmentStrategyFactory.getStrategy(appointment.getType());
        
        String body = strategy.getConfirmationMessage();
        
        
        Visitor v = visitorRepository.findById(appointment.getVisitorId());
        if (v == null) return;
        emailService.sendEmail(v.getEmail(), subject, body);
    }
}

