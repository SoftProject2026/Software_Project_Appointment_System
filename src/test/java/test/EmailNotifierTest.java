package test;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.Visitor;
import com.appointmentsystem.domain.models.enums.AppointmentType;
import com.appointmentsystem.persistence.VisitorRepository;
import com.appointmentsystem.service.EmailNotifier;
import com.appointmentsystem.service.EmailService;

public class EmailNotifierTest {

    @Test
    void testUpdate() {

        EmailService mockEmailService =
                mock(EmailService.class);

        VisitorRepository mockVisitorRepo =
                mock(VisitorRepository.class);

        Appointment mockAppointment =
                mock(Appointment.class);

        Visitor mockVisitor =
                mock(Visitor.class);

        when(mockAppointment.getType())
                .thenReturn(AppointmentType.GROUP);

        when(mockAppointment.getVisitorId())
                .thenReturn("v1");

        when(mockVisitorRepo.findById("v1"))
                .thenReturn(mockVisitor);

        when(mockVisitor.getEmail())
                .thenReturn("test@gmail.com");

        EmailNotifier notifier =
                new EmailNotifier(
                        mockEmailService,
                        mockVisitorRepo
                );

        notifier.update(mockAppointment, "BOOKED");

        verify(mockEmailService).sendEmail(
                eq("test@gmail.com"),
                eq("Appointment BOOKED"),
                eq("Your group appointment has been booked successfully.")
        );
    }
}