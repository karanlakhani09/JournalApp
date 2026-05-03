package net.karan.journalApp.controller;

import net.karan.journalApp.entity.User;
import net.karan.journalApp.repository.UserRepository;
import net.karan.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserControllerv2 {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

     @PutMapping
    public ResponseEntity<?> updateUser (@RequestBody User user){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String username = authentication.getName();
         User userinDb= userService.findByUserName(username);
         userinDb.setUsername(user.getUsername());
         userinDb.setPassword(user.getPassword());
         userService.saveNewUser(userinDb);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
