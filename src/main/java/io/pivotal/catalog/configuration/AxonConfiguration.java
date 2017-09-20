package io.pivotal.catalog.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.MongoFactory;
import org.axonframework.mongo.eventsourcing.eventstore.MongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class AxonConfiguration {

	@Value("${mongodb.url:127.0.0.1}")
	private String mongoUrl;

	@Value("${mongodb.port}")
    private int mongoPort;

	@Value("${mongodb.username}")
    private String mongoDbUsername;

    @Value("${mongodb.password}")
    private String mongoDbPassword;

    
	@Value("${mongodb.dbname:db_test}")
	private String mongoDbName;

	@Value("${mongodb.events.collection.name:events}")
	private String eventsCollectionName;

	@Value("${mongodb.events.snapshot.collection.namesnapshots}")
	private String snapshotCollectionName;

	@Bean
	Serializer axonJsonSerializer() {
		return new JacksonSerializer();
	}

	@Bean
	public EventStorageEngine eventStore() {
		return new MongoEventStorageEngine(axonJsonSerializer(), null, axonMongoTemplate(),
				new DocumentPerEventStorageStrategy());
	}

    @Bean
	public MongoClient mongoClient() {
    	
        List<ServerAddress> serverAddresses = Collections.singletonList(new ServerAddress(mongoUrl, mongoPort));
       
        if (!StringUtils.isEmpty(mongoDbUsername) && !StringUtils.isEmpty(mongoDbPassword)) {
            List<MongoCredential> mongoCredentials = Collections.singletonList(
            MongoCredential.createCredential(mongoDbUsername, mongoDbName, mongoDbPassword.toCharArray()));
            return new MongoClient(serverAddresses, mongoCredentials);
        } else {
	         return new MongoClient(serverAddresses);
	    }
        
	}
	
	
	@Bean(name = "axonMongoTemplate")
	public MongoTemplate axonMongoTemplate() {
		MongoFactory mongoFactory = new MongoFactory();
		mongoFactory.setMongoAddresses(Arrays.asList(new ServerAddress(mongoUrl)));
		
		MongoTemplate template = new DefaultMongoTemplate(mongoClient(), mongoDbName, eventsCollectionName,
				snapshotCollectionName);
		
		return template;
	}

}