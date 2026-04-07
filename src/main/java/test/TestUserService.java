package test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.appointmentsystem.persistence.UserRepository;
import com.appointmentsystem.service.UserService;
import com.appointmentsystem.domain.models.User;

public class TestUserService {
	
	private UserService userservice;
	private UserRepository mockUserRepository;
	
	@BeforeEach
	void setup() {
	     mockUserRepository= mock(UserRepository.class);
	     userservice=new UserService(mockUserRepository);
	}
	
	
	@Test
	void testcreatUserSuccess() {
		User user = new TestUser("1", "Ali", "Ali12", "ali@99.com", "123");
		when(userservice.usernameExists(user.getUsername())).thenReturn(false);
        when(userservice.emailExists(user.getEmail())).thenReturn(false);
        when(userservice.isValidEmail(user.getEmail())).thenReturn(true);
        userservice.createUser(user);
        verify(mockUserRepository, times(1)).save(user);
		
	}
	
	@Test
	void testcreatUserNullUser() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			userservice.createUser(null);
        });
		assertEquals("User cannot be null", exception.getMessage());
		
	}
	
	@Test
	void testcreatUserUsernameExists() {
		User user = new TestUser("1", "Ali", "Ali12", "ali@99.com", "123");
		when(userservice.usernameExists(user.getUsername())).thenReturn(true);
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			userservice.createUser(user);
        });
		assertEquals("Username already exists", exception.getMessage());
		
	}

	
	@Test
	void testcreatUserInvalidEmail() {
		User user = new TestUser("1", "Ali", "Ali12", "ali@99.com", "123");
		when(userservice.usernameExists(user.getUsername())).thenReturn(false);
		when(userservice.isValidEmail(user.getEmail())).thenReturn(false);
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			userservice.createUser(user);
        });
		assertEquals("Invalid email format", exception.getMessage());
		
	}
	
	@Test
    void testCreateUserEmailExists() {
        User user = new TestUser("1", "Ali", "Ali12", "ali@99.com", "123");
        when(userservice.usernameExists(user.getUsername())).thenReturn(false);
        when(userservice.isValidEmail(user.getEmail())).thenReturn(true);
        when(userservice.emailExists(user.getEmail())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        	userservice.createUser(user);
        });
        assertEquals("Email already exists", exception.getMessage());
    }
	
	@Test
    void testisValidEmail() {
		assertTrue(userservice.isValidEmail("ali@99.com"));
		assertFalse(userservice.isValidEmail("ali99.com"));
		assertFalse(userservice.isValidEmail("ali@99com"));
		assertFalse(userservice.isValidEmail("@ali@99com"));
		assertFalse(userservice.isValidEmail("ali@99com@"));
	}
	
	@Test
	void testgetUserById() {
		User user = new TestUser("1", "Ali", "Ali12", "ali@99.com", "123");
		when(mockUserRepository.findById("1")).thenReturn(user);
		User result = userservice.getUserById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Ali", result.getUsername());

        verify(mockUserRepository).findById("1");
		
	}
	
	
	@Test
	void testgetUserByUsername() {
		User user = new TestUser("1", "Ali", "Ali12", "ali@99.com", "123");
		when(mockUserRepository.findByUsername("Ali")).thenReturn(user);
		User result = userservice.getUserByUsername("Ali");

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Ali", result.getUsername());

        verify(mockUserRepository).findByUsername("Ali");
		
	}
	
	
	
}
