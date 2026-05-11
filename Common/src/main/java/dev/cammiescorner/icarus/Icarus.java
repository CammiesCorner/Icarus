package dev.cammiescorner.icarus;

import com.google.auto.service.AutoService;
import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import commonnetwork.api.Network;
import dev.cammiescorner.icarus.init.IcarusAttributes;
import dev.cammiescorner.icarus.init.IcarusItems;
import dev.cammiescorner.icarus.init.IcarusPotions;
import dev.cammiescorner.icarus.init.IcarusStatusEffects;
import dev.cammiescorner.icarus.network.c2s.ApplyBoostPacket;
import dev.cammiescorner.icarus.network.s2c.SyncConfigValuesPacket;
import dev.cammiescorner.icarus.network.s2c.SyncFlightStaminaPacket;
import dev.cammiescorner.icarus.util.IcarusHelper;
import dev.cammiescorner.icarus.util.ServerPlayerFallbackValues;
import dev.upcraft.sparkweave.api.entrypoint.MainEntryPoint;
import dev.upcraft.sparkweave.api.event.EntityTickEvents;
import dev.upcraft.sparkweave.api.event.LifeCycleEvents;
import dev.upcraft.sparkweave.api.platform.ModContainer;
import dev.upcraft.sparkweave.api.platform.services.RegistryService;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

@AutoService(MainEntryPoint.class)
public class Icarus implements MainEntryPoint {

    public static final String MODID = "icarus";
    private static final Configurator CONFIGURATOR = new Configurator(MODID);

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    @Override
    public void onInitialize(ModContainer mod) {
        CONFIGURATOR.register(IcarusConfig.class);

        Network.registerPacket(SyncConfigValuesPacket.TYPE, SyncConfigValuesPacket.class, SyncConfigValuesPacket.STREAM_CODEC, SyncConfigValuesPacket::handle);
        Network.registerPacket(SyncFlightStaminaPacket.TYPE, SyncFlightStaminaPacket.class, SyncFlightStaminaPacket.STREAM_CODEC, SyncFlightStaminaPacket::handle);
        Network.registerPacket(ApplyBoostPacket.TYPE, ApplyBoostPacket.class, ApplyBoostPacket.STREAM_CODEC, ApplyBoostPacket::handle);

        LifeCycleEvents.SERVER_STARTING.register(server -> IcarusHelper.fallbackValues = new ServerPlayerFallbackValues());
        EntityTickEvents.startTick(ServerPlayer.class).register(IcarusHelper::onPlayerTick);

        IcarusAttributes.registerAll();
        var registryService = RegistryService.get();
        IcarusItems.ITEMS.accept(registryService);
        IcarusItems.CREATIVE_TABS.accept(registryService);
        IcarusStatusEffects.STATUS_EFFECTS.accept(registryService);
        IcarusPotions.POTIONS.accept(registryService);
    }
}
