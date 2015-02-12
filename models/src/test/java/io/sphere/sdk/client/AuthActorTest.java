package io.sphere.sdk.client;


import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.fest.assertions.Assertions.assertThat;

public class AuthActorTest {

    private static final CompletableFuture<Tokens> TOKENS_FUTURE = CompletableFutureUtils.successful(Tokens.of("ac", "re", Optional.of(50L)));
    private static final CompletableFuture<Tokens> FAILED_FUTURE = CompletableFutureUtils.failed(new RuntimeException());

    @Test
    public void fetchesToken() throws Exception {
        final TokensSupplier tokensSupplier = new TestTokensSupplier(TOKENS_FUTURE);
        final AccessTokenCallback1 tokenCallback = new AccessTokenCallback1();
        final AuthActor authActor = new AuthActor(tokensSupplier, tokenCallback);
        authActor.tell(new AuthActor.FetchTokenFromSphereMessage());
        wait(tokenCallback);
        assertThat(tokenCallback.isSuccessful).isTrue();
    }

    @Test
    public void nothingHappensWithoutTell() throws Exception {
        final TokensSupplier tokensSupplier = new TestTokensSupplier(TOKENS_FUTURE);
        final AccessTokenCallback1 tokenCallback = new AccessTokenCallback1();
        final AuthActor authActor = new AuthActor(tokensSupplier, tokenCallback);
        wait(tokenCallback);
        assertThat(tokenCallback.isSuccessful).isFalse();
    }

    @Test
    public void refetchOnError() throws Exception {
        final AccessTokenCallback1 tokenCallback = new AccessTokenCallback1();
        final AuthActor authActor = new AuthActor(new TokensSupplier() {
            boolean firstTime = true;

            @Override
            public CompletableFuture<Tokens> get() {
                final CompletableFuture<Tokens> result = firstTime ? FAILED_FUTURE : TOKENS_FUTURE;
                firstTime = false;
                return result;
            }

            @Override
            public void close() {

            }
        }, tokenCallback);
        authActor.tell(new AuthActor.FetchTokenFromSphereMessage());
        wait(tokenCallback, 100);
        assertThat(tokenCallback.isSuccessful).isTrue();
    }

    private void wait(final AccessTokenCallback1 tokenCallback) throws InterruptedException, java.util.concurrent.ExecutionException {
        wait(tokenCallback, 50);
    }

    private void wait(final AccessTokenCallback1 tokenCallback, final int timeout) throws InterruptedException, java.util.concurrent.ExecutionException {
        try {
            tokenCallback.isDone.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            //ignore
        }
    }

    private static class AccessTokenCallback1 implements AccessTokenCallback {
        public final CompletableFuture<Object> isDone = new CompletableFuture<>();
        boolean isSuccessful = false;

        @Override
        public void setToken(final String accessToken) {
            if (accessToken.equals("ac")) {
                isSuccessful = true;
                isDone.complete(true);
            }
        }
    }

    private static class TestTokensSupplier implements TokensSupplier {

        private CompletableFuture<Tokens> future;

        public TestTokensSupplier(final CompletableFuture<Tokens> future) {
            this.future = future;
        }

        @Override
        public CompletableFuture<Tokens> get() {
            return future;
        }

        @Override
        public void close() {

        }
    }
}