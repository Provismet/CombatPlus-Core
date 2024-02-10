package com.provismet.CombatPlusCore.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.provismet.CombatPlusCore.utility.CombatGameRules;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

@Mixin(TridentItem.class)
public abstract class TridentItemMixin extends Item {
    public TridentItemMixin (Settings settings) {
        super(settings);
    }
    
    @Inject(method="onStoppedUsing", at=@At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V", shift=At.Shift.AFTER), cancellable=true)
    private void replaceTridentThrow (ItemStack itemStack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo info) {
        if (world.getGameRules().getBoolean(CombatGameRules.LOYALTY_STAYS_IN_HAND)) {
            int loyalty = EnchantmentHelper.getLoyalty(itemStack);

            if (loyalty > 0) {
                ItemStack temp = itemStack.copy();
                Map<Enchantment, Integer> enchants = EnchantmentHelper.get(itemStack);
                NbtList nbt = new NbtList();
                for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                    if (entry.getKey() == Enchantments.LOYALTY) continue;
                    if (entry.getKey() == null) continue;
                    
                    int level = entry.getValue();
                    nbt.add(EnchantmentHelper.createNbt(EnchantmentHelper.getEnchantmentId(entry.getKey()), level));
                }
                temp.setSubNbt("Enchantments", nbt);

                TridentEntity tridentEntity = new TridentEntity(world, user, temp);
                tridentEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 2.5f, 1.0f);
                tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;

                ((PlayerEntity)user).getItemCooldownManager().set(itemStack.getItem(), 105 - loyalty * 15);
                world.spawnEntity(tridentEntity);
                world.playSoundFromEntity(null, tridentEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);
                
                ((PlayerEntity)user).incrementStat((Stats.USED.getOrCreateStat((TridentItem)(Object)this)));

                info.cancel();
            }
        }
    }
}
