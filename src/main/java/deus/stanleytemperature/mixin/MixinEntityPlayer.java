package deus.stanleytemperature.mixin;

import deus.stanleytemperature.enums.CustomDamageTypes;
import deus.stanleytemperature.enums.PlayerTemperatureState;
import deus.stanleytemperature.management.SignalAccessor;
import deus.stanleytemperature.management.TemperatureManager;
import deus.stanleytemperature.interfaces.IStanleyPlayerEntity;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntitySnowman;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.projectile.EntitySnowball;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static deus.stanleytemperature.StanleyTemperature.MOD_CONFIG;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer implements IStanleyPlayerEntity {

	@Unique
	SignalAccessor accessor = SignalAccessor.getInstance();

	@Unique
	private PlayerTemperatureState temperature_state = PlayerTemperatureState.NORMAL;

	@Unique
	private Double current_temperature = MOD_CONFIG.getConfig().getDouble("player.defaultTemperature");

	@Unique
	private Double prev_temperature = MOD_CONFIG.getConfig().getDouble("player.defaultTemperature");

	@Final
	@Unique private final TemperatureManager temperatureManager = new TemperatureManager(this);

	@Shadow public abstract boolean killPlayer();

	@Shadow protected abstract void damageEntity(int damage, DamageType damageType);

	@Shadow public abstract boolean hurt(Entity attacker, int damage, DamageType type);

	@Shadow public abstract ItemStack getCurrentEquippedItem();

	@Inject(method = "<init>(Lnet/minecraft/core/world/World;)V", at = @At("RETURN"), remap = false)
	public void afterConstructor(World world, CallbackInfo ci) {
		//stanley_lib$registerObserver(DEFAULT_OBSERVER);
	}

	@Inject(method = "tick()V", at = @At("RETURN"), remap = false)
	public void afterUpdate(CallbackInfo ci) {
		if (MOD_CONFIG.getConfig().getBoolean("temperatureManagement.activateTemperatureManagement"))
			stanley$updateTemperature();
	}

	@Inject(method = "hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z", at = @At("RETURN"), remap = false)
	public void afterPlayerHurt(Entity attacker, int damage, DamageType type, CallbackInfoReturnable<Boolean> cir) {
		if (MOD_CONFIG.getConfig().getBoolean("snowballEffects.enabled")) {
			Object obj = attacker;
			if (obj instanceof EntitySnowball && ((EntitySnowball) obj).owner != null) {
				stanley$decreasePlayerTemperature(MOD_CONFIG.getConfig().getFloat("snowballEffect.snowballEffect"));
			} else if (attacker instanceof EntitySnowman) {
				stanley$decreasePlayerTemperature(MOD_CONFIG.getConfig().getFloat("snowballEffect.snowballEffect"));
			}
		}
	}

	@Override
	public double stanley$getPlayerTemperature() {
		return this.current_temperature.doubleValue();
	}

	@Override
	public void stanley$setPlayerTemperature(double temperature) {
		this.prev_temperature = this.current_temperature;
		this.current_temperature = temperature;
	}

	@Override
	public void stanley$increasePlayerTemperature(double amount) {
		this.prev_temperature = this.current_temperature;
		this.current_temperature += amount;
		//EntityPlayer player = (EntityPlayer) (Object) this;
		//player.sendMessage("Your temperature has increased by: " + BigDecimal.valueOf(amount).setScale(4, RoundingMode.HALF_UP) +
		//	", current temperature: " + this.current_temperature);
		accessor.temperatureIncremented.emit(current_temperature);

	}

	@Override
	public void stanley$decreasePlayerTemperature(double amount) {
		this.prev_temperature = this.current_temperature;
		this.current_temperature -= amount;
		//EntityPlayer player = (EntityPlayer) (Object) this;
		//player.sendMessage("Your temperature has decreased by: " + BigDecimal.valueOf(amount).setScale(4, RoundingMode.HALF_UP) +
		//	", current temperature: " + this.current_temperature);
		accessor.temperatureDecreased.emit(current_temperature);
	}

	@Override
	public boolean stanley$isPlayerOverheating() {
		return this.current_temperature == MOD_CONFIG.getConfig().getDouble("player.overHeatingTemperature");
	}

	@Override
	public boolean stanley$isPlayerFreezing() {
		return this.current_temperature == MOD_CONFIG.getConfig().getDouble("player.freezingTemperature");
	}

	@Override
	public void stanley$resetPlayerTemperature() {
		this.current_temperature = MOD_CONFIG.getConfig().getDouble("player.defaultTemperature");
	}

	@Override
	public void stanley$killByFreezing() {
		EntityPlayer player = (EntityPlayer) (Object) this;
		player.sendTranslatedChatMessage("killed_by.freezing");
		accessor.killedByFreezing.emit(null);
		this.killPlayer();
	}

	@Override
	public void stanley$killByOverheating() {
		EntityPlayer player = (EntityPlayer) (Object) this;
		player.sendTranslatedChatMessage("killed_by.overheating");
		accessor.killedByOverheating.emit(null);
		this.killPlayer();
	}

	@Override
	public Block stanley$getBlockUnderPlayer() {
		EntityPlayer player = (EntityPlayer) (Object) this;
		return player.world.getBlock((int) player.x, (int) player.y - 2, (int) player.z);
	}

	@Override
	public void stanley$hurtByFreezing(int amount) {
		this.damageEntity(amount, CustomDamageTypes.FREEZING);
		accessor.wasHurtFreezing.emit(null);
	}

	@Override
	public void stanley$hurtByOverheating(int amount) {
		this.damageEntity(amount, CustomDamageTypes.OVERHEATING);
		accessor.wasHurtByOverheating.emit(null);
	}


	public void stanley$setTemperatureState(PlayerTemperatureState state) {
		this.temperature_state = state;

		accessor.updatedTemperatureState.emit(state);

	}

	@Override
	public PlayerTemperatureState stanley$getState() {
		return this.temperature_state;
	}

	@Override
	public void stanley$updateTemperature() {
		temperatureManager.update();
	}

	@Override
	public boolean[] stanley$hasLeatherArmor(EntityPlayer player) {
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


	@Override
	public void stanley$hurtByCold(int amount) {
		this.damageEntity(amount, CustomDamageTypes.COLD);
	}

	@Override
	public void stanley$hurtByHeat(int amount) {
		this.damageEntity(amount, CustomDamageTypes.HEAT);
	}

	@Override
	public Item stanley$getItemInHand() {
		ItemStack itemStack = getCurrentEquippedItem();
		if (itemStack!=null)
			return itemStack.getItem();
		return null;
	}

	@Override
	public double stanley$getPlayerPreviousTemperature() {
		return prev_temperature;
	}
}
