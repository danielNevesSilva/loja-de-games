package com.PTIV.loja.de.games.service;

import com.PTIV.loja.de.games.model.User;
import com.PTIV.loja.de.games.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //the username is email
    public User getUserByUsername(String email) {
        return userRepository.getUserByUsername(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
