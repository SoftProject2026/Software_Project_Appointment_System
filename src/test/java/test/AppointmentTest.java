package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.TimeSlot;
import com.appointmentsystem.domain.models.enums.AppointmentStatus;
import com.appointmentsystem.domain.models.enums.AppointmentType;

class AppointmentTest {
    
    private Appointment appointment;
    private TimeSlot mockTimeSlot;
    
    @BeforeEach
    void setUp() {
        mockTimeSlot = mock(TimeSlot.class);
        appointment = new Appointment("property-123", "visitor-456", mockTimeSlot, AppointmentType.IN_PERSON);
    }
  
    @Test
    void testConstructor_ShouldCreateAppointmentCorrectly() {
        assertNotNull(appointment.getId());
        assertEquals("property-123", appointment.getPropertyId());
        assertEquals("visitor-456", appointment.getVisitorId());
        assertEquals(AppointmentType.IN_PERSON, appointment.getType());
        assertEquals(AppointmentStatus.CONFIRMED, appointment.getStatus());
        assertEquals(mockTimeSlot, appointment.getSlot());
    }
    
    @Test
    void testSetSlot_ShouldUpdateTimeSlot() {
        TimeSlot newSlot = mock(TimeSlot.class);
        appointment.setSlot(newSlot);
        assertEquals(newSlot, appointment.getSlot());
    }
    
    @Test
    void testCancel_ShouldChangeStatusToCancelled() {
        appointment.cancel();
        assertEquals(AppointmentStatus.CANCELLED, appointment.getStatus());
    }
    
    @Test
    void testIsFuture_WhenSlotInFuture_ShouldReturnTrue() {
        when(mockTimeSlot.getStartTime()).thenReturn(LocalDateTime.now().plusDays(1));
        assertTrue(appointment.isFuture());
    }
    
    @Test
    void testIsFuture_WhenSlotInPast_ShouldReturnFalse() {
        when(mockTimeSlot.getStartTime()).thenReturn(LocalDateTime.now().minusDays(1));
        assertFalse(appointment.isFuture());
    }
    
    @Test
    void testToString_ShouldReturnFormattedString() {
        when(mockTimeSlot.toString()).thenReturn("2024-01-01 10:00 - 2024-01-01 11:00");
        String result = appointment.toString();
        
        assertTrue(result.contains("Date: 2024-01-01 10:00 - 2024-01-01 11:00"));
        assertTrue(result.contains("IN_PERSON"));
        assertTrue(result.contains("CONFIRMED"));
    }
    
    @Test
    void testDefaultConstructor_ShouldCreateEmptyAppointment() {
        Appointment emptyAppointment = new Appointment();
        assertNull(emptyAppointment.getId());
        assertNull(emptyAppointment.getPropertyId());
    }
    
    @Test
    void testConstructor_WithVirtualType_ShouldWork() {
        Appointment virtualApp = new Appointment("p1", "v1", mockTimeSlot, AppointmentType.VIRTUAL);
        assertEquals(AppointmentType.VIRTUAL, virtualApp.getType());
    }
    
    @Test
    void testMultipleAppointments_ShouldHaveDifferentIds() {
        Appointment app1 = new Appointment("p1", "v1", mockTimeSlot, AppointmentType.IN_PERSON);
        Appointment app2 = new Appointment("p1", "v1", mockTimeSlot, AppointmentType.IN_PERSON);
        
        assertNotEquals(app1.getId(), app2.getId());
    }
}
