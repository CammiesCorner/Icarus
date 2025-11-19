package dev.cammiescorner.icarus.fabric.mixin;

import dev.cammiescorner.icarus.item.WingItem;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WingItem.class)
public class WingItemMixin extends Item implements Trinket {

    private WingItemMixin(Properties properties) {
        super(properties);
        throw new UnsupportedOperationException();
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void construct(WingItem.WingType wingType, CallbackInfo ci) {
        TrinketsApi.registerTrinket(this, this);
    }
}
