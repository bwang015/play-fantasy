package com.nbafantasy.util;

public class ConfigurationImpl implements Configuration {

	@Override
	public String getPlayerTable() {
		return config.getString("player.table");
	}

}
