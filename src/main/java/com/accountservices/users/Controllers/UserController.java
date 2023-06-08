package com.accountservices.users.Controllers;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.accountservices.users.Model.User;
import com.accountservices.users.Repositories.UserRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @PostMapping("/signup")
    public void post_User(@RequestBody User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        this.userRepo.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        Optional<User> optional = userRepo.findByEmail(user.getEmail());

        if (optional.isPresent()) {
            User userDb = optional.get();
            if (BCrypt.checkpw(user.getPassword(), userDb.getPassword())) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.ok("Incorrect Password!!!");
            }
        } else {
            return ResponseEntity.ok("User not found.");
        }
    }

    @PostMapping("/update")
    public String updateUsers(@RequestBody User user) {
        this.userRepo.save(user);
        return "user details updated";
    }

    @DeleteMapping("/delete")
    public String deleteUser(User user) {
        this.userRepo.delete(user);
        return "user deleted";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        this.userRepo.deleteById(id);
        return "User of Id has been deleted!!";

    }

    @PostMapping("/update/{id}")
    public String updateById(@PathVariable Long id, @RequestBody User newUser) {
        User existingUser = userRepo.findById(id).orElse(null);

        if (existingUser != null) {
            existingUser.setUserId(newUser.getUserId());
            existingUser.setCreated(newUser.getCreated());
            existingUser.setUpdated(newUser.getUpdated());
            existingUser.setEmail(newUser.getEmail());
            existingUser.setAddress(newUser.getAddress());
            existingUser.setFirstName(newUser.getFirstName());
            existingUser.setLastName(newUser.getLastName());
            existingUser.setGender(newUser.getGender());
            existingUser.setNationalId(newUser.getNationalId());
            existingUser.setPassword(newUser.getPassword());
            existingUser.setPhoneNumber(newUser.getPhoneNumber());
            existingUser.setRole(newUser.getRole());
            existingUser.set_active(newUser.is_active());

            userRepo.save(newUser);

        }

        return "User Details Updated Successfully!!";
    }

    @PostMapping("/entity")
    public List<User> getEntity(@RequestBody User body) {
        
        return userRepo.findByFirstName(body.getFirstName(),body.getLastName());
    }

}
