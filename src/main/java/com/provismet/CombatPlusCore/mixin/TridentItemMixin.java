package com.provismet.CombatPlusCore.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import com.provismet.CombatPlusCore.utility.CPCGameRules;

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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TridentItem.class)
public abstract class TridentItemMixin extends Item {
    public TridentItemMixin (Settings settings) {
        super(settings);
    }
    
    @Inject(method="onStoppedUsing", at=@At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/player/PlayerEntity;)V"), cancellable=true)
    private void replaceTridentThrow (ItemStack itemStack, World world, LivingEntity user, int remainingUseTicks, CallbackInfoReturnable<Boolean> cir) {
        if (world instanceof ServerWorld serverWorld && serverWorld.getGameRules().getBoolean(CPCGameRules.LOYALTY_STAYS_IN_HAND) && user instanceof PlayerEntity player) {
            int loyalty = EnchantmentHelper.getTridentReturnAcceleration(serverWorld, itemStack, player);

            if (loyalty > 0) {
                ItemStack temp = itemStack.copy();
                ItemEnchantmentsComponent.Builder tempEnchantmentBuilder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);
                ItemEnchantmentsComponent existingEnchantments = temp.getEnchantments();

                // Don't want the thrown tridents to return to the player, so loyalty must be removed from the enchantment list.
                for (RegistryEntry<Enchantment> enchantmentEntry : existingEnchantments.getEnchantments()) {
                    if (!enchantmentEntry.value().getEffect(EnchantmentEffectComponentTypes.TRIDENT_RETURN_ACCELERATION).isEmpty()) continue;
                    tempEnchantmentBuilder.add(enchantmentEntry, existingEnchantments.getLevel(enchantmentEntry));
                }
                temp.set(DataComponentTypes.ENCHANTMENTS, tempEnchantmentBuilder.build());

                TridentEntity tridentEntity = new TridentEntity(world, player, temp);
                tridentEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, 2.5f, 1.0f);
                tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;

                player.getItemCooldownManager().set(itemStack, 105 - loyalty * 15);
                world.spawnEntity(tridentEntity);
                RegistryEntry<SoundEvent> tridentSound = EnchantmentHelper.getEffect(itemStack, EnchantmentEffectComponentTypes.TRIDENT_SOUND).orElse(SoundEvents.ITEM_TRIDENT_THROW);
                world.playSoundFromEntity(null, tridentEntity, tridentSound.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);

                player.incrementStat((Stats.USED.getOrCreateStat((TridentItem)(Object)this)));
                itemStack.damage(1, player);

                cir.setReturnValue(true);
            }
        }
    }
}
