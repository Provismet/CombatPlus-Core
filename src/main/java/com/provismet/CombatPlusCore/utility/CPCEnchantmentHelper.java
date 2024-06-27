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
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.loot.context.LootContext;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.random.Random;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.List;

/**
 * Enchantment helper to apply hooks from Combat+ enchantment components.
 */
public class CPCEnchantmentHelper {
    /**
     * Gets the bonus attack damage that the user should deal against the target.
     * 
     * <p> This method internally calls the vanilla {@link EnchantmentHelper#getDamage}.
     * 
     * @param defaultSlot The equipment slot to be passed to the vanilla EnchantmentHelper. Almost always MAINHAND.
     * @param user The wielder of the item.
     * @param target The entity that was struck.
     * @return The additional damage to deal to the target.
     */
    public static float getAttackDamage (EquipmentSlot defaultSlot, LivingEntity user, LivingEntity target) {
        return 0f;
    }

    /**
     * Gets the bonus attack damage that the user should deal against the target.
     * 
     * <p> This method internally calls the vanilla {@link EnchantmentHelper#getDamage} with a MAINHAND equipment slot.
     * 
     * @param user The wielder of the item.
     * @param target The entity that was struck.
     * @return The additional damage to deal to the target.
     */
    public static float getAttackDamage (LivingEntity user, LivingEntity target) {
        return CPCEnchantmentHelper.getAttackDamage(EquipmentSlot.MAINHAND, user, target);
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

    /**
     * Modifies a value from a given enchantment component type.
     *
     * @param valueType The type of value to modify.
     * @param random Random source.
     * @param item The enchanted item.
     * @param base The original float value.
     * @return The new float value.
     */
    public static float modifyValue (ComponentType<EnchantmentValueEffect> valueType, Random random, ItemStack item, float base) {
        MutableFloat value = new MutableFloat(base);
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> enchantment.value().modifyValue(valueType, random, level, value), item);
        return value.floatValue();
    }

    /**
     * Modifies a value from a given enchantment component type.
     *
     * @param valueType The type of value to modify.
     * @param world The server-side world.
     * @param item The enchanted item.
     * @param base The original float value.
     * @return The new float value.
     */
    public static float modifyValue (ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> valueType, ServerWorld world, ItemStack item, float base) {
        MutableFloat value = new MutableFloat(base);
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> enchantment.value().modifyValue(valueType, world, level, item, value), item);
        return value.floatValue();
    }

    /**
     * Modifies a value from a given enchantment component type.
     *
     * @param valueType The type of value to modify.
     * @param world The server-side world.
     * @param item The enchanted item.
     * @param user The owner of the item.
     * @param base The original float value.
     * @return The new float value.
     */
    public static float modifyValue (ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> valueType, ServerWorld world, ItemStack item, LivingEntity user, float base) {
        MutableFloat value = new MutableFloat(base);
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> enchantment.value().modifyValue(valueType, world, level, item, user, value), item);
        return value.floatValue();
    }

    /**
     * Modifies a value from a given enchantment component type.
     *
     * @param valueType The type of value to modify.
     * @param world The server-side world.
     * @param item The enchanted item.
     * @param user The owner of the item.
     * @param damageSource The associated damage source.
     * @param base The original float value.
     * @return The new float value.
     */
    public static float modifyValue (ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> valueType, ServerWorld world, ItemStack item, LivingEntity user, DamageSource damageSource, float base) {
        MutableFloat value = new MutableFloat(base);
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> enchantment.value().modifyValue(valueType, world, level, item, user, damageSource, value), item);
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

    /**
     * Safely checks if an enchantment is within an enchantment tag.
     * 
     * @param enchantment The enchantment.
     * @param enchantmentTag The enchantment tag.
     * @return Whether or not the enchantment is present in the tag. Unregistered enchantments will return false.
     */
    public static boolean isInTag (RegistryEntry<Enchantment> enchantment, TagKey<Enchantment> enchantmentTag) {
        try {
            return enchantment.isIn(enchantmentTag);
        }
        catch (Exception e) {
            return false;
        }
    }
}
