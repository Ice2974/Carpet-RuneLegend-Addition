package com.ice2974.carpetrunelegendaddition.mixins;

import carpet.network.CarpetClient;
import com.ice2974.carpetrunelegendaddition.helpers.WitherSkullDeflection;
import com.ice2974.carpetrunelegendaddition.settings.CarpetRuneLegendSettings;
import com.ice2974.carpetrunelegendaddition.settings.WitherSkullDeflectionMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;

@Mixin(LocalPlayer.class)
abstract class LocalPlayerWitherSkullPickMixin {
    @ModifyArg(
            method = "pick(Lnet/minecraft/world/entity/Entity;DDF)Lnet/minecraft/world/phys/HitResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/ProjectileUtil;"
                    + "getEntityHitResult(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;"
                    + "Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;"
                    + "D)Lnet/minecraft/world/phys/EntityHitResult;"),
            index = 4)
    private static Predicate<Entity> carpetRuneLegendAddition$pickWitherSkulls(Predicate<Entity> vanilla) {
        if (CarpetRuneLegendSettings.deflectableWitherSkulls == WitherSkullDeflectionMode.OFF
                || (!Minecraft.getInstance().hasSingleplayerServer() && !CarpetClient.isCarpet())) {
            return vanilla;
        }
        return vanilla.or(WitherSkullDeflection::allows);
    }
}
