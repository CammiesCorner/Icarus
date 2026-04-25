package dev.cammiescorner.icarus.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.cammiescorner.icarus.api.SlowFallingEntity;
import dev.cammiescorner.icarus.init.IcarusAttributes;
import dev.cammiescorner.icarus.network.s2c.SyncFlightStaminaPacket;
import dev.cammiescorner.icarus.util.IcarusHelper;
import dev.cammiescorner.icarus.util.StaminaProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements SlowFallingEntity, StaminaProvider {
    @Unique private boolean icarus$slowFalling;
    @Unique public float icarus$flightStamina;

    private PlayerMixin(EntityType<? extends LivingEntity> $$0, Level $$1) {
        super($$0, $$1);
        throw new UnsupportedOperationException();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickStamina(CallbackInfo ci) {
        if(!level().isClientSide() && onGround() && icarus$getStamina() < icarus$getMaxStamina())
            icarus$modifyStamina(IcarusHelper.getConfigValues(this).staminaRegen());
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void saveStamina(CompoundTag compound, CallbackInfo ci) {
        compound.putFloat("IcarusStamina", icarus$flightStamina);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readStamina(CompoundTag compound, CallbackInfo ci) {
        icarus$flightStamina = compound.getFloat("IcarusStamina");
    }

    @ModifyReturnValue(method = "createAttributes", at = @At("RETURN"))
    private static AttributeSupplier.Builder createPlayerAttributes(AttributeSupplier.Builder builder) {
        IcarusAttributes.registerAll();

        return builder.add(IcarusAttributes.STAMINA.holder());
    }

    @Override
    public void icarus$setSlowFalling(boolean slowFalling) {
        this.icarus$slowFalling = slowFalling;
    }

    @Override
    public boolean icarus$isSlowFalling() {
        return icarus$slowFalling;
    }

    @Override
    public float icarus$getMaxStamina() {
        return (float) getAttribute(IcarusAttributes.STAMINA.holder()).getValue();
    }

    @Override
    public float icarus$getStamina() {
        return icarus$flightStamina;
    }

    @Override
    public void icarus$setStamina(float stamina) {
        stamina = Math.clamp(stamina, 0, icarus$getMaxStamina());

        if((Player) (Object) this instanceof ServerPlayer player && icarus$flightStamina != stamina)
            SyncFlightStaminaPacket.send(player, stamina);

        icarus$flightStamina = stamina;
    }

    @Override
    public void icarus$modifyStamina(float amount) {
        icarus$setStamina(icarus$getStamina() + amount);
    }
}
