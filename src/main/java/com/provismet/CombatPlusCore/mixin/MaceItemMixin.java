package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.api.item.interfaces.MeleeWeapon;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MaceItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MaceItem.class)
public abstract class MaceItemMixin extends Item implements MeleeWeapon {
    protected MaceItemMixin(Properties settings) {
        super(settings);
    }
}
