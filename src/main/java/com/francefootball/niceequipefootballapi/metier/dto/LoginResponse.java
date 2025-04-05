package com.francefootball.niceequipefootballapi.metier.dto;

public record LoginResponse(String token, long expiresIn) {
}
