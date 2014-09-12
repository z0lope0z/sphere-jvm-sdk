package io.sphere.sdk.products.attributeaccess.staticmethodstyle;

import io.sphere.sdk.attributes.AttributeGetterSetter;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.attributes.AttributeTypes;
import io.sphere.sdk.products.Product;

public class TShirt {
    public static AttributeGetterSetter<Product, LocalizedString> longDescription() {
        return AttributeTypes.ofLocalizedString().getterSetter("longDescription");
    }
}