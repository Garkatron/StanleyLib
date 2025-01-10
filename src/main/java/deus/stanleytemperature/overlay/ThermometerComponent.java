package deus.stanleytemperature.overlay;

import deus.stanleytemperature.enums.PlayerTemperatureState;
import deus.stanleytemperature.interfaces.IStanleyPlayerEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.gamemode.Gamemode;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static deus.stanleytemperature.StanleyTemperature.MOD_CONFIG;

public class ThermometerComponent extends MovableHudComponent {

	private int width = getTemperatureBarW();
	private int height = getTemperatureBarH();
	private float currentTemperature = 0.0f;

	private static final int COMPACT_HEIGHT = 48;
	private static final int REGULAR_HEIGHT = 182;
	private static final int COMPACT_WIDTH = 48;
	private static final int REGULAR_WIDTH = 182;
	private static final int SPECIAL_VALUE = 5; // Valor especial requerido

	public Color freezing = new Color(0, 0, 255, 255); // Azul
	public Color cold = new Color(152, 152, 232, 255); // Gris claro
	public Color normal = new Color(122, 122, 122, 255); // Gris neutro
	public Color hot = new Color(255, 165, 0, 255); // Naranja
	public Color overheating = new Color(255, 0, 0, 255); // Rojo
	public Color flashColor = new Color(255, 255, 255, 255); // Blanco

	private float flashTime = 0.0f;
	private long previousTime = 0;

	private final String texture = getTexture();
	// assets/stanleylib/textures/gui/thermometer.png
	// /assets/minecraft/textures/gui/icons.png

	public ThermometerComponent(String key, int xSize, int ySize, Layout layout) {
		super(key, xSize, ySize, layout);
	}

	@Override
	public boolean isVisible(Minecraft mc)
	{
		EntityPlayer player = mc.thePlayer;
		if (player == null)
			return true;

		return (!player.getGamemode().isPlayerInvulnerable() && mc.gameSettings.immersiveMode.drawHotbar() && player.getGamemode()
			.equals(Gamemode.survival));
	}

	private void renderSprite(Minecraft mc, Gui gui, int x, int y, double targetTemperature, PlayerTemperatureState state) {
		float delta = (System.currentTimeMillis() - previousTime) / 1000f;
		flashTime += delta * 16f;

		// Ajuste suave de la temperatura actual hacia la temperatura objetivo
		float smoothingFactor = 0.1f; // Puedes ajustar este valor para un suavizado más rápido o más lento
		currentTemperature += (targetTemperature - currentTemperature) * smoothingFactor;

		GL11.glDisable(GL11.GL_BLEND);

		switch (state) {
			case OVERHEATING:
				setColor(lerpColor(overheating, flashColor,
					Math.min(
						Math.max(
							((float)Math.sin(flashTime) + 1) / 2.0f,
							0.0f),
						1.0f)));
				break;
			case HOT:
				setColor(hot);
				break;
			case NORMAL:
				setColor(normal);
				break;
			case COLD:
				setColor(cold);
				break;
			case FREEZING:
				setColor(lerpColor(freezing, flashColor,
					Math.min(
						Math.max(
							((float)Math.sin(flashTime) + 1) / 2.0f,
							0.0f),
						1.0f)));
				break;
		}

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(texture));

		int textureWidth = width;
		int textureHeight = height;

		// Draw the thermometer texture background
		gui.drawTexturedModalRect(x, y, 0, 0, textureWidth, textureHeight);

		// Calculate the height or width to draw based on orientation
		if (MOD_CONFIG.getConfig().getString("Gui.temperatureBar").equals("vertical")) {
			int filledHeight = (int) (currentTemperature * height);
			// Ensure filled height doesn't exceed the component height
			filledHeight = Math.min(filledHeight, height);
			gui.drawTexturedModalRect(x, y + height - filledHeight, width, height - filledHeight, width, filledHeight);
		} else {
			int filledWidth = (int) (currentTemperature * width);
			// Ensure filled width doesn't exceed the component width
			filledWidth = Math.min(filledWidth, width);
			gui.drawTexturedModalRect(x, y, 0, height, filledWidth, height);
		}

		previousTime = System.currentTimeMillis(); // Actualizar el tiempo anterior
	}

	@Override
	public void render(Minecraft mc, GuiIngame gui, int xSizeScreen, int ySizeScreen, float partialTick) {
		int x = this.getLayout().getComponentX(mc, this, xSizeScreen);
		int y = this.getLayout().getComponentY(mc, this, ySizeScreen);

		IStanleyPlayerEntity customPlayer = (IStanleyPlayerEntity) mc.thePlayer;

		double targetTemperature = customPlayer.stanley$getPlayerTemperature() / 100.0;

		if (targetTemperature>=1) {
			targetTemperature = 1;
		} else if (targetTemperature<=0) {
			targetTemperature=0;
		}

		renderSprite(mc, gui, x, y, targetTemperature, customPlayer.stanley$getState());
	}


	@Override
	public void renderPreview(Minecraft mc, Gui gui, Layout layout, int xSizeScreen, int ySizeScreen) {
		{
			int x = layout.getComponentX(mc, this, xSizeScreen);
			int y = layout.getComponentY(mc, this, ySizeScreen);

			renderSprite(mc, gui, x, y, 0.5f, PlayerTemperatureState.NORMAL);
		}
	}
	private void setColor(Color color)
	{
		GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
	}

	public Color lerpColor(Color a, Color b, float t)
	{
		return new Color(
			(int) (a.getRed() + (b.getRed() - a.getRed()) * t),
			(int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t),
			(int) (a.getBlue() + (b.getBlue() - a.getBlue()) * t),
			(int) (a.getAlpha() + (b.getAlpha() - a.getAlpha()) * t));
	}

	public double lerp(double a, double b, double t){
		return a + (b - a) * t;
	}

	public static int getTemperatureBarH() {
		String orientation = MOD_CONFIG.getConfig().getString("Gui.temperatureBar");
		boolean isCompact = MOD_CONFIG.getConfig().getBoolean("Gui.temperatureBarCompact");

		if ("vertical".equals(orientation)) {
			return isCompact ? COMPACT_HEIGHT : REGULAR_HEIGHT;
		} else {
			return SPECIAL_VALUE;
		}
	}

	public static int getTemperatureBarW() {
		String orientation = MOD_CONFIG.getConfig().getString("Gui.temperatureBar");
		boolean isCompact = MOD_CONFIG.getConfig().getBoolean("Gui.temperatureBarCompact");

		if ("horizontal".equals(orientation)) {
			return isCompact ? COMPACT_WIDTH : REGULAR_WIDTH;
		} else {
			return SPECIAL_VALUE;
		}
	}

	public static String getTexture() {
		String orientation = MOD_CONFIG.getConfig().getString("Gui.temperatureBar");
		boolean isCompact = MOD_CONFIG.getConfig().getBoolean("Gui.temperatureBarCompact");
		if ("horizontal".equals(orientation)) {
			return isCompact ? "assets/stanleytemperature/textures/gui/c_thermometer.png" : "assets/stanleytemperature/textures/gui/thermometer.png";
		} else {

			if ("vertical".equals(orientation)) {
				return isCompact ? "assets/stanleytemperature/textures/gui/vc_thermometer.png" : "assets/stanleytemperature/textures/gui/v_thermometer.png";
			} else {
				return "";
			}
		}
	}
}
