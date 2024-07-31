package deus.stanleylib.core;

import deus.stanleylib.config.TemperatureConfig;
import deus.stanleylib.core.enums.PlayerTemperatureState;
import deus.stanleylib.interfaces.mixin.IPlayerEntity;
import deus.stanleylib.interfaces.mixin.IStanleyPlayerEntity;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.season.Season;
import net.minecraft.core.world.weather.Weather;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static deus.stanleylib.main.*;

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
		IPlayerEntity iplayer = (IPlayerEntity) custom_player;

		Block under_player_block = iplayer.stanley_lib$getBlockUnderPlayer();
		EntityPlayer player = (EntityPlayer) (Object) this.custom_player;

		Biome current_biome_at_block = player.world.getBlockBiome((int) player.x, (int) player.y, (int) player.z);
		Season current_season = player.world.seasonManager.getCurrentSeason();
		boolean[] leather_armors = iplayer.hasLeatherArmor(player);
		Weather current_weather = player.world.getCurrentWeather();

		BigDecimal current_temperature = BigDecimal.valueOf(custom_player.stanley_lib$getPlayerTemperature());

		// Temperature state checks
		if (current_temperature.compareTo(BigDecimal.valueOf(60)) >= 0) {
			custom_player.stanley_lib$setTemperatureState(PlayerTemperatureState.OVERHEATING);
			if (!sent_messages[0])
				player.sendMessage("You are overheating! Current temperature: " + current_temperature);
			sent_messages[0] = true;
			sent_messages[1] = true;
			sent_messages[2] = true;
			sent_messages[3] = true;
			custom_player.stanley_lib$killByOverheating();
		} else if (current_temperature.compareTo(BigDecimal.valueOf(40)) >= 0) {
			custom_player.stanley_lib$setTemperatureState(PlayerTemperatureState.HOT);
			if (!sent_messages[1])
				player.sendMessage("You are hot. Current temperature: " + current_temperature);
			sent_messages[1] = true;
			sent_messages[0] = true;
			sent_messages[2] = true;
			sent_messages[3] = true;
		} else if (current_temperature.compareTo(BigDecimal.valueOf(DEFAULT_TEMPERATURE)) == 0) {
			custom_player.stanley_lib$setTemperatureState(PlayerTemperatureState.NORMAL);
			sent_messages[0] = true;
			sent_messages[1] = true;
			sent_messages[2] = true;
			sent_messages[3] = true;
			// player.sendMessage("Your temperature is normal. Current temperature: " + current_temperature);
		} else if (current_temperature.compareTo(BigDecimal.valueOf(15)) <= 0 && current_temperature.compareTo(BigDecimal.valueOf(-30)) > 0) {
			custom_player.stanley_lib$setTemperatureState(PlayerTemperatureState.COLD);
			if (!sent_messages[2])
				player.sendMessage("You are cold. Current temperature: " + current_temperature);
			sent_messages[2] = true;
			sent_messages[0] = true;
			sent_messages[1] = true;
			sent_messages[3] = true;
		} else if (current_temperature.compareTo(BigDecimal.valueOf(-40)) <= 0) {
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
		BigDecimal totalAdjustment = BigDecimal.ZERO;

		if (ticks_remaining >= secondsToTicks(NEEDED_TIME_TO_UPDATE)) {
			ticks_remaining = 0;

			// Adjust temperature based on current weather
			if (MOD_CONFIG.getConfig().getBoolean("weatherEffects.weatherAffectsTemperature")) {
				BigDecimal weatherAdjustment = BigDecimal.valueOf(MOD_CONFIG.getTemperatureConfig().getWeatherTemperatureAdjustment(current_weather));
				totalAdjustment = totalAdjustment.add(weatherAdjustment);
			}

			// Adjust temperature based on the block player is standing on
			if (MOD_CONFIG.getConfig().getBoolean("blockEffects.playerOverBlockAffectsTemperature")) {
				if (under_player_block != null) {
					BigDecimal blockAdjustment = BigDecimal.valueOf(MOD_CONFIG.getTemperatureConfig().getBlockTemperatureAdjustment(under_player_block.blockMaterial));
					totalAdjustment = totalAdjustment.add(blockAdjustment);
				}
			}

			// Adjust temperature based on the current season
			if (MOD_CONFIG.getConfig().getBoolean("seasonEffects.seasonAffectsTemperature")) {
				if (current_season != null) {
					BigDecimal seasonAdjustment = BigDecimal.valueOf(MOD_CONFIG.getTemperatureConfig().getSeasonAdjustment(current_season));
					totalAdjustment = totalAdjustment.add(seasonAdjustment);
				}
			}

			// Adjust temperature based on the current biome
			if (MOD_CONFIG.getConfig().getBoolean("biomeEffects.biomeAffectsTemperature")) {
				if (current_biome_at_block != null) {
					BigDecimal biomeAdjustment = BigDecimal.valueOf(MOD_CONFIG.getTemperatureConfig().getBiomeAdjustment(current_biome_at_block));
					totalAdjustment = totalAdjustment.add(biomeAdjustment);
				}
			}

			// Adjust temperature based on leather armor protection
			if (MOD_CONFIG.getConfig().getBoolean("leatherProtection.leatherProtectsTemperature")) {
				int leatherArmorCount = 0;
				for (boolean leatherArmor : leather_armors) {
					if (leatherArmor) {
						leatherArmorCount++;
					}
				}
				BigDecimal leatherArmorAdjustment = BigDecimal.valueOf(leatherArmorCount).multiply(BigDecimal.valueOf(MOD_CONFIG.getConfig().getDouble("leatherProtection.leatherProtectionPercentage")));
				totalAdjustment = totalAdjustment.add(leatherArmorAdjustment);
			}

			// Apply total temperature adjustment
			if (totalAdjustment.compareTo(BigDecimal.ZERO) != 0) {
				adjustPlayerTemperature(totalAdjustment);
			}
		}
		ticks_remaining++;
	}

	public void adjustPlayerTemperature(BigDecimal totalAdjustment) {
		totalAdjustment = totalAdjustment.setScale(4, RoundingMode.HALF_UP); // round to 4 decimal places
		if (totalAdjustment.compareTo(BigDecimal.ZERO) > 0) {
			custom_player.stanley_lib$increasePlayerTemperature(totalAdjustment.doubleValue());
		} else {
			custom_player.stanley_lib$decreasePlayerTemperature(totalAdjustment.negate().doubleValue());
		}
	}
}
