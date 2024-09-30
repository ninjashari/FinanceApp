package com.finance.app.financeapp.service;

import com.finance.app.financeapp.dto.User;

public interface UserService {

    User registerUser(User user);

    User findByUsername(String username);
}
