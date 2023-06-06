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
    @Autowired
    private ObjectMapper objectMapper;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(new User("2023-06-01", "2023-06-02", "john@example.com", "password123", "John", "Doe", "Male",
                "1234567890", "123 Main Street, City", "ABC123XYZ", "true", "USER"));

        userList.add(new User("2023-06-01", "2023-06-02", "john@example.com", "password123", "John", "Doe", "Male",
                "1234567890", "123 Main Street, City", "ABC123XYZ", "true", "USER"));

        when(userRepo.findAll()).thenReturn(userList);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostUser() throws Exception {
        User user = (new User("2023-06-01", "2023-06-02", "john@example.com", "password123", "John", "Doe", "Male",
                "1234567890", "123 Main Street, City", "ABC123XYZ", "true", "USER"));
        ;

        mockMvc.perform(post("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isOk());

    }
    // @Test
    // public void testLogin_ValidCredentials() throws Exception {
    //     User user = new User();
    //     user.setEmail("test@example.com");
    //     user.setPassword("password");

    //     User userDb = new User();
    //     userDb.setEmail("test@example.com");
    //     userDb.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));

    //     given(userRepo.findByEmail(user.getEmail())).willReturn(Optional.of(userDb));

    //     mockMvc.perform(post("/user/login")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(user)))
    //             .andExpect(status().isOk());
    // }
    @Test
    public void testLogin_ValidCredentials() throws Exception {
        User user = (new User("2023-06-01", "2023-06-02", "john@example.com", "password123", "John", "Doe", "Male",
                "1234567890", "123 Main Street, City", "ABC123XYZ", "true", "USER"));
        
        User userDb = (new User("2023-06-01", "2023-06-02", "john@example.com", "password123", "John", "Doe", "Male",
                "1234567890", "123 Main Street, City", "ABC123XYZ", "true", "USER"));
        

        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(userDb));

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isOk());

    }

    @Test
    public void testLogin_IncorrectPassword() throws Exception {
        User user = new User("john@example.com", "wrongpassword");

        User userDb = new User("John", "john@example.com", "$2a$10$abcde...");

        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(userDb));

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isOk());

        
    }

    @Test
    public void testLogin_UserNotFound() throws Exception {
        User user = (new User("2023-06-01", "2023-06-02", "john@example.com", "password123", "John", "Doe", "Male",
                "1234567890", "123 Main Street, City", "ABC123XYZ", "true", "USER"));

        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isOk());

        
    }

    // Utility method to convert objects to JSON strings
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
