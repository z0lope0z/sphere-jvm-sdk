package io.sphere.sdk.client;

import com.ning.http.client.*;
import io.sphere.sdk.http.*;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

final class NingHttpClientAdapterImpl extends AutoCloseableService implements NingHttpClientAdapter {
    private static final SphereInternalLogger LOGGER = SphereInternalLogger.getLogger(NingHttpClientAdapterImpl.class);
    private final AsyncHttpClient asyncHttpClient;
    private final ForkJoinPool threadPool = new ForkJoinPool();

    private NingHttpClientAdapterImpl(final AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
    }

    @Override
    public CompletableFuture<HttpResponse> execute(final HttpRequest httpRequest) {
        LOGGER.debug(() -> "executing " + httpRequest);
        final Request request = asNingRequest(httpRequest);
        final CompletableFuture<Response> future = wrap(asyncHttpClient.executeRequest(request));
        return future.thenApply((Response response) -> {
            final byte[] responseBodyAsBytes = getResponseBodyAsBytes(response);
            Optional<byte[]> body = responseBodyAsBytes.length > 0 ? Optional.of(responseBodyAsBytes) : Optional.empty();
            final HttpResponse httpResponse = HttpResponse.of(response.getStatusCode(), body, Optional.of(httpRequest), HttpHeaders.of(response.getHeaders()));
            LOGGER.debug(() -> "response " + httpResponse);
            return httpResponse;
        });
    }

    private byte[] getResponseBodyAsBytes(final Response response) {
        try {
            return response.getResponseBodyAsBytes();
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }

    /* package scope for testing */
    <T> Request asNingRequest(final HttpRequest request) {
        final RequestBuilder builder = new RequestBuilder()
                .setUrl(request.getUrl())
                .setMethod(request.getHttpMethod().toString());

        request.getHeaders().getHeadersAsMap().forEach((name, values) -> values.forEach( value -> builder.addHeader(name, value)));

        request.getBody().ifPresent(body -> {
            if (body instanceof StringHttpRequestBody) {
                final String bodyAsString = ((StringHttpRequestBody) body).getString();
                builder.setBodyEncoding(StandardCharsets.UTF_8.name()).setBody(bodyAsString);
            } else if (body instanceof FileHttpRequestBody) {
                builder.setBody(((FileHttpRequestBody) body).getFile());
            } else if (body instanceof FormUrlEncodedHttpRequestBody) {
                ((FormUrlEncodedHttpRequestBody) body).getData().forEach((name, value) -> builder.addQueryParam(name, value));
            }
        });
        final Request build = builder.build();
        return build;
    }

    @Override
    protected void internalClose() {
        closeQuietly(asyncHttpClient);
        threadPool.shutdown();
    }

    public static NingHttpClientAdapterImpl of(final AsyncHttpClient asyncHttpClient) {
        return new NingHttpClientAdapterImpl(asyncHttpClient);
    }

    /**
     * Creates a {@link java.util.concurrent.CompletableFuture} from a {@link com.ning.http.client.ListenableFuture}.
     * @param listenableFuture the future of the ning library
     * @param executor the executor to run the future in
     * @param <T> Type of the value that will be returned.
     * @return the Java 8 future implementation
     */
    private static <T> CompletableFuture<T> wrap(final ListenableFuture<T> listenableFuture, final Executor executor) {
        final CompletableFuture<T> result = new CompletableFuture<>();
        final Runnable listener = () -> {
            try {
                final T value = listenableFuture.get();
                result.complete(value);
            } catch (final InterruptedException | ExecutionException e) {
                result.completeExceptionally(e.getCause());
            }
        };
        listenableFuture.addListener(listener, executor);
        return result;
    }

    private CompletableFuture<Response> wrap(final ListenableFuture<Response> listenableFuture) {
        return wrap(listenableFuture, threadPool);
    }
}