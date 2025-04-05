package com.francefootball.niceequipefootballapi.metier.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class UserDto extends UserLoginDto {
    Set<String> roles;

    public UserDto(String username, String password) {
        super(username, password);
    }
}

