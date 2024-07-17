package deus.stanleylib.config;

import turniplabs.halplibe.util.ConfigHandler;

import java.util.Properties;

import static deus.stanleylib.main.MOD_ID;


public class ModConfig {

	private static ConfigHandler config;

	public int getInt(String prop) {
		return config.getInt(prop);
	}
	public float getFloat(String prop) {
		return Float.parseFloat(config.getString(prop));
	}
	public boolean getBool(String prop) {return  Boolean.parseBoolean(config.getString(prop));}

	public ModConfig() {

		Properties prop = new Properties();

		prop.setProperty("weather_affects_temperature", "true");

		prop.setProperty("on_weather.overworldRain", "-0.1F");
		prop.setProperty("on_weather.overworldSnow", "-0.2F");
		prop.setProperty("on_weather.overworldStorm", "-0.1F");
		prop.setProperty("on_weather.overworldWinterSnow", "-0.3F");
		prop.setProperty("on_weather.waterRain", "-0.1F");
		prop.setProperty("on_weather.waterStorm", "-0.2F");
		prop.setProperty("on_weather.waterWinterSnow", "-0.4F");

		prop.setProperty("player_over_block_affects_temperature", "true");
		prop.setProperty("on_player_over.snowBlock", "-0.05F");
		prop.setProperty("on_player_over.water", "-0.05F");

		prop.setProperty("temperature.protection_percentage", "0.02F");

		prop.setProperty("season.summer.value", "0.15F");
		prop.setProperty("season.fall.value", "0.0F");
		prop.setProperty("season.winter.value", "-0.15F");
		prop.setProperty("season.spring.value", "0.0F");

		config = new ConfigHandler(MOD_ID, prop);
		config.updateConfig();
	}
}
