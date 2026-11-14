package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.helpers.WitherSkullDeflection;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.PiercingWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PiercingWeapon.class)
abstract class PiercingWeaponWitherSkullDeflectionMixin {
    // Only change the left-click stab predicate; the right-click charge also uses canHitEntity.
    @Redirect(
            method = "lambda$attack$0(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/PiercingWeapon;"
                    + "canHitEntity(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;)Z"))
    private static boolean carpetRuneLegendAddition$allowWitherSkullStab(Entity attacker, Entity target) {
        return PiercingWeapon.canHitEntity(attacker, target)
                || (attacker instanceof Player player && !player.isSpectator()
                    && WitherSkullDeflection.allows(target) && !target.isInvulnerableToPiercingWeapon()
                    && !attacker.isPassengerOfSameVehicle(target));
    }
}
