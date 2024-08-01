package deus.stanleylib.mixin;

import deus.stanleylib.StanleyLib;
import deus.stanleylib.config.IStanleySettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.option.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin {
	@Shadow
	public GameSettings gameSettings;

	@Shadow
	public void displayGuiScreen(GuiScreen guiscreen) {

	}

	@Inject(method = "startGame", at = @At(value = "TAIL"))
	private void startOfGameInit(CallbackInfo ci)
	{
		StanleyLib.options = (IStanleySettings) gameSettings;

		if (!StanleyLib.options.initialRunSetupFinished().value)
		{
			//displayGuiScreen(new GuiFirstTimeSetupScreen());
			//System.out.println("First time setup not finished, opening setup screen.");
		}
	}
}
