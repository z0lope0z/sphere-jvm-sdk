package io.sphere.sdk.zones;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.models.SphereException;
import io.sphere.sdk.shippingmethods.commands.ShippingMethodDeleteCommand;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodQuery;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodQueryModel;
import io.sphere.sdk.zones.commands.ZoneCreateCommand;
import io.sphere.sdk.zones.commands.ZoneDeleteCommand;
import io.sphere.sdk.zones.queries.ZoneQuery;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.utils.SetUtils.setOf;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class ZoneFixtures {
    public static synchronized void withZone(final TestClient client, final Consumer<Zone> consumer, final CountryCode country, final CountryCode ... moreCountries) {
        withUpdateableZone(client, consumerToFunction(consumer), country, moreCountries);
    }

    public static synchronized void withUpdateableZone(final TestClient client, final Function<Zone, Zone> f, final CountryCode country, final CountryCode... moreCountries) {
        final Set<CountryCode> countries = setOf(country, moreCountries);
        final ZoneDraft draft = ZoneDraft.ofCountries("zone " + country, countries, "Zone X");
        withUpdateableZone(client, draft, f);
    }

    public static synchronized void withUpdateableZone(final TestClient client, final Function<Zone, Zone> f, final Location location, final Location... moreLocations) {
        final Set<Location> locations = setOf(location, moreLocations);
        final ZoneDraft draft = ZoneDraft.of("zone " + location, locations, "Zone X");
        withUpdateableZone(client, draft, f);
    }

    private static void withUpdateableZone(final TestClient client, final ZoneDraft draft, final Function<Zone, Zone> f) {
        final ZoneCreateCommand createCommand = ZoneCreateCommand.of(draft);
        Zone zone = client.execute(createCommand);
        zone = f.apply(zone);//zone possibly has been updated
        client.execute(ZoneDeleteCommand.of(zone));
    }

    public static void deleteZonesForCountries(final TestClient client, final CountryCode country, final CountryCode ... moreCountries) {
        final Set<CountryCode> countries = setOf(country, moreCountries);
        final ZoneQuery query = ZoneQuery.of();
        final Consumer<Zone> action = zone -> {
            try {
                client.execute(ZoneDeleteCommand.of(zone));
            } catch (final SphereException e) {
                final ShippingMethodQueryModel model = ShippingMethodQuery.model();
                client.execute(ShippingMethodQuery.of().withPredicate(model.zoneRates().zone().is(zone)))
                        .head()
                        .ifPresent(sm -> {
                            client.execute(ShippingMethodDeleteCommand.of(sm));
                            client.execute(ZoneDeleteCommand.of(zone));
                        });
            }
        };
        client.execute(query).getResults().stream()
                .filter(zone -> countries.stream().anyMatch(zone::contains))
                .forEach(action);

    }
}
