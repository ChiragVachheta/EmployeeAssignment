package com.assignment.service;

import com.assignment.model.EndUser;

public interface EndUserService {
    EndUser findByUsername(String username);

    EndUser saveUser(EndUser user);

    EndUser findByEmail(String email);
}
