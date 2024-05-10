package com.assignment.service;

import com.assignment.model.EndUser;
import com.assignment.repo.EndUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EndUserServiceImpl implements EndUserService {

    @Autowired
    EndUserRepository endUserRepository;

    @Override
    public EndUser findByUsername(String username) {
        return endUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found."));
    }

    @Override
    public EndUser saveUser(EndUser user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return endUserRepository.save(user);
    }

    @Override
    public EndUser findByEmail(String email) {
        return endUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found."));
    }
}
