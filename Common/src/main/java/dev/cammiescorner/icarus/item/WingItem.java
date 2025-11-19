package dev.cammiescorner.icarus.item;

import dev.cammiescorner.icarus.IcarusConfig;
import dev.cammiescorner.icarus.init.IcarusItemTags;
import dev.cammiescorner.icarus.util.IcarusHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class WingItem extends Item {
    private final WingType wingType;

    public WingItem(WingType wingType) {
        super(new Item.Properties().durability(IcarusConfig.wingsDurability).rarity(wingType.rarity()));
        this.wingType = wingType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (IcarusHelper.equipFunc.test(player, stack)) {
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }

        return super.use(level, player, hand);
    }

    public boolean isUsable(LivingEntity entity, ItemStack stack) {
        return IcarusConfig.wingsDurability <= 0 || stack.getDamageValue() < stack.getMaxDamage() - 1;
    }

    public boolean onFlightTick(LivingEntity entity, ItemStack wings, int ticks) {
        if (IcarusConfig.wingsDurability > 0 && wings.is(IcarusItemTags.MELTS) && !(entity instanceof Player player && player.isCreative())) {
            var cfg = IcarusHelper.getConfigValues(entity);
            if (ticks % 20 == 0 || (cfg.maxHeightEnabled() && entity.getY() > entity.level().getHeight() + cfg.maxHeightAboveWorld() && ticks % 2 == 0)) {
                wings.hurtAndBreak(1, entity, EquipmentSlot.CHEST);
            }
        }

        return this.isUsable(entity, wings);
    }

    @Override
    public boolean isValidRepairItem(ItemStack current, ItemStack repair) {
        return repair.is(IcarusItemTags.WING_REPAIR_ITEMS);
    }

    public WingType getWingType() {
        return this.wingType;
    }

    public enum WingType {
        FEATHERED, DRAGON, MECHANICAL_FEATHERED, MECHANICAL_LEATHER, LIGHT, UNIQUE;

        public Rarity rarity() {
            return this == UNIQUE ? Rarity.EPIC : Rarity.RARE;
        }
    }
}
