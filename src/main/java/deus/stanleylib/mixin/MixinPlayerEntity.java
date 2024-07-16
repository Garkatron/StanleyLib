package deus.stanleylib.mixin;

import deus.stanleylib.interfaces.ISubject;
import deus.stanleylib.core.PlayerTemperatureObserver;
import deus.stanleylib.core.enums.CustomDamageTypes;
import deus.stanleylib.core.enums.PlayerTemperatureState;
import deus.stanleylib.interfaces.mixin.IPlayerEntity;
import deus.stanleylib.interfaces.mixin.IStanleyPlayerEntity;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
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
		this.killPlayer();
	}

	@Override
	public void stanley_lib$killByOverheating() {
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

	public void stanley_lib$setState(PlayerTemperatureState state) {
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

		System.out.println("TEMPERATURE: "+current_temperature);

		Weather current_weather = player.world.getCurrentWeather();

		switch ((int) current_temperature) {

			case 40: {
				stanley_lib$setState(PlayerTemperatureState.OVERHEATING);
				break;
			}
			case 20: {
				stanley_lib$setState(PlayerTemperatureState.HOT);
				break;
			}
			case (int) DEFAULT_TEMPERATURE: {
				stanley_lib$setState(PlayerTemperatureState.NORMAL);
				break;
			}
			case -20: {
				stanley_lib$setState(PlayerTemperatureState.COLD);
				break;
			}
			case -40: {
				stanley_lib$setState(PlayerTemperatureState.FREEZING);
				break;
			}
		}

		if (ticks_remaining >= secondsToTicks(NEEDED_TIME_TO_UPDATE)) {
			ticks_remaining = 0;
			if (current_weather.equals(Weather.overworldRain)) {
				stanley_lib$decreasePlayerTemperature(10);
			} else if (current_weather.equals(Weather.overworldSnow)) {
				stanley_lib$decreasePlayerTemperature(20);
			}
		}

		ticks_remaining++;

	}

}
