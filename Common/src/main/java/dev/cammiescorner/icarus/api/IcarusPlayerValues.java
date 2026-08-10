package dev.cammiescorner.icarus.api;

import dev.cammiescorner.icarus.IcarusConfig;
import dev.cammiescorner.icarus.init.IcarusDimensionTypeTags;
import dev.cammiescorner.icarus.init.IcarusLevelTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

public interface IcarusPlayerValues {

    default float wingsSpeed() {
        return IcarusConfig.wingsSpeed;
    }

    default float maxSlowedMultiplier() {
        return IcarusConfig.maxSlowedMultiplier;
    }

    default boolean armorSlows() {
        return IcarusConfig.armorSlows;
    }

    default boolean canLoopDeLoop() {
        return IcarusConfig.canLoopDeLoop;
    }

    default boolean canSlowFall() {
        return IcarusConfig.canSlowFall;
    }

    default float exhaustionAmount() {
        return IcarusConfig.exhaustionAmount;
    }

    default int maxHeightAboveWorld() {
        return IcarusConfig.maxHeightAboveWorld;
    }

    default boolean maxHeightEnabled() {
        return IcarusConfig.maxHeightEnabled;
    }

    default boolean dropOutOfSkyWhenTired() {
        return IcarusConfig.dropOutOfSkyWhenTired;
    }

    default boolean useStaminaForFlight() {
        return IcarusConfig.useStaminaForFlight;
    }

    default float staminaAmount() {
        return IcarusConfig.staminaAmount;
    }

    default float staminaRegen() {
        return IcarusConfig.staminaRegen;
    }

    default TagKey<LevelStem> noFlyingAllowedInLevels() {
        return IcarusLevelTags.NO_FLYING_ALLOWED;
    }

    default TagKey<DimensionType> noFlyingAllowedInDimensions() {
        return IcarusDimensionTypeTags.NO_FLYING_ALLOWED;
    }

    default float requiredFoodAmount() {
        return IcarusConfig.requiredFoodAmount;
    }
}
