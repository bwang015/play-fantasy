package com.nbafantasy.database;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DynamoDBServiceImpl implements DynamoDBService {

	@Inject
	DynamoDBServiceConfig dbConfig;

	private DynamoDB dynamoDB;
	private AmazonDynamoDBAsync dynamoClient;
	private DynamoDBMapper modelMapper;

	@Override
	public DynamoDB getDB() {
		if(dynamoDB == null)
			this.dynamoDB = new DynamoDB(getAsyncDynamoClient());
		return dynamoDB;
	}

	@Override
	public DynamoDBMapper getDBMapper() {
		if(this.modelMapper != null)
			return modelMapper;
		
		modelMapper = new DynamoDBMapper(getAsyncDynamoClient());
		return modelMapper;
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
