package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.helpers.WitherSkullDeflection;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.PiercingWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PiercingWeapon.class)
abstract class PiercingWeaponWitherSkullDeflectionMixin {
    // Only change the left-click stab predicate; the right-click charge also uses canHitEntity.
    // WrapOperation instead of Redirect: allows other mods to wrap the same call.
    @WrapOperation(
            method = "lambda$attack$0(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/PiercingWeapon;"
                    + "canHitEntity(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;)Z"))
    private static boolean carpetRuneLegendAddition$allowWitherSkullStab(Entity attacker, Entity target,
                                                                         Operation<Boolean> original) {
        return original.call(attacker, target)
                || (attacker instanceof Player player && !player.isSpectator()
                    && WitherSkullDeflection.allows(target) && !target.isInvulnerableToPiercingWeapon()
                    && !attacker.isPassengerOfSameVehicle(target));
    }
}
