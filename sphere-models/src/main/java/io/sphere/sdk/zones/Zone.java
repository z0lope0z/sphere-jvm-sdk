package io.sphere.sdk.zones;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

import java.util.Optional;
import java.util.Set;

/**
 * Zones define Shipping Rates for a set of Locations.
 */
@JsonDeserialize(as = ZoneImpl.class)
public interface Zone extends DefaultModel<Zone> {

    String getName();

    Optional<String> getDescription();

    Set<Location> getLocations();

    @Override
    default Reference<Zone> toReference() {
        return Reference.of(typeId(), getId(), this);
    }

    public static String typeId(){
        return "zone";
    }

    public static TypeReference<Zone> typeReference(){
        return new TypeReference<Zone>() {
            @Override
            public String toString() {
                return "TypeReference<Zone>";
            }
        };
    }

    /**
     * Looks up the locations and checks if the country is present. It does not matter if the location has a state specified.
     *
     * @param countryCode the country to search for
     * @return true if the country is somehow in the locations.
     */
    default boolean contains(final CountryCode countryCode) {
        return getLocations().stream().anyMatch(location -> location.getCountry().equals(countryCode));
    }
}