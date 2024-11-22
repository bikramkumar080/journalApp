package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.Repository.UserEntryRepository;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserEntryService {

    @Autowired
    private UserEntryRepository userEntryRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public  void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userEntryRepository.save(user);
    }

    public  void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ADMIN");
        userEntryRepository.save(user);
    }

    public List<User> getAllEntries() {
        return userEntryRepository.findAll();
    }

    public Optional<User> getEntryById(String id) {
        return userEntryRepository.findById(id);
    }

    public void deleteById(String id) {
        userEntryRepository.deleteById(id);
    }

    public  User findUserByName(String userName) {
        return userEntryRepository.findByUsername(userName);
    }


}
