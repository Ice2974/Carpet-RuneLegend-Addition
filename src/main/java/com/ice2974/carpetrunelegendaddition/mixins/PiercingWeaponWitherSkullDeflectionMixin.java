package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.helpers.WitherSkullDeflection;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Either;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.item.component.PiercingWeapon;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * 可反弹凋灵头（长矛突刺部分）：规则开启时，玩家左键长矛突刺可以将符合条件的凋灵头
 * 选为突刺目标（进而由 {@code Player#stabAttack} 中的
 * {@code deflectProjectile} 完成反弹）；右键蓄力路径不受影响。
 *
 * <p>注入点选择 {@code PiercingWeapon#attack} 对
 * {@code ProjectileUtil.getHitEntitiesAlong(entity, attackRange, predicate, block)} 的调用：
 * 原版把命中谓词 {@code lambda$attack$0}（即 {@code canHitEntity(attacker, target)}）作为该调用的
 * 参数传入。这里用 {@code @WrapOperation} 包装这个稳定公共方法调用，并把谓词替换为
 * “原版谓词 OR 凋灵头扩展条件”。相比直接注入编译器生成的
 * {@code lambda$attack$0}（其编号随原版源码中 lambda 声明顺序变化，小版本更新即可能漂移），
 * 目标方法与参数类型都是稳定 API，版本更新更安全；{@code @WrapOperation} 也允许多个模组
 * 依次包装同一调用点。
 */
@Mixin(PiercingWeapon.class)
abstract class PiercingWeaponWitherSkullDeflectionMixin {
    @WrapOperation(
            method = "attack(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/ProjectileUtil;"
                            + "getHitEntitiesAlong(Lnet/minecraft/world/entity/Entity;"
                            + "Lnet/minecraft/world/item/component/AttackRange;Ljava/util/function/Predicate;"
                            + "Lnet/minecraft/world/level/ClipContext$Block;)Lcom/mojang/datafixers/util/Either;"))
    private static Either<BlockHitResult, Collection<EntityHitResult>> carpetRuneLegendAddition$allowWitherSkullStab(
            Entity attacker, AttackRange attackRange, Predicate<Entity> hitPredicate, ClipContext.Block block,
            Operation<Either<BlockHitResult, Collection<EntityHitResult>>> original) {
        return original.call(attacker, attackRange,
                hitPredicate.or(target -> attacker instanceof Player player && !player.isSpectator()
                        && WitherSkullDeflection.allows(target)
                        && !target.isInvulnerableToPiercingWeapon()
                        && !attacker.isPassengerOfSameVehicle(target)),
                block);
    }
}
