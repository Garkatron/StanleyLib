package deus.stanleytemperature.config;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import static deus.stanleytemperature.StanleyTemperature.MOD_ID;

public class ConfigHandler {

	private static final TomlConfigHandler config;

	static {
		Toml toml = new Toml("StanleyLibConfig");

		toml.addCategory("Gui")
			.addEntry("temperatureBar","vertical or horizontal","horizontal")
			.addEntry("temperatureBarCompact","small version",true);

		toml.addCategory("temperatureManagement")
			.addEntry("activateTemperatureManagement", true);

		toml.addCategory("player")
				.addEntry("overHeatingTemperature", 100.0)
			.addEntry("hotTemperature", 70.0)
			.addEntry("defaultTemperature", 50.0)
			.addEntry("coldTemperature", 25.0)
			.addEntry("freezingTemperature", 0.0)
		;

		toml.addCategory("weatherEffects")
			.addEntry("weatherAffectsTemperature", true)
			.addEntry("overworldRain", -0.1)
			.addEntry("overworldSnow", -0.2)
			.addEntry("overworldStorm", -0.1)
			.addEntry("overworldWinterSnow", -0.3);

		toml.addCategory("itemEffects")
			.addEntry("itemAffectsTemperature", true)
			.addEntry("torch", 0.1)
			.addEntry("redstoneTorch", 0.05)
			.addEntry("lavaBucket", 0.25)
			.addEntry("netherCoal", 0.25);

		toml.addCategory("foodEffects")
			.addEntry("foodAffectsTemperature", true)
			.addEntry("soup", 0.8)
			.addEntry("milk", -0.5);

		toml.addCategory("lifeEffects")
			.addEntry("lifeAffectsTemperature", true)
			.addEntry("applyEffectEveryXHearts", 2)
			.addEntry("temperatureResistancePenalizationPerHeart", -1.0);

		toml.addCategory("blockEffects")
			.addEntry("playerOverBlockAffectsTemperature", true)
			.addEntry("snowBlock", -0.05)
			.addEntry("waterBlock", -0.05)
			.addEntry("fire", -2.00)
			.addEntry("lava", -2.00)
			.addEntry("iceBlock", -0.03);

		toml.addCategory("snowballEffects")
			.addEntry("snowballAffectsTemperature", true)
			.addEntry("snowballEffect", 0.01);

		toml.addCategory("biomeEffects")
			.addEntry("biomeAffectsTemperature", true)
			.addEntry("plains", 0.0)
			.addEntry("taiga", -0.1)
			.addEntry("nether", 5.0)
			.addEntry("birchForest", 0.0)
			.addEntry("caatinga", -0.01)
			.addEntry("borealForest", 0.0)
			.addEntry("swampLand", -1.0)
			.addEntry("desert", 2.0)
			.addEntry("forest", -0.015);

		toml.addCategory("leatherProtection")
			.addEntry("leatherProtectionPercentage", 0.01)
			.addEntry("leatherProtectsTemperature", true);

		toml.addCategory("seasonEffects")
			.addEntry("seasonAffectsTemperature", true)
			.addEntry("summerTemperature", 0.04)
			.addEntry("fallTemperature", 0.0)
			.addEntry("winterTemperature", -0.05)
			.addEntry("springTemperature", 0.0);

		config = new TomlConfigHandler(null, MOD_ID, toml);
	}

	private final TemperatureConfig temperatureConfig;

	public ConfigHandler() {
		this.temperatureConfig = new TemperatureConfig(config);
	}

	public TemperatureConfig getTemperatureConfig() {
		return temperatureConfig;
	}

	public TomlConfigHandler getConfig() {
		return config;
	}
}
