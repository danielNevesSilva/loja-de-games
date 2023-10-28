package com.PTIV.loja.de.games.controller;


import com.PTIV.loja.de.games.dto.AuthenticationDTO;
import com.PTIV.loja.de.games.dto.RegisterDTO;
import com.PTIV.loja.de.games.dto.UserDto;
import com.PTIV.loja.de.games.model.User;
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

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    //login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid Model model) {

            return "index";
    }

    //register page
    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    //error in register page
    @GetMapping("/registerError")
    public String getRegisterError() {
        return "registerError";
    }

    // post details for registering a new customer
    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model) {
        User existing = userService.getUserByUsername(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        // Faça a conversão de UserDto para User
        User userEntity = new User(user);

        userService.saveUser(userEntity);
        return "redirect:/register?success";
    }
}