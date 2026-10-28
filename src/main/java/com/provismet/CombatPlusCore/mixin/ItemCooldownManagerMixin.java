package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.registries.CombatPlusDataComponentTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemCooldowns.class)
public abstract class ItemCooldownManagerMixin {
    @Inject(method = "getCooldownGroup", at = @At("HEAD"), cancellable = true)
    private void useGroupComponent (ItemStack stack, CallbackInfoReturnable<Identifier> cir) {
        Identifier group = stack.get(CombatPlusDataComponentTypes.COOLDOWN_GROUP);
        if (group != null) {
            cir.setReturnValue(group);
        }
    }
}
