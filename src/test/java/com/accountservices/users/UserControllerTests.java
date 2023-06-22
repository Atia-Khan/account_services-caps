package com.accountservices.users;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.accountservices.users.Controllers.UserController;
import com.accountservices.users.Model.User;
import com.accountservices.users.Repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)




public class UserControllerTests {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepo;
  
    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetAllUsers() throws Exception {

        User user1 = new User(1L, null, null, "Atia@xloopdigital.com", "12345", "Atia", "Khan", "Female", "032416544", "Maven", "6544543555", false, null);
        User user2 = new User(1L, null, null, "Atia@xloopdigital.com", "12345", "Atia", "Khan", "Female", "032416544", "Maven", "6544543555", false, null);
        List<User> userList = new ArrayList<>();

        userList.add(user1);
        userList.add(user2);

        when(userRepo.findAll()).thenReturn(userList);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostUser() throws Exception {
        User user1 = new User(1L, null, null, "Atia@xloopdigital.com", "12345", "Atia", "Khan", "Female", "032416544", "Maven", "6544543555", false, null);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(user1);
        mockMvc.perform(post("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogin_ValidCredentials() throws Exception {
        User user1 = new User(1L, null, null, "Atia@xloopdigital.com", "12345", "Atia", "Khan", "Female", "032416544", "Maven", "6544543555", false, null);
        User userDb = new User(1L, null, null, "Atia@xloopdigital.com", BCrypt.hashpw("12345", BCrypt.gensalt()), "Atia", "Khan", "Female", "032416544", "Maven", "6544543555", false, null);
    
        when(userRepo.findByEmail(user1.getEmail())).thenReturn(Optional.of(userDb));
    
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user1)))
                .andExpect(status().isOk());
    }
  

    // @Test
    // public void testLogin_IncorrectPassword() throws Exception {
    //     String email = "john@example.com";
    //     String plainPassword = "123456789";
    //     String hashedPassword = BCrypt.hashpw("mypassword", BCrypt.gensalt());
        

    //     User user = new User(email, plainPassword);
    //     User userDb = new User("another@example.com", hashedPassword); // Using a different email to simulate user not found


    //     when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(userDb));

    //     mockMvc.perform(post("/user/login")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(asJsonString(user)))
    //             .andExpect(status().isOk())
    //             .andExpect(content().string("Incorrect Password!!!"));
    // }


    // @Test
    // public void testLogin_UserNotFound() throws Exception {
    //     User user = (new User(1L, null, null, "Atia@xloopdigital.com", "12345", "Atia", "Khan", "Female", "032416544", "Maven", "6544543555", false, null));
    //     User userDb = new User(1L, null, null, "Atia@xloopdigital.com", BCrypt.hashpw("12345", BCrypt.gensalt()), "Atia", "Khan", "Female", "032416544", "Maven", "6544543555", false, null);

    //     when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());

    //     mockMvc.perform(post("/user/login")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(asJsonString(user)))
    //             .andExpect(status().isOk());

        
    // }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
