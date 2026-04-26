package dev.cammiescorner.icarus.client;

import com.google.auto.service.AutoService;
import com.google.common.base.MoreObjects;
import dev.cammiescorner.icarus.client.models.*;
import dev.cammiescorner.icarus.init.IcarusItemTags;
import dev.cammiescorner.icarus.util.IcarusHelper;
import dev.cammiescorner.icarus.util.StaminaProvider;
import dev.upcraft.sparkweave.api.client.event.RegisterLayerDefinitionsEvent;
import dev.upcraft.sparkweave.api.entrypoint.ClientEntryPoint;
import dev.upcraft.sparkweave.api.event.EntityTickEvents;
import dev.upcraft.sparkweave.api.platform.ModContainer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@AutoService(ClientEntryPoint.class)
public class IcarusClient implements ClientEntryPoint {

    private static final List<Predicate<LivingEntity>> renderPredicates = new ArrayList<>();

    @Override
    public void onInitializeClient(ModContainer mod) {
        EntityTickEvents.startTick(AbstractClientPlayer.class).register(IcarusClient::onPlayerTick);
        RegisterLayerDefinitionsEvent.EVENT.register(event -> {
            event.registerModelLayers(IcarusModels.FEATHERED, FeatheredWingsModel::getLayerDefinition);
            event.registerModelLayers(IcarusModels.LEATHER, LeatherWingsModel::getLayerDefinition);
            event.registerModelLayers(IcarusModels.LIGHT, LightWingsModel::getLayerDefinition);
            event.registerModelLayers(IcarusModels.FLANDRE, FlandresWingsModel::getLayerDefinition);
            event.registerModelLayers(IcarusModels.DISCORD, DiscordsWingsModel::getLayerDefinition);
            event.registerModelLayers(IcarusModels.ZANZA, ZanzasWingsModel::getLayerDefinition);
        });
    }

    public static boolean onPlayerTick(AbstractClientPlayer player, Level level) {
        var cfg = IcarusHelper.getConfigValues(player);
        var canFly = cfg.useStaminaForFlight() ? player instanceof StaminaProvider provider &&  provider.icarus$getStamina() > 0 : player.getFoodData().getFoodLevel() >= cfg.requiredFoodAmount();

        if (player.isFallFlying() && IcarusHelper.hasWings(player) && player.zza > 0 && canFly) {
            var rotation = player.getLookAngle();
            var velocity = player.getDeltaMovement();

            float upwardsAngleOfAttack = Mth.degreesDifferenceAbs(player.getXRot(), -90.0F);
            float speed = cfg.wingsSpeed() * (upwardsAngleOfAttack <= 15.0F ? 2.75F :1.0F);

            if (cfg.armorSlows()) {
                ItemStack wings = IcarusHelper.getEquippedWings.apply(player);
                if (wings != null && !wings.isEmpty() && !wings.is(IcarusItemTags.BYPASSES_ARMOR_SLOWDOWN)) {
                    speed = speed / Math.max(1F, player.getArmorValue() / 30F * cfg.maxSlowedMultiplier());
                }
            }

            player.setDeltaMovement(velocity.add(rotation.x * speed + (rotation.x * 1.5D - velocity.x) * speed, rotation.y * speed + (rotation.y * 1.5D - velocity.y) * speed, rotation.z * speed + (rotation.z * 1.5D - velocity.z) * speed));
        }

        return false;
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
        if (player instanceof LocalPlayer localPlayer) {
            localPlayer.displayClientMessage(message, true);
        }
    }
}
