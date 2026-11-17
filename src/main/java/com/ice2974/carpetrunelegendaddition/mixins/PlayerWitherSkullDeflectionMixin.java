package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.helpers.WitherSkullDeflection;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
abstract class PlayerWitherSkullDeflectionMixin {
    // WrapOperation instead of Redirect: allows other mods to wrap the same tag check.
    @WrapOperation(
            method = "deflectProjectile(Lnet/minecraft/world/entity/Entity;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;is(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean carpetRuneLegendAddition$allowWitherSkullDeflection(Entity target, TagKey<EntityType<?>> tag,
                                                                         Operation<Boolean> original) {
        return original.call(target, tag) || WitherSkullDeflection.allows(target);
    }
}
