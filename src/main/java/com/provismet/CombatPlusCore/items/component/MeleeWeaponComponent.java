package com.provismet.CombatPlusCore.items.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.lilylib.util.MoreMath;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.dynamic.Codecs;

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
            Codecs.NON_NEGATIVE_FLOAT.fieldOf("weapon_damage").forGetter(MeleeWeaponComponent::weaponDamage),
            Codecs.NON_NEGATIVE_FLOAT.fieldOf("dual_damage").forGetter(MeleeWeaponComponent::dualDamage)
        ).apply(instance, MeleeWeaponComponent::new)
    );
    public static final PacketCodec<RegistryByteBuf, MeleeWeaponComponent> PACKET_CODEC = PacketCodec.tuple(
        PacketCodecs.FLOAT,
        MeleeWeaponComponent::weaponDamage,
        PacketCodecs.FLOAT,
        MeleeWeaponComponent::dualDamage,
        MeleeWeaponComponent::new
    );

    public static MeleeWeaponComponent createMelee (float damage) {
        return new MeleeWeaponComponent(damage, 0);
    }

    public static MeleeWeaponComponent createDual (float damage) {
        float offhandDamage = MoreMath.roundDownToMultipleFloat(damage / 3.5f, 0.5f);
        offhandDamage = Math.max(offhandDamage, 0.5f);

        return new MeleeWeaponComponent(damage, offhandDamage);
    }

    public static MeleeWeaponComponent createFromAttributes (AttributeModifiersComponent attributes) {
        float meleeDamage = 0f;
        float dualDamage = 0f;

        for (AttributeModifiersComponent.Entry entry : attributes.modifiers()) {
            if (entry.attribute() == EntityAttributes.ATTACK_DAMAGE && entry.modifier().operation() == EntityAttributeModifier.Operation.ADD_VALUE) {
                if (entry.slot().matches(EquipmentSlot.MAINHAND)) meleeDamage += (float)entry.modifier().value();
                if (entry.slot().matches(EquipmentSlot.OFFHAND)) dualDamage += (float)entry.modifier().value();
            }
        }
        return new MeleeWeaponComponent(meleeDamage, dualDamage);
    }

    public static MeleeWeaponComponent createDualFromAttributes (AttributeModifiersComponent attributes) {
        float meleeDamage = 0f;
        for (AttributeModifiersComponent.Entry entry : attributes.modifiers()) {
            if (entry.attribute() == EntityAttributes.ATTACK_DAMAGE && entry.modifier().operation() == EntityAttributeModifier.Operation.ADD_VALUE && entry.slot().matches(EquipmentSlot.MAINHAND)) {
                meleeDamage += (float)entry.modifier().value();
            }
        }
        return MeleeWeaponComponent.createDual(meleeDamage);
    }

    public boolean isDual () {
        return this.dualDamage > 0;
    }
}
