package test;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointmentsystem.domain.models.Visitor;

class VisitorTest {
    
    private Visitor visitor;
    
    @BeforeEach
    void setUp() {
        visitor = new Visitor("visitor-123", "Alaa Ahmed", "alaa12", 
                              "alaa@email.com", "password123", "0591234567");
    }
    
    @Test
    void testConstructor_ShouldCreateVisitorCorrectly() {
        assertEquals("visitor-123", visitor.getId());
        assertEquals("Alaa Ahmed", visitor.getName());
        assertEquals("alaa12", visitor.getUsername());
        assertEquals("alaa@email.com", visitor.getEmail());
        assertEquals("password123", visitor.getPassword());
    }
    
    @Test
    void testGetId_ShouldReturnCorrectId() {
        assertEquals("visitor-123", visitor.getId());
    }
    
    @Test
    void testGetName_ShouldReturnCorrectName() {
        assertEquals("Alaa Ahmed", visitor.getName());
    }
    
    @Test
    void testGetUsername_ShouldReturnCorrectUsername() {
        assertEquals("alaa12", visitor.getUsername());
    }
    
    @Test
    void testGetPassword_ShouldReturnCorrectPassword() {
        assertEquals("password123", visitor.getPassword());
    }
    
    @Test
    void testGetEmail_ShouldReturnCorrectEmail() {
        assertEquals("alaa@email.com", visitor.getEmail());
    }
    
    @Test
    void testToString_ShouldReturnFormattedString() {
        String result = visitor.toString();
        
        assertTrue(result.contains("Alaa Ahmed"));
        assertTrue(result.contains("0591234567"));
    }
    
    @Test
    void testMultipleVisitors_ShouldBeIndependent() {
        Visitor visitor2 = new Visitor("visitor-456", "Sara Ali", "sara22", 
                                       "sara@email.com", "pass456", "0597654321");
        
        assertNotEquals(visitor.getId(), visitor2.getId());
        assertNotEquals(visitor.getName(), visitor2.getName());
        assertNotEquals(visitor.getUsername(), visitor2.getUsername());
        assertNotEquals(visitor.getEmail(), visitor2.getEmail());
        assertNotEquals(visitor.getPassword(), visitor2.getPassword());
    }
}
