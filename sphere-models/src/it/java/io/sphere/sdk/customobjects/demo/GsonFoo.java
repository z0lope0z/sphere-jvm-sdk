package io.sphere.sdk.customobjects.demo;

import io.sphere.sdk.models.Base;

public class GsonFoo extends Base {

    private final long baz;
    private final String bar;

    public GsonFoo(final String bar, final long baz) {
        this.bar = bar;
        this.baz = baz;
    }

    public String getBar() {
        return bar;
    }

    public long getBaz() {
        return baz;
    }
}
