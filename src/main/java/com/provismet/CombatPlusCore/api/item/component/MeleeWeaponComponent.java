package com.provismet.CombatPlusCore.api.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.lilylib.util.MoreMath;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;

/**
 * <p> Item data component that allows data-driven use of melee and dual weapon damage numbers. </p>
 * <p> This component should be present on all melee weapons. </p>
 *
 * @apiNote The damage numbers used here do not affect standard damage dealt. They only affect special damage callbacks from Combat+ mods, such as the parry damage from Dual Swords.
 * @implNote This is applied automatically as part of the {@code .sword()} and {@code .axe()} Item.Settings methods.
 *
 * @param weaponDamage Damage number to use for mainhand callbacks.
 * @param dualDamage Damage number to use for offhand callbacks.
 */
public record MeleeWeaponComponent (float weaponDamage, float dualDamage) {
    public static final MeleeWeaponComponent DEFAULT = new MeleeWeaponComponent(0, 0);

    public static final Codec<MeleeWeaponComponent> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            ExtraCodecs.NON_NEGATIVE_FLOAT.fieldOf("weapon_damage").forGetter(MeleeWeaponComponent::weaponDamage),
            ExtraCodecs.NON_NEGATIVE_FLOAT.fieldOf("dual_damage").forGetter(MeleeWeaponComponent::dualDamage)
        ).apply(instance, MeleeWeaponComponent::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, MeleeWeaponComponent> PACKET_CODEC = StreamCodec.composite(
        ByteBufCodecs.FLOAT,
        MeleeWeaponComponent::weaponDamage,
        ByteBufCodecs.FLOAT,
        MeleeWeaponComponent::dualDamage,
        MeleeWeaponComponent::new
    );

    /**
     * Creates a component with no dual damage.
     *
     * @param damage The mainhand damage.
     * @return A new MeleeWeaponComponent.
     */
    public static MeleeWeaponComponent createMelee (float damage) {
        return new MeleeWeaponComponent(damage, 0);
    }

    /**
     * Creates a component for a dual weapon, setting the offhand damage to a portion of the mainhand damage.
     *
     * @apiNote The dualDamage value will always be at least 0.5.
     * @param damage The mainhand damage.
     * @return A new MeleeWeaponComponent.
     */
    public static MeleeWeaponComponent createDual (float damage) {
        float offhandDamage = MoreMath.roundDownToMultipleFloat(damage / 3.5f, 0.5f);
        offhandDamage = Math.max(offhandDamage, 0.5f);

        return new MeleeWeaponComponent(damage, offhandDamage);
    }

    /**
     * Creates a component from the mainhand and offhand damage attributes.
     *
     * @param attributes Attributes component containing the damage for the new component.
     * @return A new MeleeWeaponComponent.
     */
    public static MeleeWeaponComponent createFromAttributes (ItemAttributeModifiers attributes) {
        float meleeDamage = 0f;
        float dualDamage = 0f;

        for (ItemAttributeModifiers.Entry entry : attributes.modifiers()) {
            if (entry.attribute() == Attributes.ATTACK_DAMAGE && entry.modifier().operation() == AttributeModifier.Operation.ADD_VALUE) {
                if (entry.slot().test(EquipmentSlot.MAINHAND)) meleeDamage += (float)entry.modifier().amount();
                if (entry.slot().test(EquipmentSlot.OFFHAND)) dualDamage += (float)entry.modifier().amount();
            }
        }
        return new MeleeWeaponComponent(meleeDamage, dualDamage);
    }

    /**
     * Creates a component, creating a dual weapon component from the mainhand damage of the attributes provided.
     *
     * @see MeleeWeaponComponent#createDual
     * @param attributes Attribute component containing mainhand damage.
     * @return A new MeleeWeaponComponent.
     */
    public static MeleeWeaponComponent createDualFromAttributes (ItemAttributeModifiers attributes) {
        float meleeDamage = 0f;
        for (ItemAttributeModifiers.Entry entry : attributes.modifiers()) {
            if (entry.attribute() == Attributes.ATTACK_DAMAGE && entry.modifier().operation() == AttributeModifier.Operation.ADD_VALUE && entry.slot().test(EquipmentSlot.MAINHAND)) {
                meleeDamage += (float)entry.modifier().amount();
            }
        }
        return MeleeWeaponComponent.createDual(meleeDamage);
    }

    public boolean isDual () {
        return this.dualDamage > 0;
    }
}
