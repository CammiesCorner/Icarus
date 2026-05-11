package dev.cammiescorner.icarus.effect;

import dev.cammiescorner.icarus.util.StaminaProvider;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class SecondWindStatusEffect extends InstantenousMobEffect {
	public SecondWindStatusEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
		if(livingEntity instanceof StaminaProvider provider) {
			provider.icarus$modifyStamina(provider.icarus$getMaxStamina() * 0.333f * (amplifier + 1));
		}

		return true;
	}

	@Override
	public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity livingEntity, int amplifier, double health) {
		if(livingEntity instanceof StaminaProvider provider) {
			provider.icarus$modifyStamina(provider.icarus$getMaxStamina() * 0.333f * (amplifier + 1));
		}
	}
}
