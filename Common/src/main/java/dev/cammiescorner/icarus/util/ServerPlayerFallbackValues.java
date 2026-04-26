package dev.cammiescorner.icarus.util;

import dev.cammiescorner.icarus.IcarusConfig;
import dev.cammiescorner.icarus.api.IcarusPlayerValues;

public class ServerPlayerFallbackValues implements IcarusPlayerValues {

    @Override
    public float wingsSpeed() {
        return IcarusConfig.wingsSpeed;
    }

    @Override
    public float maxSlowedMultiplier() {
        return IcarusConfig.maxSlowedMultiplier;
    }

    @Override
    public boolean armorSlows() {
        return IcarusConfig.armorSlows;
    }

    @Override
    public boolean canLoopDeLoop() {
        return IcarusConfig.canLoopDeLoop;
    }

    @Override
    public boolean canSlowFall() {
        return IcarusConfig.canSlowFall;
    }

    @Override
    public float exhaustionAmount() {
        return IcarusConfig.exhaustionAmount;
    }

    @Override
    public boolean maxHeightEnabled() {
        return IcarusConfig.maxHeightEnabled;
    }

    @Override
    public boolean dropOutOfSkyWhenTired() {
        return IcarusConfig.dropOutOfSkyWhenTired;
    }

    @Override
    public boolean useStaminaForFlight() {
        return IcarusConfig.useStaminaForFlight;
    }

    @Override
    public float staminaAmount() {
        return IcarusConfig.staminaAmount;
    }

    @Override
    public float staminaRegen() {
        return IcarusConfig.staminaRegen;
    }

    @Override
    public int maxHeightAboveWorld() {
        return IcarusConfig.maxHeightAboveWorld;
    }

    @Override
    public float requiredFoodAmount() {
        return IcarusConfig.requiredFoodAmount;
    }
}
