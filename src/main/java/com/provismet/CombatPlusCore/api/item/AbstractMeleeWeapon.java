package com.provismet.CombatPlusCore.api.item;

import com.provismet.CombatPlusCore.api.item.interfaces.MeleeWeapon;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Convenience class for creating new melee weapons.
 */
public abstract class AbstractMeleeWeapon extends Item implements MeleeWeapon {
    protected AbstractMeleeWeapon (Properties settings) {
        super(settings);
    }

    @Override
    public boolean canDestroyBlock (ItemStack stack, BlockState state, Level world, BlockPos pos, LivingEntity user) {
        if (user.hasInfiniteMaterials()) return false;
        return super.canDestroyBlock(stack, state, world, pos, user);
    }
}
