package deus.stanleytemperature.interfaces;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;

public interface IPlayerEntity {
	Block stanley$getBlockUnderPlayer();
	Item stanley$getItemInHand();

	boolean[] stanley$hasLeatherArmor(Player player);
}
