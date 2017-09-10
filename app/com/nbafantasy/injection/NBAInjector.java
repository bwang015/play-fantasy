package com.nbafantasy.injection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.nbafantasy.database.DatabaseService;
import com.nbafantasy.database.DatabaseServiceImpl;
import com.nbafantasy.database.DynamoDBService;
import com.nbafantasy.database.DynamoDBServiceConfig;
import com.nbafantasy.database.DynamoDBServiceConfigImpl;
import com.nbafantasy.database.DynamoDBServiceImpl;
import com.nbafantasy.service.PlayerService;
import com.nbafantasy.service.PlayerServiceImpl;
import com.nbafantasy.util.Configuration;
import com.nbafantasy.util.ConfigurationImpl;


public class NBAInjector extends AbstractModule{
	
	private ObjectMapper mapper;

	@Override
	protected void configure() {
		mapper = new ObjectMapper();
		
		bind(DynamoDBServiceConfig.class).to(DynamoDBServiceConfigImpl.class);
		bind(DatabaseService.class).to(DatabaseServiceImpl.class);
		bind(DynamoDBService.class).to(DynamoDBServiceImpl.class);
		bind(ObjectMapper.class).annotatedWith(Names.named("mapper")).toInstance(mapper);
		bind(Configuration.class).to(ConfigurationImpl.class);
		bind(PlayerService.class).to(PlayerServiceImpl.class);
	}

}
