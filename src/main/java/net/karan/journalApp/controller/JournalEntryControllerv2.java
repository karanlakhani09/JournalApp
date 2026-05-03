package net.karan.journalApp.controller;

import net.karan.journalApp.entity.JournalEntry;
import net.karan.journalApp.entity.User;
import net.karan.journalApp.service.JournalEntryService;
import net.karan.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {
    private Map<Long, JournalEntry> JE = new HashMap<>();

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping ()
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if (all !=null) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myentries ){  //input yha lane ke liye Request body use karte h
        try {
           // myentries.setDate(LocalDateTime.now());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
                journalEntryService.saveEntry(myentries,userName);
            return new ResponseEntity<>(myentries,HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getbyId(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry>collect = user.getJournalEntries().stream().filter(x-> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findByID(myId);
            if (journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> delbyId(@PathVariable ObjectId myId){ //@PathVariable String userName){   // wild card ? = koi doosri class ka objet bhi le ke aa skte h
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        journalEntryService.deleteById(myId, userName);                       // resposnse entity m wrap kara kar koi aur class ka object bhi return kara skte h
        return new ResponseEntity<>(myId,HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> putbyid(@PathVariable ObjectId myId ,@RequestBody JournalEntry mynewentries ){// ,  @PathVariable String userName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry>collect = user.getJournalEntries().stream().filter(x-> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findByID(myId);
            if (journalEntry.isPresent()) {
                JournalEntry old = journalEntryService.findByID(myId).orElse(null);
                if (old != null) {
                    old.setTitle(mynewentries.getTitle() != null && !mynewentries.getTitle().equals("") ? mynewentries.getTitle() : old.getTitle());
                    old.setContent(mynewentries.getContent() != null && !mynewentries.getContent().equals("") ? mynewentries.getContent() : old.getContent());
                    journalEntryService.saveEntry(old);
                    return new ResponseEntity<>(old, HttpStatus.OK);
                }
            }
        }
                return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

