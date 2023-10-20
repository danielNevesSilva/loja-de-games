package com.PTIV.loja.de.games.exceptions;

public class EmailCadastradoException extends RuntimeException {
    public EmailCadastradoException(String email) {
        super("E-mail já cadastrado: " + email);
    }
}
