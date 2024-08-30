package dev.morazzer.cookies.entities.request;

public record AuthRequest(String sharedSecret, String username) {
}
