package test;

import com.appointmentsystem.domain.models.User;

public class TestUser extends User {
	

    public TestUser(String id, String name, String username, String email, String password) {
        super(id, name, username, email, password);
    }
    
    @Override
    public String getRole() {
        return "TEST"; 
    }
}