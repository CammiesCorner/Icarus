package dev.cammiescorner.icarus.fabric.entrypoints;

import dev.cammiescorner.icarus.client.hud.IcarusStaminaOverlay;
import dev.cammiescorner.icarus.client.renderers.WingsLayer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityType;

public class Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityType == EntityType.PLAYER) {
                registrationHelper.register(new WingsLayer<>(entityRenderer, context.getModelSet()));
            }
        });

        HudRenderCallback.EVENT.register((gui, tickDelta) -> {
            var client = Minecraft.getInstance();
            if(client.player != null && !client.player.isSpectator() && !client.options.hideGui)
                IcarusStaminaOverlay.render(gui, tickDelta, client.player);
        });
    }
}
