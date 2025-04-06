package com.francefootball.niceequipefootballapi.controller;

import com.francefootball.niceequipefootballapi.metier.dto.LoginResponse;
import com.francefootball.niceequipefootballapi.metier.dto.UserDto;
import com.francefootball.niceequipefootballapi.metier.dto.UserLoginDto;
import com.francefootball.niceequipefootballapi.metier.service.impl.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.francefootball.niceequipefootballapi.PathRequest.PATH_AUTH;

@AllArgsConstructor
@RequestMapping(PATH_AUTH)
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> register(@RequestBody UserDto registerUserDto) {
        return new ResponseEntity<>(authenticationService.signup(registerUserDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody UserLoginDto loginUserDto) {
        return ResponseEntity.ok(authenticationService.authenticate(loginUserDto));
    }
}
