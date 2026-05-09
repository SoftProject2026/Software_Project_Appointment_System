package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.*;

import com.appointmentsystem.domain.models.*;
import com.appointmentsystem.domain.models.enums.*;
import com.appointmentsystem.persistence.*;
import com.appointmentsystem.service.*;

class AppointmentServiceTest {

    private AppointmentRepository appointmentRepository;
    private PropertyRepository propertyRepository;
    private AppointmentService service;

    private Appointment appointment;
    private TimeSlot slot;
    private Company company;

    private AppointmentObserver observer;

    private ByteArrayOutputStream out;

    @BeforeEach
    void setUp() {
        appointmentRepository = mock(AppointmentRepository.class);
        propertyRepository = mock(PropertyRepository.class);

        service = new AppointmentService(appointmentRepository, propertyRepository);

        appointment = mock(Appointment.class);
        slot = new TimeSlot(LocalDateTime.now().plusDays(1));
        company = mock(Company.class);
        observer = mock(AppointmentObserver.class);

        when(company.getId()).thenReturn("c1");

        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

   

    @Test
    void testBookAppointment_success() {
        slot.setAvailable(true);

        Appointment result = service.bookAppointment("p1", "v1", slot, AppointmentType.IN_PERSON);

        assertNotNull(result);
        assertFalse(slot.isAvailable());
        verify(appointmentRepository).save(any());
    }

    @Test
    void testBookAppointment_slotNull() {
        assertThrows(IllegalArgumentException.class,
            () -> service.bookAppointment("p1", "v1", null, AppointmentType.IN_PERSON));
    }

    @Test
    void testBookAppointment_slotNotAvailable() {
        slot.setAvailable(false);

        assertThrows(IllegalStateException.class,
            () -> service.bookAppointment("p1", "v1", slot, AppointmentType.IN_PERSON));
    }


    @Test
    void testCancelAppointment_success() {
        when(appointmentRepository.findById("1")).thenReturn(appointment);
        when(appointment.isFuture()).thenReturn(true);
        when(appointment.getSlot()).thenReturn(slot);

        service.addObserver(observer);

        service.cancelAppointment("1");

        verify(appointment).cancel();
        verify(appointmentRepository).update(appointment);
        verify(observer).update(any(), eq("CANCELLED"));
        assertTrue(slot.isAvailable());
    }

    @Test
    void testCancelAppointment_notFound() {
        when(appointmentRepository.findById("1")).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
            () -> service.cancelAppointment("1"));
    }

    @Test
    void testCancelAppointment_notFuture() {
        when(appointmentRepository.findById("1")).thenReturn(appointment);
        when(appointment.isFuture()).thenReturn(false);

        assertThrows(IllegalStateException.class,
            () -> service.cancelAppointment("1"));
    }


    @Test
    void testModify_success() {
        TimeSlot oldSlot = mock(TimeSlot.class);

        when(appointmentRepository.findById("1")).thenReturn(appointment);
        when(appointment.isFuture()).thenReturn(true);
        when(appointment.getSlot()).thenReturn(oldSlot);
        when(appointment.getStatus()).thenReturn(AppointmentStatus.CONFIRMED);

        TimeSlot newSlot = new TimeSlot(LocalDateTime.now().plusDays(2));

        service.modifyAppointment("1", newSlot);

        verify(oldSlot).setAvailable(true);
        assertFalse(newSlot.isAvailable());
        verify(appointment).setSlot(newSlot);
        verify(appointmentRepository).update(appointment);
    }

    @Test
    void testModify_cancelled() {
        when(appointmentRepository.findById("1")).thenReturn(appointment);
        when(appointment.isFuture()).thenReturn(true);
        when(appointment.getStatus()).thenReturn(AppointmentStatus.CANCELLED);

        assertThrows(IllegalStateException.class,
            () -> service.modifyAppointment("1", slot));
    }

    @Test
    void testModify_slotTaken() {
        slot.setAvailable(false);

        when(appointmentRepository.findById("1")).thenReturn(appointment);
        when(appointment.isFuture()).thenReturn(true);
        when(appointment.getStatus()).thenReturn(AppointmentStatus.CONFIRMED);

        assertThrows(IllegalStateException.class,
            () -> service.modifyAppointment("1", slot));
    }


    @Test
    void testViewCompanyAppointments_found() {
        Appointment a1 = mock(Appointment.class);
        Property p1 = mock(Property.class);

        when(a1.getPropertyId()).thenReturn("p1");
        when(a1.toString()).thenReturn("A1");

        when(propertyRepository.findById("p1")).thenReturn(p1);
        when(p1.getCompanyId()).thenReturn("c1");

        when(appointmentRepository.findAll()).thenReturn(List.of(a1));

        service.viewCompanyAppointments(company);

        assertTrue(out.toString().contains("A1"));
    }

    @Test
    void testViewCompanyAppointments_none() {
        when(appointmentRepository.findAll()).thenReturn(List.of());

        service.viewCompanyAppointments(company);

        assertTrue(out.toString().contains("No appointments"));
    }

    // ================= VIEW ALL =================

    @Test
    void testViewAllAppointments() {
        Appointment a1 = mock(Appointment.class);
        when(a1.toString()).thenReturn("A1");

        when(appointmentRepository.findAll()).thenReturn(List.of(a1));

        service.viewAllAppointments();

        assertTrue(out.toString().contains("0. A1"));
    }


    @Test
    void testObserver_added() {
        service.addObserver(observer);
        assertEquals(1, service.getObservers().size());
    }

    @Test
    void testObserver_notify_onBook() {
        service.addObserver(observer);
        slot.setAvailable(true);

        service.bookAppointment("p1", "v1", slot, AppointmentType.IN_PERSON);

        verify(observer).update(any(), eq("BOOKED"));
    }
}