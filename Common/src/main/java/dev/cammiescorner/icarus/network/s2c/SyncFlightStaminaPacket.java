package dev.cammiescorner.icarus.network.s2c;

import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.icarus.Icarus;
import dev.cammiescorner.icarus.util.StaminaProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record SyncFlightStaminaPacket(float stamina) {
	public static final ResourceLocation ID = Icarus.id("sync_flight_stamina");
	public static final CustomPacketPayload.Type<CustomPacketPayload> TYPE = new CustomPacketPayload.Type<>(ID);
	public static final StreamCodec<FriendlyByteBuf, SyncFlightStaminaPacket> STREAM_CODEC = StreamCodec.ofMember(SyncFlightStaminaPacket::encode, SyncFlightStaminaPacket::decode);

	private void encode(FriendlyByteBuf buf) {
		buf.writeFloat(stamina());
	}

	private static SyncFlightStaminaPacket decode(FriendlyByteBuf buf) {
		float stamina = buf.readFloat();

		return new SyncFlightStaminaPacket(stamina);
	}

	public static void send(ServerPlayer player, float stamina) {
		Dispatcher.sendToClient(new SyncFlightStaminaPacket(stamina), player);
	}

	public static void handle(PacketContext<SyncFlightStaminaPacket> ctx) {
		ClientHandler.handle(ctx);
	}

	private static class ClientHandler {
		private static void handle(PacketContext<SyncFlightStaminaPacket> ctx) {
			Minecraft.getInstance().execute(() -> {
				var client = Minecraft.getInstance();

				if(client.player instanceof StaminaProvider provider)
					provider.icarus$setStamina(ctx.message().stamina());
			});
		}
	}
}
