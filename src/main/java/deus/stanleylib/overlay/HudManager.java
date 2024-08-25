package deus.stanleylib.overlay;

import deus.stanleylib.StanleyLib;
import net.minecraft.client.gui.hud.*; // Asegúrate de que las importaciones sean correctas

import static deus.stanleylib.StanleyLib.MOD_CONFIG;

public class HudManager {
	private static final HudComponent thermometer = HudComponents.register(new ThermometerComponent(
		"thermometer",
		ThermometerComponent.getTemperatureBarW(),
		ThermometerComponent.getTemperatureBarH()
		,
		new SnapLayout(HudComponents.HOTBAR, ComponentAnchor.TOP_LEFT, ComponentAnchor.BOTTOM_LEFT)));

	public static void init() {

		StanleyLib.LOGGER.debug("Registering HUD components");
	}



}

