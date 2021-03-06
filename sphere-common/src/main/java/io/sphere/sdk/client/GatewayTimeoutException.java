package io.sphere.sdk.client;

/**
 * This error might occur on long running processes
 * such as deletion of resources with connections to other resources.
 */
public class GatewayTimeoutException extends ServerErrorException {
    private static final long serialVersionUID = 0L;
    private static final int STATUS_CODE = 504;

    public GatewayTimeoutException(final String message) {
        super(message, STATUS_CODE);
    }

    public GatewayTimeoutException() {
        super(STATUS_CODE);
    }
}
