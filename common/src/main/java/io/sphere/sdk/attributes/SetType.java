package io.sphere.sdk.attributes;

public class SetType extends AttributeTypeBase {
    private final AttributeType elementType;

    public SetType(final AttributeType elementType) {
        super("set");
        this.elementType = elementType;
    }

    public AttributeType getElementType() {
        return elementType;
    }
}