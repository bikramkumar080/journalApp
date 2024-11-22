package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Repository.UserEntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class UserEntryServiceTest {

    @Autowired
    private UserEntryRepository userEntryRepository;

    @Test
    public void testFindUserByName(){
        Assert.notNull(userEntryRepository.findByUsername("testuser"), "User not found");
    }
}
