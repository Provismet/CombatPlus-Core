package com.provismet.CombatPlusCore.utility;

import com.provismet.CombatPlusCore.registries.CPCEnchantmentComponentTypes;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContext;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.List;

/**
 * Enchantment helper to apply hooks from Combat+ enchantment components.
 *
 * @see EnchantmentHelper
 */
public class CPCEnchantmentHelper {
    /**
     * Replaces the original {@link EnchantmentHelper#getDamage} to provide gamerule compatible damage and multi-item damage bonuses.
     *
     * @param world The world.
     * @param itemStack The enchanted item.
     * @param target The entity that was attacked.
     * @param damageSource The damage source of the attack.
     * @param baseDamage The initial damage amount for the attack.
     * @return The total damage dealt.
     */
    public static float getDamage (ServerWorld world, ItemStack itemStack, Entity target, DamageSource damageSource, float baseDamage) {
        MutableFloat damage = new MutableFloat();
        damage.add(CPCEnchantmentHelper.modifyValue(CPCEnchantmentComponentTypes.GAMERULE_DAMAGE, world, itemStack, target, damageSource, 0));
        if (target instanceof PlayerEntity) damage.setValue(damage.floatValue() * world.getGameRules().get(CPCGameRules.PVP_DAMAGE_MODIFIER).get());
        damage.add(baseDamage);

        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            enchantment.value().modifyDamage(world, level, itemStack, target, damageSource, damage);
        }, itemStack);

        if (damageSource.getAttacker() instanceof LivingEntity attacker) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack equippedItem = attacker.getEquippedStack(slot);
                if (equippedItem == null || equippedItem.isEmpty()) continue;

                CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
                    if (enchantment.value().slotMatches(slot))
                        enchantment.value().modifyValue(CPCEnchantmentComponentTypes.BONUS_DAMAGE, world, level, itemStack, attacker, damageSource, damage);
                }, equippedItem);
            }
        }

        return damage.floatValue();
    }

    /**
     * Calls enchantment callbacks for charged hits.
     *
     * @param world The world the effect should occur in.
     * @param user The wielder of the item.
     * @param target The entity that was struck.
     * @param slot The equipment slot to trigger callbacks for.
     */
    public static void postChargedHit (ServerWorld world, LivingEntity user, LivingEntity target, EquipmentSlot slot) {
        CPCEnchantmentHelper.forEachEnchantment((RegistryEntry<Enchantment> enchantment, int level, EnchantmentEffectContext context) -> {
            for (EnchantmentEffectEntry<CPCEnchantmentEntityEffect> effect : enchantment.value().getEffect(CPCEnchantmentComponentTypes.POST_CHARGED_ATTACK)) {
                LootContext conditional = CPCLootContext.createDoubleEntity(world, level, user, target, user.getEquippedStack(slot));
                if (effect.test(conditional)) effect.effect().apply(world, level, context, user, target);
            }
        }, user, slot);
    }

    /**
     * Calls enchantment callbacks for critical hits.
     *
     * @param world The world the effect should occur in.
     * @param user The wielder of the item.
     * @param target The entity that was struck.
     * @param slot The equipment slot to trigger callbacks for.
     */
    public static void postCriticalHit (ServerWorld world, LivingEntity user, LivingEntity target, EquipmentSlot slot) {
        CPCEnchantmentHelper.forEachEnchantment((RegistryEntry<Enchantment> enchantment, int level, EnchantmentEffectContext context) -> {
            for (EnchantmentEffectEntry<CPCEnchantmentEntityEffect> effect : enchantment.value().getEffect(CPCEnchantmentComponentTypes.POST_CRITICAL_ATTACK)) {
                LootContext conditional = CPCLootContext.createDoubleEntity(world, level, user, target, user.getEquippedStack(slot));
                if (effect.test(conditional)) effect.effect().apply(world, level, context, user, target);
            }
        }, user, slot);
    }

    /**
     * Calls enchantment callbacks for kills.
     *
     * @param world The world the effect should occur in.
     * @param user The wielder of the item.
     * @param target The entity that was struck.
     * @param slot The equipment slot to trigger callbacks for.
     */
    public static void postKill (ServerWorld world, LivingEntity user, LivingEntity target, EquipmentSlot slot) {
        CPCEnchantmentHelper.forEachEnchantment((RegistryEntry<Enchantment> enchantment, int level, EnchantmentEffectContext context) -> {
            for (EnchantmentEffectEntry<CPCEnchantmentEntityEffect> effect : enchantment.value().getEffect(CPCEnchantmentComponentTypes.POST_KILL)) {
                LootContext conditional = CPCLootContext.createDoubleEntity(world, level, user, target, user.getEquippedStack(slot));
                if (effect.test(conditional)) effect.effect().apply(world, level, context, user, target);
            }
        }, user, slot);
    }

    public static void postBlock (ServerWorld world, LivingEntity user, LivingEntity attacker, EquipmentSlot slot) {
        CPCEnchantmentHelper.forEachEnchantment((RegistryEntry<Enchantment> enchantment, int level, EnchantmentEffectContext context) -> {
            for (EnchantmentEffectEntry<CPCEnchantmentEntityEffect> effect : enchantment.value().getEffect(CPCEnchantmentComponentTypes.POST_BLOCK)) {
                LootContext conditional = CPCLootContext.createReversedDoubleEntity(world, level, user, attacker, user.getEquippedStack(slot));
                if (effect.test(conditional)) effect.effect().apply(world, level, context, user, attacker);
            }
        }, user, slot);
    }

    /**
     * Modifies a value from a given enchantment component type.
     *
     * @param valueType The type of value to modify.
     * @param random Random source.
     * @param itemStack The enchanted item.
     * @param base The original float value.
     * @return The new float value.
     */
    public static float modifyValue (ComponentType<EnchantmentValueEffect> valueType, Random random, ItemStack itemStack, float base) {
        MutableFloat value = new MutableFloat(base);
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> enchantment.value().modifyValue(valueType, random, level, value), itemStack);
        return value.floatValue();
    }

    /**
     * Modifies a value from a given enchantment component type.
     *
     * @param valueType The type of value to modify.
     * @param world The server-side world.
     * @param itemStack The enchanted item.
     * @param base The original float value.
     * @return The new float value.
     */
    public static float modifyValue (ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> valueType, ServerWorld world, ItemStack itemStack, float base) {
        MutableFloat value = new MutableFloat(base);
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> enchantment.value().modifyValue(valueType, world, level, itemStack, value), itemStack);
        return value.floatValue();
    }

    /**
     * Modifies a value from a given enchantment component type.
     *
     * @param valueType The type of value to modify.
     * @param world The server-side world.
     * @param itemStack The enchanted item.
     * @param user The owner of the item.
     * @param base The original float value.
     * @return The new float value.
     */
    public static float modifyValue (ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> valueType, ServerWorld world, ItemStack itemStack, Entity user, float base) {
        MutableFloat value = new MutableFloat(base);
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> enchantment.value().modifyValue(valueType, world, level, itemStack, user, value), itemStack);
        return value.floatValue();
    }

    /**
     * Modifies a value from a given enchantment component type.
     *
     * @param valueType The type of value to modify.
     * @param world The server-side world.
     * @param itemStack The enchanted itemStack.
     * @param user The owner of the item.
     * @param damageSource The associated damage source.
     * @param base The original float value.
     * @return The new float value.
     */
    public static float modifyValue (ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> valueType, ServerWorld world, ItemStack itemStack, Entity user, DamageSource damageSource, float base) {
        MutableFloat value = new MutableFloat(base);
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> enchantment.value().modifyValue(valueType, world, level, itemStack, user, damageSource, value), itemStack);
        return value.floatValue();
    }

    public static void forEachEnchantment (Consumer consumer, ItemStack item) {
        ItemEnchantmentsComponent itemEnchantmentsComponent = item.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
        for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : itemEnchantmentsComponent.getEnchantmentEntries()) {
            consumer.accept(entry.getKey(), entry.getIntValue());
        }
    }

    /**
     * Iterates over all enchantments on an item, activating the consumer for each.
     *
     * @param consumer A consumer / lambda to be called.
     * @param user The owner of the enchanted item.
     * @param slot The equipment slot the item is in.
     */
    public static void forEachEnchantment (ContextConsumer consumer, LivingEntity user, EquipmentSlot slot) {
        CPCEnchantmentHelper.forEachEnchantment(consumer, user, slot, user.getEquippedStack(slot));
    }

    /**
     * Iterates over all enchantments on an item, activating the consumer for each.
     *
     * @param consumer A consumer / lambda to be called.
     * @param user The owner of the enchanted item.
     * @param slot The equipment slot the item is in.
     * @param itemStack The enchanted item.
     */
    public static void forEachEnchantment (ContextConsumer consumer, LivingEntity user, EquipmentSlot slot, ItemStack itemStack) {
        if (itemStack.isEmpty()) return;

        ItemEnchantmentsComponent enchantments = itemStack.getEnchantments();
        if (enchantments.isEmpty()) return;

        EnchantmentEffectContext context = new EnchantmentEffectContext(itemStack, slot, user);
        for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : enchantments.getEnchantmentEntries()) {
            if (entry.getKey().value().slotMatches(slot)) consumer.accept(entry.getKey(), entry.getIntValue(), context);
        }
    }

    @FunctionalInterface
    public interface Consumer {
        public void accept (RegistryEntry<Enchantment> enchantment, int level);
    }

    @FunctionalInterface
    public interface ContextConsumer {
        public void accept (RegistryEntry<Enchantment> enchantment, int level, EnchantmentEffectContext context);
    }
}
