package io.sphere.sdk.models;


import java.time.Instant;
import java.util.Random;

/**
 * A non-fluent builder to reduce the number of type parameters.
 *
 * @param <T> the interface of the type which the builder builds
 * @see io.sphere.sdk.models.DefaultModelFluentBuilder
 */
public abstract class DefaultModelBuilder<T> implements Builder<T> {
    private static final Random RANDOM = new Random();

    protected String id = "id" + RANDOM.nextInt();
    protected long version = 1;
    protected Instant createdAt = Instant.now();
    protected Instant lastModifiedAt = Instant.now();

    public void setId(final String id) {
        this.id = id;
    }

    public void setVersion(final long version) {
        this.version = version;
    }

    public void setCreatedAt(final Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastModifiedAt(final Instant lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
