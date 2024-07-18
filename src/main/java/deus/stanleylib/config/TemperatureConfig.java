package deus.stanleylib.config;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.biome.*;
import net.minecraft.core.world.season.*;
import net.minecraft.core.world.weather.Weather;

import java.util.HashMap;
import java.util.Map;

import static deus.stanleylib.main.MOD_CONFIG;

public class TemperatureConfig {
	private Map<Weather, Float> weatherTemperatureAdjustments;
	private Map<Material, Float> blockTemperatureAdjustments;
	private Map<Class<? extends Season>, Float> seasonAdjustments;
	private Map<Class<? extends Biome>, Float> biomeAdjustments;

	public static float LEATHER_ARMOR_PROTECTION = MOD_CONFIG.getFloat("temperature.leather.protection_percentage");

	public TemperatureConfig() {
		weatherTemperatureAdjustments = new HashMap<>();
		blockTemperatureAdjustments = new HashMap<>();
		seasonAdjustments = new HashMap<>();
		biomeAdjustments = new HashMap<>();

		// Populate maps with values from MOD_CONFIG
		weatherTemperatureAdjustments.put(Weather.overworldRain, MOD_CONFIG.getFloat("on_weather.overworldRain"));
		weatherTemperatureAdjustments.put(Weather.overworldSnow, MOD_CONFIG.getFloat("on_weather.overworldSnow"));
		weatherTemperatureAdjustments.put(Weather.overworldStorm, MOD_CONFIG.getFloat("on_weather.overworldStorm"));
		weatherTemperatureAdjustments.put(Weather.overworldWinterSnow, MOD_CONFIG.getFloat("on_weather.overworldWinterSnow"));

		blockTemperatureAdjustments.put(Material.snow, MOD_CONFIG.getFloat("on_player_over.snowBlock"));
		blockTemperatureAdjustments.put(Material.water, MOD_CONFIG.getFloat("on_player_over.water"));
		blockTemperatureAdjustments.put(Material.ice, MOD_CONFIG.getFloat("on_player_over.iceBlock"));

		seasonAdjustments.put(SeasonSummer.class, MOD_CONFIG.getFloat("temperature.season.summer.value"));
		seasonAdjustments.put(SeasonFall.class, MOD_CONFIG.getFloat("temperature.season.fall.value"));
		seasonAdjustments.put(SeasonSpring.class, MOD_CONFIG.getFloat("temperature.season.spring.value"));
		seasonAdjustments.put(SeasonWinter.class, MOD_CONFIG.getFloat("temperature.season.winter.value"));

		biomeAdjustments.put(BiomeDesert.class, MOD_CONFIG.getFloat("temperature.biome.desert"));
		biomeAdjustments.put(BiomeBorealForest.class, MOD_CONFIG.getFloat("temperature.biome.boreal_forest"));
		biomeAdjustments.put(BiomeBirchForest.class, MOD_CONFIG.getFloat("temperature.biome.birch_forest"));
		biomeAdjustments.put(BiomeCaatinga.class, MOD_CONFIG.getFloat("temperature.biome.caatinga"));
		biomeAdjustments.put(BiomePlains.class, MOD_CONFIG.getFloat("temperature.biome.plains"));
		biomeAdjustments.put(BiomeSwamp.class, MOD_CONFIG.getFloat("temperature.biome.swanp_land"));
		biomeAdjustments.put(BiomeTaiga.class, MOD_CONFIG.getFloat("temperature.biome.Taiga"));
		biomeAdjustments.put(BiomeForest.class, MOD_CONFIG.getFloat("temperature.biome.forest"));
		biomeAdjustments.put(BiomeNether.class, MOD_CONFIG.getFloat("temperature.biome.nether"));

	}

	public float getWeatherTemperatureAdjustment(Weather weather) {
		return weatherTemperatureAdjustments.getOrDefault(weather, 0.0F);
	}

	public float getBlockTemperatureAdjustment(Material material) {
		return blockTemperatureAdjustments.getOrDefault(material, 0.0F);
	}

	public float getSeasonAdjustment(Season season) {
		return seasonAdjustments.getOrDefault(season.getClass(), 0.0F);
	}

	public float getBiomeAdjustment(Biome biome) {
		return biomeAdjustments.getOrDefault(biome.getClass(), 0.0F);

	}

}
