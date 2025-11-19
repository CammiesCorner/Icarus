package dev.cammiescorner.icarus.neoforge.entrypoints;

import dev.cammiescorner.icarus.client.renderers.WingsLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class Client {

    @SubscribeEvent
    public static void onRegisterModelLayers(EntityRenderersEvent.AddLayers event) {
        for (var skin : event.getSkins()) {
            LivingEntityRenderer<Player, EntityModel<Player>> renderer = event.getSkin(skin);
            if (renderer != null) {
                renderer.addLayer(new WingsLayer<>(renderer, event.getEntityModels()));
            }
        }
    }
}
