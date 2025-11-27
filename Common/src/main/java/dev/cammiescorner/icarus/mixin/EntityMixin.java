package dev.cammiescorner.icarus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.cammiescorner.icarus.IcarusConfig;
import dev.cammiescorner.icarus.client.ClientPlayerFallbackValues;
import dev.cammiescorner.icarus.util.IcarusHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("UnreachableCode")
@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract float getXRot();

    @Shadow public float xRotO;

    @ModifyReturnValue(method = "getPickRadius", at = @At("RETURN"))
    private float icarus$targetRadius(float original) {
        if(((Object) this) instanceof Player player && player.isFallFlying())
            return Math.max(IcarusConfig.flyingTargetRadius, original * (1 + IcarusConfig.flyingTargetRadius));

        return original;
    }

    @ModifyExpressionValue(method = "turn", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F", ordinal = 0))
    private float icarus$updateLookDirection(float original) {
        if(((Object) this) instanceof LivingEntity living && living.isFallFlying() && IcarusHelper.hasWings(living) && IcarusHelper.getConfigValues(living).canLoopDeLoop()) {
            if(!living.level().isClientSide() || ClientPlayerFallbackValues.canClientLoopDeLoop(living)) {
                return Mth.wrapDegrees(this.getXRot());
            }
        }
        return original;
    }

    @ModifyExpressionValue(method = "turn", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F", ordinal = 1))
    private float icarus$updateLookDirection0(float original) {
        if (((Object) this) instanceof LivingEntity living && living.isFallFlying() && IcarusHelper.hasWings(living) && IcarusHelper.getConfigValues(living).canLoopDeLoop()) {
            if(!living.level().isClientSide() || ClientPlayerFallbackValues.canClientLoopDeLoop(living)) {
                return Mth.wrapDegrees(this.xRotO);
            }
        }
        return original;
    }
}
