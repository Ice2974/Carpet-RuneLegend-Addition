package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.settings.CarpetRuneLegendSettings;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 始终触发横扫：规则开启时，主手持有剑且攻击成功就触发横扫，
 * 不再要求攻击冷却完成度、非暴击、非疾跑、着地与移动速度达到原版阈值。
 *
 * <p>注入 {@code Player#isSweepAttack} 的 HEAD 并返回 true，保留主手剑检查；
 * 攻击是否“成功”仍由 {@code Player#attack} 中 {@code hurtOrSimulate} 的返回值决定，
 * 本注入不会让落空或完全格挡的攻击触发横扫。
 */
@Mixin(Player.class)
abstract class PlayerAlwaysSweepAttackMixin {
    @Inject(method = "isSweepAttack(ZZZ)Z", at = @At("HEAD"), cancellable = true)
    private void carpetRuneLegendAddition$alwaysSweepAttack(boolean fullCharge, boolean criticalHit,
                                                             boolean sprintingKnockback,
                                                             CallbackInfoReturnable<Boolean> cir) {
        if (!CarpetRuneLegendSettings.alwaysSweepAttack) {
            return;
        }
        Player player = (Player) (Object) this;
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ItemTags.SWORDS)) {
            cir.setReturnValue(true);
        }
    }
}
