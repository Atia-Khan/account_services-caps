package com.accountservices.users.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accountservices.users.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT a FROM Users a where a.firstName like ?1 and a.lastName like ?2")
    List<User> findByFirstName(String first_name,String last_name);   

}
