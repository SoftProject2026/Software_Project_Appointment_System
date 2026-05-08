package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointmentsystem.domain.models.Company;

class CompanyTest {
    
    private Company company;
    
    @BeforeEach
    void setUp() {
        company = new Company("comp-123", "Tech Solutions", "tech123", "tech@email.com", "pass123", "CR-789");
    }
    
    @Test
    void testConstructor_ShouldCreateCompanyCorrectly() {
        assertEquals("comp-123", company.getId());
        assertEquals("Tech Solutions", company.getCompanyName());
        assertEquals("tech123", company.getUsername());
        assertEquals("tech@email.com", company.getEmail());
        assertEquals("pass123", company.getPassword());
        assertEquals("CR-789", company.getCommercialRegister());
        assertFalse(company.isVerified()); // جديد غير موثق
    }
    
    @Test
    void testNewCompany_ShouldBeNotVerified() {
        assertFalse(company.isVerified());
    }
    
    @Test
    void testSetVerified_ShouldChangeVerificationStatus() {
        company.setVerified(true);
        assertTrue(company.isVerified());
        
        company.setVerified(false);
        assertFalse(company.isVerified());
    }
    
    @Test
    void testSetCompanyName_ShouldUpdateName() {
        company.setCompanyName("New Tech Solutions");
        assertEquals("New Tech Solutions", company.getCompanyName());
    }
    
    @Test
    void testGetters_ShouldReturnCorrectValues() {
        assertEquals("comp-123", company.getId());
        assertEquals("tech123", company.getUsername());
        assertEquals("pass123", company.getPassword());
        assertEquals("tech@email.com", company.getEmail());
        assertEquals("CR-789", company.getCommercialRegister());
    }
    
    @Test
    void testToString_ShouldReturnFormattedString() {
        String result = company.toString();
        
        assertTrue(result.contains("Name: Tech Solutions"));
        assertTrue(result.contains("Verified: false"));
    }
    
    @Test
    void testToString_AfterVerification_ShouldShowVerified() {
        company.setVerified(true);
        String result = company.toString();
        
        assertTrue(result.contains("Verified: true"));
    }
    
    @Test
    void testDefaultConstructor_ShouldCreateEmptyCompany() {
        Company emptyCompany = new Company();
        
        assertNull(emptyCompany.getId());
        assertNull(emptyCompany.getCompanyName());
        assertNull(emptyCompany.getUsername());
        assertNull(emptyCompany.getEmail());
        assertNull(emptyCompany.getPassword());
        assertNull(emptyCompany.getCommercialRegister());
        assertFalse(emptyCompany.isVerified()); // افتراضي false
    }
    
    @Test
    void testMultipleCompanies_ShouldBeIndependent() {
        Company company2 = new Company("comp-456", "Another Company", "another", "another@email.com", "pass456", "CR-456");
        
        assertNotEquals(company.getId(), company2.getId());
        assertNotEquals(company.getCompanyName(), company2.getCompanyName());
        assertNotEquals(company.getUsername(), company2.getUsername());
    }
}