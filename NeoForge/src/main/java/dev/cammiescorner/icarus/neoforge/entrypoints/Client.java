package dev.cammiescorner.icarus.neoforge.entrypoints;

import dev.cammiescorner.icarus.api.client.IcarusAPIClient;
import dev.cammiescorner.icarus.client.hud.IcarusStaminaOverlay;
import dev.cammiescorner.icarus.client.renderers.WingsLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(value = Dist.CLIENT)
public class Client {
    public Client() {
        IcarusAPIClient.addRenderPredicate((entity) -> {
            var inventory = CuriosApi.getCuriosInventory(entity);
            ItemStack stack = IcarusAPIClient.getWingsForRendering(entity);
            var curio = inventory.orElseThrow().findFirstCurio(compare -> compare.equals(stack));

            return curio.isEmpty() || curio.get().slotContext().visible();
        });
    }

    @SubscribeEvent
    public static void onRegisterModelLayers(EntityRenderersEvent.AddLayers event) {
        for (var skin : event.getSkins()) {
            LivingEntityRenderer<Player, EntityModel<Player>> renderer = event.getSkin(skin);
            if (renderer != null) {
                renderer.addLayer(new WingsLayer<>(renderer, event.getEntityModels()));
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.HOTBAR, IcarusStaminaOverlay.ID, (guiGraphics, deltaTracker) -> {
            if(Minecraft.getInstance().player instanceof LocalPlayer player)
                IcarusStaminaOverlay.render(guiGraphics, deltaTracker, player);
        });
    }
}
