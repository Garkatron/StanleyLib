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

		prop.setProperty("stanley.activate.temperature_management","true");

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
		prop.setProperty("on_player_over.iceBlock", "-0.03F");

		prop.setProperty("temperature.snowball_affects_temperature", "true");
		prop.setProperty("temperature.snowball", "0.01F");

		prop.setProperty("temperature.biome.affects_temperature", "true");
		prop.setProperty("temperature.biome.plains", "0.0F");
		prop.setProperty("temperature.biome.Taiga", "-0.1F");
		prop.setProperty("temperature.biome.nether", "5.0F");
		prop.setProperty("temperature.biome.birch_forest", "0.0F");
		prop.setProperty("temperature.biome.caatinga", "-0.01F");
		prop.setProperty("temperature.biome.boreal_forest", "0.0F");
		prop.setProperty("temperature.biome.swanp_land", "-1.0F");
		prop.setProperty("temperature.biome.desert", "2.0F");
		prop.setProperty("temperature.biome.forest", "-0.015F");

		prop.setProperty("temperature.leather.protection_percentage", "0.01F");
		prop.setProperty("temperature.leather.protects_temperature", "true");

		prop.setProperty("temperature.season.affects_temperature", "true");
		prop.setProperty("temperature.season.summer.value", "0.04F");
		prop.setProperty("temperature.season.fall.value", "0.0F");
		prop.setProperty("temperature.season.winter.value", "-0.05F");
		prop.setProperty("temperature.season.spring.value", "0.0F");

		config = new ConfigHandler(MOD_ID, prop);
		config.updateConfig();
	}
}
