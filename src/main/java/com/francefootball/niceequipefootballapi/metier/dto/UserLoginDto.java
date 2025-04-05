package com.francefootball.niceequipefootballapi.metier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginDto{
        private String username;
        private String password;
}
