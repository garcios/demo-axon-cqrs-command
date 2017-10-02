package com.demo.catalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableDiscoveryClient
@SpringBootApplication
public class AxonCqrsCommandSideApplication {

    private static final Logger LOG = LoggerFactory.getLogger(AxonCqrsCommandSideApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AxonCqrsCommandSideApplication.class, args);
        LOG.info("Starting the COMMAND-SIDE Axon CQRS Demo using SpringBoot.");
    }
}
