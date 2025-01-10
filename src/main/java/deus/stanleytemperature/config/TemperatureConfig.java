package deus.stanleytemperature.config;

import net.minecraft.client.Minecraft;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucketIceCream;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemSoup;
import net.minecraft.core.world.biome.*;
import net.minecraft.core.world.season.*;
import net.minecraft.core.world.weather.*;
import turniplabs.halplibe.util.TomlConfigHandler;

import java.util.HashMap;
import java.util.Map;

public class TemperatureConfig {

	private final Map<Class<? extends Weather>, Double> weatherTemperatureAdjustments;
	private final Map<Material, Double> blockTemperatureAdjustments;
	private final Map<Class<? extends Season>, Double> seasonAdjustments;
	private final Map<Class<? extends Biome>, Double> biomeAdjustments;
	private final Map<Class<? extends ItemFood>, Double> foodAdjustments;

	public TemperatureConfig(TomlConfigHandler configHandler) {
		weatherTemperatureAdjustments = new HashMap<>();
		blockTemperatureAdjustments = new HashMap<>();
		seasonAdjustments = new HashMap<>();
		biomeAdjustments = new HashMap<>();
		foodAdjustments = new HashMap<>();

		// Weather adjustments
		if (configHandler.getBoolean("weatherEffects.weatherAffectsTemperature")) {
			weatherTemperatureAdjustments.put(WeatherRain.class, configHandler.getDouble("weatherEffects.overworldRain"));
			weatherTemperatureAdjustments.put(WeatherSnow.class, configHandler.getDouble("weatherEffects.overworldSnow"));
			weatherTemperatureAdjustments.put(WeatherStorm.class, configHandler.getDouble("weatherEffects.overworldStorm"));
			weatherTemperatureAdjustments.put(WeatherClear.class, configHandler.getDouble("weatherEffects.overworldClear"));
			// Add more weather types if needed
		}

		if (configHandler.getBoolean("foodEffects.foodAffectsTemperature")) {
			foodAdjustments.put(ItemBucketIceCream.class, configHandler.getDouble("foodEffects.bucketIcecream"));
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

	public Double getWeatherTemperatureAdjustment(Class<? extends Weather> weather) {
		return weatherTemperatureAdjustments.getOrDefault(weather, 0.0);
	}

	public Double getBlockTemperatureAdjustment(Material material) {
		return blockTemperatureAdjustments.getOrDefault(material, 0.0);
	}

	public Double getFoodAdjustment(Class<? extends ItemFood> item) {
		return foodAdjustments.getOrDefault(item, 0.0);
	}

	public Double getSeasonAdjustment(Season season) {
		return seasonAdjustments.getOrDefault(season.getClass(), 0.0);
	}

	public Double getBiomeAdjustment(Biome biome) {
		return biomeAdjustments.getOrDefault(biome.getClass(), 0.0);
	}
}
