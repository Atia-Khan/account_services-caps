package com.accountservices.users.Controllers;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public void login(@RequestBody User user) {
        Optional<User> optional = userRepo.findByEmail(user.getEmail());
        if (optional.isPresent()) {
            User userDb = optional.get();
            if (userDb.getPassword().equals(user.getPassword())) {
                System.out.println("Login successful");
            }
        }
    }
}

