package com.assignment.service;

import com.assignment.model.EndUser;
import com.assignment.model.Response;
import com.assignment.repo.EndUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EndUserServiceImpl implements EndUserService {
    private static final Logger logger = LoggerFactory.getLogger(EndUserServiceImpl.class);
    @Autowired
    EndUserRepository endUserRepository;

    @Override
    public Response findByUsername(String username) {
        Response response = new Response();
        try{
            EndUser endUser = endUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found."));
            response.setData(endUser);
            return response;
        }catch (Exception ex){
            response.setError(ex.getMessage());
            logger.error("findByUsername() ",ex.getStackTrace());
            return response;
        }
    }

    @Override
    public Response saveUser(EndUser user){
        Response response = new Response();
        Optional<EndUser> byEmail = endUserRepository.findByEmail(user.getEmail());
        if(byEmail.isPresent()){
            response.setData(byEmail.get());
            return response;
        }else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
            EndUser save = endUserRepository.save(user);
            response.setData(save);
            return response;
        }
    }
    @Override
    public Response findByEmail(String email) {
        Response response = new Response();
        try{
            EndUser endUser = endUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found."));
            response.setData(endUser);
            return response;
        }catch (Exception ex){
            response.setError(ex.getMessage());
            return response;
        }
    }
}
