package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.settings.CarpetRuneLegendSettings;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 强化不死图腾：规则开启时，不死图腾（带 {@code minecraft:death_protection} 组件的物品）
 * 触发后，在原版将生命值设为 1 点并应用死亡保护效果之后，额外恢复 10 点生命值。
 *
 * <p>注入 {@code LivingEntity#checkTotemDeathProtection} 的返回点：原版在该方法中
 * 消耗物品、将生命值设为 1 点并应用 {@code DeathProtection} 效果。返回 {@code true}
 * 表示本次确实消耗了死亡保护物品；此时调用 {@link LivingEntity#heal(float)} 恢复
 * 10 点生命值，20 点生命上限的实体最终为 11 点，恢复量受最大生命值限制。伤害绕过
 * 无敌（如虚空伤害）时原版直接返回 {@code false}，本规则不介入；规则关闭时行为与
 * 原版完全一致。
 */
@Mixin(LivingEntity.class)
abstract class LivingEntityEnhancedTotemMixin {
    @Inject(
            method = "checkTotemDeathProtection(Lnet/minecraft/world/damagesource/DamageSource;)Z",
            at = @At("RETURN"))
    private void carpetRuneLegendAddition$healAfterTotem(DamageSource source,
                                                         CallbackInfoReturnable<Boolean> cir) {
        if (!CarpetRuneLegendSettings.enhancedTotemOfUndying || !cir.getReturnValueZ()) {
            return;
        }
        ((LivingEntity) (Object) this).heal(10.0F);
    }
}
