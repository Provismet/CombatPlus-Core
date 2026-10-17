package com.provismet.CombatPlusCore.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import com.provismet.CombatPlusCore.utility.CPCGameRules;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TridentItem.class)
public abstract class TridentItemMixin extends Item {
    public TridentItemMixin (Properties settings) {
        super(settings);
    }
    
    @Inject(method="releaseUsing", at=@At(value="INVOKE", target="Lnet/minecraft/world/item/ItemStack;hurtWithoutBreaking(ILnet/minecraft/world/entity/player/Player;)V"), cancellable=true)
    private void replaceTridentThrow (ItemStack itemStack, Level world, LivingEntity user, int remainingUseTicks, CallbackInfoReturnable<Boolean> cir) {
        if (world instanceof ServerLevel serverWorld && serverWorld.getGameRules().get(CPCGameRules.LOYALTY_STAYS_IN_HAND) && user instanceof Player player) {
            int loyalty = EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverWorld, itemStack, player);

            if (loyalty > 0) {
                ItemStack temp = itemStack.copy();
                ItemEnchantments.Mutable tempEnchantmentBuilder = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
                ItemEnchantments existingEnchantments = temp.getEnchantments();

                // Don't want the thrown tridents to return to the player, so loyalty must be removed from the enchantment list.
                for (Holder<Enchantment> enchantmentEntry : existingEnchantments.keySet()) {
                    if (!enchantmentEntry.value().getEffects(EnchantmentEffectComponents.TRIDENT_RETURN_ACCELERATION).isEmpty()) continue;
                    tempEnchantmentBuilder.upgrade(enchantmentEntry, existingEnchantments.getLevel(enchantmentEntry));
                }
                temp.set(DataComponents.ENCHANTMENTS, tempEnchantmentBuilder.toImmutable());

                ThrownTrident tridentEntity = new ThrownTrident(world, player, temp);
                tridentEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 2.5f, 1.0f);
                tridentEntity.pickup = AbstractArrow.Pickup.DISALLOWED;

                player.getCooldowns().addCooldown(itemStack, 105 - loyalty * 15);
                world.addFreshEntity(tridentEntity);
                Holder<SoundEvent> tridentSound = EnchantmentHelper.pickHighestLevel(itemStack, EnchantmentEffectComponents.TRIDENT_SOUND).orElse(SoundEvents.TRIDENT_THROW);
                world.playSound(null, tridentEntity, tridentSound.value(), SoundSource.PLAYERS, 1.0f, 1.0f);

                player.awardStat((Stats.ITEM_USED.get((TridentItem)(Object)this)));
                itemStack.hurtWithoutBreaking(1, player);

                cir.setReturnValue(true);
            }
        }
    }
}
