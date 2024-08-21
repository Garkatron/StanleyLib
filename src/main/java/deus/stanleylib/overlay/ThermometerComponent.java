package deus.stanleylib.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import org.lwjgl.opengl.GL11;

public class ThermometerComponent extends MovableHudComponent {

	private final String texture = "assets/stanleylib/textures/gui/thermometer.png"; // Ruta de la textura con el prefijo /
	// assets/stanleylib/textures/gui/thermometer.png
	// /assets/minecraft/textures/gui/icons.png

	public ThermometerComponent(String key, int xSize, int ySize, Layout layout) {
		super(key, xSize, ySize, layout);
	}

	@Override
	public boolean isVisible(Minecraft minecraft) {
		return true; // Cambiado para que siempre sea visible
	}

	private void renderSprite(Minecraft mc, Gui gui, int x, int y) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // Color blanco (sin modificación)
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(texture)); // Carga la texturaç
		GL11.glEnable(GL11.GL_BLEND); // Asegúrate de que el blending esté habilitado
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); // Configura el modo de blending

		gui.drawTexturedModalRect(x, y, 0, 0, 16, 16); // Dibuja el sprite de 16x16 desde la posición (0, 0) en la textura

		GL11.glDisable(GL11.GL_BLEND); // Deshabilita el blending después de dibujar
	}

	@Override
	public void render(Minecraft mc, GuiIngame gui, int xSizeScreen, int ySizeScreen, float partialTick) {
		int x = this.getLayout().getComponentX(mc, this, xSizeScreen);
		int y = this.getLayout().getComponentY(mc, this, ySizeScreen);
		renderSprite(mc, gui, x, y); // Llama al método para renderizar el sprite
	}

	@Override
	public void renderPreview(Minecraft mc, Gui gui, Layout layout, int xSizeScreen, int ySizeScreen) {
		int x = layout.getComponentX(mc, this, xSizeScreen);
		int y = layout.getComponentY(mc, this, ySizeScreen);
		renderSprite(mc, gui, x, y); // Llama al método para renderizar el sprite en la vista previa
	}
}
