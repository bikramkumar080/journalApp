package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> journalEntries(){
        return journalEntryService.getAllEntries();
    }

    @PostMapping
    public void addJournalEntry(@RequestBody JournalEntry journalEntry){
        journalEntryService.save(journalEntry);
    }

    @GetMapping("id/{id}")
    public JournalEntry getJournalEntryById(@PathVariable String id){
        return journalEntryService.getEntryById(id).orElse(null);
    }

    @DeleteMapping("id/{id}")
    public boolean deleteJournalEntryById(@PathVariable String id){
        journalEntryService.deleteById(id);
        return true;
    }

    @PutMapping("id/{id}")
    public JournalEntry updateJournalEntry(@PathVariable String id, @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalEntryService.getEntryById(id).orElse(null);
        if (old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
            journalEntryService.save(old);
            return old;
        } else {
            newEntry.setId(id);
            journalEntryService.save(newEntry);
            return newEntry;
        }
    }
}
