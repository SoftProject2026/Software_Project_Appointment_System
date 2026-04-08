package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointmentsystem.domain.models.Admin;
import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.enums.AppointmentStatus;
import com.appointmentsystem.persistence.AdminRepository;
import com.appointmentsystem.persistence.AppointmentRepository;
import com.appointmentsystem.service.AdminService;

class TestAdminService {
	
	private AdminService adminService;
    private AdminRepository adminRepository;
    private AppointmentRepository appointmentRepository;
    private Admin mockAdmin;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		
		adminRepository = mock(AdminRepository.class);
		appointmentRepository = mock(AppointmentRepository.class);
		mockAdmin =mock(Admin.class);
		adminService = new AdminService(adminRepository, appointmentRepository);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testLogout() {
		when(adminRepository.getAdmin()).thenReturn(mockAdmin);
		adminService.logout();
		verify(mockAdmin).logout();
		verify(adminRepository).update(mockAdmin);
		
	}
	@Test
	void testLogoutNullAdmin() {
		when(adminRepository.getAdmin()).thenReturn(null);
		mockAdmin.logout();
		
		verify(adminRepository,never()).update(any());
		
	}
	
	@Test
	void testGetAdmin() {
		when(adminRepository.getAdmin()).thenReturn(mockAdmin);
		Admin result=adminRepository.getAdmin();
		assertNotNull(result);
		assertEquals(mockAdmin,result);
		
	}
	
	@Test
	void testGetAllAppointmentsAdminNull() {
		when(adminRepository.getAdmin()).thenReturn(null);
		
		Exception ex=assertThrows(IllegalStateException.class, () -> {
	        adminService.getAllAppointments();
		});
		
		assertEquals("Admin not found",ex.getMessage());
		
	}
	
	@Test
	void testGetAllAppointments() {
		Appointment appointment1=mock(Appointment.class);
		Appointment appointment2=mock(Appointment.class);
		
		when(appointment1.getId()).thenReturn("a1");
        when(appointment1.getPropertyId()).thenReturn("p1");
        when(appointment1.getVisitorId()).thenReturn("v1");
        when(appointment1.getCompanyId()).thenReturn("c1");

        LocalDateTime start = LocalDateTime.of(2026, 4, 8, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 8, 11, 0);

        when(appointment1.getStartTime()).thenReturn(start);
        when(appointment1.getEndTime()).thenReturn(end);
        when(appointment1.getStatus()).thenReturn(AppointmentStatus.CONFIRMED);
        when(appointment1.didVisitorAttend()).thenReturn(false);
        
        when(appointment2.getId()).thenReturn("a2");
        when(appointment2.getPropertyId()).thenReturn("p2");
        when(appointment2.getVisitorId()).thenReturn("v2");
        when(appointment2.getCompanyId()).thenReturn("c2");


        when(appointment2.getStartTime()).thenReturn(start);
        when(appointment2.getEndTime()).thenReturn(end);
        when(appointment2.getStatus()).thenReturn(AppointmentStatus.CONFIRMED);
        when(appointment2.didVisitorAttend()).thenReturn(false);
		List<Appointment> appointments = List.of(appointment1,appointment2);
		when(adminRepository.getAdmin()).thenReturn(mock(Admin.class));
	    when(appointmentRepository.findAll()).thenReturn(appointments);

		List<Appointment> result = adminService.getAllAppointments();
		assertEquals(2, result.size());
		verify(appointmentRepository, times(1)).findAll();
		
		
	}
	

}
