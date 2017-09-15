package com.nbafantasy.injection;

import com.google.inject.AbstractModule;
import com.nbafantasy.database.*;
import com.nbafantasy.service.PlayerService;
import com.nbafantasy.service.PlayerServiceImpl;
import com.nbafantasy.service.UploadService;
import com.nbafantasy.service.UploadServiceImpl;
import com.nbafantasy.util.Configuration;
import com.nbafantasy.util.ConfigurationImpl;


public class NBAInjector extends AbstractModule{

	@Override
	protected void configure() {
		bind(DynamoDBServiceConfig.class).to(DynamoDBServiceConfigImpl.class);
		bind(DatabaseService.class).to(DatabaseServiceImpl.class);
		bind(DynamoDBService.class).to(DynamoDBServiceImpl.class);
		bind(Configuration.class).to(ConfigurationImpl.class);
		bind(PlayerService.class).to(PlayerServiceImpl.class);
		bind(UploadService.class).to(UploadServiceImpl.class);
	}

}
