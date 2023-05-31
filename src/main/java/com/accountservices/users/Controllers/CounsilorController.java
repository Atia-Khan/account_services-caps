package com.accountservices.users.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accountservices.users.Model.Counsilor;
import com.accountservices.users.Repositories.CounsilorRepository;
@RestController
@RequestMapping("/counsilor")
public class CounsilorController {
    @Autowired
    private CounsilorRepository counsilorRepo;

    @GetMapping("")
    public List<Counsilor> getCounsilor(){
      return counsilorRepo.findAll();

    }

}
