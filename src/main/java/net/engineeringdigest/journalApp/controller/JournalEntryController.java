package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserEntryService;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping
    public ResponseEntity<?> journalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userEntryService.findUserByName(username);
        List<JournalEntry> journalEntries = journalEntryService.findByUser(user.getId());
        if(!journalEntries.isEmpty()){
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<JournalEntry> addJournalEntry(@RequestBody JournalEntry journalEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            if (journalEntry.getId() == null) {
                journalEntry.setId(UUID.randomUUID().toString());
            }
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.save(journalEntry,username);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getJournalEntryIdById(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userEntryService.findUserByName(username);
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(id);
        if (journalEntry.isPresent() && journalEntry.get().getUser().getId().equals(user.getId())) {
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<String> deleteJournalEntryById(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userEntryService.findUserByName(username);
        if(journalEntryService.getEntryById(id).isPresent() && journalEntryService.getEntryById(id).get().getUser().getId().equals(user.getId())) {
            journalEntryService.deleteById(id);
            return new ResponseEntity<>("Deleted successfully" ,HttpStatus.NO_CONTENT);
        } else if (journalEntryService.getEntryById(id).isPresent()) {
            return new ResponseEntity<>("You are not authorized to delete this entry", HttpStatus.UNAUTHORIZED);

        }
        return new ResponseEntity<>("Id with " + id + " is not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable String id, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userEntryService.findUserByName(username);
        JournalEntry old = journalEntryService.getEntryById(id).orElse(null);
        if (old != null && old.getUser().getId().equals(user.getId())) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
            old.setDate(LocalDateTime.now());
                journalEntryService.save(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        } else if (old != null) {
            return new ResponseEntity<>("You are not authorized to update this entry", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Id with "+ id + " is not found", HttpStatus.NOT_FOUND);
        }
    }
}
