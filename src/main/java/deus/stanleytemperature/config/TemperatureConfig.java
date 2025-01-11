package deus.stanleytemperature.config;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.ItemBucketIceCream;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.world.biome.*;
import net.minecraft.core.world.season.*;
import net.minecraft.core.world.weather.*;
import turniplabs.halplibe.util.TomlConfigHandler;

import java.util.HashMap;
import java.util.Map;

public class TemperatureConfig {

	private final Map<Class<? extends Weather>, Double> weatherValues;
	private final Map<Material, Double> blockValues;
	private final Map<Class<? extends Season>, Double> seasonValues;
	private final Map<Class<? extends Biome>, Double> biomeValues;
	private final Map<Class<? extends ItemFood>, Double> vanillaFoodValues;
	private final Map<String, Double> foodValues;

	public TemperatureConfig(TomlConfigHandler configHandler) {
		weatherValues = new HashMap<>();
		blockValues = new HashMap<>();
		seasonValues = new HashMap<>();
		biomeValues = new HashMap<>();
		vanillaFoodValues = new HashMap<>();
		foodValues = new HashMap<>();

		// Weather adjustments
		if (configHandler.getBoolean("weatherEffects.enabled")) {
			weatherValues.put(WeatherRain.class, configHandler.getDouble("weatherEffects.overworldRain"));
			weatherValues.put(WeatherSnow.class, configHandler.getDouble("weatherEffects.overworldSnow"));
			weatherValues.put(WeatherStorm.class, configHandler.getDouble("weatherEffects.overworldStorm"));
			weatherValues.put(WeatherClear.class, configHandler.getDouble("weatherEffects.overworldClear"));
			// Add more weather types if needed
		}

		if (configHandler.getBoolean("vanillaFoodEffects.enabled")) {
			vanillaFoodValues.put(ItemBucketIceCream.class, configHandler.getDouble("vanillaFoodEffects.bucketIcecream"));
			// Add more weather types if needed
		}


		if (configHandler.getBoolean("foodEffects.enabled")) {
			foodValues.put("netherrackMeatBalls", configHandler.getDouble("foodEffects.netherrackMeatBalls"));
			foodValues.put("iceCubes", configHandler.getDouble("foodEffects.iceCubes"));
			// Add more weather types if needed
		}


		// Block adjustments
		if (configHandler.getBoolean("blockEffects.enabled")) {
			blockValues.put(Material.snow, configHandler.getDouble("blockEffects.snowBlock"));
			blockValues.put(Material.water, configHandler.getDouble("blockEffects.waterBlock"));
			blockValues.put(Material.ice, configHandler.getDouble("blockEffects.iceBlock"));
			blockValues.put(Material.lava, configHandler.getDouble("blockEffects.lava"));
			blockValues.put(Material.fire, configHandler.getDouble("blockEffects.fire"));
			// Add more block materials if needed
		}

		// Season adjustments
		if (configHandler.getBoolean("seasonEffects.enabled")) {
			seasonValues.put(SeasonSummer.class, configHandler.getDouble("seasonEffects.summerTemperature"));
			seasonValues.put(SeasonFall.class, configHandler.getDouble("seasonEffects.fallTemperature"));
			seasonValues.put(SeasonSpring.class, configHandler.getDouble("seasonEffects.springTemperature"));
			seasonValues.put(SeasonWinter.class, configHandler.getDouble("seasonEffects.winterTemperature"));
		}

		// Biome adjustments
		if (configHandler.getBoolean("biomeEffects.enabled")) {
			biomeValues.put(BiomeDesert.class, configHandler.getDouble("biomeEffects.desert"));
			biomeValues.put(BiomeBorealForest.class, configHandler.getDouble("biomeEffects.borealForest"));
			biomeValues.put(BiomeBirchForest.class, configHandler.getDouble("biomeEffects.birchForest"));
			biomeValues.put(BiomeCaatinga.class, configHandler.getDouble("biomeEffects.caatinga"));
			biomeValues.put(BiomePlains.class, configHandler.getDouble("biomeEffects.plains"));
			biomeValues.put(BiomeSwamp.class, configHandler.getDouble("biomeEffects.swampLand"));
			biomeValues.put(BiomeTaiga.class, configHandler.getDouble("biomeEffects.taiga"));
			biomeValues.put(BiomeForest.class, configHandler.getDouble("biomeEffects.forest"));
			biomeValues.put(BiomeNether.class, configHandler.getDouble("biomeEffects.nether"));
		}
	}

	public Double getFoodValues(String item) {
		return foodValues.getOrDefault(item, 0.0);
	}

	public Double getWeatherValues(Class<? extends Weather> weather) {
		return weatherValues.getOrDefault(weather, 0.0);
	}

	public Double getBlockValues(Material material) {
		return blockValues.getOrDefault(material, 0.0);
	}



	public Double getVanillaFoodValues(Class<? extends ItemFood> item) {
		return vanillaFoodValues.getOrDefault(item, 0.0);
	}

	public Double getSeasonValues(Season season) {
		return seasonValues.getOrDefault(season.getClass(), 0.0);
	}

	public Double getBiomeValues(Biome biome) {
		return biomeValues.getOrDefault(biome.getClass(), 0.0);
	}
}
