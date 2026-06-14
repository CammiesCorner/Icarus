package dev.cammiescorner.icarus.network.c2s;

import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.icarus.Icarus;
import dev.cammiescorner.icarus.init.IcarusItemTags;
import dev.cammiescorner.icarus.util.IcarusHelper;
import dev.cammiescorner.icarus.util.StaminaProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ApplyBoostPacket() {
    private static final ApplyBoostPacket INSTANCE = new ApplyBoostPacket();
    public static final ResourceLocation ID = Icarus.id("apply_boost");
    public static final CustomPacketPayload.Type<CustomPacketPayload> TYPE = new CustomPacketPayload.Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, ApplyBoostPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public static void sendToServer() {
        Dispatcher.sendToServer(INSTANCE);
    }

    public static void handle(PacketContext<ApplyBoostPacket> ctx) {
        var player = ctx.sender();

        var wings = IcarusHelper.getEquippedWings(player);
        if((wings == null || !wings.is(IcarusItemTags.FREE_FLIGHT)) && !player.isCreative()) {
            var cfg = IcarusHelper.getConfigValues(player);

            if(cfg.useStaminaForFlight() && player instanceof StaminaProvider provider)
                provider.icarus$addStamina(-cfg.staminaAmount());
            else
                player.getFoodData().addExhaustion(cfg.exhaustionAmount());
        }
    }
}
