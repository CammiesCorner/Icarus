package dev.cammiescorner.icarus.init;

import dev.cammiescorner.icarus.Icarus;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;

public class IcarusPotions {
	public static final RegistryHandler<Potion> POTIONS = RegistryHandler.create(Registries.POTION, Icarus.MODID);

	public static RegistrySupplier<Potion> ENERGIZED = POTIONS.register("energized", () -> new Potion(new MobEffectInstance(IcarusStatusEffects.ENERGIZED.holder(), 9600)));
	public static RegistrySupplier<Potion> ENERGIZED_LONG = POTIONS.register("energized_long", () -> new Potion(new MobEffectInstance(IcarusStatusEffects.ENERGIZED.holder(), 19200)));
	public static RegistrySupplier<Potion> ENERGIZED_STRONG = POTIONS.register("energized_strong", () -> new Potion(new MobEffectInstance(IcarusStatusEffects.ENERGIZED.holder(), 7200, 1)));

	public static RegistrySupplier<Potion> SECOND_WIND = POTIONS.register("second_wind", () -> new Potion(new MobEffectInstance(IcarusStatusEffects.SECOND_WIND.holder(), 1)));
	public static RegistrySupplier<Potion> SECOND_WIND_STRONG = POTIONS.register("second_wind_strong", () -> new Potion(new MobEffectInstance(IcarusStatusEffects.SECOND_WIND.holder(), 1, 1)));

	public static void registerPotionRecipes(PotionBrewing.Builder builder) {
		builder.addStartMix(Items.PITCHER_PLANT, ENERGIZED.holder());
		builder.addMix(ENERGIZED.holder(), Items.REDSTONE, ENERGIZED_LONG.holder());
		builder.addMix(ENERGIZED.holder(), Items.GLOWSTONE_DUST, ENERGIZED_STRONG.holder());

		builder.addStartMix(Items.TORCHFLOWER, SECOND_WIND.holder());
		builder.addMix(SECOND_WIND.holder(), Items.GLOWSTONE_DUST, SECOND_WIND_STRONG.holder());
	}
}
