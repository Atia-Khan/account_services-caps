package com.accountservices.users.Repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.accountservices.users.Model.ForgotPassword;
@Repository
public interface ForgotRepo extends JpaRepository<ForgotPassword, Long> {
    Optional<ForgotPassword> findByEmailAndToken(String email, String token);
}