package com.PTIV.loja.de.games.service;

import com.PTIV.loja.de.games.model.Funcionario;

import java.util.List;

public interface FuncionarioService{

    void salvarFuncionario(Funcionario funcionario);

    Funcionario buscarFuncionarioPorId(Long id);

    List<Funcionario> listarFuncionarios();

    void deletarFuncionario(Long id);

    Funcionario findByEmail(String email);

    void atualizarFuncionario(Funcionario funcionario);

    void alterarStatus(Long id, String status);

    String getFuncaoByEmail(String email);

}
