package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.settings.CarpetRuneLegendSettings;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
abstract class PlayerDisableAttackCooldownMixin {
    @Inject(method = "getAttackStrengthScale(F)F", at = @At("HEAD"), cancellable = true)
    private void carpetRuneLegendAddition$fullAttackStrength(float partialTick, CallbackInfoReturnable<Float> cir) {
        if (CarpetRuneLegendSettings.disableAttackCooldown) {
            cir.setReturnValue(1.0F);
        }
    }

    // The minimum charge check reads the ticker directly on both sides.
    @Inject(method = "cannotAttackWithItem(Lnet/minecraft/world/item/ItemStack;I)Z",
            at = @At("HEAD"), cancellable = true)
    private void carpetRuneLegendAddition$checkMinimumAttackCharge(ItemStack stack, int tickOffset,
                                                                 CallbackInfoReturnable<Boolean> cir) {
        if (CarpetRuneLegendSettings.disableAttackCooldown) {
            cir.setReturnValue(1.0F < stack.getOrDefault(DataComponents.MINIMUM_ATTACK_CHARGE, 0.0F));
        }
    }
}
