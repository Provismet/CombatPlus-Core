package com.provismet.CombatPlusCore.items;

import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractMeleeWeapon extends Item implements MeleeWeapon {
    protected AbstractMeleeWeapon (Settings settings) {
        super(settings);
    }

    @Override
    public boolean canMine (ItemStack stack, BlockState state, World world, BlockPos pos, LivingEntity user) {
        if (user.isInCreativeMode()) return false;
        return super.canMine(stack, state, world, pos, user);
    }
}
