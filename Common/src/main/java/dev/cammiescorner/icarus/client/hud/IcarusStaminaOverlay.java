package dev.cammiescorner.icarus.client.hud;

import dev.cammiescorner.icarus.Icarus;
import dev.cammiescorner.icarus.util.StaminaProvider;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;

public class IcarusStaminaOverlay {
	private static final ResourceLocation OVERLAY_TEXTURE = Icarus.id("textures/hud/stamina.png");

	// TODO move to correct location & make it drain from the bottom
	public static void render(GuiGraphics guiGraphics, DeltaTracker tickDelta, LocalPlayer player) {
		int x = 0;
		int y = 0;

		guiGraphics.blit(OVERLAY_TEXTURE, x, y, 0, 0, 32, 16);

		if(player instanceof StaminaProvider provider) {
			var staminaRatio = provider.icarus$getStamina() / provider.icarus$getMaxStamina();

			guiGraphics.blit(OVERLAY_TEXTURE, x, y, 0, 16, 32, Math.round(16 * staminaRatio));
		}
	}
}
