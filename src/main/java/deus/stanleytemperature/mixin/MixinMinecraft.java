package deus.stanleytemperature.mixin;

import deus.stanleytemperature.interfaces.IMinecraft;
import gssl.Signal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFood;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft implements IMinecraft {

	@Shadow
	public EntityPlayerSP thePlayer;
	@Unique
	@Final
	private final Signal<ItemFood> onConsumeItem = new Signal<>();

	@Inject(method = "clickMouse(IZZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/controller/PlayerController;useItem(Lnet/minecraft/core/entity/player/EntityPlayer;Lnet/minecraft/core/world/World;Lnet/minecraft/core/item/ItemStack;)Z"), remap = false)
	private void customClickMouse(int clickType, boolean attack, boolean repeat, CallbackInfo ci) {
		if (thePlayer.getHeldItem().getItem()instanceof ItemFood itemFood) {
			onConsumeItem.emit(itemFood);
		}
	}

	@Override
	public Signal<ItemFood> stanley$getOnCunsumeItemSignal() {
		return onConsumeItem;
	}
}
