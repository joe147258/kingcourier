package com.kingcourier.kingcourier.controller;

import com.kingcourier.kingcourier.domain.User;
import com.kingcourier.kingcourier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class Registration {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/check-username/{username}")
    public Boolean checkUsername(@PathVariable String username) {
        if(userRepo.existsByUsername(username)) {
            return false;
        } else {
            return true;
        }
    }

    @PostMapping("/")
    public String registerUser(@RequestParam String username, @RequestParam String publicKey) {
        if(userRepo.existsByUsername(username)) return "Error";

        int id = 0;
        while (userRepo.existsById(id)) {
            id++;
        }

        User newUser = new User(id, username, publicKey);
        userRepo.save(newUser);
        // TODO: Have a shared common library with shared communication codes (enums) Not sure how this works in java.
        return "Success";
    }
}
