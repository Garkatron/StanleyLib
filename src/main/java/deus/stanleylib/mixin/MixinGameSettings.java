package deus.stanleylib.mixin;

import deus.stanleylib.config.IStanleySettings;
import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.FloatOption;
import net.minecraft.client.option.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = GameSettings.class, remap = false)
public class MixinGameSettings implements IStanleySettings {

	private final GameSettings mixinInst = (GameSettings) ((Object) this);

	@Unique
	public BooleanOption initialRunSetupFinished =
		new BooleanOption(mixinInst, "stanleylib.options.initialRunSetupFinished", false);

	@Override
	public BooleanOption initialRunSetupFinished() {
		return initialRunSetupFinished;
	}

}
