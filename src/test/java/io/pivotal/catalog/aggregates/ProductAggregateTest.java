package io.pivotal.catalog.aggregates;

import io.pivotal.catalog.commands.AddProductToCatalogCommand;
import io.pivotal.catalog.events.ProductAddedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class ProductAggregateTest {

    private FixtureConfiguration fixture;
    private String id;
    private String name;
    private double price;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture(ProductAggregate.class);
        id = UUID.randomUUID().toString();
        name = "test-"+id;
    }


    @Test
    public void testCommandAddProductToCatalogPasses() {
        fixture.given()
                .when(new AddProductToCatalogCommand(id, name, price))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ProductAddedEvent(id, name, price));
    }

    @Test
    public void testCommandAddProductToCatalogFailsWhenIdNull() {
        fixture.given()
                .when(new AddProductToCatalogCommand(null, name, price))
                .expectException(IllegalArgumentException.class);

        fixture.given()
                .when(new AddProductToCatalogCommand("", name, price))
                .expectException(IllegalArgumentException.class);
    }

    @Test
    public void testCommandAddProductToCatalogFailsWhenNameNull() {
        fixture.given()
                .when(new AddProductToCatalogCommand(id, null, price))
                .expectException(IllegalArgumentException.class);

        fixture.given()
                .when(new AddProductToCatalogCommand(id, "", price))
                .expectException(IllegalArgumentException.class);
    }


}
