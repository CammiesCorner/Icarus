package dev.cammiescorner.icarus.neoforge.entrypoints;

import dev.cammiescorner.icarus.Icarus;
import dev.cammiescorner.icarus.item.WingItem;
import dev.cammiescorner.icarus.util.IcarusHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod(Icarus.MODID)
public class Main {

    public Main() {
        IcarusHelper.getEquippedWings = entity -> {
            var component = CuriosApi.getCuriosInventory(entity);
            if (component.isPresent()) {
                var slot = component.orElseThrow().findFirstCurio(stack -> stack.getItem() instanceof WingItem);
                if (slot.isPresent()) {
                    return slot.orElseThrow().stack();
                }
            }

            return ItemStack.EMPTY;
        };

        IcarusHelper.hasWings = entity -> {
            var component = CuriosApi.getCuriosInventory(entity);
            return component.isPresent() && component.orElseThrow().isEquipped(stack -> stack.getItem() instanceof WingItem);
        };
    }
}
