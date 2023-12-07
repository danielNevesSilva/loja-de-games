package com.PTIV.loja.de.games.controller;


import com.PTIV.loja.de.games.dto.AuthenticationDTO;
import com.PTIV.loja.de.games.dto.RegisterDTO;
import com.PTIV.loja.de.games.dto.UserDto;
import com.PTIV.loja.de.games.exceptions.EmailCadastradoException;
import com.PTIV.loja.de.games.model.Role;
import com.PTIV.loja.de.games.model.User;
import com.PTIV.loja.de.games.repository.RoleRepository;
import com.PTIV.loja.de.games.repository.UserRepository;
import com.PTIV.loja.de.games.service.RoleService;
import com.PTIV.loja.de.games.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //Pagina login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //Pagina registro
    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    //Erro na página de registro
    @GetMapping("/registerError")
    public String getRegisterError() {
        return "registerError";
    }

    // postar detalhes para registrar um novo cliente
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, BindingResult result) {

        if (isEmailAlreadyRegistered(user.getEmail())) {
            result.rejectValue("email", null, "Já tem uma conta registrada com esse email");
        }
        String rawData = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(rawData));

        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>()); // Inicialize a lista de papéis
        }

        if (userRepository.count() == 0) {
            Role admin = roleRepository.findByName("ADMIN");
            if (admin != null) {
                user.getRoles().add(admin);
            }
        } else {
            Role customer = roleRepository.findByName("CUSTOMER");
            if (customer != null) {
                user.getRoles().add(customer);
            }
        }
        userRepository.save(user);
        return "redirect:/login";
    }
    private boolean isEmailAlreadyRegistered (String email){
        return userRepository.getUserByUsername(email) != null;
    }

}