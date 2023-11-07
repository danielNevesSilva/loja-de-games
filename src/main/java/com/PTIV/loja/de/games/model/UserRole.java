package com.PTIV.loja.de.games.model;

public enum UserRole {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER");
    private String role;
    UserRole(String role){
        this.role = role;
    }
    public String getRole(){
        return role;
    }
}
