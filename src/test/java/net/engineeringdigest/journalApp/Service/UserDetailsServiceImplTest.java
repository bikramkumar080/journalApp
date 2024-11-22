package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Repository.UserEntryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.when;


public class UserDetailsServiceImplTest {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserEntryRepository userEntryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest() {
        // Arrange
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("cehjb cejc");
        user.setRole("USER");
        when(userEntryRepository.findByUsername("testuser")).thenReturn(user);
        String username = "testuser";
        // Act
        UserDetails loadedUser = userDetailsServiceImpl.loadUserByUsername(username);
        // Assert
        Assertions.assertNotNull(loadedUser);
    }
}
