package io.sphere.sdk.errors;

public class InvalidTokenException extends UnauthorizedException {
    private static final long serialVersionUID = 0L;

    public InvalidTokenException() {
        super("Invalid token for request.");
    }
}
