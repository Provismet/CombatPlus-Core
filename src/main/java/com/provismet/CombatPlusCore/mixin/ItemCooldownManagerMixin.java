package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.registries.CPCDataComponentTypes;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemCooldownManager.class)
public abstract class ItemCooldownManagerMixin {
    @Inject(method = "getGroup", at = @At("HEAD"), cancellable = true)
    private void useGroupComponent (ItemStack stack, CallbackInfoReturnable<Identifier> cir) {
        Identifier group = stack.get(CPCDataComponentTypes.COOLDOWN_GROUP);
        if (group != null) {
            cir.setReturnValue(group);
        }
    }
}
