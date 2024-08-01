package deus.stanleylib.interfaces;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;

public interface IPlayerEntity {
	Block stanley_lib$getBlockUnderPlayer();

	boolean[] hasLeatherArmor(EntityPlayer player);
}
