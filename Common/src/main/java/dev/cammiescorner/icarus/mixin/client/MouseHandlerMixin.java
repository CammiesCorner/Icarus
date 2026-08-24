package dev.cammiescorner.icarus.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.cammiescorner.icarus.client.ClientPlayerFallbackValues;
import dev.cammiescorner.icarus.util.IcarusHelper;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @WrapOperation(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"))
    private void changelookDirectionX(LocalPlayer player, double x, double y, Operation<Void> op) {
        double actualX;
        if ((player.getXRot() > 90 || player.getXRot() < -90) && ClientPlayerFallbackValues.canClientLoopDeLoop(player) && player.isFallFlying() && IcarusHelper.hasWings(player) && IcarusHelper.getConfigValues(player).canLoopDeLoop()) {
            actualX = -x;
        }
        else {
            actualX = x;
        }

        op.call(player, actualX, y);
    }
}
