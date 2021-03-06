package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.DefaultModelFluentBuilder;
import io.sphere.sdk.attributes.AttributeDefinition;

import java.util.List;

/** Builds product types for unit tests.

 {@include.example io.sphere.sdk.producttypes.Example#builderDemo()}
 */
public final class ProductTypeBuilder extends DefaultModelFluentBuilder<ProductTypeBuilder, ProductType> {

    private final String name;
    private final String description;
    private final List<AttributeDefinition> attributes;

    private ProductTypeBuilder(final String id, final String name, final String description, final List<AttributeDefinition> attributes) {
        this.name = name;
        this.description = description;
        this.attributes = attributes;
        this.id = id;
    }

    public static ProductTypeBuilder of(final String id, final String name, final String description, final List<AttributeDefinition> attributes) {
        return new ProductTypeBuilder(id, name, description, attributes);
    }

    public static ProductTypeBuilder of(final String id, final ProductTypeDraft productTypeDraft) {
        return new ProductTypeBuilder(id, productTypeDraft.getName(), productTypeDraft.getDescription(), productTypeDraft.getAttributes());
    }

    @Override
    protected ProductTypeBuilder getThis() {
        return this;
    }

    @Override
    public ProductType build() {
        return new ProductTypeImpl(id, version, createdAt, lastModifiedAt, name, description, attributes);
    }
}
