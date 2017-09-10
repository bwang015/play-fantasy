package com.nbafantasy.database;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

@Singleton
public class DynamoDBServiceConfigImpl implements DynamoDBServiceConfig {	
	@Inject
	private Config config;
	
	private String region;
	private String host;
	
	public DynamoDBServiceConfigImpl() {
		this.config = ConfigFactory.load();
		this.region = config.getString("dynamodb.region");
		this.host = config.getString("dynamodb.host");
	}
	
	@Override
	public String getRegion() {
		return region;
	}
	
	@Override
	public String getHost() {
		return host;
	}
}
