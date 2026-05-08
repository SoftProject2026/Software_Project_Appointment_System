package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.Company;
import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.domain.models.TimeSlot;
import com.appointmentsystem.domain.models.Visitor;
import com.appointmentsystem.persistence.CompanyRepository;
import com.appointmentsystem.persistence.PropertyRepository;
import com.appointmentsystem.persistence.VisitorRepository;
import com.appointmentsystem.service.AppointmentService;
import com.appointmentsystem.service.CompanyService;
import com.appointmentsystem.service.FollowUpStrategy;
import com.appointmentsystem.service.GroupStrategy;
import com.appointmentsystem.service.InPersonStrategy;
import com.appointmentsystem.service.PropertyService;
import com.appointmentsystem.service.UrgentStrategy;
import com.appointmentsystem.service.VirtualStrategy;
import com.appointmentsystem.service.VisitorService;
import com.appointmentsystem.service.AppointmentStrategy;
public class AppointmentStrategyTest {

    @Test
    void testGroup() {

        AppointmentStrategy s = new GroupStrategy();

        assertEquals(
            "Your group appointment has been booked successfully.",
            s.getConfirmationMessage()
        );
    }

    @Test
    void testVirtual() {

        AppointmentStrategy s = new VirtualStrategy();

        assertEquals(
            "Your virtual appointment has been booked successfully.",
            s.getConfirmationMessage()
        );
    }

    @Test
    void testUrgent() {

        AppointmentStrategy s = new UrgentStrategy();

        assertEquals(
            "Your urgent appointment has been booked successfully.",
            s.getConfirmationMessage()
        );
    }

    @Test
    void testInPerson() {

        AppointmentStrategy s = new InPersonStrategy();

        assertEquals(
            "Your in Person appointment has been booked successfully.",
            s.getConfirmationMessage()
        );
    }

    @Test
    void testFollowUp() {

        AppointmentStrategy s = new FollowUpStrategy();

        assertEquals(
            "Your follow-up appointment has been booked successfully.",
            s.getConfirmationMessage()
        );
    }
}