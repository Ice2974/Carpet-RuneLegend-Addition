package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.settings.CarpetRuneLegendSettings;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 禁用饱食度消耗：规则开启时，取消所有通过 {@link FoodData#addExhaustion(float)} 产生的消耗。
 *
 * <p>26.3 中疾跑 / 游泳等移动（{@code ServerPlayer#checkMovementStatistics}）、跳跃
 * （{@code ServerPlayer#jumpFromGround}）、攻击与长矛突刺（{@code Player#attack}、
 * {@code Player#stabAttack}）、受伤（{@code Player#actuallyHurt}）、饥饿效果
 * （{@code HungerMobEffect}）以及自然恢复（{@link FoodData#tick}）最终都会调用该方法，
 * 因此在这一个入口取消即可覆盖全部自然消耗。{@code Player#causeFoodExhaustion} 本身
 * 已跳过客户端与无敌状态，{@code FoodData#tick} 只在服务端执行，所以不需要额外的
 * 客户端同步或规则监听。
 *
 * <p>进食（{@link FoodData#eat}）、存档加载、指令 / NBT 直接修改等不经过该方法，
 * 规则开启时仍会正常改变饱食度。
 */
@Mixin(FoodData.class)
abstract class FoodDataDisableExhaustionMixin {
    @Inject(method = "addExhaustion(F)V", at = @At("HEAD"), cancellable = true)
    private void carpetRuneLegendAddition$disableFoodExhaustion(float exhaustion, CallbackInfo ci) {
        if (CarpetRuneLegendSettings.disableFoodExhaustion) {
            ci.cancel();
        }
    }
}
