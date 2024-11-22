package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserEntryService userEntryService;

    public  void save(JournalEntry journalEntry, String userName) {
        User user = userEntryService.findUserByName(userName);
        journalEntry.setUser(user);
        journalEntryRepository.save(journalEntry);
    }

    public  void save(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(String id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(String id) {
        journalEntryRepository.deleteById(id);
    }

    public List<JournalEntry> findByUser(String id){
        return journalEntryRepository.findByUserId(id);
    }


}
