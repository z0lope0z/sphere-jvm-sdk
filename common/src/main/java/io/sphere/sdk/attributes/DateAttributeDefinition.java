package io.sphere.sdk.attributes;

import io.sphere.sdk.models.LocalizedString;

public class DateAttributeDefinition extends AttributeDefinitionBase<DateType> {
    DateAttributeDefinition(final DateType attributeType, final String name, final LocalizedString label, final boolean isRequired, final AttributeConstraint attributeConstraint, final boolean isSearchable) {
        super(attributeType, name, label, isRequired, attributeConstraint, isSearchable);
    }
}