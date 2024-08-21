package deus.stanleylib.overlay;

import net.minecraft.client.gui.hud.*; // Asegúrate de que las importaciones sean correctas

public class HudManager {
	private static HudComponent thermometer;
	private String initialSettingsString;

	public void onInitialize() {
		// Registra el componente Thermometer y establece su disposición en la esquina superior derecha del hotbar
		thermometer = HudComponents.register(new ThermometerComponent(
			"thermometer",
			16,
			16,
			new SnapLayout(HudComponents.HOTBAR, ComponentAnchor.TOP_RIGHT, ComponentAnchor.BOTTOM_RIGHT)
		));

		// Configura los layouts de HEALTH_BAR y ARMOR_BAR para que se ajusten al componente Thermometer
		updateComponentLayouts();

		// Guarda la configuración inicial del HUD como una cadena de texto
		initialSettingsString = HudComponents.INSTANCE.toSettingsString();
	}

	private void updateComponentLayouts() {
		// Ajusta el layout de HEALTH_BAR si es un SnapLayout
		Layout healthBarLayout = HudComponents.HEALTH_BAR.getLayout();
		if (healthBarLayout instanceof SnapLayout) {
			((SnapLayout) healthBarLayout).setParent(thermometer);
		}

		// Ajusta el layout de ARMOR_BAR si es un SnapLayout
		Layout armorBarLayout = HudComponents.ARMOR_BAR.getLayout();
		if (armorBarLayout instanceof SnapLayout) {
			((SnapLayout) armorBarLayout).setParent(thermometer);
		}
	}
}
