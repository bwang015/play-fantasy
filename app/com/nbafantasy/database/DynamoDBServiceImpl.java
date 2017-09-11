package com.nbafantasy.database;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Singleton
public class DynamoDBServiceImpl implements DynamoDBService {

	@Inject
	DynamoDBServiceConfig dbConfig;

	private AmazonDynamoDBAsync dynamoClient;
	private DynamoDBMapper modelMapper;

	@Override
	public DynamoDBMapper getDBMapper() {
		if(this.modelMapper != null)
			return modelMapper;
		
		this.modelMapper = new DynamoDBMapper(getAsyncDynamoClient());
		return this.modelMapper;
	}

	@Override
	public AmazonDynamoDBAsync getAsyncDynamoClient() {
		if(dynamoClient != null)
			return dynamoClient;
        String region = dbConfig.getRegion();
        String host = dbConfig.getHost();
        
        AmazonDynamoDBAsync client = AmazonDynamoDBAsyncClientBuilder.standard()
        		.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(host, region))
        		.withCredentials(new EnvironmentVariableCredentialsProvider())
        		.build();
        this.dynamoClient = client;
        return client;
	}
	
}
