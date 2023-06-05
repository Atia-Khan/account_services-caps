package com.accountservices.users.Controllers;
import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountservices.users.Model.User;
import com.accountservices.users.Repositories.UserRepository;
@CrossOrigin("*                                               ")
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
<<<<<<< HEAD
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
=======
        String password = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(password);
>>>>>>> 67a04bb (refactored)
        this.userRepo.save(user);
    }    
  
    @PostMapping("/login")
    public void login(@RequestBody User user) {
        Optional<User> optional = userRepo.findByEmail(user.getEmail());

        if (optional.isPresent()) {
            User userDb = optional.get();
            if (BCrypt.checkpw(user.getPassword(), userDb.getPassword())) {
                System.out.println("Login successful");
            }else {
                System.out.println("Incorrect Password!!!");
            }
        } 

        else {
            System.out.println("User not found.");
        }
    }

}

