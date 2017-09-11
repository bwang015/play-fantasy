package com.nbafantasy.test.database;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nbafantasy.database.DynamoDBService;
import com.nbafantasy.database.DynamoDBServiceConfig;

import javax.inject.Inject;

/**
 * Created by bwang on 9/10/17.
 */
public class LocalDynamoDBService implements DynamoDBService{

    private DynamoDBServiceConfig dbConfig;
    private AmazonDynamoDBAsync dynamoClient;
    private DynamoDBMapper modelMapper;

    @Inject
    public LocalDynamoDBService(DynamoDBServiceConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

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
