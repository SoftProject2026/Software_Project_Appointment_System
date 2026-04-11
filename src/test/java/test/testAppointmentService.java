package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.appointmentsystem.persistence.AppointmentRepository;
import com.appointmentsystem.persistence.PropertyRepository;
import com.appointmentsystem.service.AppointmentService;
import com.appointmentsystem.service.PropertyService;

class testAppointmentService {

	private AppointmentRepository mockAppointmentRepository;
	private PropertyService propertyservice;
	private AppointmentService appointmentService;
	private Company mockCompany;
    private Appointment mockAppointment;
    private TimeSlot mockSlot;
    

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mockCompany  = mock(Company.class);
		mockAppointmentRepository = mock(AppointmentRepository.class);
		//mockProperty = mock(Property.class);
		appointmentService=new AppointmentService(mockAppointmentRepository);
		mockAppointment = mock(Appointment.class);
		when(mockCompany.getId()).thenReturn("123");
        when(mockCompany.getCompanyName()).thenReturn("Al-Nabaly");
        when(mockCompany.getUsername()).thenReturn("Al-Nabaly12");
        when(mockCompany.getEmail()).thenReturn("alaa@99.com");
        when(mockCompany.getPassword()).thenReturn("1111");
        when(mockCompany.getCommercialRegister()).thenReturn("cr");
        when(mockCompany.isVerified()).thenReturn(false);
        mockSlot = mock(TimeSlot.class);
        
        
        when(mockAppointment.isFuture()).thenReturn(true);
        when(mockAppointment.getSlot()).thenReturn(mockSlot);
        when(mockAppointmentRepository.findById("app123")).thenReturn(mockAppointment);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetAppointmentById() {
		String appointmentId = "1";
        when(mockAppointmentRepository.findById(appointmentId))
            .thenReturn(mockAppointment);
        
        Appointment result = appointmentService.getAppointmentById(appointmentId);
        assertEquals(mockAppointment, result);
        verify(mockAppointmentRepository, times(1)).findById(appointmentId);
	}
	
	@Test
	void testGetAllAppointments() {
		
		List<Appointment> list = Arrays.asList(new Appointment(), new Appointment());
		when(mockAppointmentRepository.findAll())
	    .thenReturn(list);
		List<Appointment> result = appointmentService.getAllAppointments();
		assertEquals(list, result);
        assertEquals(2, result.size());
        verify(mockAppointmentRepository, times(1)).findAll();
		
	}
	
	@Test
	void testgetAppointmentsByVisitor() {
		String visitorId = "visitor123";
		List<Appointment> list = Arrays.asList(new Appointment(), new Appointment());
		when(mockAppointmentRepository.findByVisitorId(visitorId))
        .thenReturn(list);
		List<Appointment> result = appointmentService.getAppointmentsByVisitor(visitorId);
		assertEquals(list, result);
        assertEquals(2, result.size());
        verify(mockAppointmentRepository, times(1)).findByVisitorId(visitorId);
	}
	
	@Test
	void testCancelAppointment() {
		when(mockAppointment.isFuture()).thenReturn(true);
	    when(mockAppointmentRepository.findById("app123")).thenReturn(mockAppointment);
	    appointmentService.cancelAppointment("app123");
	    verify(mockAppointment).cancel();
	    verify(mockSlot).setAvailable(true);
	    verify(mockAppointmentRepository).update(mockAppointment);
	}
	
	@Test
	void testCancelAppointmentNotFuture() {
		when(mockAppointment.isFuture()).thenReturn(false);
		when(mockAppointmentRepository.findById("app123")).thenReturn(mockAppointment);
	    Exception ex = assertThrows(RuntimeException.class,() -> appointmentService.cancelAppointment("app123"));
        assertEquals("Cannot cancel past appointment", ex.getMessage());
	    verify(mockAppointment,never()).cancel();
	    verify(mockAppointmentRepository,never()).update(any());
	}
	
	@Test
	void testCancelAppointmentNull() {
	    when(mockAppointmentRepository.findById("app123")).thenReturn(null);
	    Exception ex = assertThrows(RuntimeException.class,() -> appointmentService.cancelAppointment("app123"));
        assertEquals("Appointment not found", ex.getMessage());
	    verify(mockAppointment,never()).cancel();
	    verify(mockAppointmentRepository,never()).update(any());
	}
	
	@Test
	void testModifyAppointmentNull() {
		when(mockAppointmentRepository.findById("app123")).thenReturn(null);
	    Exception ex = assertThrows(RuntimeException.class,() -> appointmentService.modifyAppointment("app123",mockSlot));
        assertEquals("Appointment not found", ex.getMessage());
	    verify(mockAppointment,never()).cancel();
	    verify(mockAppointmentRepository,never()).update(any());
	}
	
	@Test
	void testModifyAppointmentNotFuture() {
		when(mockAppointment.isFuture()).thenReturn(false);
		when(mockAppointmentRepository.findById("app123")).thenReturn(mockAppointment);
	    Exception ex = assertThrows(RuntimeException.class,() -> appointmentService.modifyAppointment("app123",mockSlot));
        assertEquals("Past appointment", ex.getMessage());
	    verify(mockAppointment,never()).cancel();
	    verify(mockAppointmentRepository,never()).update(any());
	}
	
	@Test
	void testModifyAppointmentNotAvailableSlot() {
		
		when(mockSlot.isAvailable()).thenReturn(false);
		when(mockAppointmentRepository.findById("app123")).thenReturn(mockAppointment);
	    Exception ex = assertThrows(RuntimeException.class,() -> appointmentService.modifyAppointment("app123",mockSlot));
        assertEquals("Slot taken", ex.getMessage());
	    verify(mockAppointment,never()).cancel();
	    verify(mockAppointmentRepository,never()).update(any());
	}
	
	@Test
	void testModifyAppointment() {
		TimeSlot oldSlot = mock(TimeSlot.class);
	    when(mockAppointment.getSlot()).thenReturn(oldSlot);
	    when(mockAppointment.isFuture()).thenReturn(true);
		when(mockSlot.isAvailable()).thenReturn(true);
	    when(mockAppointmentRepository.findById("app123")).thenReturn(mockAppointment);
	    appointmentService.modifyAppointment("app123",mockSlot);
	    verify(oldSlot).setAvailable(true);
	    verify(mockSlot).setAvailable(false);        
	    verify(mockAppointment).setSlot(mockSlot);
	    verify(mockAppointmentRepository).update(mockAppointment);
	    
	}
	
	

}
