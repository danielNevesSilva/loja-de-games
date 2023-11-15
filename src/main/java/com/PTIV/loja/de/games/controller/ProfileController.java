package com.PTIV.loja.de.games.controller;

import com.PTIV.loja.de.games.dto.CustomerProfileDTO;
import com.PTIV.loja.de.games.model.CustomerProfile;
import com.PTIV.loja.de.games.model.User;
import com.PTIV.loja.de.games.service.CustomUserDetailsService;
import com.PTIV.loja.de.games.service.CustomerProfileService;
import com.PTIV.loja.de.games.service.ShoppingCartService;
import com.PTIV.loja.de.games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ProfileController {
    @Autowired
    private CustomerProfileService customerProfileService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    // setting a variable for the directory of the product images
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/prescriptionImages";


    @GetMapping("/profile")
    public String viewProfile(Model model){
        //output the existing details

        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("email", user.getEmail());

        if(user.getCustomerProfile() != null){

            CustomerProfile customerProfile = customerProfileService.getCustomerProfile(user.getCustomerProfile().getId()).get();
            model.addAttribute("customerProfile", customerProfile);
            Integer totalQuantity = shoppingCartService.cartCount(shoppingCartService.listCartItems(customerProfile));
            model.addAttribute("cartCount", totalQuantity);
        }
        return "profile";
    }

    @GetMapping("/profile/add")
    public String getAddCustomerProfile(Model model){
        //get the current user
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        //output the existing details
        model.addAttribute("lastName", customUserDetailsService.returnLastName());
        model.addAttribute("firstName", customUserDetailsService.returnFirstName());
        model.addAttribute("email", customUserDetailsService.returnUsername());

        //the data transfer object will hold the attrbutes of the CustomerProfile
        model.addAttribute("customerProfileDTO", new CustomerProfileDTO());

        // if user already has a CustomerProfile, display the cart number
        if(user.getCustomerProfile() != null){
            CustomerProfile customerProfile = customerProfileService.getCustomerProfile(
                    user.getCustomerProfile().getId()).get();
            Integer totalQuantity = shoppingCartService.cartCount(
                    shoppingCartService.listCartItems(customerProfile));
            model.addAttribute("cartCount", totalQuantity);
        }
        //display the profile add page
        return "profileAdd";
    }

    @PostMapping("/profile/add")
    public String postAddCustomerProfile(@ModelAttribute("customerProfileDTO") CustomerProfileDTO customerProfileDTO) throws IOException {

        // get the user
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        CustomerProfile customerProfile;
        //if customerProfile exists then assign this user to this customerProfile
        if(user.getCustomerProfile() != null){
            Integer customerId = user.getCustomerProfile().getId();
            customerProfile = customerProfileService.getCustomerProfile(customerId).get();
        }else{
            //initalise the customerProfile
            customerProfile = new CustomerProfile();
            //set and id
            customerProfile.setId(customerProfileDTO.getId());
        }
        //set all the attributes
        customerProfile.setEmail(user.getEmail());
        customerProfile.setCep(customerProfileDTO.getCep());
        customerProfile.setRua(customerProfileDTO.getRua());
        customerProfile.setNumero(customerProfileDTO.getNumero());
        customerProfile.setBairro(customerProfileDTO.getBairro());
        customerProfile.setCidade(customerProfileDTO.getCidade());
        customerProfile.setUf(customerProfileDTO.getUf());
        customerProfile.setLogradouro(customerProfileDTO.getLogradouro());

        //setting a variable for the name

        //if the multipartFile object is not empty in passing from the front-end

        //this writes to the db
        customerProfileService.addCustomerProfile(customerProfile);
        //this sets the foreign key on the Users table linking the new entry
        user.setCustomerProfile(customerProfile);
        //set the CustomerProfile as the this user
        customerProfile.setUser(user);
        //save the suer
        userService.saveUser(user);
        //redirect back to the profile
        return "redirect:/profile";
    }

    @GetMapping("/profile/update")
    public String getUpdateCustomerProfile(Model model){
        //get the user
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("email", user.getEmail());
        //get the user's customer profile
        CustomerProfile customerProfile = customerProfileService.getCustomerProfile(user.getCustomerProfile().getId()).get();

        CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO();
        customerProfile.setCep(customerProfileDTO.getCep());
        customerProfile.setRua(customerProfileDTO.getRua());
        customerProfile.setNumero(customerProfileDTO.getNumero());
        customerProfile.setBairro(customerProfileDTO.getBairro());
        customerProfile.setCidade(customerProfileDTO.getCidade());
        customerProfile.setUf(customerProfileDTO.getUf());
        customerProfile.setLogradouro(customerProfileDTO.getLogradouro());

        Integer totalQuantity = shoppingCartService.cartCount(shoppingCartService.listCartItems(customerProfile));
        model.addAttribute("cartCount", totalQuantity);
        model.addAttribute("customerProfileDTO", customerProfileDTO);

        return "profileAdd";
    }
}
