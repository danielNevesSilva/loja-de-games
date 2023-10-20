package com.PTIV.loja.de.games.model;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
@Table(name = "funcionarios")
public class Funcionario implements Serializable {

    private static final long serialVersionUID =1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "password")
    private String password;
    @Column(name = "funcao")
    private String funcao ;
    @Column(name = "status", columnDefinition = "VARCHAR(255) DEFAULT 'Ativo'")
    private String status;



    public Funcionario (){
        this.status = "Ativo";
    }
    public Funcionario(Long id, String username, String email, String cpf, String password, String funcao, String status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.funcao = funcao;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
