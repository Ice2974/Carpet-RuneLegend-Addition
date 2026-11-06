package com.ice2974.carpetrunelegendaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.utils.Translations;
import com.ice2974.carpetrunelegendaddition.settings.CarpetRuneLegendSettings;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.util.Map;

public final class CarpetRuneLegendAdditionMod implements ModInitializer, CarpetExtension {
    public static final String MOD_ID = "carpet-runelegend-addition";
    public static final String MOD_NAME = "Carpet RuneLegend Addition";

    private static final CarpetRuneLegendAdditionMod INSTANCE = new CarpetRuneLegendAdditionMod();
    private static String version = "unknown";

    @Override
    public void onInitialize() {
        version = FabricLoader.getInstance()
                .getModContainer(MOD_ID)
                .map(c -> c.getMetadata().getVersion().getFriendlyString())
                .orElse("unknown");
        CarpetServer.manageExtension(INSTANCE);
    }

    @Override
    public void onGameStarted() {
        // Register rules in Carpet's native settings manager so /carpet <rule> works.
        CarpetServer.settingsManager.parseSettingsClass(CarpetRuneLegendSettings.class);
    }

    @Override
    public String version() {
        return version;
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        // The lang JSON files are the single source of truth; Carpet merges these maps with putIfAbsent.
        return Translations.getTranslationFromResourcePath("assets/" + MOD_ID + "/lang/" + lang + ".json");
    }
}
