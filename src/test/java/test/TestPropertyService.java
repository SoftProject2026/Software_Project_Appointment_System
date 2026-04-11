package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointmentsystem.domain.models.Company;
import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.persistence.PropertyRepository;
import com.appointmentsystem.service.PropertyService;

class TestPropertyService {
	
	private PropertyRepository mockPropertyRepository;
	private PropertyService propertyservice;
	
	private Company mockCompany;
    private Property mockProperty;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mockCompany  = mock(Company.class);
		mockPropertyRepository = mock(PropertyRepository.class);
		mockProperty = mock(Property.class);
		propertyservice=new PropertyService(mockPropertyRepository);
		
		when(mockCompany.getId()).thenReturn("123");
        when(mockCompany.getCompanyName()).thenReturn("Al-Nabaly");
        when(mockCompany.getUsername()).thenReturn("Al-Nabaly12");
        when(mockCompany.getEmail()).thenReturn("alaa@99.com");
        when(mockCompany.getPassword()).thenReturn("1111");
        when(mockCompany.getCommercialRegister()).thenReturn("cr");
        when(mockCompany.isVerified()).thenReturn(false);
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAddProperty() {
		propertyservice.addProperty(mockCompany, mockProperty);	
		verify(mockPropertyRepository, times(1)).save(mockProperty);
	}
	
	@Test
    void testviewMyPropertiesNullProperty() {
		
		when(mockPropertyRepository.findByCompanyId(mockCompany.getId())).thenReturn(new ArrayList<>());
		propertyservice.viewMyProperties(mockCompany);

		verify(mockPropertyRepository, times(1))
        .findByCompanyId(mockCompany.getId());
    }
	
	@Test
    void testviewMyProperties() {
		
	    Property p1=mock(Property.class);
		Property p2=mock(Property.class);
		when(mockPropertyRepository.findByCompanyId(mockCompany.getId()))
	     .thenReturn(Arrays.asList(p1, p2));
		propertyservice.viewMyProperties(mockCompany);
		verify(mockPropertyRepository).findByCompanyId(mockCompany.getId());
    }
	
	@Test
	void testDeleteProperty() {
		propertyservice.deleteProperty(mockCompany.getId());	
		verify(mockPropertyRepository, times(1)).delete(mockCompany.getId());
	}
	
	@Test
	void testGetPropertyById() {
		when(mockPropertyRepository.findById("1"))
	    .thenReturn(mockProperty);
		Property result=propertyservice.getPropertyById("1");
		assertEquals(mockProperty, result);
	}
	
	@Test
	void testGetPropertyByCompany() {
		List<Property> list = Arrays.asList(new Property(), new Property());
		when(mockPropertyRepository.findByCompanyId("1"))
	    .thenReturn(list);
		List<Property> result = propertyservice.getPropertiesByCompany("1");
		assertEquals(list, result);
	}
	
	@Test
	void testGetAllProperty() {
		List<Property> list = Arrays.asList(new Property(), new Property());
		when(mockPropertyRepository.findAll())
	    .thenReturn(list);
		List<Property> result = propertyservice.getAllProperties();
		assertEquals(list, result);
	}
	
	

	
	 
}
