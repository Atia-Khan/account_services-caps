package com.accountservices.users.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accountservices.users.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
