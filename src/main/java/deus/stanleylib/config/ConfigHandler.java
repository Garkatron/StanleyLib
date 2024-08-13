package deus.stanleylib.config;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import static deus.stanleylib.StanleyLib.MOD_ID;

public class ConfigHandler {

	private static final TomlConfigHandler config;

	static {
		Toml toml = new Toml("StanleyLibConfig");

		toml.addCategory("temperatureManagement")
			.addEntry("activateTemperatureManagement", true);

		toml.addCategory("player")
			.addEntry("overHeatingTemperature", 60.0)
			.addEntry("hotTemperature", 45.0)
			.addEntry("defaultTemperature", 36.5)
			.addEntry("coldTemperature", -15.0)
			.addEntry("freezingTemperature", -30.0)
		;

		config = new TomlConfigHandler(null, MOD_ID, toml);
	}

	public TomlConfigHandler getConfig() {
		return config;
	}
}
