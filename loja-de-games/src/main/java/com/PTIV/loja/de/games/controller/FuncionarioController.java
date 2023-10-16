package com.PTIV.loja.de.games.controller;

import com.PTIV.loja.de.games.exceptions.CpfCadastradoException;
import com.PTIV.loja.de.games.exceptions.EmailCadastradoException;
import com.PTIV.loja.de.games.model.Funcionario;
import com.PTIV.loja.de.games.service.FuncionarioService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/funcionario")
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/cadastro")
    public ModelAndView mostrarFormularioCadastro(@RequestParam(required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView("cadastro");

        if (id != null) {
            // É uma operação de alteração, busque o funcionário
            Funcionario funcionario = funcionarioService.buscarFuncionarioPorId(id);
            modelAndView.addObject("funcionario", funcionario);
        } else {
            // É uma operação de cadastro, crie um novo funcionário
            modelAndView.addObject("funcionario", new Funcionario());
        }

        return modelAndView;
    }

    @PostMapping("/cadastro")
    public String processarFormularioCadastro(@ModelAttribute Funcionario funcionario, Model model) {
        try {
            funcionarioService.salvarFuncionario(funcionario);
            return "redirect:/funcionario/lista";
        } catch (EmailCadastradoException e) {
            model.addAttribute("error", "E-mail já cadastrado");
        } catch (CpfCadastradoException e) {
            model.addAttribute("error", "CPF já cadastrado");
        }

        return "cadastro";
    }

    @GetMapping("/lista")
    public ModelAndView mostrarListaFuncionarios(Model model) {
        ModelAndView modelAndView = new ModelAndView("listaFuncionarios");
        List<Funcionario> funcionarios = funcionarioService.listarFuncionarios(); // Supondo que você tenha um serviço para lidar com as operações de funcionários
        model.addAttribute("funcionarios", funcionarios);
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView mostrarFormularioLogin() {
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView processarLogin(@RequestParam String email, @RequestParam String password, Model model) {
        Funcionario funcionario = funcionarioService.findByEmail(email);

        if (funcionario != null && funcionario.getPassword().equals(password)) {
            // Login bem-sucedido
            System.out.println("Funcionario: " + funcionario); // Verifica o conteúdo do objeto
            model.addAttribute("funcionario", funcionario);
            return new ModelAndView("redirect:/funcionario/dashboard");

        } else {
            // Login falhou
            model.addAttribute("error", "Credenciais inválidas");
            return new ModelAndView("login");
        }
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView mostrarFormularioAlteracao(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cadastro");
        Funcionario funcionario = funcionarioService.buscarFuncionarioPorId(id);
        modelAndView.addObject("funcionario", funcionario);
        return modelAndView;
    }

    @PostMapping("/alterar")
    public String processarFormularioEdicao(@ModelAttribute Funcionario funcionario) {
        funcionarioService.atualizarFuncionario(funcionario);
        return "redirect:/funcionario/lista";
    }
    @PostMapping("/alterarStatusFuncionario")
    @ResponseBody
    public ResponseEntity<String> alterarStatus(@RequestParam Long id, @RequestParam String status) {
        try {
            funcionarioService.alterarStatus(id, status);
            return ResponseEntity.ok("Status do funcionário alterado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao alterar o status do funcionário.");
        }
    }
    @GetMapping("/dashboard")
    public ModelAndView mostrarDashboard() {
            return new ModelAndView("backoffice");
    }

}