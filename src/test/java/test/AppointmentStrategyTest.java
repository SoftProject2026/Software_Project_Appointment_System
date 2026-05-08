package test;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;


import com.appointmentsystem.service.FollowUpStrategy;
import com.appointmentsystem.service.GroupStrategy;
import com.appointmentsystem.service.InPersonStrategy;

import com.appointmentsystem.service.UrgentStrategy;
import com.appointmentsystem.service.VirtualStrategy;

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