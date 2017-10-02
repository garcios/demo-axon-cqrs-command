package com.demo.catalog.services;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.catalog.commands.AddProductCommand;
import com.demo.catalog.services.CatalogService;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CatalogServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogServiceTest.class);

    private String id;
    private String name;
    private double price;
    private AddProductCommand command;

    @MockBean
    private CommandGateway commandGateway;

    private CatalogService service;


    @Before
    public void init() {
        id = UUID.randomUUID().toString();
        name = "test-" + id;
        command = new AddProductCommand(id, name, price);
        service = new CatalogService(commandGateway);
    }

    @Test
    public void testApi() throws Exception {
        //Arrange
        when(commandGateway.send(any()))
                .thenAnswer(i -> {
                    AddProductCommand command = i.getArgumentAt(0, AddProductCommand.class);
                    assertEquals(id, command.getId());
                    CompletableFuture<String> response = new CompletableFuture<String>();
                    response.complete(command.getId());
                    return response;
                });

        //Act
        CompletableFuture<String> response = service.addProduct(command);

        //Assert
        verify(commandGateway, times(1)).send(any());
        verifyNoMoreInteractions(commandGateway);
        assertEquals(id, response.get().toString());
    }

    public String getId() {
        return id;
    }

}
