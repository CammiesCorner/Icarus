package dev.cammiescorner.icarus.fabric.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.cammiescorner.icarus.init.IcarusAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {

    @ModifyReturnValue(method = "createAttributes", at = @At("RETURN"))
    private static AttributeSupplier.Builder createPlayerAttributes(AttributeSupplier.Builder builder) {
        // ensure attributes are registered even if some other mod calls this earlier
        IcarusAttributes.registerAll();

        return builder.add(IcarusAttributes.MAX_STAMINA.holder());
    }
}
