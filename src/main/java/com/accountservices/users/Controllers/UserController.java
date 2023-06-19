package com.accountservices.users.Controllers;
    
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.accountservices.users.Model.ForgotPassword;
import com.accountservices.users.Model.User;
import com.accountservices.users.Repositories.ForgotRepo;
import com.accountservices.users.Repositories.UserRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private ForgotRepo forgotRepo;


    @GetMapping("")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/signup")
    public void postUser(@RequestBody User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
         user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    @PostMapping("/ForgotPassword/{email}")
    public ResponseEntity<Map<String, String>> forgotPassword(@PathVariable("email") String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "yes Present");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "User not found");
            return ResponseEntity.ok(response);
        }
    }
    @PostMapping("/ForgotPassword/token")
    public ResponseEntity<String> saveForgotPasswordToken(@RequestBody ForgotPassword forgotPassword) {
        long expirationTimeMillis = System.currentTimeMillis() + (3 * 60 * 1000); // Set expiration time to 3 minutes from now
        forgotPassword.setExpirationTime(expirationTimeMillis);
        forgotRepo.save(forgotPassword);
        return ResponseEntity.ok("Data Saved");
    }

    // @PostMapping("/ForgotPassword/token")
    // public ResponseEntity<String> saveForgotPasswordToken(@RequestBody ForgotPassword forgotPassword) {
    //     forgotRepo.save(forgotPassword);
    //     return ResponseEntity.ok("Data Saved");
    // }

    // @PostMapping("/forgotpassword/update")
    // public ResponseEntity<String> updatePassword(@RequestBody ForgotPassword request) {
    //     Optional<ForgotPassword> optionalForgotPassword = forgotRepo.findByEmailAndToken(request.getEmail(), request.getToken());

    //     if (optionalForgotPassword.isPresent()) {
    //         Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

    //         if (optionalUser.isPresent()) {
    //             User user = optionalUser.get();
    //             String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
    //             user.setPassword(hashedPassword);
    //             userRepository.save(user);
    //             return ResponseEntity.ok("Password updated successfully!");
    //         } else {
    //             return ResponseEntity.ok("User not found.");
    //         }
    //     } else {
    //         return ResponseEntity.ok("Invalid email or token.");
    //     }
    // }
    @PostMapping("/forgotpassword/update")
    public ResponseEntity<String> updatePassword(@RequestBody ForgotPassword request, String password) {
        Optional<ForgotPassword> optionalForgotPassword = forgotRepo.findByEmailAndToken(request.getEmail(), request.getToken());

        if (optionalForgotPassword.isPresent()) {
            Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
                    user.setPassword(hashedPassword);
                    userRepository.save(user);
                    return ResponseEntity.ok("Password updated successfully!");
                } else {
                    return ResponseEntity.ok("User not found.");
                }
            }
        else {
            return ResponseEntity.ok("Invalid email or token.");
        }
    }


        // @PostMapping("/login")
        // public ResponseEntity<Object> login(@RequestBody User user) {
        //     Optional<User> optional = userRepository.findByEmail(user.getEmail());

        //     if (optional.isPresent()) {
        //         User userDb = optional.get();
        //         if (user.getPassword().equals(userDb.getPassword())) {
        //             Map<String, Object> responseJson = new HashMap<>();
        //             responseJson.put("message", "Login successful");
        //             responseJson.put("role", userDb.getRole());
        //             return ResponseEntity.ok(responseJson);
        //         } else {
        //             Map<String, Object> errorResponse = new HashMap<>();
        //             errorResponse.put("error", "Invalid credentials");
        //             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        //         }
        //     } else {
        //         Map<String, Object> errorResponse = new HashMap<>();
        //         errorResponse.put("error", "User not found");
        //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        //     }
        // }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        Optional<User> optional = userRepository.findByEmail(user.getEmail());

        if (optional.isPresent()) {
            User userDb = optional.get();
            if (BCrypt.checkpw(user.getPassword(), userDb.getPassword())) {
                Map<String, Object> responseJson = new HashMap<>();
                responseJson.put("message", "Login successful");
                responseJson.put("role", userDb.getRole());
                return ResponseEntity.ok(responseJson);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
    }
    
    // @PostMapping("/login")
    // public ResponseEntity<Object> login(@RequestBody User user) {
    //     Optional<User> optional = userRepository.findByEmail(user.getEmail());

    //     if (optional.isPresent()) {
    //         User userDb = optional.get();
    //         if (BCrypt.checkpw(user.getPassword(), userDb.getPassword()))
    //         {
    //             if(user.getPassword().equals(userDb.getPassword())) {
    //                 Map<String, Object> responseJson = new HashMap<>();
    //                 responseJson.put("message", "Login successful");
    //                 responseJson.put("role", userDb.getRole());
    //              return ResponseEntity.ok(responseJson);
    //             }
    //         } else {
    //             return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("User not found!!");
    //         }
    //     } else {    
    //         return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("User not found!!");
    //     }
    //     return null;
    // }


    @DeleteMapping("/delete")
    public String deleteUser(User user) {
        userRepository.delete(user);
        return "User deleted";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User with ID has been deleted!";
    }

    @PostMapping("/update/{id}")
    public String updateById(@PathVariable Long id, @RequestBody User newUser) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser != null) {
            existingUser.setId(newUser.getId());
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

            userRepository.save(existingUser);
        }

        return "User details updated successfully!";
    }
}
