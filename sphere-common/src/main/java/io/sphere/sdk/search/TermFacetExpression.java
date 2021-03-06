package io.sphere.sdk.search;

import java.util.Optional;

import static java.util.Arrays.asList;

public class TermFacetExpression<T, V> extends TermExpression<T, V> implements FacetExpressionBase<T> {

    TermFacetExpression(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer, final Optional<String> alias) {
        super(searchModel, typeSerializer, asList(), alias);
    }

    @Override
    public String toSphereFacet() {
        return super.toSphereSearchExpression();
    }

    @Override
    public String resultPath() {
        return super.buildResultPath();
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FacetExpression && toSphereFacet().equals(((FacetExpression) o).toSphereFacet());
    }
}
