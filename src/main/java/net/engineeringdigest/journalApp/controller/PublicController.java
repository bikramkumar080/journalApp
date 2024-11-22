package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Service.UserEntryService;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/register")
    public ResponseEntity<User> addUserEntry(@RequestBody User user){
        try {
            if (user.getId() == null) {
                user.setId(UUID.randomUUID().toString());
            }
            userEntryService.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
