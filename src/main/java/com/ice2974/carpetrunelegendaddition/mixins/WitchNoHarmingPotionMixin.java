package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.settings.CarpetRuneLegendSettings;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionIds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 女巫永不使用瞬伤药水：规则开启时，女巫远程攻击在“实际选中药水”之后、生成投掷物之前
 * 取消本次投掷，因此不会产生溅射药水实体，也不会播放随后的 {@code WITCH_THROW} 投掷音效；
 * 而不是先投出药水再让它失效。
 *
 * <p>26.3 的 {@link Witch#performRangedAttack} 先调用
 * {@code PotionContents.createItemStack(Items.SPLASH_POTION, potion)} 创建药水物品，
 * 再调用 {@code Projectile.spawnProjectileUsingShoot(...)} 生成投掷物，最后才播放
 * {@code SoundEvents.WITCH_THROW}。这里在创建药水物品处记录本次实际选中的药水是否为
 * {@link PotionIds#HARMING}，并在生成投掷物的调用前取消整个方法，因而无需复制原版的
 * 药水选择条件；治疗 / 再生（灾厄村民）、缓慢、中毒、虚弱等其他选择不受影响。
 * 规则关闭时不做任何干预，保持原版行为。
 */
@Mixin(Witch.class)
abstract class WitchNoHarmingPotionMixin {
    @Unique
    private boolean carpetRuneLegendAddition$harmingPotionSelected;

    @ModifyArg(
            method = "performRangedAttack(Lnet/minecraft/world/entity/LivingEntity;F)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/item/alchemy/PotionContents;createItemStack(Lnet/minecraft/world/item/Item;Lnet/minecraft/core/Holder;)Lnet/minecraft/world/item/ItemStack;"),
            index = 1)
    private Holder<Potion> carpetRuneLegendAddition$trackPotion(Holder<Potion> potion) {
        this.carpetRuneLegendAddition$harmingPotionSelected =
                CarpetRuneLegendSettings.witchNeverUseHarmingPotion && potion.is(PotionIds.HARMING);
        return potion;
    }

    @Inject(
            method = "performRangedAttack(Lnet/minecraft/world/entity/LivingEntity;F)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/Projectile;spawnProjectileUsingShoot(Lnet/minecraft/world/entity/projectile/Projectile$ProjectileFactory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;DDDFF)Lnet/minecraft/world/entity/projectile/Projectile;"),
            cancellable = true)
    private void carpetRuneLegendAddition$skipHarmingPotion(LivingEntity target, float velocity, CallbackInfo ci) {
        if (this.carpetRuneLegendAddition$harmingPotionSelected) {
            this.carpetRuneLegendAddition$harmingPotionSelected = false;
            ci.cancel();
        }
    }
}
