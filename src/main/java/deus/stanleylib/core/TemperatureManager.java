package deus.stanleylib.core;

import deus.stanleylib.config.TemperatureConfig;
import deus.stanleylib.core.enums.PlayerTemperatureState;
import deus.stanleylib.interfaces.mixin.IPlayerEntity;
import deus.stanleylib.interfaces.mixin.IStanleyPlayerEntity;
import deus.stanleylib.mixin.MixinPlayerEntity;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.season.Season;
import net.minecraft.core.world.season.SeasonNull;
import net.minecraft.core.world.season.SeasonWinter;
import net.minecraft.core.world.weather.Weather;
import org.spongepowered.asm.mixin.Unique;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static deus.stanleylib.config.TemperatureConfig.LEATHER_ARMOR_PROTECTION;
import static deus.stanleylib.main.*;
import static deus.stanleylib.main.MOD_CONFIG;

public class TemperatureManager {

	private IStanleyPlayerEntity custom_player;

	private boolean[] sent_messages = new boolean[4];

	private int ticks_remaining = 0;

	public TemperatureManager(IStanleyPlayerEntity custom_player) {
		this.custom_player = custom_player;
		sent_messages[0] = false;
		sent_messages[1] = false;
		sent_messages[2] = false;
		sent_messages[3] = false;
	}

	public void update() {
		IPlayerEntity iplayer = (IPlayerEntity)custom_player;

		Block under_player_block = iplayer.stanley_lib$getBlockUnderPlayer();
		EntityPlayer player = (EntityPlayer) (Object) this.custom_player;
		Biome current_biome_at_block = player.world.getBlockBiome((int) player.x, (int) player.y, (int) player.z);
		Season current_season = player.world.seasonManager.getCurrentSeason();
		boolean[] leather_armors = iplayer.hasLeatherArmor(player);

		Weather current_weather = player.world.getCurrentWeather();
		TemperatureConfig temperatureConfig = new TemperatureConfig();



		double current_temperature = custom_player.stanley_lib$getPlayerTemperature();

		if (current_temperature >= 60) {
			custom_player.stanley_lib$setTemperatureState(PlayerTemperatureState.OVERHEATING);
			if (!sent_messages[0])
				player.sendMessage("You are overheating! Current temperature: " + current_temperature);
			sent_messages[0] = true;

			sent_messages[2] = true;
			sent_messages[1] = true;
			sent_messages[3] = true;

			custom_player.stanley_lib$killByOverheating();
		} else if (current_temperature >= 40) {
			custom_player.stanley_lib$setTemperatureState(PlayerTemperatureState.HOT);
			if (!sent_messages[1])
				player.sendMessage("You are hot. Current temperature: " + current_temperature);
			sent_messages[1] = true;

			sent_messages[0] = true;
			sent_messages[2] = true;
			sent_messages[3] = true;

		} else if (current_temperature == DEFAULT_TEMPERATURE) {
			custom_player.stanley_lib$setTemperatureState(PlayerTemperatureState.NORMAL);

			sent_messages[0] = true;
			sent_messages[1] = true;
			sent_messages[3] = true;
			sent_messages[2] = true;

			// player.sendMessage("Your temperature is normal. Current temperature: " + current_temperature);
		} else if (current_temperature <= 15 && current_temperature > -30) {
			custom_player.stanley_lib$setTemperatureState(PlayerTemperatureState.COLD);
			if (!sent_messages[2])
				player.sendMessage("You are cold. Current temperature: " + current_temperature);
			sent_messages[2] = true;

			sent_messages[0] = true;
			sent_messages[1] = true;
			sent_messages[3] = true;

		} else if (current_temperature <= -40) {
			custom_player.stanley_lib$setTemperatureState(PlayerTemperatureState.FREEZING);
			if (!sent_messages[3])
				player.sendMessage("You are freezing! Current temperature: " + current_temperature);
			sent_messages[3] = true;

			sent_messages[0] = true;
			sent_messages[1] = true;
			sent_messages[2] = true;

			custom_player.stanley_lib$killByFreezing();
		}

		// Calculate total temperature adjustment
		float totalAdjustment = 0.0F;

		if (ticks_remaining >= secondsToTicks(NEEDED_TIME_TO_UPDATE)) {
			ticks_remaining = 0;

			// Adjust temperature based on current weather
			if (MOD_CONFIG.getBool("weather_affects_temperature")) {
				float weatherAdjustment = temperatureConfig.getWeatherTemperatureAdjustment(current_weather);
				totalAdjustment += weatherAdjustment;
			}

			// Adjust temperature based on the block player is standing on
			if (MOD_CONFIG.getBool("player_over_block_affects_temperature")) {
				if (under_player_block != null) {
					// Adjust temperature based on block material
					float blockAdjustment = temperatureConfig.getBlockTemperatureAdjustment(under_player_block.blockMaterial);
					totalAdjustment += blockAdjustment;

				}
			}

			if (current_season!=null || current_season instanceof SeasonNull) {
				float seasonAdjustment = temperatureConfig.getSeasonAdjustment(current_season);
				//System.out.println(seasonAdjustment);
				totalAdjustment += seasonAdjustment;
			}

			// Adjust temperature based on the number of leather armor pieces worn
			int leatherArmorCount = 0;
			for (boolean leatherArmor : leather_armors) {
				if (leatherArmor) {
					leatherArmorCount++;
				}
			}
			totalAdjustment += leatherArmorCount * LEATHER_ARMOR_PROTECTION;


			// Apply total temperature adjustment
			if (totalAdjustment != 0.0F) {
				adjustPlayerTemperature(totalAdjustment);
				//System.out.println(current_temperature);

			}



		}
		ticks_remaining++;

	}
	public void adjustPlayerTemperature(float totalAdjustment) {
		if (totalAdjustment > 0.0F) {
			custom_player.stanley_lib$increasePlayerTemperature(totalAdjustment);
		} else {
			custom_player.stanley_lib$decreasePlayerTemperature(-totalAdjustment);
		}
	}
}
