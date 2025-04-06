package com.francefootball.niceequipefootballapi.metier.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto extends UserLoginDto {
    Set<String> roles;

    public UserDto(String username, String password) {
        super(username, password);
    }
}

