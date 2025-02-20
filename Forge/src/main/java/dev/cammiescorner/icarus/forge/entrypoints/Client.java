package dev.cammiescorner.icarus.forge.entrypoints;

import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import dev.cammiescorner.icarus.Icarus;
import dev.cammiescorner.icarus.IcarusConfig;
import dev.cammiescorner.icarus.client.IcarusModels;
import dev.cammiescorner.icarus.client.models.*;
import dev.cammiescorner.icarus.client.renderers.WingsLayer;
import dev.cammiescorner.icarus.item.WingItem;
import dev.cammiescorner.icarus.util.ColorHelper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Icarus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Client {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> new ConfigScreen(parent, null, Icarus.CONFIGURATOR.getConfig(IcarusConfig.class)))));
    }

    @SubscribeEvent
    public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Item event) {
        ForgeRegistries.ITEMS.getEntries().forEach(entry -> {
            var item = entry.getValue();
            var key = entry.getKey();
            if(key.location().getNamespace().equals(Icarus.MODID) && item instanceof WingItem) {
                event.register((itemStack, tintIndex) -> tintIndex == 0 ? ColorHelper.dyeToDecimal(((WingItem) itemStack.getItem()).getPrimaryColor(itemStack)) : ColorHelper.dyeToDecimal(((WingItem) itemStack.getItem()).getSecondaryColor(itemStack)), item);
            }
        });
    }

    @SubscribeEvent
    public static void onRegisterModelLayers(EntityRenderersEvent.AddLayers event) {
        for (String skin : event.getSkins()) {
            try {
                LivingEntityRenderer<Player, EntityModel<Player>> renderer = event.getSkin(skin);
                if (renderer != null) {
                    renderer.addLayer(new WingsLayer<>(renderer, event.getEntityModels()));
                }
            }
            catch (Exception ignore) {
                // need this for LexForge
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(IcarusModels.FEATHERED, FeatheredWingsModel::getLayerDefinition);
        event.registerLayerDefinition(IcarusModels.LEATHER, LeatherWingsModel::getLayerDefinition);
        event.registerLayerDefinition(IcarusModels.LIGHT, LightWingsModel::getLayerDefinition);
        event.registerLayerDefinition(IcarusModels.FLANDRE, FlandresWingsModel::getLayerDefinition);
        event.registerLayerDefinition(IcarusModels.DISCORD, DiscordsWingsModel::getLayerDefinition);
        event.registerLayerDefinition(IcarusModels.ZANZA, ZanzasWingsModel::getLayerDefinition);
    }
}
