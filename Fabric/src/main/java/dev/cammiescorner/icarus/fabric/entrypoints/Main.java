package dev.cammiescorner.icarus.fabric.entrypoints;

import dev.cammiescorner.icarus.init.IcarusPotions;
import dev.cammiescorner.icarus.item.WingItem;
import dev.cammiescorner.icarus.util.IcarusHelper;
import dev.emi.trinkets.TrinketSlot;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;

public class Main implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> IcarusHelper.onServerPlayerJoin(handler.getPlayer()));

        FabricBrewingRecipeRegistryBuilder.BUILD.register(IcarusPotions::registerPotionRecipes);

        IcarusHelper.getEquippedWings = entity -> {
            var component = TrinketsApi.getTrinketComponent(entity);
            if (component.isPresent()) {
                var items = component.orElseThrow().getEquipped(stack -> stack.getItem() instanceof WingItem);
                if (!items.isEmpty()) {
                    return items.get(0).getB();
                }
            }

            return ItemStack.EMPTY;
        };

        IcarusHelper.hasWings = entity -> {
            var component = TrinketsApi.getTrinketComponent(entity);
            return component.isPresent() && component.orElseThrow().isEquipped(stack -> stack.getItem() instanceof WingItem);
        };

        IcarusHelper.equipFunc = (user, stack) -> {
            var optional = TrinketsApi.getTrinketComponent(user);
            if (optional.isPresent()) {
                TrinketComponent comp = optional.get();
                for (var group : comp.getInventory().values()) {
                    for (TrinketInventory inv : group.values()) {
                        for (int i = 0; i < inv.getContainerSize(); i++) {
                            if (inv.getItem(i).isEmpty()) {
                                SlotReference ref = new SlotReference(inv, i);
                                if (TrinketSlot.canInsert(stack, ref, user)) {
                                    ItemStack newStack = stack.copy();
                                    newStack.setCount(1);
                                    inv.setItem(i, newStack);
                                    var soundEvent = stack.getItem() instanceof Equipable eq ? eq.getEquipSound() : null;
                                    if (!stack.isEmpty()) {
                                        user.gameEvent(GameEvent.EQUIP);
                                        if(soundEvent != null && !user.isSilent()) {
                                            user.level().playSeededSound(null, user.getX(), user.getY(), user.getZ(), soundEvent, user.getSoundSource(), 1.0F, 1.0F, user.getRandom().nextLong());
                                        }
                                    }
                                    stack.shrink(1);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        };

        EntityElytraEvents.CUSTOM.register((entity, tickElytra) -> IcarusHelper.onFallFlyingTick(entity, IcarusHelper.getEquippedWings(entity), tickElytra));
    }
}
