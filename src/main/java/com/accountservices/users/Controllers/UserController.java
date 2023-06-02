package com.accountservices.users.Controllers;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accountservices.users.Model.User;
import com.accountservices.users.Repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserRepository userRepo;

    @GetMapping("")
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    @PostMapping("/signup")
    public void post_User(@RequestBody User user){
        this.userRepo.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        // Optional<User> optionalUser = userRepo.findById(id);
        // User user = optionalUser.get();
        Long id=user.getUserId();
        Optional<User> optional=userRepo.findById(id);

        User userDb = 

        if(user.getEmail().equals(user) && user.getPassword().equals(user)){
            return ResponseEntity.ok("Login successful");
        }
        
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
        
    }    
}

