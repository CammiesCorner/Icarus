package dev.cammiescorner.icarus.api;

import dev.cammiescorner.icarus.IcarusConfig;
import dev.cammiescorner.icarus.init.IcarusDimensionTypeTags;
import dev.cammiescorner.icarus.init.IcarusLevelTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

public interface IcarusPlayerValues {

    float wingsSpeed();

    float maxSlowedMultiplier();

    boolean armorSlows();

    boolean canLoopDeLoop();

    boolean canSlowFall();

    float exhaustionAmount();

    int maxHeightAboveWorld();

    boolean maxHeightEnabled();

    boolean useStaminaForFlight();

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
