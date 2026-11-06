package com.ice2974.carpetrunelegendaddition.settings;

import carpet.api.settings.Rule;

public final class CarpetRuneLegendSettings {
    public static final String RL_FEATURE = "RL_FEATURE";

    private CarpetRuneLegendSettings() {
    }

    // Placeholder rule: registered only, no behaviour reads this value.
    @Rule(categories = {RL_FEATURE})
    public static boolean bedrockBlocking = false;
}
