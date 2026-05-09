package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.Company;
import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.domain.models.TimeSlot;
import com.appointmentsystem.domain.models.Visitor;
import com.appointmentsystem.domain.models.enums.AppointmentType;
import com.appointmentsystem.persistence.AppointmentRepository;
import com.appointmentsystem.persistence.PropertyRepository;
import com.appointmentsystem.persistence.VisitorRepository;
import com.appointmentsystem.service.AppointmentObserver;
import com.appointmentsystem.service.AppointmentService;
import com.appointmentsystem.service.AppointmentStrategyFactory;
import com.appointmentsystem.service.EmailNotifier;
import com.appointmentsystem.service.EmailService;


/**
 * Test class for AppointmentService.
 * 
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * @version 1.0
 */
class testAppointmentService {

    private AppointmentRepository mockAppointmentRepository;
    private PropertyRepository mockPropertyRepository;
    
    private AppointmentService appointmentService;
    private Company mockCompany;
    private Appointment mockAppointment;
    private TimeSlot slot;
    private VisitorRepository mockVisitorRepository;
    private EmailService mockEmailService;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private AppointmentObserver observer;
    private EmailNotifier notifier;



    @BeforeEach
    void setUp() throws Exception {
        
        mockCompany = mock(Company.class);
        mockAppointmentRepository = mock(AppointmentRepository.class);
        mockPropertyRepository = mock(PropertyRepository.class);
        mockVisitorRepository = mock(VisitorRepository.class);
        mockEmailService = mock(EmailService.class);
        mockAppointment = mock(Appointment.class);
        slot = new TimeSlot(LocalDateTime.now().plusDays(1));
        observer = mock(AppointmentObserver.class);
        notifier = new EmailNotifier(mockEmailService, mockVisitorRepository);
        
        
        appointmentService = new AppointmentService(
            mockAppointmentRepository,
            mockPropertyRepository
        );
        
       
        when(mockCompany.getId()).thenReturn("123");
        when(mockCompany.getCompanyName()).thenReturn("Al-Nabaly");
        when(mockCompany.getUsername()).thenReturn("Al-Nabaly12");
        when(mockCompany.getEmail()).thenReturn("alaa@99.com");
        when(mockCompany.getPassword()).thenReturn("1111");
        when(mockCompany.getCommercialRegister()).thenReturn("cr");
        when(mockCompany.isVerified()).thenReturn(false);
        
        
        when(mockAppointment.isFuture()).thenReturn(true);
        when(mockAppointment.getSlot()).thenReturn(slot);
        when(mockAppointmentRepository.findById("app123")).thenReturn(mockAppointment);
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        
        System.setOut(new PrintStream(outputStream));
        slot.setAvailable(true);
        when(mockAppointment.getType())
        .thenReturn(AppointmentType.IN_PERSON);
    }
    
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }


    /**
     * Tests get appointment by ID.
     */
    /**
     * Tests get all appointments.
     */
    @Test
    void testGetAllAppointments1() {
        
        Appointment mockApp1 = mock(Appointment.class);
        Appointment mockApp2 = mock(Appointment.class);
        List<Appointment> list = Arrays.asList(mockApp1, mockApp2);
        
        when(mockAppointmentRepository.findAll()).thenReturn(list);
        
        
        List<Appointment> result = appointmentService.getAllAppointments();
        
        
        assertEquals(2, result.size());
        verify(mockAppointmentRepository, times(1)).findAll();
    }
    

 

    /**
     * Tests get appointments by visitor.
     */
    @Test
    void testgetAppointmentsByVisitor() {
        String visitorId = "visitor123";
        List<Appointment> list = Arrays.asList(new Appointment(), new Appointment());
        when(mockAppointmentRepository.findByVisitorId(visitorId)).thenReturn(list);
        List<Appointment> result = appointmentService.getAppointmentsByVisitor(visitorId);
        assertEquals(list, result);
        assertEquals(2, result.size());
        verify(mockAppointmentRepository, times(1)).findByVisitorId(visitorId);
    }
    
    /**
     * Tests cancel appointment successfully.
     */
    @Test
    void testCancelAppointment() {
        when(mockAppointment.isFuture()).thenReturn(true);
        when(mockAppointmentRepository.findById("app123")).thenReturn(mockAppointment);
        appointmentService.cancelAppointment("app123");
        verify(mockAppointment).cancel();
        assertTrue(slot.isAvailable());    
        verify(mockAppointmentRepository).update(mockAppointment);
    }
    
    /**
     * Tests cancel past appointment - should fail.
     */
    @Test
    void testCancelAppointmentNotFuture() {
        when(mockAppointment.isFuture()).thenReturn(false);
        when(mockAppointmentRepository.findById("app123")).thenReturn(mockAppointment);
        Exception ex = assertThrows(RuntimeException.class, () -> 
            appointmentService.cancelAppointment("app123"));
        assertEquals("Cannot cancel past appointment", ex.getMessage());
        verify(mockAppointment, never()).cancel();
        verify(mockAppointmentRepository, never()).update(any());
    }
    
    /**
     * Tests cancel null appointment - should fail.
     */
    @Test
    void testCancelAppointmentNull() {
        when(mockAppointmentRepository.findById("app123")).thenReturn(null);
        Exception ex = assertThrows(RuntimeException.class, () -> 
            appointmentService.cancelAppointment("app123"));
        assertEquals("Appointment not found", ex.getMessage());
        verify(mockAppointment, never()).cancel();
        verify(mockAppointmentRepository, never()).update(any());
    }
    
    /**
     * Tests modify null appointment - should fail.
     */
    @Test
    void testModifyAppointmentNull() {
        when(mockAppointmentRepository.findById("app123")).thenReturn(null);
        Exception ex = assertThrows(RuntimeException.class, () -> 
            appointmentService.modifyAppointment("app123", slot));
        assertEquals("Appointment not found", ex.getMessage());
        verify(mockAppointment, never()).cancel();
        verify(mockAppointmentRepository, never()).update(any());
    }
    
    /**
     * Tests modify past appointment - should fail.
     */
    @Test
    void testModifyAppointmentNotFuture() {
        when(mockAppointment.isFuture()).thenReturn(false);
        when(mockAppointmentRepository.findById("app123")).thenReturn(mockAppointment);
        Exception ex = assertThrows(RuntimeException.class, () -> 
            appointmentService.modifyAppointment("app123", slot));
        assertEquals("Past appointment", ex.getMessage());
        verify(mockAppointment, never()).cancel();
        verify(mockAppointmentRepository, never()).update(any());
    }
    
    /**
     * Tests modify with unavailable slot - should fail.
     */
    @Test
    void testModifyAppointmentNotAvailableSlot() {
    	slot.setAvailable(false);
        when(mockAppointmentRepository.findById("app123")).thenReturn(mockAppointment);
        Exception ex = assertThrows(RuntimeException.class, () -> 
            appointmentService.modifyAppointment("app123", slot));
        assertEquals("Slot taken", ex.getMessage());
        verify(mockAppointment, never()).cancel();
        verify(mockAppointmentRepository, never()).update(any());
    }
    
    /**
     * Tests modify appointment successfully.
     */
    @Test
    void testModifyAppointment() {
        TimeSlot oldSlot = mock(TimeSlot.class);
        when(mockAppointment.getSlot()).thenReturn(oldSlot);
        when(mockAppointment.isFuture()).thenReturn(true);
        //slot.setAvailable(true);
        when(mockAppointmentRepository.findById("app123")).thenReturn(mockAppointment);
        appointmentService.modifyAppointment("app123", slot);
        verify(oldSlot).setAvailable(true);
        assertFalse(slot.isAvailable());        verify(mockAppointment).setSlot(slot);
        verify(mockAppointmentRepository).update(mockAppointment);
    }
    
    
    /**
     * Tests email sent on booking.
     */
    @Test
    void testEmailSentOnBooking() {
        
        EmailService mockEmailService = mock(EmailService.class);
        VisitorRepository mockVisitorRepo = mock(VisitorRepository.class);
        Visitor mockVisitor = mock(Visitor.class);

        //appointmentService.setEmailService(mockEmailService);
        //appointmentService.setVisitorRepository(mockVisitorRepo);
        appointmentService.addObserver(
        	    new EmailNotifier(mockEmailService, mockVisitorRepo)
        	);
        
        when(mockVisitor.getEmail()).thenReturn("visitor@example.com");
        when(mockVisitorRepo.findById("v1")).thenReturn(mockVisitor);

        TimeSlot slot = new TimeSlot(LocalDateTime.now().plusDays(1));

        
        appointmentService.bookAppointment("p1", "v1", slot, AppointmentType.IN_PERSON);

        
        verify(mockEmailService, times(1))
            .sendEmail(eq("visitor@example.com"), eq("Appointment BOOKED"), any());
    }
    
    /////
    ///
    
    
   
    
    /**
     * Tests get appointment by ID - found
     */
    @Test
    void testGetAppointmentById_Found() {
        when(mockAppointmentRepository.findById("app123")).thenReturn(mockAppointment);
        
        Appointment result = appointmentService.getAppointmentById("app123");
        
        assertNotNull(result);
        assertEquals(mockAppointment, result);
        verify(mockAppointmentRepository).findById("app123");
    }
    
    /**
     * Tests get appointment by ID - not found
     */
    @Test
    void testGetAppointmentById_NotFound() {
        when(mockAppointmentRepository.findById("app999")).thenReturn(null);
        
        Appointment result = appointmentService.getAppointmentById("app999");
        
        assertNull(result);
        verify(mockAppointmentRepository).findById("app999");
    }

    
    
    /**
     * Tests get upcoming appointments - with future confirmed appointments
     */
    @Test
    void testGetUpcomingAppointments_WithFutureAndConfirmed() {
        Appointment futureConfirmed = mock(Appointment.class);
        when(futureConfirmed.isFuture()).thenReturn(true);
        when(futureConfirmed.getStatus()).thenReturn(com.appointmentsystem.domain.models.enums.AppointmentStatus.CONFIRMED);
        
        Appointment futureCancelled = mock(Appointment.class);
        when(futureCancelled.isFuture()).thenReturn(true);
        when(futureCancelled.getStatus()).thenReturn(com.appointmentsystem.domain.models.enums.AppointmentStatus.CANCELLED);
        
        Appointment pastConfirmed = mock(Appointment.class);
        when(pastConfirmed.isFuture()).thenReturn(false);
        when(pastConfirmed.getStatus()).thenReturn(com.appointmentsystem.domain.models.enums.AppointmentStatus.CONFIRMED);
        
        List<Appointment> allAppointments = Arrays.asList(futureConfirmed, futureCancelled, pastConfirmed);
        when(mockAppointmentRepository.findAll()).thenReturn(allAppointments);
        
        List<Appointment> result = appointmentService.getUpcomingAppointments();
        
        assertEquals(1, result.size());
        assertEquals(futureConfirmed, result.get(0));
        verify(mockAppointmentRepository).findAll();
    }
    
    /**
     * Tests get upcoming appointments - empty list
     */
    @Test
    void testGetUpcomingAppointments_EmptyList() {
        when(mockAppointmentRepository.findAll()).thenReturn(Arrays.asList());
        
        List<Appointment> result = appointmentService.getUpcomingAppointments();
        
        assertTrue(result.isEmpty());
        verify(mockAppointmentRepository).findAll();
    }
    
    /**
     * Tests get upcoming appointments - no future appointments
     */
    @Test
    void testGetUpcomingAppointments_NoFutureAppointments() {
        Appointment pastAppointment = mock(Appointment.class);
        when(pastAppointment.isFuture()).thenReturn(false);
        when(pastAppointment.getStatus()).thenReturn(com.appointmentsystem.domain.models.enums.AppointmentStatus.CONFIRMED);
        
        when(mockAppointmentRepository.findAll()).thenReturn(Arrays.asList(pastAppointment));
        
        List<Appointment> result = appointmentService.getUpcomingAppointments();
        
        assertTrue(result.isEmpty());
    }

    
    
    /**
     * Tests book appointment with null slot - should fail
     */
    @Test
    void testBookAppointment_SlotNull() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            appointmentService.bookAppointment("p1", "v1", null, AppointmentType.IN_PERSON));
        
        assertEquals("Invalid slot", ex.getMessage());
        verify(mockAppointmentRepository, never()).save(any());
    }
    
    /**
     * Tests book appointment with unavailable slot - should fail
     */
    @Test
    void testBookAppointment_SlotNotAvailable() {
    	slot.setAvailable(false);
        
        Exception ex = assertThrows(IllegalStateException.class, () ->
            appointmentService.bookAppointment("p1", "v1", slot, AppointmentType.IN_PERSON));
        
        assertEquals("Slot already booked", ex.getMessage());
        verify(mockAppointmentRepository, never()).save(any());
    }

    
    
    /**
     * Tests modify cancelled appointment - should fail
     */
    @Test
    void testModifyAppointment_CancelledAppointment() {
        when(mockAppointmentRepository.findById("app123")).thenReturn(mockAppointment);
        when(mockAppointment.isFuture()).thenReturn(true);
        //slot.setAvailable(true);
        when(mockAppointment.getStatus()).thenReturn(com.appointmentsystem.domain.models.enums.AppointmentStatus.CANCELLED);
        
        Exception ex = assertThrows(RuntimeException.class, () ->
            appointmentService.modifyAppointment("app123", slot));
        
        assertEquals("Cannot modify cancelled appointment", ex.getMessage());
        verify(mockAppointment, never()).setSlot(any());
    }

    
    
    /**
     * Tests cancel appointment that throws exception when cancelling
     */
    @Test
    void testCancelAppointment_WithException() {
        when(mockAppointmentRepository.findById("app123")).thenReturn(mockAppointment);
        when(mockAppointment.isFuture()).thenReturn(true);
        doThrow(new IllegalStateException("Already cancelled")).when(mockAppointment).cancel();
        
        assertThrows(IllegalStateException.class, () ->
            appointmentService.cancelAppointment("app123"));
    }
    


    /**
     * Tests view company appointments - when there are appointments for the company
     */
    @Test
    void testViewCompanyAppointments_WithAppointments() {
        // Arrange
        Appointment appointment1 = mock(Appointment.class);
        Appointment appointment2 = mock(Appointment.class);
        
        when(appointment1.getPropertyId()).thenReturn("property1");
        when(appointment2.getPropertyId()).thenReturn("property2");
        when(appointment1.toString()).thenReturn("Appointment 1");
        when(appointment2.toString()).thenReturn("Appointment 2");
        
        Property property1 = mock(Property.class);
        Property property2 = mock(Property.class);
        when(property1.getCompanyId()).thenReturn("123");  
        when(property2.getCompanyId()).thenReturn("123");
        
        when(mockAppointmentRepository.findAll()).thenReturn(Arrays.asList(appointment1, appointment2));
        when(mockPropertyRepository.findById("property1")).thenReturn(property1);
        when(mockPropertyRepository.findById("property2")).thenReturn(property2);
        
        // Capture System.out
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(outputStream));
        
        try {
            // Act
            appointmentService.viewCompanyAppointments(mockCompany);
            
            // Assert
            String output = outputStream.toString();
            assertTrue(output.contains("Appointment 1"));
            assertTrue(output.contains("Appointment 2"));
            assertFalse(output.contains("No appointments"));
            
            verify(mockAppointmentRepository).findAll();
            verify(mockPropertyRepository).findById("property1");
            verify(mockPropertyRepository).findById("property2");
        } finally {
            System.setOut(originalOut);
        }
    }

    /**
     * Tests view company appointments - when no appointments for this company
     */
    @Test
    void testViewCompanyAppointments_NoAppointmentsForCompany() {
        // Arrange
        Appointment appointment = mock(Appointment.class);
        when(appointment.getPropertyId()).thenReturn("property1");
        
        Property property = mock(Property.class);
        when(property.getCompanyId()).thenReturn("differentCompany");
        
        when(mockAppointmentRepository.findAll()).thenReturn(Arrays.asList(appointment));
        when(mockPropertyRepository.findById("property1")).thenReturn(property);
        
        // Capture System.out
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(outputStream));
        
        try {
            // Act
            appointmentService.viewCompanyAppointments(mockCompany);
            
            // Assert
            String output = outputStream.toString();
            assertTrue(output.contains("No appointments"));
            verify(mockAppointmentRepository).findAll();
        } finally {
            System.setOut(originalOut);
        }
    }

    /**
     * Tests view company appointments - when no appointments at all
     */
    @Test
    void testViewCompanyAppointments_EmptyAppointmentsList() {
        // Arrange
        when(mockAppointmentRepository.findAll()).thenReturn(Arrays.asList());
        
        // Capture System.out
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(outputStream));
        
        try {
            // Act
            appointmentService.viewCompanyAppointments(mockCompany);
            
            // Assert
            String output = outputStream.toString();
            assertTrue(output.contains("No appointments"));
            verify(mockAppointmentRepository).findAll();
            verify(mockPropertyRepository, never()).findById(anyString());
        } finally {
            System.setOut(originalOut);
        }
    }

    /**
     * Tests view company appointments - when property is not found (null)
     */
    @Test
    void testViewCompanyAppointments_PropertyNotFound() {
        // Arrange
        Appointment appointment = mock(Appointment.class);
        when(appointment.getPropertyId()).thenReturn("property1");
        
        when(mockAppointmentRepository.findAll()).thenReturn(Arrays.asList(appointment));
        when(mockPropertyRepository.findById("property1")).thenReturn(null);  // property غير موجود
        
        // Capture System.out
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(outputStream));
        
        try {
            // Act
            appointmentService.viewCompanyAppointments(mockCompany);
            
            // Assert
            String output = outputStream.toString();
            assertTrue(output.contains("No appointments"));
            verify(mockPropertyRepository).findById("property1");
        } finally {
            System.setOut(originalOut);
        }
    }

    /**
     * Tests view company appointments - mixed appointments (some for company, some not)
     */
    @Test
    void testViewCompanyAppointments_MixedAppointments() {
        // Arrange
        Appointment companyAppointment = mock(Appointment.class);
        Appointment otherAppointment = mock(Appointment.class);
        
        when(companyAppointment.getPropertyId()).thenReturn("property1");
        when(otherAppointment.getPropertyId()).thenReturn("property2");
        when(companyAppointment.toString()).thenReturn("Company Appointment");
        when(otherAppointment.toString()).thenReturn("Other Appointment");
        
        Property companyProperty = mock(Property.class);
        Property otherProperty = mock(Property.class);
        when(companyProperty.getCompanyId()).thenReturn("123");  
        when(otherProperty.getCompanyId()).thenReturn("differentCompany");  
        
        when(mockAppointmentRepository.findAll()).thenReturn(Arrays.asList(companyAppointment, otherAppointment));
        when(mockPropertyRepository.findById("property1")).thenReturn(companyProperty);
        when(mockPropertyRepository.findById("property2")).thenReturn(otherProperty);
        
        // Capture System.out
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(outputStream));
        
        try {
            // Act
            appointmentService.viewCompanyAppointments(mockCompany);
            
            // Assert
            String output = outputStream.toString();
            assertTrue(output.contains("Company Appointment"));
            assertFalse(output.contains("Other Appointment"));
            assertFalse(output.contains("No appointments"));
        } finally {
            System.setOut(originalOut);
        }
    }
    
    @Test
    void testViewAllAppointments_WhenNoAppointments_ShouldPrintNoAppointments() {
        when(mockAppointmentRepository.findAll()).thenReturn(List.of());
        
        appointmentService.viewAllAppointments();
        
        String output = outputStream.toString();
        assertTrue(output.contains("No appointments"));
        verify(mockAppointmentRepository).findAll();
    }

    @Test
    void testViewAllAppointments_WithAppointments_ShouldPrintAllWithIndexes() {
        Appointment app1 = mock(Appointment.class);
        Appointment app2 = mock(Appointment.class);
        
        when(app1.toString()).thenReturn("First Appointment");
        when(app2.toString()).thenReturn("Second Appointment");
        when(mockAppointmentRepository.findAll()).thenReturn(List.of(app1, app2));
        
        appointmentService.viewAllAppointments();
        
        String output = outputStream.toString();
        assertTrue(output.contains("0. First Appointment"));
        assertTrue(output.contains("1. Second Appointment"));
    }

    @Test
    void testViewAllAppointments_ShouldNotThrowException() {
        when(mockAppointmentRepository.findAll()).thenReturn(List.of());
        
        assertDoesNotThrow(() -> appointmentService.viewAllAppointments());
    }  

    @Test
    void testDefaultConstructor() {
        AppointmentService service = new AppointmentService();
        assertNotNull(service);
    }
    
    @Test
    void testAddObserver() {
        

        appointmentService.addObserver(observer);

        assertEquals(1, appointmentService.getObservers().size());
    }
    
    @Test
    void testNotifyObserverOnBooking() {
       

        appointmentService.addObserver(observer);

        
        appointmentService.bookAppointment(
            "p1",
            "v1",
            slot,
            AppointmentType.IN_PERSON
        );

        verify(observer).update(any(), eq("BOOKED"));
    }
    @Test
    void testUpdateVisitorNull() {
       

        when(mockAppointment.getVisitorId()).thenReturn("v1");
        when(mockVisitorRepository.findById("v1")).thenReturn(null);

        notifier.update(mockAppointment, "BOOKED");

        verify(mockEmailService, never())
            .sendEmail(any(), any(), any());
    }
    @Test
    void testTimeSlotToString() {
    	TimeSlot slot = new TimeSlot(LocalDateTime.now());
        
        assertNotNull(slot.toString());
    }
    @Test
    void testSetAvailable() {
    	 TimeSlot slot = new TimeSlot(LocalDateTime.now());
    	    slot.setAvailable(false);
    	    assertFalse(slot.isAvailable());
    }
    
    @Test
    void testBookAppointmentWithoutObservers() {

        TimeSlot slot = new TimeSlot(LocalDateTime.now().plusDays(1));

        assertDoesNotThrow(() ->
            appointmentService.bookAppointment(
                "p1",
                "v1",
                slot,
                AppointmentType.IN_PERSON
            )
        );
    }
    
    @Test
    void testInvalidTypeThrowsException() {
        assertThrows(NullPointerException.class, () ->
            AppointmentStrategyFactory.getStrategy(null)
        );
    }
    
    @Test
    void testBookAppointment_nullSlot() {
        assertThrows(IllegalArgumentException.class, () ->
        appointmentService.bookAppointment("p1", "v1", null, AppointmentType.IN_PERSON)
        );
    }

    @Test
    void testBookAppointment_slotNotAvailable() {
    	slot.setAvailable(false);
        assertThrows(IllegalStateException.class, () ->
        appointmentService.bookAppointment("p1", "v1", slot, AppointmentType.IN_PERSON)
        );
    }
    
    
   
}
