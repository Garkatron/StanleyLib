package deus.stanleylib.mixin;

import deus.stanleylib.enums.CustomDamageTypes;
import deus.stanleylib.interfaces.IPlayerEntity;
import deus.stanleylib.interfaces.IStanleyPlayerEntity;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.weather.Weather;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import static deus.stanleylib.main.*;

@Mixin(EntityPlayer.class)
public abstract class MixinPlayerEntity implements IStanleyPlayerEntity, IPlayerEntity {

	@Unique
	private double current_temperature = DEFAULT_TEMPERATURE;

	@Shadow
	public abstract boolean killPlayer();

	@Shadow
	protected abstract void damageEntity(int damage, DamageType damageType);

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
	public void stanley_lib$hurtByFreezing(int amount) {
		this.damageEntity(amount, CustomDamageTypes.FREEZING);
	}

	@Override
	public void stanley_lib$hurtByOverheating(int amount) {
		this.damageEntity(amount, CustomDamageTypes.OVERHEATING);
	}

	@Override
	public void stanley_lib$updateTemperature() {
		Block under_player_block = this.stanley_lib$getBlockUnderPlayer();
		EntityPlayer player = (EntityPlayer) (Object) this;

		Weather current_weather = player.world.getCurrentWeather();

		if (current_weather.equals(Weather.overworldRain)) {
			stanley_lib$decreasePlayerTemperature(10);
		}

	}

	//@Inject(method = "<init>(Lnet/minecraft/core/world/World;)V", at = @At("RETURN"), remap = false)
	@Override
	public Block stanley_lib$getBlockUnderPlayer() {
		EntityPlayer player = (EntityPlayer) (Object) this;
		return player.world.getBlock((int) player.x, (int) player.y, (int) player.z);

	}
}
