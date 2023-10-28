package com.PTIV.loja.de.games.dto;

import com.PTIV.loja.de.games.model.UserRole;

public record RegisterDTO(String login, String password, UserRole role)  {
}
