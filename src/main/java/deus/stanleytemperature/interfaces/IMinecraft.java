package deus.stanleytemperature.interfaces;

import gssl.Signal;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFood;

public interface IMinecraft {

	Signal<ItemFood> stanley$getOnCunsumeItemSignal();

}
