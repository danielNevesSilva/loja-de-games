package com.PTIV.loja.de.games.service;

import com.PTIV.loja.de.games.exceptions.CpfCadastradoException;
import com.PTIV.loja.de.games.exceptions.EmailCadastradoException;
import com.PTIV.loja.de.games.model.Funcionario;
import com.PTIV.loja.de.games.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public void salvarFuncionario(Funcionario funcionario) {
        String email = funcionario.getEmail();
        String cpf = funcionario.getCpf();

        if (funcionarioRepository.existsByEmail(email)) {
            throw new EmailCadastradoException(email);
        }else if (funcionarioRepository.existsByCpf(cpf)){
            throw new CpfCadastradoException(cpf);
        }

        funcionarioRepository.save(funcionario);
    }

    @Override
    public Funcionario buscarFuncionarioPorId(Long id) {
        return funcionarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.findAll();
    }

    @Override
    public void deletarFuncionario(Long id) {

    }

    @Override
    public Funcionario findByEmail(String email) {

        return funcionarioRepository.findByEmail(email);
    }

    @Override
    public void atualizarFuncionario(Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
    }

    @Override
    public void alterarStatus(Long id, String novoStatus) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        funcionario.setStatus(novoStatus);
        funcionarioRepository.save(funcionario);
    }

    public String getFuncaoByEmail(String email) {
        Funcionario funcionario = funcionarioRepository.findByEmail(email);
        return (funcionario != null) ? funcionario.getFuncao() : null;
    }
}
