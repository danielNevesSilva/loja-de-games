package com.PTIV.loja.de.games.service;

import com.PTIV.loja.de.games.model.CustomerUserDetails;
import com.PTIV.loja.de.games.model.User;
import com.PTIV.loja.de.games.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //method to get the user by email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.getUserByUsername(email);
        if(user == null){
            throw new UsernameNotFoundException("O email não existe");
        }
        // Verifique a função do usuário (sua lógica pode variar)
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            System.out.println("è um admin da porra toda");
        }else{
            authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
            System.out.println("è um zé ruela");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public String returnUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public String returnFirstName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        return user.getFirstName();
    }

    public String returnLastName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        return user.getLastName();
    }

    public Integer returnId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        return user.getId();
    }
}
