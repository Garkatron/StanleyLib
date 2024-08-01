package deus.stanleylib.management;

import deus.stanleylib.enums.PlayerTemperatureState;

import deus.stanleylib.mixin.IPlayerEntity;
import deus.stanleylib.mixin.IStanleyPlayerEntity;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.season.Season;
import net.minecraft.core.world.weather.Weather;

import static deus.stanleylib.main.*;

public class TemperatureManager {

	private IStanleyPlayerEntity custom_player;

	private boolean[] sent_messages = new boolean[4];

	private int ticks_remaining = 0;

	Double overheatingTemperature = MOD_CONFIG.getConfig().getDouble("player.overHeatingTemperature");
	Double hotTemperature = MOD_CONFIG.getConfig().getDouble("player.hotTemperature");
	Double defaultTemperature = MOD_CONFIG.getConfig().getDouble("player.defaultTemperature");
	Double coldTemperature = MOD_CONFIG.getConfig().getDouble("player.coldTemperature");
	Double freezingTemperature = MOD_CONFIG.getConfig().getDouble("player.freezingTemperature");


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

		Double current_temperature = custom_player.stanley_lib$getPlayerTemperature();

		updatePlayerTemperatureState(player, current_temperature);

		// Calculate total temperature adjustment
		Double totalAdjustment = 0.0;

		if (ticks_remaining >= secondsToTicks(NEEDED_TIME_TO_UPDATE)) {
			ticks_remaining = 0;

			// Adjust temperature based on current weather
			if (MOD_CONFIG.getConfig().getBoolean("weatherEffects.weatherAffectsTemperature")) {
				totalAdjustment += MOD_CONFIG.getTemperatureConfig().getWeatherTemperatureAdjustment(current_weather);
			}

			// Adjust temperature based on the block player is standing on
			if (MOD_CONFIG.getConfig().getBoolean("blockEffects.playerOverBlockAffectsTemperature")) {
				if (under_player_block != null) {
					totalAdjustment += MOD_CONFIG.getTemperatureConfig().getBlockTemperatureAdjustment(under_player_block.blockMaterial);
				}
			}

			// Adjust temperature based on the current season
			if (MOD_CONFIG.getConfig().getBoolean("seasonEffects.seasonAffectsTemperature")) {
				if (current_season != null) {
					totalAdjustment += MOD_CONFIG.getTemperatureConfig().getSeasonAdjustment(current_season);
				}
			}

			// Adjust temperature based on the current biome
			if (MOD_CONFIG.getConfig().getBoolean("biomeEffects.biomeAffectsTemperature")) {
				if (current_biome_at_block != null) {
					totalAdjustment += MOD_CONFIG.getTemperatureConfig().getBiomeAdjustment(current_biome_at_block);
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
				double leatherArmorAdjustment = leatherArmorCount * MOD_CONFIG.getConfig().getDouble("leatherProtection.leatherProtectionPercentage");
				totalAdjustment += leatherArmorAdjustment;
			}

			if (totalAdjustment != 0.0f) {
				adjustPlayerTemperature(totalAdjustment);
			}
		}
		ticks_remaining++;
	}

	public void updatePlayerTemperatureState(EntityPlayer player, Double current_temperature) {
		boolean isOverheating = current_temperature >= overheatingTemperature;

		boolean isHot = current_temperature >= hotTemperature && current_temperature < overheatingTemperature;

		boolean isNormal = current_temperature == defaultTemperature;

		boolean isCold = current_temperature <= coldTemperature && current_temperature > freezingTemperature;

		boolean isFreezing = current_temperature <= freezingTemperature;

		// Temperature state checks
		if (isOverheating) {
			custom_player.stanley_lib$setTemperatureState(PlayerTemperatureState.OVERHEATING);
			if (!sent_messages[0]) {
				player.sendMessage("You are overheating! Current temperature: " + current_temperature);
			}
			sent_messages[0] = sent_messages[1] = sent_messages[2] = sent_messages[3] = true;
			custom_player.stanley_lib$killByOverheating();
		} else if (isHot) {
			custom_player.stanley_lib$setTemperatureState(PlayerTemperatureState.HOT);
			if (!sent_messages[1]) {
				player.sendMessage("You are hot. Current temperature: " + current_temperature);
			}
			sent_messages[0] = sent_messages[1] = sent_messages[2] = sent_messages[3] = true;
		} else if (isNormal) {
			custom_player.stanley_lib$setTemperatureState(PlayerTemperatureState.NORMAL);
			sent_messages[0] = sent_messages[1] = sent_messages[2] = sent_messages[3] = true;
			// player.sendMessage("Your temperature is normal. Current temperature: " + current_temperature);
		} else if (isCold) {
			custom_player.stanley_lib$setTemperatureState(PlayerTemperatureState.COLD);
			if (!sent_messages[2]) {
				player.sendMessage("You are cold. Current temperature: " + current_temperature);
			}
			sent_messages[0] = sent_messages[1] = sent_messages[2] = sent_messages[3] = true;
		} else if (isFreezing) {
			custom_player.stanley_lib$setTemperatureState(PlayerTemperatureState.FREEZING);
			if (!sent_messages[3]) {
				player.sendMessage("You are freezing! Current temperature: " + current_temperature);
			}
			sent_messages[0] = sent_messages[1] = sent_messages[2] = sent_messages[3] = true;
			custom_player.stanley_lib$killByFreezing();
		}
	}

	public void adjustPlayerTemperature(Double totalAdjustment) {
		double rTotalAdjustment = Math.round(totalAdjustment * 10000.0f) / 10000.0f; // round to 4 decimal places
		if (rTotalAdjustment > 0) {
			custom_player.stanley_lib$increasePlayerTemperature(rTotalAdjustment);
		} else {
			custom_player.stanley_lib$decreasePlayerTemperature(-rTotalAdjustment);
		}
	}
}
