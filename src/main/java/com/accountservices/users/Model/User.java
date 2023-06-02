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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private Date created;
    private Date updated;
    @Column(unique = true)
    private String email;
    private String password;
    private String First_name;
    private String Last_name;
    private String gender;
    private String phone_number;
    private String address;
    private String national_identity;
    private boolean is_active;

  @Enumerated(EnumType.STRING)
    private Role role;
}
