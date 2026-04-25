package dev.cammiescorner.icarus.client.hud;

import com.mojang.math.Axis;
import dev.cammiescorner.icarus.Icarus;
import dev.cammiescorner.icarus.util.IcarusHelper;
import dev.cammiescorner.icarus.util.StaminaProvider;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;

public class IcarusStaminaOverlay {
	private static final ResourceLocation OVERLAY_TEXTURE = Icarus.id("textures/hud/stamina.png");
	public static final ResourceLocation ID = Icarus.id("stamina");

	public static void render(GuiGraphics guiGraphics, DeltaTracker tickDelta, LocalPlayer player) {
		if(!player.isCreative() && player instanceof StaminaProvider provider && IcarusHelper.hasWings(player)) {
			var client = Minecraft.getInstance();
			int x = client.getWindow().getGuiScaledWidth() / 2 + 16;
			int y = client.getWindow().getGuiScaledHeight() - 32;
			var staminaRatio = provider.icarus$getStamina() / provider.icarus$getMaxStamina();
			var poseStack = guiGraphics.pose();

			poseStack.pushPose();
			poseStack.translate(x, y, 0);
			poseStack.mulPose(Axis.ZP.rotation((float) Math.toRadians(180)));
			guiGraphics.blit(OVERLAY_TEXTURE, 0, 0, 0, 0, 32, 11);
			guiGraphics.blit(OVERLAY_TEXTURE, 0, 1, 0, 11, 32, Math.round(9 * staminaRatio));
			poseStack.popPose();
		}
	}
}
