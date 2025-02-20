package dev.cammiescorner.icarus.forge.mixin;

import dev.cammiescorner.icarus.item.WingItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@Mixin(WingItem.class)
public abstract class WingItemMixin extends Item implements ICurioItem {

    private WingItemMixin(Properties pProperties) {
        super(pProperties);
        throw new UnsupportedOperationException();
    }
}
