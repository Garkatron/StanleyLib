package deus.stanleytemperature.interfaces;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;

public interface IPlayerEntity {
	Block stanley_lib$getBlockUnderPlayer();
	Item stanley_lib$getItemInHand();

	boolean[] hasLeatherArmor(EntityPlayer player);
}
