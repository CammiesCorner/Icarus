package dev.cammiescorner.icarus.forge;

import dev.cammiescorner.icarus.Icarus;
import dev.cammiescorner.icarus.client.IcarusClient;
import dev.cammiescorner.icarus.util.IcarusHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.caelus.api.CaelusApi;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = Icarus.MODID)
public class EventHandler {

    public static final UUID WINGS_FLIGHT_MODIFIER_ID = UUID.fromString("4b6eac76-f013-4382-8b22-5d43bc37939c");
    public static final AttributeModifier WINGS_FLIGHT = new AttributeModifier(WINGS_FLIGHT_MODIFIER_ID, "icarus_wings", 1.0D, AttributeModifier.Operation.ADDITION);

    @SuppressWarnings("UnreachableCode")
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            var attributeInstance = event.player.getAttribute(CaelusApi.getInstance().getFlightAttribute());

            if(attributeInstance != null) {
                if (attributeInstance.hasModifier(WINGS_FLIGHT)) {
                    if(event.player.isFallFlying()) {
                        if (!IcarusHelper.onFallFlyingTick(event.player, IcarusHelper.getEquippedWings(event.player), true)) {
                            attributeInstance.removeModifier(WINGS_FLIGHT);
                        }
                    }
                    else {
                        if(!IcarusHelper.hasWings(event.player)) {
                            attributeInstance.removeModifier(WINGS_FLIGHT);
                        }
                    }
                }
                else {
                    if (IcarusHelper.hasWings(event.player)) {
                        attributeInstance.addTransientModifier(WINGS_FLIGHT);
                    }
                }
            }

            if (event.side.isServer()) {
                IcarusHelper.onPlayerTick(event.player);
            } else {
                IcarusClient.onPlayerTick(event.player);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        IcarusHelper.onServerPlayerJoin((ServerPlayer) event.getEntity());
    }

    @SubscribeEvent
    public static void onServerStart(ServerStartingEvent event) {
        Icarus.onServerStart(event.getServer());
    }
}
