package com.accountservices.users.Model;



import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity (name = "Users")
public class User {
    public User(String string, String string2, String string3, String string4, String string5, String string6,
      String string7, String string8, String string9, String string10, String string11, String string12) {
  }

    public User(String string, String string2) {
    }

    public User(String string, String string2, String string3) {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private Date created;
    private Date updated;
    @Column(unique = true)
    private String email;
    private String password;
    
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private String address;
    @Column(unique = true)
    private String nationalId;
    private boolean is_active;

  @Enumerated(EnumType.STRING)
    private Role role;
}
