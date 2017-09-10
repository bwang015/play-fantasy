package com.nbafantasy.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public interface Configuration {
	Config config = ConfigFactory.load();
	String getPlayerTable();
}
