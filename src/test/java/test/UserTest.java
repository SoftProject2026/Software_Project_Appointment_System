package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import com.appointmentsystem.domain.models.User;

public class UserTest{
	
	
	@Test
	void testUserGet() {
		User user = new TestUser("1234","Ali","Ali@12","Ali45@gmail.com","1100");
		
		assertEquals("1234", user.getId());
        assertEquals("Ali", user.getName());
        assertEquals("Ali@12", user.getUsername());
        assertEquals("Ali45@gmail.com", user.getEmail());
        assertEquals("1100", user.getPassword());
        assertTrue(user.isActive());
		
	}
	
	@Test
	void testUserSet() {
		User user = new TestUser("1234","Ali","Ali@12","Ali45@gmail.com","1100");
		 LocalDateTime now = LocalDateTime.now();
		user.setId("12");
        user.setName("Omar");
        user.setUsername("Omar12");
        user.setEmail("Omar45@gmail.com");
        user.setPassword("2222");
        user.setLastLogin(now);
        user.setActive(false);
        
        assertEquals("12", user.getId());
        assertEquals("Omar", user.getName());
        assertEquals("Omar12", user.getUsername());
        assertEquals("Omar45@gmail.com", user.getEmail());
        assertEquals("2222", user.getPassword());
        assertEquals(now, user.getLastLogin());
        assertFalse(user.isActive());
        
	}
	
	@Test 
	void testEquals() {
		User user1 = new TestUser("1234","Ali","Ali@12","Ali45@gmail.com","1100");
		User user2 = new TestUser("1234","Ali","Ali@12","Ali45@gmail.com","1100");
		User user3 = new TestUser("12","Omar","Omar12","Omar45@gmail.com","2222");
		
		assertTrue(user1.equals(user2));
		assertFalse(user1.equals(user3));
		assertFalse(user1.equals(null));
		assertFalse(user1.equals("not Obj"));
	}
	
	@Test 
	void testCheckPassword() {
		User user = new TestUser("1234","Ali","Ali@12","Ali45@gmail.com","1100");
		
		assertFalse(user.checkPassword("wrong"));
		
	}

}
