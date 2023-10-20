package com.PTIV.loja.de.games.exceptions;

public class CpfCadastradoException extends RuntimeException {
    public CpfCadastradoException(String email) {
        super("cpf já cadastrado: " + email);
    }
}
