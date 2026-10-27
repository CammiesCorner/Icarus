package dev.cammiescorner.icarus.neoforge.entrypoints;

import dev.cammiescorner.icarus.Icarus;
import dev.cammiescorner.icarus.client.IcarusClient;
import dev.cammiescorner.icarus.client.hud.IcarusStaminaOverlay;
import dev.cammiescorner.icarus.client.renderers.WingsLayer;
import dev.cammiescorner.icarus.item.WingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import top.theillusivec4.curios.api.CuriosApi;

@Mod(value = Icarus.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT)
public class Client {

    private static final String RENDERING_CACHE_KEY = Icarus.id("rendering").toString();

    public Client() {
        IcarusClient.getVisualEquippedWings = entity -> {
            var component = CuriosApi.getCuriosInventory(entity);
            if (component.isPresent()) {
                var slot = component.orElseThrow().findFirstCurio(stack -> stack.getItem() instanceof WingItem, RENDERING_CACHE_KEY);
                if (slot.isPresent()) {
                    var slotContents = slot.orElseThrow();
                    if (slotContents.slotContext().visible()) {
                        return slotContents.stack();
                    }
                }
            }

            return ItemStack.EMPTY;
        };
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
            if (Minecraft.getInstance().player instanceof LocalPlayer player)
                IcarusStaminaOverlay.render(guiGraphics, deltaTracker, player);
        });
    }
}
