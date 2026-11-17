package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.settings.CarpetRuneLegendSettings;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
abstract class PlayerDisableMeleeCriticalHitsMixin {
    @Inject(method = "canCriticalAttack(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void carpetRuneLegendAddition$disableMeleeCriticalHits(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (CarpetRuneLegendSettings.disableMeleeCriticalHits) {
            cir.setReturnValue(false);
        }
    }
}
