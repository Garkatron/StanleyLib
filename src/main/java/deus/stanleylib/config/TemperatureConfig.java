package deus.stanleylib.config;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.biome.*;
import net.minecraft.core.world.season.*;
import net.minecraft.core.world.weather.Weather;
import turniplabs.halplibe.util.TomlConfigHandler;

import java.util.HashMap;
import java.util.Map;

public class TemperatureConfig {

	private final Map<Weather, Double> weatherTemperatureAdjustments;
	private final Map<Material, Double> blockTemperatureAdjustments;
	private final Map<Class<? extends Season>, Double> seasonAdjustments;
	private final Map<Class<? extends Biome>, Double> biomeAdjustments;

	public TemperatureConfig(TomlConfigHandler configHandler) {
		weatherTemperatureAdjustments = new HashMap<>();
		blockTemperatureAdjustments = new HashMap<>();
		seasonAdjustments = new HashMap<>();
		biomeAdjustments = new HashMap<>();

		// Weather adjustments
		if (configHandler.getBoolean("weatherEffects.weatherAffectsTemperature")) {
			weatherTemperatureAdjustments.put(Weather.overworldRain, configHandler.getDouble("weatherEffects.overworldRain"));
			weatherTemperatureAdjustments.put(Weather.overworldSnow, configHandler.getDouble("weatherEffects.overworldSnow"));
			weatherTemperatureAdjustments.put(Weather.overworldStorm, configHandler.getDouble("weatherEffects.overworldStorm"));
			weatherTemperatureAdjustments.put(Weather.overworldWinterSnow, configHandler.getDouble("weatherEffects.overworldWinterSnow"));
			// Add more weather types if needed
		}

		// Block adjustments
		if (configHandler.getBoolean("blockEffects.playerOverBlockAffectsTemperature")) {
			blockTemperatureAdjustments.put(Material.snow, configHandler.getDouble("blockEffects.snowBlock"));
			blockTemperatureAdjustments.put(Material.water, configHandler.getDouble("blockEffects.waterBlock"));
			blockTemperatureAdjustments.put(Material.ice, configHandler.getDouble("blockEffects.iceBlock"));
			blockTemperatureAdjustments.put(Material.lava, configHandler.getDouble("blockEffects.lava"));
			blockTemperatureAdjustments.put(Material.fire, configHandler.getDouble("blockEffects.fire"));
			// Add more block materials if needed
		}

		// Season adjustments
		if (configHandler.getBoolean("seasonEffects.seasonAffectsTemperature")) {
			seasonAdjustments.put(SeasonSummer.class, configHandler.getDouble("seasonEffects.summerTemperature"));
			seasonAdjustments.put(SeasonFall.class, configHandler.getDouble("seasonEffects.fallTemperature"));
			seasonAdjustments.put(SeasonSpring.class, configHandler.getDouble("seasonEffects.springTemperature"));
			seasonAdjustments.put(SeasonWinter.class, configHandler.getDouble("seasonEffects.winterTemperature"));
		}

		// Biome adjustments
		if (configHandler.getBoolean("biomeEffects.biomeAffectsTemperature")) {
			biomeAdjustments.put(BiomeDesert.class, configHandler.getDouble("biomeEffects.desert"));
			biomeAdjustments.put(BiomeBorealForest.class, configHandler.getDouble("biomeEffects.borealForest"));
			biomeAdjustments.put(BiomeBirchForest.class, configHandler.getDouble("biomeEffects.birchForest"));
			biomeAdjustments.put(BiomeCaatinga.class, configHandler.getDouble("biomeEffects.caatinga"));
			biomeAdjustments.put(BiomePlains.class, configHandler.getDouble("biomeEffects.plains"));
			biomeAdjustments.put(BiomeSwamp.class, configHandler.getDouble("biomeEffects.swampLand"));
			biomeAdjustments.put(BiomeTaiga.class, configHandler.getDouble("biomeEffects.taiga"));
			biomeAdjustments.put(BiomeForest.class, configHandler.getDouble("biomeEffects.forest"));
			biomeAdjustments.put(BiomeNether.class, configHandler.getDouble("biomeEffects.nether"));
		}
	}

	public Double getWeatherTemperatureAdjustment(Weather weather) {
		return weatherTemperatureAdjustments.getOrDefault(weather, 0.0);
	}

	public Double getBlockTemperatureAdjustment(Material material) {
		return blockTemperatureAdjustments.getOrDefault(material, 0.0);
	}

	public Double getSeasonAdjustment(Season season) {
		return seasonAdjustments.getOrDefault(season.getClass(), 0.0);
	}

	public Double getBiomeAdjustment(Biome biome) {
		return biomeAdjustments.getOrDefault(biome.getClass(), 0.0);
	}
}
