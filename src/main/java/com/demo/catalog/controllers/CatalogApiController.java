package com.demo.catalog.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.catalog.commands.AddProductCommand;
import com.demo.catalog.commands.UpdatePriceCommand;
import com.demo.catalog.services.CatalogService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;


@RestController
public class CatalogApiController {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogApiController.class);

    private final CatalogService catalogService;

    public CatalogApiController(CatalogService commandGateway) {
        this.catalogService = commandGateway;
    }

    @PostMapping("/products")
    public CompletableFuture<String> addProductToCatalog(@RequestBody Map<String, String> request) {

        AddProductCommand command = new AddProductCommand(request.get("id"), request.get("name"), Double.parseDouble(request.get("price")));
        LOG.info("Executing command: {}", command);
        return catalogService.addProduct(command);
    }
    
    @PutMapping("/products/{id}")
    public CompletableFuture<String> updatePrice(@RequestBody Map<String, String> request, @PathVariable String id) {

    	UpdatePriceCommand command = new UpdatePriceCommand(id, Double.parseDouble(request.get("price")));
        LOG.info("Executing command: {}", command);
        return catalogService.updatePrice(command);
    }
 
    
}

