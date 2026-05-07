package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointmentsystem.domain.models.Admin;
import com.appointmentsystem.service.AdminService;

/**
 * Test class for AdminService.
 * 
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * @version 1.0
 */
class TestAdminService {

	
	private AdminService adminService;




	@BeforeEach
	void setUp() throws Exception {
		adminService = new AdminService();
	}


    
    /**
     * Tests successful admin login.
     */
    @Test
    void testLogin() {
        Admin loggedInAdmin = adminService.login("admin", "1234");
        assertNotNull(loggedInAdmin);
        assertEquals("admin", loggedInAdmin.getUsername());
        assertEquals("1", loggedInAdmin.getId());

	}
	
	@Test
	void testLoginWrongPassword() {
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {

            adminService.login("admin", "wrongPassword");
        });

        assertEquals("Invalid admin login", exception.getMessage());		
	}
	
	@Test
	void testLoginWrongUsername() {
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {

            adminService.login("wrongUsername", "1234");
        });
        assertEquals("Invalid admin login", exception.getMessage());

	
	}
	
/*
	@Test
	void testLogout() {
		when(adminRepository.getAdmin()).thenReturn(mockAdmin);
		adminService.logout();
		verify(mockAdmin).logout();
		verify(adminRepository).update(mockAdmin);
*/
	

}
