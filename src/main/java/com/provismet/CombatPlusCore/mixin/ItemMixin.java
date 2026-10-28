package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.registries.CombatPlusDataComponentTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "getUseDuration", at = @At("HEAD"), cancellable = true)
    private void componentUseTime (ItemStack stack, LivingEntity user, CallbackInfoReturnable<Integer> cir) {
        Integer maxTicks = stack.get(CombatPlusDataComponentTypes.MAX_USE_TIME);
        if (maxTicks != null) cir.setReturnValue(maxTicks);
    }
}
