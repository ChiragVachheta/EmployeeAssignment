package com.assignment.service;

import com.assignment.model.EndUser;
import com.assignment.model.Response;

public interface EndUserService {
    Response findByUsername(String username);

    Response saveUser(EndUser user);

    Response findByEmail(String email);
}
