package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import com.appointmentsystem.domain.models.User;

public class UserTest{
	
	
	@Test
	void testUserget() {
		User user = new TestUser("1234","Ali","Ali@12","Ali45@gmail.com","1100");
		
		assertEquals("1234", user.getId());
        assertEquals("Ali", user.getName());
        assertEquals("Ali@12", user.getUsername());
        assertEquals("Ali45@gmail.com", user.getEmail());
        assertEquals("1100", user.getPassword());
        assertNotNull(user.getCreatedAt());
        assertTrue(user.isActive());
		
	}
	
	@Test
	void testUserset() {
		User user = new TestUser("1234","Ali","Ali@12","Ali45@gmail.com","1100");
		user.setId("12");
        user.setName("Omar");
        user.setUsername("Omar12");
        user.setEmail("Omar45@gmail.com");
        user.setPassword("2222");
        user.setActive(false);
        
        assertEquals("12", user.getId());
        assertEquals("Omar", user.getName());
        assertEquals("Omar12", user.getUsername());
        assertEquals("Omar45@gmail.com", user.getEmail());
        assertEquals("2222", user.getPassword());
        assertFalse(user.isActive());
        
	}
	
	@Test 
	void testequals() {
		User user1 = new TestUser("1234","Ali","Ali@12","Ali45@gmail.com","1100");
		User user2 = new TestUser("1234","Ali","Ali@12","Ali45@gmail.com","1100");
		User user3 = new TestUser("12","Omar","Omar12","Omar45@gmail.com","2222");
		
		assertTrue(user1.equals(user2));
		assertFalse(user1.equals(user3));
		assertFalse(user1.equals(null));
		assertFalse(user1.equals("not Obj"));
	}

}
