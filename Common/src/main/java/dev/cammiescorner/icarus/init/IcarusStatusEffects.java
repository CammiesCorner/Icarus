package dev.cammiescorner.icarus.init;

import dev.cammiescorner.icarus.Icarus;
import dev.cammiescorner.icarus.effect.EnergizedStatusEffect;
import dev.cammiescorner.icarus.effect.FlightlessStatusEffect;
import dev.cammiescorner.icarus.effect.SecondWindStatusEffect;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class IcarusStatusEffects {
    public static final RegistryHandler<MobEffect> STATUS_EFFECTS = RegistryHandler.create(Registries.MOB_EFFECT, Icarus.MODID);

    public static final RegistrySupplier<MobEffect> FLIGHTLESS = STATUS_EFFECTS.register("flightless", () -> new FlightlessStatusEffect(MobEffectCategory.NEUTRAL, 0xb6ccf0));
    public static final RegistrySupplier<MobEffect> ENERGIZED = STATUS_EFFECTS.register("energized", () -> new EnergizedStatusEffect(MobEffectCategory.BENEFICIAL, 0x00f0ff).addAttributeModifier(IcarusAttributes.MAX_STAMINA.holder(), Icarus.id("energized_attribute_modifier"), 2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    public static final RegistrySupplier<MobEffect> SECOND_WIND = STATUS_EFFECTS.register("second_wind", () -> new SecondWindStatusEffect(MobEffectCategory.BENEFICIAL, 0xffff33));

    public static Holder<MobEffect> flightlessHolder() {
        return FLIGHTLESS.holder();
    }
}
