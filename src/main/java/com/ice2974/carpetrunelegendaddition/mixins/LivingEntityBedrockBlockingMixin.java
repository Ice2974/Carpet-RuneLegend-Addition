package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.settings.CarpetRuneLegendSettings;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 基岩版格挡：物品成功格挡近战攻击后，把攻击者朝远离格挡者的方向击退。
 *
 * <p>注入 {@link LivingEntity#applyItemBlocking} 的返回点：该方法的返回值是本次
 * 实际被格挡掉的伤害量，大于 0 即表示格挡成功。26.3 中只有带
 * {@code minecraft:blocks_attacks} 组件的物品会走这条格挡路径，因此这里不额外
 * 限定物品或实体类型。弹射物被格挡时不触发击退。
 */
@Mixin(LivingEntity.class)
abstract class LivingEntityBedrockBlockingMixin {
    /**
     * 击退强度。{@code LivingEntity#knockback} 会先按 1 - 击退抗性 缩放；
     * 经专用服务器实测，该值在地面静止、无击退抗性的目标上约等于 1 个方块的位移。
     */
    private static final double KNOCKBACK_STRENGTH = 0.09D;

    @Inject(method = "applyItemBlocking(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)F",
            at = @At("RETURN"))
    private void carpetRuneLegendAddition$bedrockBlocking(ServerLevel level, DamageSource source, float amount,
                                                           CallbackInfoReturnable<Float> cir) {
        if (!CarpetRuneLegendSettings.bedrockBlocking) {
            return;
        }
        if (cir.getReturnValue() <= 0.0F) {
            return;
        }
        if (source.is(DamageTypeTags.IS_PROJECTILE)) {
            return;
        }
        if (source.is(DamageTypeTags.NO_KNOCKBACK)) {
            return;
        }
        if (!(source.getDirectEntity() instanceof LivingEntity attacker)) {
            return;
        }
        LivingEntity blocker = (LivingEntity) (Object) this;
        if (attacker == blocker) {
            return;
        }
        // knockback 会沿 (x, z) 的反方向施加冲量：传「格挡者 - 攻击者」，攻击者被推离格挡者。
        attacker.knockback(KNOCKBACK_STRENGTH,
                blocker.getX() - attacker.getX(),
                blocker.getZ() - attacker.getZ(),
                source, amount);
    }
}
