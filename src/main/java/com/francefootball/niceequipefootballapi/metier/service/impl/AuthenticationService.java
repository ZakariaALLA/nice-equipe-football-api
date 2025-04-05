package com.francefootball.niceequipefootballapi.metier.service.impl;

import com.francefootball.niceequipefootballapi.metier.dto.LoginResponse;
import com.francefootball.niceequipefootballapi.metier.dto.UserDto;
import com.francefootball.niceequipefootballapi.metier.dto.UserLoginDto;
import com.francefootball.niceequipefootballapi.metier.enums.EnumRoles;
import com.francefootball.niceequipefootballapi.metier.exception.BusinessException;
import com.francefootball.niceequipefootballapi.persistence.model.Role;
import com.francefootball.niceequipefootballapi.persistence.model.User;
import com.francefootball.niceequipefootballapi.persistence.repository.RoleRepository;
import com.francefootball.niceequipefootballapi.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse signup(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user.getRoles().clear();

        userDto.getRoles().forEach(roleName -> {
            EnumRoles roleEnum = EnumRoles.fromLibelle(roleName.toUpperCase());
            Role role = roleRepository.findByName(roleEnum)
                    .orElseThrow(() -> new BusinessException("Role not found: " + roleName));
            user.addRole(role);
        });

        userRepository.save(user);
        return this.authenticate(userDto);
    }

    public LoginResponse authenticate(UserLoginDto userDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword()
                )
        );
        User user = userRepository.findByUsername(userDto.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        return new LoginResponse(
                jwtToken,
                jwtService.getExpirationTime()
        );
    }
}
