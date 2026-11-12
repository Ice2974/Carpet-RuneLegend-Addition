package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.settings.CarpetRuneLegendSettings;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 亡灵生物不免疫中毒：规则开启时，位于 {@code #minecraft:undead} 的实体可以正常获得
 * {@link MobEffects#POISON} 效果，之后由原版 {@code PoisonMobEffect} 在 tick 中正常造成魔法伤害。
 *
 * <p>26.3 的 {@link LivingEntity#canBeAffected} 使用
 * {@code EntityTypeTags.IGNORES_POISON_AND_REGEN}（内容为 {@code #minecraft:undead}）
 * 拒绝中毒与生命恢复；这里只对中毒放行，生命恢复仍保持原版免疫。
 * 女巫投掷、溅射 / 滞留药水、药箭、命令等入口最终都调用
 * {@code LivingEntity#addEffect}，因此共用本注入；规则关闭时不做任何干预，
 * 保持原版行为。
 */
@Mixin(LivingEntity.class)
abstract class LivingEntityPoisonImmunityMixin {
    @Inject(
            method = "canBeAffected(Lnet/minecraft/world/effect/MobEffectInstance;)Z",
            at = @At("HEAD"),
            cancellable = true)
    private void carpetRuneLegendAddition$allowPoisonOnUndead(MobEffectInstance effect,
                                                              CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (CarpetRuneLegendSettings.undeadNotImmuneToPoison
                && effect.is(MobEffects.POISON)
                && self.is(EntityTypeTags.UNDEAD)) {
            cir.setReturnValue(true);
        }
    }
}
