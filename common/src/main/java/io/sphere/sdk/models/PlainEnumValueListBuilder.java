package io.sphere.sdk.models;

import java.util.LinkedList;
import java.util.List;

import static io.sphere.sdk.utils.ListUtils.*;

public final class PlainEnumValueListBuilder implements Builder<List<PlainEnumValue>> {
    private final List<PlainEnumValue> list = new LinkedList<>();

    public PlainEnumValueListBuilder add(final String key, final String label) {
        list.add(PlainEnumValue.of(key, label));
        return this;
    }

    public static PlainEnumValueListBuilder of(final String key, final String label) {
        return new PlainEnumValueListBuilder().add(key, label);
    }

    @Override
    public List<PlainEnumValue> build() {
        return immutableCopyOf(list);
    }
}