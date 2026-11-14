package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.helpers.WitherSkullDeflection;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
abstract class PlayerWitherSkullDeflectionMixin {
    @Redirect(
            method = "deflectProjectile(Lnet/minecraft/world/entity/Entity;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;is(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean carpetRuneLegendAddition$allowWitherSkullDeflection(Entity target, TagKey<EntityType<?>> tag) {
        return target.is(tag) || WitherSkullDeflection.allows(target);
    }
}
