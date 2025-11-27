package dev.cammiescorner.icarus.mixin.client;

import dev.cammiescorner.icarus.client.ClientPlayerFallbackValues;
import dev.cammiescorner.icarus.util.IcarusHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @ModifyArg(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"), index = 0)
    public double changeLookDirectionX(double x) {
        var player = minecraft.player;

        // we inject inside a null check so this is just to make the IDE happy
        assert player != null;

        if ((player.getXRot() > 90 || player.getXRot() < -90) && ClientPlayerFallbackValues.canClientLoopDeLoop(player) && player.isFallFlying() && IcarusHelper.hasWings(player) && IcarusHelper.getConfigValues(player).canLoopDeLoop()) {
            return -x;
        }

        return x;
    }
}
