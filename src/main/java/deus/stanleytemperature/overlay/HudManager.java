package deus.stanleytemperature.overlay;

import deus.stanleytemperature.StanleyTemperature;
import net.minecraft.client.gui.hud.*; // Asegúrate de que las importaciones sean correctas


public class HudManager {
	private static final HudComponent thermometer = HudComponents.register(new ThermometerComponent(
		"thermometer",
		ThermometerComponent.getTemperatureBarW(),
		ThermometerComponent.getTemperatureBarH()
		,
		new SnapLayout(HudComponents.HOTBAR, ComponentAnchor.TOP_LEFT, ComponentAnchor.BOTTOM_LEFT)));

	public static void init() {

		StanleyTemperature.LOGGER.debug("Registering HUD components");
	}



}

