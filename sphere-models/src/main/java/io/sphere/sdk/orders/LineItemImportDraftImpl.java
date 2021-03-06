package io.sphere.sdk.orders;

import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.taxcategories.TaxRate;

import java.util.Optional;
import java.util.Set;

final class LineItemImportDraftImpl extends Base implements LineItemImportDraft {
    private final Optional<String> productId;
    private final LocalizedStrings name;
    private final ProductVariantImportDraft variant;
    private final Price price;
    private final long quantity;
    private final Optional<Set<ItemState>> state;
    private final Optional<Reference<Channel>> supplyChannel;
    private final Optional<TaxRate> taxRate;

    public LineItemImportDraftImpl(final LocalizedStrings name, final Optional<String> productId, final ProductVariantImportDraft variant, final Price price, final long quantity, final Optional<Set<ItemState>> state, final Optional<Reference<Channel>> supplyChannel, final Optional<TaxRate> taxRate) {
        this.name = name;
        this.productId = productId;
        this.variant = variant;
        this.price = price;
        this.quantity = quantity;
        this.state = state;
        this.supplyChannel = supplyChannel;
        this.taxRate = taxRate;
    }

    @Override
    public LocalizedStrings getName() {
        return name;
    }

    @Override
    public Price getPrice() {
        return price;
    }

    @Override
    public Optional<String> getProductId() {
        return productId;
    }

    @Override
    public long getQuantity() {
        return quantity;
    }

    @Override
    public Optional<Set<ItemState>> getState() {
        return state;
    }

    @Override
    public Optional<Reference<Channel>> getSupplyChannel() {
        return supplyChannel;
    }

    @Override
    public Optional<TaxRate> getTaxRate() {
        return taxRate;
    }

    @Override
    public ProductVariantImportDraft getVariant() {
        return variant;
    }
}
