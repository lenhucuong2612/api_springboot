package com.example.my_demo.security;

import com.example.my_demo.entity.User;
import com.example.my_demo.exceptions.ResourceNotFoundException;
import com.example.my_demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //loading user from database by username
        User user=this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User","Email: "+username,0));
        System.out.println("Role"+user.getRoles());
        return new CustomerUserDetail(user);
    }
}
