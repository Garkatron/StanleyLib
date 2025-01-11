package deus.stanleytemperature.mixin;

import deus.stanleytemperature.enums.PlayerTemperatureState;
import deus.stanleytemperature.interfaces.IStanleyPlayerEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.*;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemBucketIceCream.class)
public class MixinIceCreamBucket {

	private static final Random ITEM_RAND = new Random();

	@Inject(method = "onUseItem", at = @At(value = "HEAD"), remap = false, cancellable = true)
	private void customOnUseItem(ItemStack itemstack, World world, Player entityplayer, CallbackInfoReturnable<ItemStack> cir) {
		// Check if the player can consume the ice cream
		boolean canConsumeIceCream = entityplayer.getHealth() < entityplayer.getMaxHealth()
			&& entityplayer.getHealth() + entityplayer.getTotalHealingRemaining() < entityplayer.getMaxHealth()
			&& itemstack.consumeItem(entityplayer);

		// Get the temperature state of the player
		PlayerTemperatureState state = ((IStanleyPlayerEntity) entityplayer).stanley$getState();

		// Determine sound effect
		String soundEffect = ((ItemBucketIceCream) (Object) this).getTicksPerHeal() >= 10 ? "random.bite_extended" : "random.bite";

		// Check the player's temperature state and apply effects
		if (canConsumeIceCream) {
			// Heal the player and apply the sound effect
			entityplayer.eatFood((ItemBucketIceCream) (Object) this);
			playSound(world, entityplayer, soundEffect);
			cir.setReturnValue(new ItemStack(Items.BUCKET));

		} else {
			playSound(world, entityplayer, soundEffect);
			cir.setReturnValue(new ItemStack(Items.BUCKET));
		}
	}

	// Helper method to play sound at the entity
	private void playSound(World world, Player entityplayer, String soundEffect) {
		world.playSoundAtEntity(entityplayer, entityplayer, soundEffect,
			0.5F + (ITEM_RAND.nextFloat() - ITEM_RAND.nextFloat()) * 0.1F,
			1.1F + (ITEM_RAND.nextFloat() - ITEM_RAND.nextFloat()) * 0.1F);
	}
}
