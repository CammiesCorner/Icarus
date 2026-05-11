package dev.cammiescorner.icarus.mixin;

import dev.cammiescorner.icarus.init.IcarusStatusEffects;
import dev.cammiescorner.icarus.util.StaminaProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "onEffectRemoved", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getPassengers()Ljava/util/List;"))
	private void onEffectRemoved(MobEffectInstance effectInstance, CallbackInfo info) {
		if(effectInstance.is(IcarusStatusEffects.ENERGIZED.holder()) && this instanceof StaminaProvider provider) {
			provider.icarus$setStamina(provider.icarus$getStamina());
		}
	}
}
