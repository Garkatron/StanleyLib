package deus.stanleylib.config;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import static deus.stanleylib.main.MOD_ID;

public class ConfigHandler {

	private static final TomlConfigHandler config;
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

	static {
		Toml toml = new Toml("StanleyLibConfig");

		toml.addCategory("temperatureManagement")
			.addEntry("activateTemperatureManagement", true);

		toml.addCategory("player")
			.addEntry("overHeatingTemperature",60.0)
			.addEntry("hotTemperature",45.0)
			.addEntry("defaultTemperature",36.5)
			.addEntry("coldTemperature",-15.0)
			.addEntry("freezingTemperature",-30.0)
		;

		toml.addCategory("weatherEffects")
			.addEntry("weatherAffectsTemperature", true)
			.addEntry("overworldRain", -0.1F)
			.addEntry("overworldSnow", -0.2F)
			.addEntry("overworldStorm", -0.1F)
			.addEntry("overworldWinterSnow", -0.3F);

		toml.addCategory("itemEffects")
			.addEntry("itemAffectsTemperature", true)
			.addEntry("torch", 0.8F)
			.addEntry("redstoneTorch", 0.5F)
			.addEntry("lavaBucket", 0.25F)
			.addEntry("netherCoal", 0.25F)
			.addEntry("iceCream", -0.05F);

		toml.addCategory("foodEffects")
			.addEntry("foodAffectsTemperature", true)
			.addEntry("soup", 0.8F)
			.addEntry("milk", -0.5F);

		toml.addCategory("lifeEffects")
			.addEntry("lifeAffectsTemperature", true)
			.addEntry("lowLifePenalization",2)
			.addEntry("heightLifeAdvantage",2);

		toml.addCategory("blockEffects")
			.addEntry("playerOverBlockAffectsTemperature", true)
			.addEntry("snowBlock", -0.05F)
			.addEntry("waterBlock", -0.05F)
			.addEntry("iceBlock", -0.03F);

		toml.addCategory("snowballEffects")
			.addEntry("snowballAffectsTemperature", true)
			.addEntry("snowballEffect", 0.01F);

		toml.addCategory("biomeEffects")
			.addEntry("biomeAffectsTemperature", true)
			.addEntry("plains", 0.0F)
			.addEntry("taiga", -0.1F)
			.addEntry("nether", 5.0F)
			.addEntry("birchForest", 0.0F)
			.addEntry("caatinga", -0.01F)
			.addEntry("borealForest", 0.0F)
			.addEntry("swampLand", -1.0F)
			.addEntry("desert", 2.0F)
			.addEntry("forest", -0.015F);

		toml.addCategory("leatherProtection")
			.addEntry("leatherProtectionPercentage", 0.01F)
			.addEntry("leatherProtectsTemperature", true);

		toml.addCategory("seasonEffects")
			.addEntry("seasonAffectsTemperature", true)
			.addEntry("summerTemperature", 0.04F)
			.addEntry("fallTemperature", 0.0F)
			.addEntry("winterTemperature", -0.05F)
			.addEntry("springTemperature", 0.0F);

		config = new TomlConfigHandler(null, MOD_ID, toml);
	}
}
