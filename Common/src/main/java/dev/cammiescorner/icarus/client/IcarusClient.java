package dev.cammiescorner.icarus.client;

import com.google.common.base.MoreObjects;
import dev.cammiescorner.icarus.init.IcarusItemTags;
import dev.cammiescorner.icarus.network.c2s.ApplyBoostPacket;
import dev.cammiescorner.icarus.util.IcarusHelper;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class IcarusClient {

    private static final List<Predicate<LivingEntity>> renderPredicates = new ArrayList<>();

    public static void onPlayerTick(Player player) {
        if (player.isFallFlying() && IcarusHelper.hasWings(player) && player.zza > 0) {
            var cfg = IcarusHelper.getConfigValues(player);
            var rotation = player.getLookAngle();
            var velocity = player.getDeltaMovement();

            float speed = (cfg.wingsSpeed() * (player.getXRot() < -75 && player.getXRot() > -105 ? 2.75F : 1F));

            if (cfg.armorSlows()) {
                ItemStack wings = IcarusHelper.getEquippedWings.apply(player);
                if (wings != null && !wings.isEmpty() && !wings.is(IcarusItemTags.BYPASSES_ARMOR_SLOWDOWN)) {
                    speed = speed / Math.max(1F, (player.getArmorValue() / 30F) * cfg.maxSlowedMultiplier());
                }
            }

            player.setDeltaMovement(velocity.add(rotation.x * speed + (rotation.x * 1.5D - velocity.x) * speed,
                    rotation.y * speed + (rotation.y * 1.5D - velocity.y) * speed,
                    rotation.z * speed + (rotation.z * 1.5D - velocity.z) * speed));

            ApplyBoostPacket.sendToServer();
        }
    }

    @ApiStatus.Internal
    public static ItemStack getWingsForRendering(LivingEntity entity) {
        return MoreObjects.firstNonNull(IcarusHelper.getEquippedWings.apply(entity), ItemStack.EMPTY);
    }

    @ApiStatus.Internal
    public static void addRenderPredicate(Predicate<LivingEntity> predicate) {
        renderPredicates.add(predicate);
    }

    public static boolean shouldRenderWings(LivingEntity entity) {
        if (!renderPredicates.isEmpty()) {
            for (Predicate<LivingEntity> predicate : renderPredicates) {
                if (!predicate.test(entity)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void sendActionbarMessage(Player player, Component message) {
        if(player instanceof LocalPlayer localPlayer) {
            localPlayer.displayClientMessage(message, true);
        }
    }
}
