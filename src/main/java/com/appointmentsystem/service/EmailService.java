package com.appointmentsystem.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService {
    private final String username;
    private final String password;
    private Transport transport;

    public EmailService(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
   
    public EmailService(String username, String password, Transport transport) {
        this.username = username;
        this.password = password;
        this.transport = transport;
    }

    public void sendEmail(String to, String subject, String body) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            if (transport != null) {
                transport.sendMessage(message, message.getAllRecipients());
            } else {
                Transport.send(message);
            }
            
            System.out.println("Email sent successfully to " + to);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}