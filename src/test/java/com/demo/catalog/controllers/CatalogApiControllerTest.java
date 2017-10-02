package com.demo.catalog.controllers;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.catalog.commands.AddProductCommand;
import com.demo.catalog.controllers.CatalogApiController;
import com.demo.catalog.services.CatalogService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CatalogApiControllerTest {

    @MockBean
    CatalogService commandGateway;
    CatalogApiController controller;

    Map<String, String> request;
    String id;
    String name;

    @Before
    public void init(){
        controller = new CatalogApiController(commandGateway);
        id = UUID.randomUUID().toString();
        name = "test-"+id;
        request = new HashMap<String, String>();
        request.put("id", id);
        request.put("name", name);
    }


    @Test
    public void checkControllerCallsServiceCorrectly() throws ExecutionException, InterruptedException {

        assertNotNull(request);
        assertNotNull(request.containsKey("id"));
        assertNotNull(request.containsKey("name"));
        // Arrange

        when(commandGateway.addProduct(any(AddProductCommand.class)))
                .thenAnswer(i -> {
                    AddProductCommand command = i.getArgumentAt(0, AddProductCommand.class);
                    CompletableFuture<String> response =  new CompletableFuture<String>();
                    response.complete(command.getId());
                    return response;
                });

        // Act
        CompletableFuture<String> answer = controller.addProductToCatalog(request);

        // Assert
        verify(commandGateway, times(1)).addProduct(any(AddProductCommand.class));
        verifyNoMoreInteractions(commandGateway);
        assertEquals(id, answer.get().toString());
    }


}
