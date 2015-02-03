package io.sphere.sdk.search;

final class SimpleFilterExpression<T> extends FilterExpressionBase<T> {
    private final String sphereFilterExpression;

    SimpleFilterExpression(final String sphereFilterExpression) {
        this.sphereFilterExpression = sphereFilterExpression;
    }

    @Override
    public String toSphereFilter() {
        return sphereFilterExpression;
    }
}