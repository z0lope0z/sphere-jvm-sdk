package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.json.JsonUtils;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeBuilder;
import io.sphere.sdk.search.PagedSearchResult;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

import static java.util.Locale.ENGLISH;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class ProductProjectionTest {
    @Test
    public void transformProductIntoProductProjection() throws Exception {
        final Product product = getProduct();

        final Optional<ProductProjection> staged = product.toProjection(ProductProjectionType.STAGED);
        assertThat(staged).overridingErrorMessage("staged is always present").isPresent();
        assertThat(staged.get().getName()).isEqualTo(product.getMasterData().getStaged().getName());

        final Optional<ProductProjection> current = product.toProjection(ProductProjectionType.CURRENT);
        assertThat(current).overridingErrorMessage("current can be empty").isAbsent();
    }

    private Product getProduct() {
        final ProductType productType = ProductTypeBuilder.of("product-type-id", "product-type-name", "", Collections.emptyList()).get();
        final ProductVariant emptyProductVariant = ProductVariantBuilder.of(1).sku("sku-5000").get();
        final LocalizedStrings name = LocalizedStrings.of(ENGLISH, "name");
        final LocalizedStrings slug = LocalizedStrings.of(ENGLISH, "slug");
        final ProductData staged = ProductDataBuilder.of(name, slug, emptyProductVariant).build();
        final ProductCatalogData masterData = ProductCatalogDataBuilder.ofStaged(staged).get();

        return ProductBuilder.of(productType, masterData).id("foo-id").build();
    }

    @Test
    public void deserialization() throws Exception {
        final String jsonString = "{\"offset\":0,\"count\":1,\"total\":15,\"results\":[{\"masterVariant\":{\"id\":1,\"sku\":\"2\",\"prices\":[{\"value\":{\"currencyCode\":\"EUR\",\"centAmount\":1413}}],\"images\":[{\"url\":\"https://s3.eu-central-1.amazonaws.com/commercetools-angry-bird-demo/Red+Skywalker+Plush+Toy.jpg\",\"dimensions\":{\"w\":0,\"h\":0}}],\"attributes\":[{\"name\":\"size\",\"value\":{\"key\":\"onesize\",\"label\":\"one size\"}},{\"name\":\"color\",\"value\":{\"key\":\"multicolor\",\"label\":\"multi color\"}}]},\"id\":\"184aaadc-63f6-4e6a-95fe-22b4f001612f\",\"version\":2,\"productType\":{\"typeId\":\"product-type\",\"id\":\"371e1d3a-e553-4b76-a834-dd3745c9afa0\"},\"name\":{\"en\":\"Red Skywalker Plush Toy\"},\"description\":{\"en\":\"Red Skywalker is a committed Rebel and is learning to be a Jedi Bird warrior.\"},\"categories\":[{\"typeId\":\"category\",\"id\":\"bc67f617-79ca-4f48-bf1b-18d5dbc7b552\"}],\"slug\":{\"en\":\"Red-Skywalker-Plush-Toy\"},\"metaTitle\":{\"en\":\"Red Skywalker Plush Toy\"},\"metaKeywords\":{\"en\":\"star wars, toy, angry birds\"},\"metaDescription\":{\"en\":\"Red Skywalker is a committed Rebel and is learning to be a Jedi Bird warrior.\"},\"variants\":[],\"searchKeywords\":{},\"hasStagedChanges\":false,\"published\":true,\"taxCategory\":{\"typeId\":\"tax-category\",\"id\":\"ab3939bd-0e2d-4fbb-a640-ae8a33c4e2c9\"},\"createdAt\":\"2015-02-25T11:20:11.466Z\",\"lastModifiedAt\":\"2015-02-25T11:20:11.687Z\"}],\"facets\":{}}";
        final PagedSearchResult<ProductProjection> pagedSearchResult = JsonUtils.readObjectFromJsonString(new TypeReference<PagedSearchResult<ProductProjection>>() {
        }, jsonString);
    }
}