package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Optional;

/**
 * A {@link io.sphere.sdk.models.Reference} is a loose reference to another resource on the SPHERE.IO platform.
 *
 * The reference may have a copy of the referenced object available via the method {@link io.sphere.sdk.models.Reference#getObj()}
 *
 * For equals, only the {@link Reference#getTypeId()} and {@link io.sphere.sdk.models.Reference#getId()} are compared used and {@link Reference#getObj()} will be ignored.
 *
 * @param <T> the type of the referenced object
 */
public final class Reference<T> implements Referenceable<T>, Identifiable<T> {
    private final String typeId;
    private final String id;
    private final Optional<T> obj;

    @JsonCreator
    private Reference(final String typeId, final String id, final Optional<T> obj) {
        this.id = id;
        this.typeId = typeId;
        this.obj = obj;
    }

    /**
     * Id of the object this reference represents.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Type id of the object this reference represents, e.g. "customer".
     * @return the type id
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * The optional value of the referenced object.
     * @return The optional value of the referenced object.
     */
    @JsonIgnore
    public Optional<T> getObj() {
        return obj;
    }

    public Reference<T> filled(final T obj) {
        return filled(Optional.ofNullable(obj));
    }

    public Reference<T> filled(final Optional<T> obj) {
        return new Reference<>(getTypeId(), getId(), obj);
    }


    public static <T> Reference<T> of(final String typeId, final String id) {
        return new Reference<>(typeId, id, Optional.empty());
    }

    public static <T> Reference<T> of(final String typeId, final String id, T obj) {
        return Reference.<T>of(typeId, id).filled(obj);
    }

    public static <T extends Identifiable<T>> Reference<T> of(final String typeId, final T obj) {
        return Reference.of(typeId, obj.getId(), obj);
    }

    public boolean referencesSameResource(final Referenceable<T> counterpart) {
        final Reference<T> reference = counterpart.toReference();
        return reference.getId().equals(getId()) && reference.getTypeId().equals(getTypeId());
    }

    @Override
    public Reference<T> toReference() {
        return this;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "typeId='" + typeId + '\'' +
                ", id='" + id + '\'' +
                ", obj=" + obj +
                '}';
    }

    @SuppressWarnings("rawtypes")//at runtime generic type is not determinable
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reference reference = (Reference) o;

        if (!getId().equals(reference.getId())) return false;
        if (!getTypeId().equals(reference.getTypeId())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + typeId.hashCode();
        result = 31 * result + obj.hashCode();
        return result;
    }
}
