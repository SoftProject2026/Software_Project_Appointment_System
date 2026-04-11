package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointmentsystem.domain.models.*;
import com.appointmentsystem.domain.models.enums.*;
import com.appointmentsystem.persistence.VisitorRepository;
import com.appointmentsystem.service.*;

class TestVisitorService {

    private VisitorRepository mockVisitorRepository;
    private VisitorService visitorservice;
    private PropertyService propertyservice;
    private AppointmentService appointmentservice;

    private Visitor mockVisitor;
    private Property property;
    private TimeSlot slot;
    private Appointment app;
    private Scanner scanner;

    private TimeSlot newSlot;

    @BeforeEach
    void setUp() {

        mockVisitorRepository = mock(VisitorRepository.class);
        propertyservice = mock(PropertyService.class);
        appointmentservice = mock(AppointmentService.class);
        scanner = mock(Scanner.class);
        when(scanner.nextInt()).thenReturn(0);

        visitorservice = new VisitorService(
                mockVisitorRepository,
                propertyservice,
                appointmentservice,
                scanner
        );

        mockVisitor = mock(Visitor.class);
        property = mock(Property.class);
        slot = mock(TimeSlot.class);
        app = mock(Appointment.class);
        newSlot = mock(TimeSlot.class);

        when(mockVisitor.getId()).thenReturn("123");
        when(mockVisitor.getName()).thenReturn("Alaa");
        when(mockVisitor.getUsername()).thenReturn("Alaa12");
        when(mockVisitor.getEmail()).thenReturn("alaa@99.com");
        when(mockVisitor.getPassword()).thenReturn("1111");
        when(mockVisitor.getPhone()).thenReturn("059885745");

        when(property.getId()).thenReturn("p1");
        when(property.getAvailableSlots()).thenReturn(List.of(slot));

        when(propertyservice.getAllProperties()).thenReturn(List.of(property));
        when(propertyservice.getPropertyById("p1")).thenReturn(property);

        when(app.getId()).thenReturn("a1");
        when(app.getPropertyId()).thenReturn("p1");

        when(appointmentservice.getAppointmentsByVisitor("123"))
                .thenReturn(List.of(app));

        
    }


    @Test
    void testSignupVisitorNotNull() {
        when(mockVisitorRepository.findByUsername("Alaa12")).thenReturn(mockVisitor);
        Exception ex = assertThrows(RuntimeException.class,() -> visitorservice.signup(mockVisitor));
        assertEquals("Username exists", ex.getMessage());
        verify(mockVisitorRepository, never()).save(any());
    }

    @Test
    void testSignupVisitorNull() {
        when(mockVisitorRepository.findByUsername("Alaa12")).thenReturn(null);
        visitorservice.signup(mockVisitor);
        verify(mockVisitorRepository, times(1)).save(mockVisitor);
    }

    @Test
    void testLogin() {
        when(mockVisitorRepository.findByUsername("Alaa12")).thenReturn(mockVisitor);
        when(mockVisitor.getUsername()).thenReturn("Alaa12");
        when(mockVisitor.getPassword()).thenReturn("1111");

        Visitor result = visitorservice.login("Alaa12", "1111");

        assertNotNull(result);
        assertEquals("Alaa12", result.getUsername());
    }

    @Test
    void testLoginWrongPassword() {
        when(mockVisitorRepository.findByUsername("Alaa12")).thenReturn(mockVisitor);
        when(mockVisitor.getPassword()).thenReturn("1111");
        RuntimeException ex = assertThrows(RuntimeException.class,() -> visitorservice.login("Alaa12", "999"));
        assertEquals("Invalid credentials", ex.getMessage());
    }

    @Test
    void testLoginNullVisitor() {
        when(mockVisitorRepository.findByUsername("Alaa12")).thenReturn(null);
        RuntimeException ex = assertThrows(RuntimeException.class,() -> visitorservice.login("Alaa12", "1111"));
        assertEquals("Invalid credentials", ex.getMessage());
    }


    @Test
    void testBookAppointment() {

        Appointment appointment = new Appointment("p1","123",slot,AppointmentType.IN_PERSON);
        when(appointmentservice.bookAppointment("p1", "123", slot, AppointmentType.IN_PERSON)).thenReturn(appointment);
        visitorservice.bookAppointment(mockVisitor);
        verify(appointmentservice).bookAppointment("p1", "123", slot, AppointmentType.IN_PERSON);
    }

    @Test
    void testBookAppointmentNullProperty() {
        when(propertyservice.getAllProperties()).thenReturn(List.of());
        visitorservice.bookAppointment(mockVisitor);
        verify(appointmentservice, never()).bookAppointment(any(), any(), any(), any());
    }

    @Test
    void testBookAppointmentNoSlot() {
        when(property.getAvailableSlots()).thenReturn(List.of());
        when(propertyservice.getAllProperties()).thenReturn(List.of(property));
        visitorservice.bookAppointment(mockVisitor);
        verify(appointmentservice, never()).bookAppointment(any(), any(), any(), any());
    }


    @Test
    void testViewMyAppointmentsNull() {

        when(appointmentservice.getAppointmentsByVisitor("123")).thenReturn(List.of());
        List<Appointment> result =visitorservice.viewMyAppointments(mockVisitor);
        assertTrue(result.isEmpty());
        verify(appointmentservice).getAppointmentsByVisitor("123");
    }

    @Test
    void testViewMyAppointments() {

        Appointment a1 = mock(Appointment.class);
        Appointment a2 = mock(Appointment.class);
        when(appointmentservice.getAppointmentsByVisitor("123")).thenReturn(List.of(a1, a2));
        List<Appointment> result =visitorservice.viewMyAppointments(mockVisitor);
        assertEquals(2, result.size());
        verify(appointmentservice).getAppointmentsByVisitor("123");
    }

    @Test
    void testCancelAppointmentNull() {

        when(appointmentservice.getAppointmentsByVisitor("123")).thenReturn(List.of());
        visitorservice.cancelAppointment(mockVisitor);
        verify(appointmentservice, never()).cancelAppointment(any());
    }

    @Test
    void testCancelAppointment() {
        visitorservice.cancelAppointment(mockVisitor);
        verify(appointmentservice).cancelAppointment("a1");
    }


    @Test
    void testModifyAppointmentNullApp() {
        when(appointmentservice.getAppointmentsByVisitor("123")).thenReturn(List.of());
        visitorservice.modifyAppointment(mockVisitor);
        verify(appointmentservice, never()).modifyAppointment(any(), any());
    }

    @Test
    void testModifyAppointment() {

        Appointment app = mock(Appointment.class);
        when(app.getId()).thenReturn("a1");
        when(app.getPropertyId()).thenReturn("p1");
        when(appointmentservice.getAppointmentsByVisitor("123")).thenReturn(List.of(app));
        when(propertyservice.getPropertyById("p1")).thenReturn(property);
        when(property.getAvailableSlots()).thenReturn(List.of(newSlot));
        when(scanner.nextInt()).thenReturn(0).thenReturn(0);
        visitorservice.modifyAppointment(mockVisitor);
        verify(appointmentservice).modifyAppointment("a1", newSlot);
    }

    @Test
    void testModifyAppointmentNoSlot() {
        when(appointmentservice.getAppointmentsByVisitor("123")).thenReturn(List.of(app));
        when(propertyservice.getPropertyById("p1")).thenReturn(property);
        when(property.getAvailableSlots()).thenReturn(List.of());
        visitorservice.modifyAppointment(mockVisitor);
        verify(scanner, times(1)).nextInt();
        verify(appointmentservice, never()).modifyAppointment(any(), any());
        verifyNoMoreInteractions(scanner);
    }
}