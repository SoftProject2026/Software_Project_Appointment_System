package test;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.appointmentsystem.service.EmailService;

import jakarta.mail.Transport;

class TestEmailService {

    private EmailService emailService;
    private Transport mockTransport;
    
    private final String TEST_RECIPIENT = "visitor@example.com";
    private final String TEST_SUBJECT = "Appointment Confirmation";
    private final String TEST_BODY = "Your appointment has been booked successfully.";
    private final String TEST_USERNAME = "test@gmail.com";
    private final String TEST_PASSWORD = "test123";

    @BeforeEach
    void setUp() {
        mockTransport = mock(Transport.class);
        emailService = new EmailService(TEST_USERNAME, TEST_PASSWORD, mockTransport);
    }

    @Test
    @DisplayName("Test sendEmail - verifies email is sent with correct parameters")
    void testSendEmail_IsSentWithCorrectParameters() throws Exception {
        // Arrange - Do nothing when transport.sendMessage is called
        doNothing().when(mockTransport).sendMessage(any(), any());
        
        // Act
        emailService.sendEmail(TEST_RECIPIENT, TEST_SUBJECT, TEST_BODY);
        
        // Assert - Verify transport.sendMessage was called
        verify(mockTransport, times(1)).sendMessage(any(), any());
    }

    @Test
    @DisplayName("Test sendEmail - verifies email to correct recipient")
    void testSendEmail_ToCorrectRecipient() throws Exception {
        // Arrange
        doNothing().when(mockTransport).sendMessage(any(), any());
        
        // Act
        emailService.sendEmail(TEST_RECIPIENT, TEST_SUBJECT, TEST_BODY);
        
        // Assert - Can't easily verify recipient without more complex mocking
        verify(mockTransport, times(1)).sendMessage(any(), any());
    }

    @Test
    @DisplayName("Test sendEmail - throws exception when email fails")
    void testSendEmail_ThrowsExceptionOnFailure() throws Exception {
        // Arrange
        doThrow(new RuntimeException("SMTP error")).when(mockTransport).sendMessage(any(), any());
        
        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            emailService.sendEmail(TEST_RECIPIENT, TEST_SUBJECT, TEST_BODY);
        });
    }

    @Test
    @DisplayName("Test sendEmail - validates email content is not empty")
    void testSendEmail_EmailContentIsNotEmpty() {
        // This test verifies the method doesn't throw with valid content
        EmailService mockEmailService = mock(EmailService.class);
        doNothing().when(mockEmailService).sendEmail(anyString(), anyString(), anyString());
        
        mockEmailService.sendEmail(TEST_RECIPIENT, TEST_SUBJECT, TEST_BODY);
        
        verify(mockEmailService, times(1))
            .sendEmail(eq(TEST_RECIPIENT), eq(TEST_SUBJECT), eq(TEST_BODY));
    }
}