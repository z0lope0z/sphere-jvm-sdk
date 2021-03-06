package io.sphere.sdk.client;

/**
 * The SPHERE.IO API is currently not available.
 *
 */
public class ServiceUnavailableException extends ServerErrorException {
    private static final long serialVersionUID = 0L;
    private static final int STATUS_CODE = 503;

    public ServiceUnavailableException(final String message) {
        super(message, STATUS_CODE);
    }

    public ServiceUnavailableException() {
        super(STATUS_CODE);
    }
}
