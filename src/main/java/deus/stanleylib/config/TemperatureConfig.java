package deus.stanleylib.config;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.weather.Weather;

import java.util.HashMap;
import java.util.Map;

import static deus.stanleylib.main.MOD_CONFIG;

public class TemperatureConfig {
	private Map<Weather, Integer> weatherTemperatureAdjustments;
	private Map<Material, Integer> blockTemperatureAdjustments;
	private Map<Weather, Integer> waterWeatherAdjustments;

	public TemperatureConfig() {
		weatherTemperatureAdjustments = new HashMap<>();
		blockTemperatureAdjustments = new HashMap<>();
		waterWeatherAdjustments = new HashMap<>();

		// Populate maps with values from MOD_CONFIG
		weatherTemperatureAdjustments.put(Weather.overworldRain, MOD_CONFIG.getInt("on_weather.overworldRain"));
		weatherTemperatureAdjustments.put(Weather.overworldSnow, MOD_CONFIG.getInt("on_weather.overworldSnow"));
		weatherTemperatureAdjustments.put(Weather.overworldStorm, MOD_CONFIG.getInt("on_weather.overworldStorm"));
		weatherTemperatureAdjustments.put(Weather.overworldWinterSnow, MOD_CONFIG.getInt("on_weather.overworldWinterSnow"));

		blockTemperatureAdjustments.put(Material.snow, MOD_CONFIG.getInt("on_player_over.snowBlock"));
		blockTemperatureAdjustments.put(Material.water, MOD_CONFIG.getInt("on_player_over.water"));

		waterWeatherAdjustments.put(Weather.overworldRain, MOD_CONFIG.getInt("on_weather.waterRain"));
		waterWeatherAdjustments.put(Weather.overworldWinterSnow, MOD_CONFIG.getInt("on_weather.waterWinterSnow"));
		waterWeatherAdjustments.put(Weather.overworldStorm, MOD_CONFIG.getInt("on_weather.waterStorm"));
	}

	public int getWeatherTemperatureAdjustment(Weather weather) {
		return weatherTemperatureAdjustments.getOrDefault(weather, 0);
	}

	public int getBlockTemperatureAdjustment(Material material) {
		return blockTemperatureAdjustments.getOrDefault(material, 0);
	}

	public int getWaterWeatherAdjustment(Weather weather) {
		return waterWeatherAdjustments.getOrDefault(weather, 0);
	}
}
