package deus.stanleytemperature.items;

import deus.stanleytemperature.StanleyTemperature;
import deus.stanleytemperature.enums.PlayerTemperatureState;
import deus.stanleytemperature.interfaces.IStanleyPlayerEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.*;
import net.minecraft.core.world.World;

public class StanleyItemFood extends ItemFood {

	protected double sAmount;
	protected StanleyFoodType type;

	public StanleyItemFood(String name, String namespaceId, int id, int healAmount, int ticksPerHeal, boolean favouriteWolfMeat, int maxStackSize, double sAmount, StanleyFoodType type) {
		super(name, namespaceId, id, healAmount, ticksPerHeal, favouriteWolfMeat, maxStackSize);
		this.sAmount = sAmount;
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {

		PlayerTemperatureState state = ((IStanleyPlayerEntity) entityplayer).stanley$getState();
		String soundEffect = this.getTicksPerHeal() >= 10 ? "random.bite_extended" : "random.bite";

		boolean canConsumeIceCream = entityplayer.getHealth() < entityplayer.getMaxHealth()
			&& entityplayer.getHealth() + entityplayer.getTotalHealingRemaining() < entityplayer.getMaxHealth()
			&& itemstack.consumeItem(entityplayer);


		if (canConsumeIceCream) {
			entityplayer.eatFood(this);
		}


		if (StanleyFoodType.COLD.equals(type)) {
			((IStanleyPlayerEntity)entityplayer).stanley$decreasePlayerTemperature(sAmount);

		} else {
			((IStanleyPlayerEntity)entityplayer).stanley$increasePlayerTemperature(sAmount);

		}

		playSound(world, entityplayer, soundEffect);

		return new ItemStack(this, itemstack.stackSize-1);
	}

	// Helper method to play sound at the entity
	private void playSound(World world, Player entityplayer, String soundEffect) {
		world.playSoundAtEntity(entityplayer, entityplayer, soundEffect,
			0.5F + (itemRand.nextFloat() - itemRand.nextFloat()) * 0.1F,
			1.1F + (itemRand.nextFloat() - itemRand.nextFloat()) * 0.1F);
	}
}
