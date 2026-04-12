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
import com.appointmentsystem.service.PropertyService;
import com.appointmentsystem.service.VisitorService;

/**
 * Test class for CompanyService.
 * 
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
class TestCompanyService {
	
	private CompanyRepository mockCompanyRepository;
    private CompanyService companyservice;
    private PropertyService propertyservice;
    private PropertyRepository mockPropertyRepository;
    private AppointmentService appointmentservice;

    private Company mockCompany;
    private Property mockProperty;
    private DateTimeFormatter formatter;

	/**
	 * Set up method called once before all tests.
	 * 
	 * @throws Exception if an error occurs during setup
	 * 
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * Tear down method called once after all tests.
	 * 
	 * @throws Exception if an error occurs during teardown
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Set up method called before each test.
	 * Initializes mocks and sets up default behaviors.
	 * 
	 * @throws Exception if an error occurs during setup
	 * 
	 * 
	 */
	@BeforeEach
	void setUp() throws Exception {
		
		mockCompanyRepository = mock(CompanyRepository.class);
		propertyservice  = mock(PropertyService.class);
		mockCompany  = mock(Company.class);
		//companyservice = new CompanyService(mockCompanyRepository);
		mockPropertyRepository = mock(PropertyRepository.class);
		mockProperty = mock(Property.class);
		companyservice = new CompanyService(mockCompanyRepository, mockPropertyRepository);
		when(mockProperty.getTimeSlots()).thenReturn(new ArrayList<>());
		
		when(mockCompany.getId()).thenReturn("123");
        when(mockCompany.getCompanyName()).thenReturn("Al-Nabaly");
        when(mockCompany.getUsername()).thenReturn("Al-Nabaly12");
        when(mockCompany.getEmail()).thenReturn("alaa@99.com");
        when(mockCompany.getPassword()).thenReturn("1111");
        when(mockCompany.getCommercialRegister()).thenReturn("cr");
        when(mockCompany.isVerified()).thenReturn(false);
        
        when(mockProperty.getTimeSlots()).thenReturn(new ArrayList<>());
        
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
	}

	/**
	 * Tear down method called after each test.
	 * 
	 * @throws Exception if an error occurs during teardown
	 * 
	 * 
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Tests signup failure when company already exists.
	 * 
	 * 
	 */
	@Test
	void SignupCompanyNotNull() {
	        when(mockCompanyRepository.findByUsername("Al-Nabaly12")).thenReturn(mockCompany);
	        Exception ex = assertThrows(RuntimeException.class,() -> companyservice.signup(mockCompany));
	        assertEquals("Username exists", ex.getMessage());
	        verify(mockCompanyRepository, never()).save(any());
	    
	}
	
	/**
	 * Tests successful signup when company does not exist.
	 * 
	 * 
	 */
	@Test
    void SignupCompanyNull() {
        when(mockCompanyRepository.findByUsername("Al-Nabaly12")).thenReturn(null);
        companyservice.signup(mockCompany);
        verify(mockCompanyRepository, times(1)).save(mockCompany);
    }
	
	/**
	 * Tests signup failure with invalid email format.
	 * 
	 *
	 */
	@Test
    void SignupCompanyInvalidEmail() {
		
    	when(mockCompany.getEmail()).thenReturn("invalid-email");
        when(mockCompanyRepository.findByUsername("Al-Nabaly12")).thenReturn(null);
  
        Exception ex = assertThrows(RuntimeException.class,() -> companyservice.signup(mockCompany));
        assertEquals("Invalid Email", ex.getMessage());
        verify(mockCompanyRepository, never()).save(any());
    }
	
	/**
	 * Tests successful company login with correct credentials.
	 * 
	 *
	 */
	@Test
    void testLogin() {
        when(mockCompanyRepository.findByUsername("Al-Nabaly12")).thenReturn(mockCompany);
        when(mockCompany.isVerified()).thenReturn(true);

        Company result = companyservice.login("Al-Nabaly12", "1111");

        assertNotNull(result);
        assertEquals("Al-Nabaly12", result.getUsername());
    }
	
	/**
	 * Tests login failure with wrong password.
	 * 
	 * 
	 */
	@Test
    void testLoginWrongPassword() {
        when(mockCompanyRepository.findByUsername("Al-Nabaly12")).thenReturn(mockCompany);
        when(mockCompany.getPassword()).thenReturn("1111");
        RuntimeException ex = assertThrows(RuntimeException.class,() -> companyservice.login("Al-Nabaly12", "999"));
        assertEquals("Invalid credentials", ex.getMessage());
    }
	
	/**
	 * Tests login failure when company is not found.
	 * 
	 *
	 */
	@Test
    void testLoginNullCompany() {
        when(mockCompanyRepository.findByUsername("Al-Nabaly12")).thenReturn(null);
        RuntimeException ex = assertThrows(RuntimeException.class,() -> companyservice.login("Al-Nabaly12", "1111"));
        assertEquals("Invalid credentials", ex.getMessage());
    }
	
	/**
	 * Tests login failure when company is not verified.
	 * 
	 *
	 */
	@Test
    void testLoginNonVerifiedCompany() {
		when(mockCompanyRepository.findByUsername("Al-Nabaly12")).thenReturn(mockCompany);
	    when(mockCompany.isVerified()).thenReturn(false);
	    
	    RuntimeException ex = assertThrows(RuntimeException.class, 
	            () -> companyservice.login("Al-Nabaly12", "1111"));
	        assertEquals("Company not approved", ex.getMessage());
	        verify(mockCompanyRepository, times(1)).findByUsername("Al-Nabaly12");
        
    }
	
	/**
	 * Tests approval failure when company is not found.
	 * 
	 *
	 */
	@Test
    void testApproveNullCompany() {
        when(mockCompanyRepository.findByUsername("Al-Nabaly12")).thenReturn(null);
        RuntimeException ex = assertThrows(RuntimeException.class,() -> companyservice.approve("Al-Nabaly12"));
        assertEquals("Not found", ex.getMessage());
    }
	
	/**
	 * Tests approval failure when company is already verified.
	 * 
	 *
	 */
	@Test
    void testApproveVerifiedCompany() {
        when(mockCompanyRepository.findByUsername("Al-Nabaly12")).thenReturn(mockCompany);
        when(mockCompany.isVerified()).thenReturn(true);
        RuntimeException ex = assertThrows(RuntimeException.class,() -> companyservice.approve("Al-Nabaly12"));
        assertEquals("Already approved", ex.getMessage());
    }
	
	/**
	 * Tests successful approval of a non-verified company.
	 * 
	 *
	 */
	@Test
    void testApproveNonVerifiedCompany() {
        when(mockCompanyRepository.findByUsername("Al-Nabaly12")).thenReturn(mockCompany);
        when(mockCompany.isVerified()).thenReturn(false);
        companyservice.approve("Al-Nabaly12");
        verify(mockCompany).setVerified(true);
        verify(mockCompanyRepository, times(1)).update(mockCompany);
    }
	
	/**
	 * Tests printing all companies when companies exist.
	 * 
	 */
	@Test
    void testPrintAllCompanys() {
		List<Company> companies = Arrays.asList(mockCompany);
	    when(mockCompanyRepository.findAll()).thenReturn(companies);
	    when(mockCompany.toString()).thenReturn("Company: Al-Nabaly");
	    
	    String result = companyservice.printAllCompanys();
	    
	    assertTrue(result.contains("Al-Nabaly"));
    }
	
	/**
	 * Tests printing all companies when no companies exist.
	 * 
	 *
	 */
	@Test
    void testPrintAllCompanysNull() {
		when(mockCompanyRepository.findAll()).thenReturn(new ArrayList<>());
	    String result = companyservice.printAllCompanys();
	    assertEquals("No companies", result);
    }
	
	/**
	 * Tests adding a time slot when no properties exist.
	 * 
	 *
	 */
	@Test
    void testaddTimeSlotToPropertyNullPropertyy() {
		when(mockPropertyRepository.findByCompanyId(mockCompany.getId())).thenReturn(new ArrayList<>());
        companyservice.addTimeSlotToProperty(mockCompany, 0, "2027-01-01 10:00");

        verify(mockPropertyRepository, never()).update(any());
    }
	
	/**
	 * Tests adding a time slot with a past date.
	 * 
	 *
	 */
	@Test
    void testaddTimeSlotToPropertyPastDate() {
		String pastDate = LocalDateTime.now().minusDays(1).format(formatter);
		List<Property> properties = Arrays.asList(mockProperty);
	    when(mockPropertyRepository.findByCompanyId(mockCompany.getId())).thenReturn(properties);
	    
		
        companyservice.addTimeSlotToProperty(mockCompany, 0, pastDate);

        verify(mockPropertyRepository, never()).update(any());
    }
	
	/**
	 * Tests adding a time slot with an invalid property index.
	 * 
	 *
	 */
	@Test
    void testaddTimeSlotToPropertyInvalidIndex() {
		List<Property> properties = Arrays.asList(mockProperty);
	    when(mockPropertyRepository.findByCompanyId(mockCompany.getId())).thenReturn(properties);
	    
	    companyservice.addTimeSlotToProperty(mockCompany, -1, "2027-01-01 10:00");
	    
	    verify(mockPropertyRepository, never()).update(any());
    }
	
	/**
	 * Tests successfully adding a time slot to a property.
	 * 
	 */
	@Test
	void testaddTimeSlotToProperty() {
	    Property property = mock(Property.class);
	    when(property.getCompanyId()).thenReturn("123");
	    when(property.getId()).thenReturn("some-id");
	    when(property.getTimeSlots()).thenReturn(new ArrayList<>());
	    doNothing().when(property).addTimeSlot(any(TimeSlot.class));
	    
	    List<Property> properties = List.of(property);
	    
	    when(mockCompany.getId()).thenReturn("123");
	    when(mockPropertyRepository.findByCompanyId("123"))
	            .thenReturn(properties);
	    
	    companyservice.addTimeSlotToProperty(
	            mockCompany,
	            0,
	            "2027-01-01 10:00"
	    );
	    
	    verify(mockPropertyRepository, times(1)).update(property);
	}
}