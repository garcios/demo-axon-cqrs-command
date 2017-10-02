package com.demo.catalog.services;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.demo.catalog.commands.AddProductCommand;
import com.demo.catalog.commands.UpdatePriceCommand;

import java.util.concurrent.CompletableFuture;

@Service
public class CatalogService{

    private static final Logger LOG = LoggerFactory.getLogger(CatalogService.class);

    private final CommandGateway commandGateway;

    public CatalogService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<String> addProduct(AddProductCommand command) {
        LOG.debug("Processing AddProductCommand command: {}", command);
        return this.commandGateway.send(command);
    }
    
    public CompletableFuture<String> updatePrice(UpdatePriceCommand command) {
        LOG.debug("Processing UpdatePriceCommand command: {}", command);
        return this.commandGateway.send(command);
    }
    
    
}
