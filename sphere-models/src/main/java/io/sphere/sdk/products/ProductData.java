package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.search.SearchKeywords;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * For construction in unit tests use {@link io.sphere.sdk.products.ProductDataBuilder}.
 */
@JsonDeserialize(as=ProductDataImpl.class)
public interface ProductData extends ProductDataLike {
    @Override
    LocalizedStrings getName();

    @Override
    Set<Reference<Category>> getCategories();

    @Override
    Optional<LocalizedStrings> getDescription();

    @Override
    LocalizedStrings getSlug();

    @Override
    Optional<LocalizedStrings> getMetaTitle();

    @Override
    Optional<LocalizedStrings> getMetaDescription();

    @Override
    Optional<LocalizedStrings> getMetaKeywords();

    @Override
    ProductVariant getMasterVariant();

    @Override
    List<ProductVariant> getVariants();

    @Override
    default List<ProductVariant> getAllVariants() {
        return ProductsPackage.getAllVariants(this);
    }

    @Override
    default Optional<ProductVariant> getVariant(final int variantId){
        return ProductsPackage.getVariant(variantId, this);
    }

    @Override
    default ProductVariant getVariantOrMaster(final int variantId) {
        return ProductsPackage.getVariantOrMaster(variantId, this);
    }

    @Override
    SearchKeywords getSearchKeywords();
}
