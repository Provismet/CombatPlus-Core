package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.utility.CPCItemTags;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Enchantments.class)
public class EnchantmentsMixin {
    @ModifyArgs(method="<clinit>", at=@At(value="INVOKE", target="Lnet/minecraft/enchantment/Enchantment;properties(Lnet/minecraft/registry/tag/TagKey;Lnet/minecraft/registry/tag/TagKey;IILnet/minecraft/enchantment/Enchantment$Cost;Lnet/minecraft/enchantment/Enchantment$Cost;I[Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/enchantment/Enchantment$Properties;"))
    private static void injectDamageEnchantable (Args args) {
        if (args.get(0) == ItemTags.WEAPON_ENCHANTABLE && args.get(1) == ItemTags.SWORD_ENCHANTABLE) {
            args.set(0, CPCItemTags.DAMAGE_ENCHANTABLE);
            args.set(1, CPCItemTags.DAMAGE_PRIMARY_ENCHANTABLE);
        }
        else if (args.get(0) == ItemTags.SHARP_WEAPON_ENCHANTABLE && args.get(1) == ItemTags.SWORD_ENCHANTABLE) {
            args.set(1, CPCItemTags.DAMAGE_PRIMARY_ENCHANTABLE);
        }
    }

    @ModifyArgs(method="<clinit>", at= @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;properties(Lnet/minecraft/registry/tag/TagKey;IILnet/minecraft/enchantment/Enchantment$Cost;Lnet/minecraft/enchantment/Enchantment$Cost;I[Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/enchantment/Enchantment$Properties;"))
    private static void injectAspectEnchantable (Args args) {
        if (args.get(0) == ItemTags.FIRE_ASPECT_ENCHANTABLE) {
            args.set(0, CPCItemTags.ASPECT_PRIMARY_ENCHANTABLE);
        }
    }
}
