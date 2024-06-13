package com.provismet.CombatPlusCore.items;

import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractMeleeWeapon extends ToolItem implements MeleeWeapon {
    protected AbstractMeleeWeapon (ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public boolean canMine (BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public boolean postHit (ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }
}
