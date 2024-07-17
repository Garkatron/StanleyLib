package deus.stanleylib.config;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.season.*;
import net.minecraft.core.world.weather.Weather;

import java.util.HashMap;
import java.util.Map;

import static deus.stanleylib.main.MOD_CONFIG;

public class TemperatureConfig {
	private Map<Weather, Float> weatherTemperatureAdjustments;
	private Map<Material, Float> blockTemperatureAdjustments;
	private Map<Class<? extends Season>, Float> seasonAdjustments;

	public static float LEATHER_ARMOR_PROTECTION = MOD_CONFIG.getFloat("temperature.protection_percentage");

	public TemperatureConfig() {
		weatherTemperatureAdjustments = new HashMap<>();
		blockTemperatureAdjustments = new HashMap<>();
		seasonAdjustments = new HashMap<>();

		// Populate maps with values from MOD_CONFIG
		weatherTemperatureAdjustments.put(Weather.overworldRain, MOD_CONFIG.getFloat("on_weather.overworldRain"));
		weatherTemperatureAdjustments.put(Weather.overworldSnow, MOD_CONFIG.getFloat("on_weather.overworldSnow"));
		weatherTemperatureAdjustments.put(Weather.overworldStorm, MOD_CONFIG.getFloat("on_weather.overworldStorm"));
		weatherTemperatureAdjustments.put(Weather.overworldWinterSnow, MOD_CONFIG.getFloat("on_weather.overworldWinterSnow"));

		blockTemperatureAdjustments.put(Material.snow, MOD_CONFIG.getFloat("on_player_over.snowBlock"));
		blockTemperatureAdjustments.put(Material.water, MOD_CONFIG.getFloat("on_player_over.water"));

		seasonAdjustments.put(SeasonSummer.class, MOD_CONFIG.getFloat("season.summer.value"));
		seasonAdjustments.put(SeasonFall.class, MOD_CONFIG.getFloat("season.fall.value"));
		seasonAdjustments.put(SeasonSpring.class, MOD_CONFIG.getFloat("season.summer.value"));
		seasonAdjustments.put(SeasonWinter.class, MOD_CONFIG.getFloat("season.winter.value"));

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

}
