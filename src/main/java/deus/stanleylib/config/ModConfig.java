package deus.stanleylib.config;

import turniplabs.halplibe.util.ConfigHandler;

import java.util.Properties;

import static deus.stanleylib.main.MOD_ID;


public class ModConfig {

	private static ConfigHandler config;

	public int getInt(String prop) {
		return config.getInt(prop);
	}

	public ModConfig() {
		Properties prop = new Properties();

		prop.setProperty("on_weather.overworldRain", "-6");
		prop.setProperty("on_weather.overworldSnow", "-12");
		prop.setProperty("on_weather.overworldStorm", "-18");
		prop.setProperty("on_weather.overworldWinterSnow", "-24");
		prop.setProperty("on_weather.waterRain", "-16");
		prop.setProperty("on_weather.waterStorm", "-22");
		prop.setProperty("on_weather.waterWinterSnow", "-32");
		prop.setProperty("on_player_over.snowBlock", "-8");
		prop.setProperty("on_player_over.water", "-4");

		config = new ConfigHandler(MOD_ID, prop);
		config.updateConfig();
	}
}
