package com.ice2974.carpetrunelegendaddition.helpers;

import com.ice2974.carpetrunelegendaddition.settings.CarpetRuneLegendSettings;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;

public final class WitherSkullDeflection {
    private WitherSkullDeflection() {
    }

    public static boolean allows(Entity target) {
        if (!(target instanceof WitherSkull skull) || !skull.isAlive()) {
            return false;
        }
        return switch (CarpetRuneLegendSettings.deflectableWitherSkulls) {
            case "true" -> true;
            case "black" -> !skull.isDangerous();
            case "blue" -> skull.isDangerous();
            default -> false;
        };
    }
}
