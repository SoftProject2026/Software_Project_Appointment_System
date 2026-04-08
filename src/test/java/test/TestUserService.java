package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointmentsystem.persistence.UserRepository;
import com.appointmentsystem.service.UserService;
import com.appointmentsystem.domain.models.Company;
import com.appointmentsystem.domain.models.User;

public class TestUserService {

    private UserService userservice;
    private UserRepository mockUserRepository;
    private User mockUser;
    private Company mockCompany;

    @BeforeEach
    void setup() {
        mockUserRepository = mock(UserRepository.class);
        userservice = spy(new UserService(mockUserRepository));
        mockUser = mock(User.class);
        mockCompany = mock(Company.class);

        when(mockUser.getId()).thenReturn("1");
        when(mockUser.getName()).thenReturn("Ali");
        when(mockUser.getUsername()).thenReturn("Ali12");
        when(mockUser.getEmail()).thenReturn("ali@99.com");
        when(mockUser.getPassword()).thenReturn("123");
        when(mockUser.isActive()).thenReturn(true);

        when(mockCompany.getId()).thenReturn("2");
        when(mockCompany.getName()).thenReturn("CompX");
        when(mockCompany.getUsername()).thenReturn("comp");
        when(mockCompany.getEmail()).thenReturn("comp@99.com");
        when(mockCompany.isActive()).thenReturn(true);
        when(mockCompany.isVerified()).thenReturn(true);
    }

    @Test
    void testCreateUserSuccess() {
        //doReturn(false).when(userservice).usernameExists(mockUser.getUsername());
    	//when(mockUserRepository.existsByUsername(mockUser.getUsername())).thenReturn(false);
        //doReturn(false).when(userservice).emailExists(mockUser.getEmail());
    	//when(mockUserRepository.existsByEmail(mockUser.getEmail())).thenReturn(false);
       // doReturn(true).when(userservice).isValidEmail(mockUser.getEmail());
    	when(mockUser.getEmail()).thenReturn("ali@99.com");
    	//when(mockUser.getEmail()).thenReturn("ali@99.com");

        when(mockUserRepository.existsByUsername(mockUser.getUsername())).thenReturn(false);
        when(mockUserRepository.existsByEmail(mockUser.getEmail())).thenReturn(false);


        userservice.createUser(mockUser);

        verify(mockUserRepository, times(1)).save(mockUser);
    }

    @Test
    void testCreateUserNullUser() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> userservice.createUser(null));
        assertEquals("User cannot be null", ex.getMessage());
    }

    @Test
    void testCreateUserUsernameExists() {
        //doReturn(true).when(userservice).usernameExists(mockUser.getUsername());
    	when(mockUserRepository.existsByUsername(mockUser.getUsername())).thenReturn(true);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> userservice.createUser(mockUser));
        assertEquals("Username already exists", ex.getMessage());
    }

    @Test
    void testCreateUserInvalidEmail() {
        //doReturn(false).when(userservice).usernameExists(mockUser.getUsername());
    	when(mockUserRepository.existsByUsername(mockUser.getUsername())).thenReturn(false);
        //doReturn(false).when(userservice).isValidEmail(mockUser.getEmail());
    	when(mockUserRepository.existsByEmail(mockUser.getEmail())).thenReturn(false);
    	when(mockUser.getEmail()).thenReturn("invalid-email"); 
        Exception ex = assertThrows(IllegalArgumentException.class, () -> userservice.createUser(mockUser));
        assertEquals("Invalid email format", ex.getMessage());
    }

    @Test
    void testCreateUserEmailExists() {
        //doReturn(false).when(userservice).usernameExists(mockUser.getUsername());
    	when(mockUserRepository.existsByUsername(mockUser.getUsername())).thenReturn(false);
    	when(mockUser.getEmail()).thenReturn("ali@99.com");
    	when(mockUserRepository.existsByEmail(anyString())).thenReturn(true);
        //doReturn(true).when(userservice).emailExists(mockUser.getEmail());
    	when(mockUserRepository.existsByEmail(mockUser.getEmail())).thenReturn(true);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> userservice.createUser(mockUser));
        assertEquals("Email already exists", ex.getMessage());
    }

    @Test
    void testGetUserById() {
        when(mockUserRepository.findById("1")).thenReturn(mockUser);

        User result = userservice.getUserById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Ali12", result.getUsername());
        verify(mockUserRepository).findById("1");
    }

    @Test
    void testGetUserByUsername() {
        when(mockUserRepository.findByUsername("Ali")).thenReturn(mockUser);

        User result = userservice.getUserByUsername("Ali");

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Ali12", result.getUsername());
        verify(mockUserRepository).findByUsername("Ali");
    }

    @Test
    void testAuthenticateSuccess() {
        doReturn(mockUser).when(userservice).getUserByUsername("Ali");
        when(mockUser.checkPassword("1234")).thenReturn(true);

        User result = userservice.authenticate("Ali", "1234");

        assertEquals(mockUser, result);
    }

    @Test
    void testAuthenticateUserNotFound() {
        doReturn(null).when(userservice).getUserByUsername("Ali");
        assertThrows(IllegalArgumentException.class, () -> userservice.authenticate("Ali", "12345"));
    }

    @Test
    void testAuthenticateInvalidPassword() {
        doReturn(mockUser).when(userservice).getUserByUsername("Ali");
        when(mockUser.checkPassword("wrong")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> userservice.authenticate("Ali", "wrong"));
    }

    @Test
    void testAuthenticateInactiveUser() {
        doReturn(mockUser).when(userservice).getUserByUsername("Ali");
        when(mockUser.checkPassword("1234")).thenReturn(true);
        when(mockUser.isActive()).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> userservice.authenticate("Ali", "1234"));
    }

    @Test
    void testAuthenticateCompanyNotVerified() {
        doReturn(mockCompany).when(userservice).getUserByUsername("comp");
        when(mockCompany.checkPassword("1234")).thenReturn(true);
        when(mockCompany.isVerified()).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> userservice.authenticate("comp", "1234"));
    }

    @Test
    void testLogoutUserExists() {
        doReturn(mockUser).when(userservice).getUserByUsername("Ali");
        userservice.logout("Ali");
        verify(mockUser, times(1)).logout();
        verify(mockUserRepository, times(1)).update(mockUser);
    }

    @Test
    void testLogoutUserNotExists() {
        doReturn(null).when(userservice).getUserByUsername("Ali");
        userservice.logout("Ali");
        verify(mockUserRepository, never()).update(any());
    }

    @Test
    void testGetAllActiveUsers() {
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn("1");
        when(user1.getName()).thenReturn("Ali");
        when(user1.isActive()).thenReturn(true);

        User user2 = mock(User.class);
        when(user2.getId()).thenReturn("2");
        when(user2.getName()).thenReturn("Omar");
        when(user2.isActive()).thenReturn(true);

        List<User> users = List.of(user1, user2);
        when(mockUserRepository.findAllActive()).thenReturn(users);

        List<User> result = userservice.getAllActiveUsers();

        assertEquals(2, result.size());
        verify(mockUserRepository, times(1)).findAllActive();
    }

    @Test
    void testGetUsersByRoles() {
        User user1 = mock(User.class);
        when(user1.getName()).thenReturn("Ali");
        when(user1.isActive()).thenReturn(true);

        User user2 = mock(User.class);
        when(user2.getName()).thenReturn("Omar");
        when(user2.isActive()).thenReturn(true);

        List<User> users = List.of(user1, user2);
        when(mockUserRepository.findByRole("role")).thenReturn(users);

        List<User> result = userservice.getUsersByRole("role");

        assertEquals(2, result.size());
        assertEquals("Ali", result.get(0).getName());
        assertEquals("Omar", result.get(1).getName());
        verify(mockUserRepository).findByRole("role");
    }

    @Test
    void testUpdateUser() {
        userservice.updateUser(mockUser);
        verify(mockUserRepository, times(1)).update(mockUser);
    }

    @Test
    void testDeleteUser() {
        userservice.deleteUser("1");
        verify(mockUserRepository, times(1)).delete("1");
    }
}