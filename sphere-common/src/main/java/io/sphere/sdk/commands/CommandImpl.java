package io.sphere.sdk.commands;

import com.fasterxml.jackson.core.type.TypeReference;

import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.http.HttpResponse;

/**
 * Base class to implement commands using the Jackson JSON mapper.
 *
 * @param <T> the type of the result of the command, most likely the updated entity without expanded references
 *
 */
public abstract class CommandImpl<T> extends SphereRequestBase implements Command<T> {
    @Override
    public T deserialize(final HttpResponse httpResponse) {
        return resultMapperOf(typeReference()).apply(httpResponse);
    }

    protected abstract TypeReference<T> typeReference();
}
