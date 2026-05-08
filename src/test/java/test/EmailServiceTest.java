package test;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.appointmentsystem.service.EmailService;

public class EmailServiceTest {

    @Test
    void testSendEmailCalled() {
        EmailService mockEmailService =
                mock(EmailService.class);

       
        mockEmailService.sendEmail(
                "test@gmail.com",
                "Hello",
                "Test message"
        );

        verify(mockEmailService).sendEmail(
                "test@gmail.com",
                "Hello",
                "Test message"
        );
    }
}
