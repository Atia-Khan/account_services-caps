package com.accountservices.users;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import com.accountservices.users.Model.Role;
import com.accountservices.users.Model.User;


public class UserTests {

    private User user;

    @BeforeEach
    public void setUp(){
        user = new User();
    }
    
    @Test
    void testingUserId(){
    
        long userId =  5L;
        user.setUserId(userId);
        assertEquals(userId, user.getUserId());
    }

    @Test
    void testingCreatedDate(){
        
        String createdDate = "2023-06-23";
        Date date = Date.valueOf(createdDate);
        user.setCreated(date);
        assertEquals(date, user.getCreated());
        
    }
   
    @Test
    void testingUpdatedDate(){
        
        String updatedDate = "2022-05-28";
        Date date = Date.valueOf(updatedDate);
        user.setUpdated(date);
        assertEquals(date, user.getUpdated());
    }

    @Test
    void testingEmail(){
      
        String email = "atia.khan@xloopdigital.com";
        user.setEmail("atia.khan@xloopdigital.com");
        assertEquals(email, user.getEmail());

    }


@Test
    void testingPassword(){
  String password = "pass";
  user.setPassword(password);
  assertEquals(password, user.getPassword());
    }
@Test
void testingFirstName(){
    String firstName ="Atia";
    user.setFirstName(firstName);
    assertEquals(firstName, user.getFirstName());
}

@Test
void testingLastName(){
    String lastName ="Khan";
    user.setLastName(lastName);
    assertEquals(lastName, user.getLastName());
}
@Test
void testingGender(){
    String gender = "female";
    user.setGender(gender);
    assertEquals(gender, user.getGender());

}

@Test 
void testingPhoneNumber(){
    String phoneNumber = "323565214";
    user.setPhoneNumber(phoneNumber);
    assertEquals(phoneNumber, user.getPhoneNumber());
}


@Test
void testingAddress(){
    String address = "address abc";
    user.setAddress(address);
    assertEquals(address, user.getAddress());
}

@Test 
void testingNI(){
    String ni = "1234556";
    user.setNationalId(ni);
    assertEquals(ni, user.getNationalId());
}

@Test
void testingIs_active(){
Boolean is_active = true;
user.set_active(is_active);
assertEquals(is_active, user.is_active());
}

@Test
void testingRole(){
    Role adminRole = Role.ADMIN;
    assertEquals("ADMIN", adminRole.toString());

    Role counselorRole = Role.COUNSELOR;
    assertEquals("COUNSELOR", counselorRole.toString());
    
    Role patientRole = Role.PATIENT;
    assertEquals("PATIENT", patientRole.toString());
}
}

