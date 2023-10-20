package com.PTIV.loja.de.games.repository;

import com.PTIV.loja.de.games.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository <Funcionario, Long> {

    Funcionario findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}
