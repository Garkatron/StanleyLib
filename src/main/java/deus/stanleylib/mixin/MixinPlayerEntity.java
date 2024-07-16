package deus.stanleylib.mixin;

import deus.stanleylib.config.TemperatureConfig;
import deus.stanleylib.interfaces.ISubject;
import deus.stanleylib.core.PlayerTemperatureObserver;
import deus.stanleylib.core.enums.CustomDamageTypes;
import deus.stanleylib.core.enums.PlayerTemperatureState;
import deus.stanleylib.interfaces.mixin.IPlayerEntity;
import deus.stanleylib.interfaces.mixin.IStanleyPlayerEntity;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.weather.Weather;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

import static deus.stanleylib.main.*;

@Mixin(EntityPlayer.class)
public abstract class MixinPlayerEntity implements IStanleyPlayerEntity, IPlayerEntity, ISubject {

	@Unique
	private List<PlayerTemperatureObserver> observers = new ArrayList<>();

	@Unique
	private PlayerTemperatureState temperature_state = PlayerTemperatureState.NORMAL;

	@Unique
	private double current_temperature = DEFAULT_TEMPERATURE;

	@Unique
	private int ticks_remaining = 0;

	@Shadow
	public abstract boolean killPlayer();

	@Shadow
	protected abstract void damageEntity(int damage, DamageType damageType);

	@Inject(method = "<init>(Lnet/minecraft/core/world/World;)V", at = @At("RETURN"), remap = false)
	public void afterConstructor(World world, CallbackInfo ci) {
		//stanley_lib$registerObserver(DEFAULT_OBSERVER);
	}


	@Inject(method = "Lnet/minecraft/core/entity/player/EntityPlayer;tick()V", at = @At("RETURN"), remap = false)
	public void afterUpdate(CallbackInfo ci) {
		stanley_lib$updateTemperature();
	}

	@Override
	public double stanley_lib$getPlayerTemperature() {
		return this.current_temperature;
	}

	@Override
	public void stanley_lib$setPlayerTemperature(double temperature) {
		this.current_temperature = temperature;
	}

	@Override
	public void stanley_lib$increasePlayerTemperature(double amount) {
		this.current_temperature += amount;
	}

	@Override
	public void stanley_lib$decreasePlayerTemperature(double amount) {
		this.current_temperature -= amount;
	}

	@Override
	public boolean stanley_lib$isPlayerOverheating() {
		return this.current_temperature >= MAX_TEMPERATURE;
	}

	@Override
	public boolean stanley_lib$isPlayerFreezing() {
		return this.current_temperature <= MIN_TEMPERATURE;
	}

	@Override
	public void stanley_lib$resetPlayerTemperature() {
		this.current_temperature = DEFAULT_TEMPERATURE;
	}

	@Override
	public void stanley_lib$killByFreezing() {
		EntityPlayer player = (EntityPlayer) (Object) this;
		player.sendTranslatedChatMessage("killed_by.freezing");
		this.killPlayer();
	}

	@Override
	public void stanley_lib$killByOverheating() {
		EntityPlayer player = (EntityPlayer) (Object) this;
		player.sendTranslatedChatMessage("killed_by.overheating");
		this.killPlayer();
	}

	@Override
	public Block stanley_lib$getBlockUnderPlayer() {
		EntityPlayer player = (EntityPlayer) (Object) this;
		return player.world.getBlock((int) player.x, (int) player.y, (int) player.z);
	}

	@Override
	public void stanley_lib$hurtByFreezing(int amount) {
		this.damageEntity(amount, CustomDamageTypes.FREEZING);
	}

	@Override
	public void stanley_lib$hurtByOverheating(int amount) {
		this.damageEntity(amount, CustomDamageTypes.OVERHEATING);
	}

	@Override
	public void stanley_lib$registerObserver(PlayerTemperatureObserver observer) {
		observers.add(observer);
	}

	@Override
	public void stanley_lib$removeObserver(PlayerTemperatureObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void stanley_lib$notifyObservers() {
		for (PlayerTemperatureObserver observer : observers) {
			observer.stanley_lib$update();
		}
	}

	public void stanley_lib$setTemperatureState(PlayerTemperatureState state) {
		this.temperature_state = state;
		stanley_lib$notifyObservers();
	}

	@Override
	public PlayerTemperatureState stanley_lib$getState() {
		return this.temperature_state;
	}

	@Override
	public void stanley_lib$updateTemperature() {
		Block under_player_block = this.stanley_lib$getBlockUnderPlayer();
		EntityPlayer player = (EntityPlayer) (Object) this;
		Biome current_biome_at_block = player.world.getBlockBiome((int) player.x, (int) player.y, (int) player.z);

		Weather current_weather = player.world.getCurrentWeather();
		TemperatureConfig temperatureConfig = new TemperatureConfig();

		// Determine player temperature state based on current temperature
		if (current_temperature >= 40) {
			stanley_lib$setTemperatureState(PlayerTemperatureState.OVERHEATING);
			stanley_lib$killByOverheating();
		} else if (current_temperature >= 20) {
			stanley_lib$setTemperatureState(PlayerTemperatureState.HOT);
		} else if (current_temperature == DEFAULT_TEMPERATURE) {
			stanley_lib$setTemperatureState(PlayerTemperatureState.NORMAL);
		} else if (current_temperature <= -20 && current_temperature > -40) {
			stanley_lib$setTemperatureState(PlayerTemperatureState.COLD);
		} else if (current_temperature <= -40) {
			stanley_lib$setTemperatureState(PlayerTemperatureState.FREEZING);
			stanley_lib$killByFreezing();
		}

		// Adjust temperature based on weather and block under player
		if (ticks_remaining >= secondsToTicks(NEEDED_TIME_TO_UPDATE)) {
			ticks_remaining = 0;

			// Adjust temperature based on current weather
			int weatherAdjustment = temperatureConfig.getWeatherTemperatureAdjustment(current_weather);
			if (weatherAdjustment != 0) {
				if (weatherAdjustment > 0) {
					stanley_lib$increasePlayerTemperature(weatherAdjustment);
				} else {
					stanley_lib$decreasePlayerTemperature(-weatherAdjustment);
				}
			}

			if (under_player_block==null){
				return;
			}

			// Adjust temperature based on block material
			int blockAdjustment = temperatureConfig.getBlockTemperatureAdjustment(under_player_block.blockMaterial);
			if (blockAdjustment != 0) {
				if (blockAdjustment > 0) {
					stanley_lib$increasePlayerTemperature(blockAdjustment);
				} else {
					stanley_lib$decreasePlayerTemperature(-blockAdjustment);
				}
			}

			// Special handling for water block material with different weather conditions
			if (under_player_block.blockMaterial == Material.water) {
				int waterWeatherAdjustment = temperatureConfig.getWaterWeatherAdjustment(current_weather);
				if (waterWeatherAdjustment != 0) {
					if (waterWeatherAdjustment > 0) {
						stanley_lib$increasePlayerTemperature(waterWeatherAdjustment);
					} else {
						stanley_lib$decreasePlayerTemperature(-waterWeatherAdjustment);
					}
				} else {
					if (blockAdjustment > 0) {
						stanley_lib$increasePlayerTemperature(blockAdjustment);
					} else {
						stanley_lib$decreasePlayerTemperature(-blockAdjustment);
					}
				}
			}
		}

		ticks_remaining++;
	}


}
