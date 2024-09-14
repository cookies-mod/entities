package dev.morazzer.cookies.entities.request;

/**
 * Entity to initialize authentication.
 * @param sharedSecret The secret created by the client.
 * @param username The username of the client.
 */
public record AuthRequest(String sharedSecret, String username) {
}
