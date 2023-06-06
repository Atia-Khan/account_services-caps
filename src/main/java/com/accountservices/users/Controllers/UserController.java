package com.accountservices.users.Controllers;
import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountservices.users.Model.User;
import com.accountservices.users.Repositories.UserRepository;
@CrossOrigin("*")
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
            }else {
                return ResponseEntity.ok("Incorrect Password!!!");
            }
        } 

        else {
            return ResponseEntity.ok("User not found.");
        }
    }

}

