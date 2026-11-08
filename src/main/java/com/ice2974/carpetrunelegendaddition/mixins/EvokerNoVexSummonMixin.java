package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.settings.CarpetRuneLegendSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 唤魔者永不召唤恼鬼：规则开启时，在唤魔者法术选择的 canUse 阶段直接返回 false，
 * 唤魔者从不进入召唤施法（无前摇、无准备音效、无施法动画），而不是“开始施法后召唤失败”。
 *
 * <p>26.3 中 {@code Evoker$EvokerSummonSpellGoal} 由 {@code Evoker#registerGoals} 以
 * 优先级 4 注册，是唤魔者唯一的恼鬼召唤入口；其 {@code canUse()} 原本只在
 * {@code super.canUse()} 通过后按 16 格内恼鬼数量做概率判断，因此这里提前返回 false
 * 与原版“判断后决定不召唤”的语义一致。目标类是包私有内部类，只能通过 {@code targets}
 * 字符串引用；{@code canUse()} 为 public，HEAD 注入可直接取消。规则关闭时不做任何干预，
 * 保持原版行为。
 */
@Mixin(targets = "net.minecraft.world.entity.monster.illager.Evoker$EvokerSummonSpellGoal")
abstract class EvokerNoVexSummonMixin {
    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void carpetRuneLegendAddition$neverSummonVex(CallbackInfoReturnable<Boolean> cir) {
        if (CarpetRuneLegendSettings.evokerNeverSummonVex) {
            cir.setReturnValue(false);
        }
    }
}
