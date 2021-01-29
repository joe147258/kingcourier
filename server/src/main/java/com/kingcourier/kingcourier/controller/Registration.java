package com.kingcourier.kingcourier.controller;

import com.kingcourier.kingcourier.domain.User;
import com.kingcourier.kingcourier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class Registration {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/check-username/{username}")
    public ResponseEntity<String>  checkUsername(@PathVariable String username) {
        if(userRepo.existsByUsername(username)) {
            return new ResponseEntity<String>("Username is taken.", HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<String>(HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> registerUser(@RequestParam String username, @RequestParam String publicKey) {
        int id = 0;
        while (userRepo.existsById(id)) {
            id++;
        }
        if(userRepo.existsByUsername(username)) return new ResponseEntity<String>("Username is taken.", HttpStatus.FORBIDDEN);
        User newUser = new User(id, username, publicKey);
        userRepo.save(newUser);

        return new ResponseEntity<String>(HttpStatus.CREATED);
    }
}
