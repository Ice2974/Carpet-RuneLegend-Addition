package com.ice2974.carpetrunelegendaddition.settings;

import carpet.api.settings.Rule;
import carpet.api.settings.RuleCategory;

public final class CarpetRuneLegendSettings {
    /**
     * 本模组专属规则分类（内部名）；显示值由语言文件键
     * {@code carpet.category.RL} 提供，与 Carpet 内置分类 {@link RuleCategory#FEATURE} 相互独立。
     */
    public static final String RL = "RL";

    private CarpetRuneLegendSettings() {
    }

    // 基岩版格挡：开启后物品成功格挡近战攻击时击退攻击者；行为实现见
    // com.ice2974.carpetrunelegendaddition.mixins.LivingEntityBedrockBlockingMixin。
    @Rule(categories = {RL, RuleCategory.FEATURE})
    public static boolean bedrockBlocking = false;
}
