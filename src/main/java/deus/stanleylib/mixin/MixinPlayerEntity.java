package deus.stanleylib.mixin;

import deus.stanleylib.config.TemperatureConfig;
import deus.stanleylib.core.TemperatureManager;
import deus.stanleylib.interfaces.ISubject;
import deus.stanleylib.core.PlayerTemperatureObserver;
import deus.stanleylib.core.enums.CustomDamageTypes;
import deus.stanleylib.core.enums.PlayerTemperatureState;
import deus.stanleylib.interfaces.mixin.IPlayerEntity;
import deus.stanleylib.interfaces.mixin.IStanleyPlayerEntity;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntitySnowman;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.entity.projectile.EntitySnowball;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.season.Season;
import net.minecraft.core.world.season.SeasonNull;
import net.minecraft.core.world.season.SeasonWinter;
import net.minecraft.core.world.weather.Weather;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static deus.stanleylib.config.TemperatureConfig.LEATHER_ARMOR_PROTECTION;
import static deus.stanleylib.main.*;

@Mixin(EntityPlayer.class)
public abstract class MixinPlayerEntity implements IStanleyPlayerEntity, IPlayerEntity, ISubject {

	@Unique
	private List<PlayerTemperatureObserver> observers = new ArrayList<>();

	@Unique
	private PlayerTemperatureState temperature_state = PlayerTemperatureState.NORMAL;

	@Unique
	private BigDecimal current_temperature = BigDecimal.valueOf(DEFAULT_TEMPERATURE);

	@Unique
	private TemperatureManager temperatureManager = new TemperatureManager(this);

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
		if (MOD_CONFIG.getBool("stanley.activate.temperature_management"))
			stanley_lib$updateTemperature();
	}

	@Inject(method = "Lnet/minecraft/core/entity/player/EntityPlayer;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z", at = @At("RETURN"), remap = false)
	public void afterPlayerHurt(Entity attacker, int damage, DamageType type, CallbackInfoReturnable<Boolean> cir) {
		if (MOD_CONFIG.getBool("temperature.snowball_affects_temperature")) {
			Object obj = attacker;
			if (obj instanceof EntitySnowball && ((EntitySnowball) obj).owner != null) {
				stanley_lib$decreasePlayerTemperature(MOD_CONFIG.getFloat("temperature.snowball"));
			} else if (attacker instanceof EntitySnowman) {
				stanley_lib$decreasePlayerTemperature(MOD_CONFIG.getFloat("temperature.snowball"));
			}
		}
	}

	@Override
	public double stanley_lib$getPlayerTemperature() {
		return this.current_temperature.doubleValue();
	}

	@Override
	public void stanley_lib$setPlayerTemperature(double temperature) {
		this.current_temperature = BigDecimal.valueOf(temperature).setScale(4, RoundingMode.HALF_UP);
	}

	@Override
	public void stanley_lib$increasePlayerTemperature(double amount) {
		this.current_temperature = this.current_temperature.add(BigDecimal.valueOf(amount)).setScale(4, RoundingMode.HALF_UP);
		//EntityPlayer player = (EntityPlayer) (Object) this;
		//player.sendMessage("Your temperature has increased by: " + BigDecimal.valueOf(amount).setScale(4, RoundingMode.HALF_UP) +
		//	", current temperature: " + this.current_temperature);
	}

	@Override
	public void stanley_lib$decreasePlayerTemperature(double amount) {
		this.current_temperature = this.current_temperature.subtract(BigDecimal.valueOf(amount)).setScale(4, RoundingMode.HALF_UP);
		//EntityPlayer player = (EntityPlayer) (Object) this;
		//player.sendMessage("Your temperature has decreased by: " + BigDecimal.valueOf(amount).setScale(4, RoundingMode.HALF_UP) +
		//	", current temperature: " + this.current_temperature);
	}

	@Override
	public boolean stanley_lib$isPlayerOverheating() {
		return this.current_temperature.compareTo(BigDecimal.valueOf(MAX_TEMPERATURE)) >= 0;
	}

	@Override
	public boolean stanley_lib$isPlayerFreezing() {
		return this.current_temperature.compareTo(BigDecimal.valueOf(MIN_TEMPERATURE)) <= 0;
	}

	@Override
	public void stanley_lib$resetPlayerTemperature() {
		this.current_temperature = BigDecimal.valueOf(DEFAULT_TEMPERATURE).setScale(4, RoundingMode.HALF_UP);
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
		return player.world.getBlock((int) player.x, (int) player.y - 2, (int) player.z);
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
		temperatureManager.update();
	}

	@Override
	public boolean[] hasLeatherArmor(EntityPlayer player) {
		ItemArmor helmet = getArmorFromSlot(player, 0);
		ItemArmor chestplate = getArmorFromSlot(player, 1);
		ItemArmor leggings = getArmorFromSlot(player, 2);
		ItemArmor boots = getArmorFromSlot(player, 3);

		boolean a = helmet != null && helmet.material == ArmorMaterial.LEATHER;
		boolean b = chestplate != null && chestplate.material == ArmorMaterial.LEATHER;
		boolean c = leggings != null && leggings.material == ArmorMaterial.LEATHER;
		boolean d = boots != null && boots.material == ArmorMaterial.LEATHER;

		return new boolean[]{a, b, c, d};
	}

	@Unique
	private ItemArmor getArmorFromSlot(EntityPlayer player, int slot_index) {
		if (slot_index < 0 || slot_index >= player.inventory.armorInventory.length) {
			return null;
		}
		ItemStack armorStack = player.inventory.armorInventory[slot_index];

		if (armorStack == null)
			return null;

		if (armorStack.getItem() instanceof ItemArmor) {
			return (ItemArmor) armorStack.getItem();
		}
		return null;
	}
}
