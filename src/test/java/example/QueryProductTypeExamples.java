package example;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.producttypes.*;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Predicate;

import java.util.concurrent.CompletionStage;

public class QueryProductTypeExamples {
    private SphereClient client;
    private ProductType productType;

    public void queryAll() {
        final CompletionStage<PagedQueryResult<ProductType>> result = client.execute(ProductTypeQuery.of());
    }

    public void queryByAttributeName() {
        Predicate<ProductType> hasSizeAttribute = ProductTypeQuery.model().attributes().name().is("size");
        CompletionStage<PagedQueryResult<ProductType>> result = client.execute(ProductTypeQuery.of().withPredicate(hasSizeAttribute));
    }

    public void delete() {
        final DeleteCommand<ProductType> command = ProductTypeDeleteCommand.of(productType);
        final CompletionStage<ProductType> deletedProductType = client.execute(command);
    }
}
