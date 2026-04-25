package dev.cammiescorner.icarus.client.hud;

import dev.cammiescorner.icarus.Icarus;
import dev.cammiescorner.icarus.util.IcarusHelper;
import dev.cammiescorner.icarus.util.StaminaProvider;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class IcarusStaminaOverlay {
	private static final ResourceLocation OVERLAY_TEXTURE = Icarus.id("textures/hud/stamina.png");
	public static final ResourceLocation ID = Icarus.id("stamina");

	public static void render(GuiGraphics guiGraphics, DeltaTracker tickDelta, LocalPlayer player) {
		if(!player.isCreative() && player instanceof StaminaProvider provider && IcarusHelper.hasWings(player)) {
			var client = Minecraft.getInstance();
			int x = client.getWindow().getGuiScaledWidth() / 2 - 9;
			int y = client.getWindow().getGuiScaledHeight() - 41;

			if(player.experienceLevel != 0) {
				y -= 8;
			}

			var staminaRatio = provider.icarus$getStamina() / provider.icarus$getMaxStamina();
			var targetHeight = Mth.floor(9 * staminaRatio);

			guiGraphics.blit(OVERLAY_TEXTURE, x, y, 0, 0, 18, 11, 18, 22);
			guiGraphics.blit(OVERLAY_TEXTURE, x, y + 10 - targetHeight, 0, 21 - targetHeight, 18, targetHeight, 18, 22);
		}
	}
}
