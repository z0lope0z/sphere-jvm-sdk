package io.sphere.sdk.products.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.products.ProductProjection;

final class ProductProjectionsEndpoint {
    static final JsonEndpoint<ProductProjection> ENDPOINT = JsonEndpoint.of(ProductProjection.typeReference(), "/product-projections");
}
