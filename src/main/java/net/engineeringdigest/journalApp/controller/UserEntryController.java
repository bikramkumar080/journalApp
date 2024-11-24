package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserEntryService;
import net.engineeringdigest.journalApp.Service.WeatherService;
import net.engineeringdigest.journalApp.apiresponse.WeatherResponse;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserEntryController {
    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private WeatherService weatherService;


    //    @GetMapping("id/{id}")
//    public ResponseEntity<User> getUserEntryById(@PathVariable String id){
//        Optional<User> userEntry = userEntryService.getEntryById(id);
//        if (userEntry.isPresent()) {
//            return new ResponseEntity<>(userEntry.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserEntryById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = userEntryService.findUserByName(authentication.getName());
            userEntryService.deleteById(user.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserEntry(@RequestBody User newUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userEntryService.findUserByName(authentication.getName());
          user.setUsername(newUser.getUsername());
          user.setPassword(newUser.getPassword());
          userEntryService.save(user);
          return new ResponseEntity<>(user , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        WeatherResponse weather = weatherService.getWeather("Mumbai");
        int temp = weather.getCurrent().getFeelslike();
        if(!username.isEmpty()){
            return new ResponseEntity<>(username + " Weather feels like "+temp, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
