package com.provismet.CombatPlusCore.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.provismet.CombatPlusCore.utility.CombatGameRules;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

@Mixin(TridentItem.class)
public abstract class TridentItemMixin extends Item {
    public TridentItemMixin (Settings settings) {
        super(settings);
    }
    
    @Inject(method="onStoppedUsing", at=@At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V"), cancellable=true)
    private void replaceTridentThrow (ItemStack itemStack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo info) {
        if (world.getGameRules().getBoolean(CombatGameRules.LOYALTY_STAYS_IN_HAND)) {
            int loyalty = EnchantmentHelper.getLoyalty(itemStack);

            if (loyalty > 0) {
                ItemStack temp = itemStack.copy();
                ItemEnchantmentsComponent.Builder tempEnchantmentBuilder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);
                ItemEnchantmentsComponent existingEnchantments = temp.getEnchantments();

                // Don't want the thrown tridents to return to the player, so loyalty must be removed from the enchantment list.
                for (RegistryEntry<Enchantment> enchantmentEntry : existingEnchantments.getEnchantments()) {
                    if (enchantmentEntry.value() == Enchantments.LOYALTY) continue;
                    tempEnchantmentBuilder.add(enchantmentEntry.value(), existingEnchantments.getLevel(enchantmentEntry.value()));
                }
                temp.set(DataComponentTypes.ENCHANTMENTS, tempEnchantmentBuilder.build());

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
