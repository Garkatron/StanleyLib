package deus.stanleytemperature.overlay;

import deus.stanleytemperature.StanleyTemperature;
import net.minecraft.client.gui.hud.*; // Asegúrate de que las importaciones sean correctas
import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.HudComponent;
import net.minecraft.client.gui.hud.component.HudComponents;
import net.minecraft.client.gui.hud.component.layout.LayoutSnap;


public class HudManager {
	private static final HudComponent thermometer = HudComponents.register(new ThermometerComponent(
		"thermometer",
		ThermometerComponent.getTemperatureBarW(),
		ThermometerComponent.getTemperatureBarH()
		,
		new LayoutSnap(HudComponents.HOTBAR, ComponentAnchor.TOP_LEFT, ComponentAnchor.BOTTOM_LEFT)));

	public static void init() {

		StanleyTemperature.LOGGER.debug("Registering HUD components");
	}



}

