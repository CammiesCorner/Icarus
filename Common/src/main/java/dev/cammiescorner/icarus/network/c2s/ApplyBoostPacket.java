package dev.cammiescorner.icarus.network.c2s;

import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.icarus.Icarus;
import dev.cammiescorner.icarus.init.IcarusItemTags;
import dev.cammiescorner.icarus.util.IcarusHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ApplyBoostPacket() {
    private static final ApplyBoostPacket INSTANCE = new ApplyBoostPacket();
    public static final ResourceLocation ID = Icarus.id("apply_boost");

    public static void sendToServer() {
        Dispatcher.sendToServer(ApplyBoostPacket.INSTANCE);
    }

    public static void handle(PacketContext<ApplyBoostPacket> ctx) {
        var player = ctx.sender();

        var wings = IcarusHelper.getEquippedWings(player);
        if((wings == null || !wings.is(IcarusItemTags.FREE_FLIGHT))) {
            var cfg = IcarusHelper.getConfigValues(player);
            var exhaustion = cfg.exhaustionAmount();

            if(exhaustion > 0) {
                player.causeFoodExhaustion(exhaustion);
            }
        }
    }

    public void encode(FriendlyByteBuf friendlyByteBuf) {
        // NO-OP
    }

    public static ApplyBoostPacket decode(FriendlyByteBuf friendlyByteBuf) {
        return INSTANCE;
    }
}
