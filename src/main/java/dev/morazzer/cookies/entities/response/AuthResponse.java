package dev.morazzer.cookies.entities.response;

/**
 * The auth response from the server.
 *
 * @param token The token of the client.
 */
public record AuthResponse(String token) {}
