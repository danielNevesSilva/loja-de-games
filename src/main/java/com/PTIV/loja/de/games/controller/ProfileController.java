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

    // configurando uma variável para o diretório das imagens dos produtos.
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/prescriptionImages";


    @GetMapping("/profile")
    public String viewProfile(Model model){
        //Exibir os detalhes existentes.

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
        //Obter o usuário atual.
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        //output the existing details
        model.addAttribute("lastName", customUserDetailsService.returnLastName());
        model.addAttribute("firstName", customUserDetailsService.returnFirstName());
        model.addAttribute("email", customUserDetailsService.returnUsername());

        //O objeto de transferência de dados conterá os atributos do Perfil do Cliente.
        model.addAttribute("customerProfileDTO", new CustomerProfileDTO());

        // Se o usuário já possui um Perfil de Cliente, exiba o número do carrinho.
        if(user.getCustomerProfile() != null){
            CustomerProfile customerProfile = customerProfileService.getCustomerProfile(
                    user.getCustomerProfile().getId()).get();
            Integer totalQuantity = shoppingCartService.cartCount(
                    shoppingCartService.listCartItems(customerProfile));
            model.addAttribute("cartCount", totalQuantity);
        }
        //Abrir a página de criação de perfil
        return "profileAdd";
    }

    @PostMapping("/profile/add")
    public String postAddCustomerProfile(@ModelAttribute("customerProfileDTO") CustomerProfileDTO customerProfileDTO) throws IOException {

        // Identificar o usuário
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        CustomerProfile customerProfile;
        //Se o perfil de cliente existir, então atribua este usuário a este perfil de cliente.
        if(user.getCustomerProfile() != null){
            Integer customerId = user.getCustomerProfile().getId();
            customerProfile = customerProfileService.getCustomerProfile(customerId).get();
        }else{
            //inicialize o customerProfile
            customerProfile = new CustomerProfile();
            //definir e id
            customerProfile.setId(customerProfileDTO.getId());
        }
        //definir todos os atributos
        customerProfile.setEmail(user.getEmail());
        customerProfile.setCep(customerProfileDTO.getCep());
        customerProfile.setRua(customerProfileDTO.getRua());
        customerProfile.setNumero(customerProfileDTO.getNumero());
        customerProfile.setBairro(customerProfileDTO.getBairro());
        customerProfile.setCidade(customerProfileDTO.getCidade());
        customerProfile.setUf(customerProfileDTO.getUf());
        customerProfile.setLogradouro(customerProfileDTO.getLogradouro());

        //definindo uma variável para o nome

        //se o objeto multipartFile não estiver vazio na passagem do front-end

        //isso escreve no banco de dados

        customerProfileService.addCustomerProfile(customerProfile);
        //isso define a chave estrangeira na tabela Usuários vinculando a nova entrada
        user.setCustomerProfile(customerProfile);
        //defina o CustomerProfile como este usuário
        customerProfile.setUser(user);
        //salvar o usuário
        userService.saveUser(user);
        //redirecionar de volta para o perfil
        return "redirect:/profile";
    }

    @GetMapping("/profile/update")
    public String getUpdateCustomerProfile(Model model){
        //pegar o usuário
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("email", user.getEmail());
        //obter o perfil de cliente do usuário
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
