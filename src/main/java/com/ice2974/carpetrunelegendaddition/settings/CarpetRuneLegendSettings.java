package com.ice2974.carpetrunelegendaddition.settings;

import carpet.api.settings.Rule;
import carpet.api.settings.RuleCategory;

public final class CarpetRuneLegendSettings {
    /**
     * 本模组专属规则分类（内部名）；显示值由语言文件键
     * {@code carpet.category.RuneLegend} 提供，与 Carpet 内置分类 {@link RuleCategory#FEATURE} 相互独立。
     *
     * <p>内部名不要缩短成 {@code RL} 之类的两字母串：Carpet 的 {@code /carpet list <tag>}
     * 会用 tag 对规则内部名做子串匹配，过短的分类名会把名字里恰好含 {@code rl} 的原版 /
     * Carpet 规则（如 {@code pingPlayerListLimit}、{@code railPowerLimit}）一起列出来。
     */
    public static final String RUNELEGEND = "RuneLegend";

    private CarpetRuneLegendSettings() {
    }

    // 自定义格挡击退强度：vanilla 表示遵循原版行为（不击退），数值为击退强度；
    // 行为实现见 com.ice2974.carpetrunelegendaddition.mixins.LivingEntityBlockingKnockbackMixin。
    @Rule(categories = {RUNELEGEND, RuleCategory.FEATURE},
            options = {CustomBlockingKnockbackStrength.VANILLA, "0.5"},
            strict = false,
            validators = CustomBlockingKnockbackStrength.StrengthValidator.class)
    public static String customBlockingKnockbackStrength = CustomBlockingKnockbackStrength.VANILLA;

    // 持剑攻击成功时无视攻击冷却、暴击、疾跑、着地与移动速度条件触发横扫；
    // 行为实现见 com.ice2974.carpetrunelegendaddition.mixins.PlayerAlwaysSweepAttackMixin。
    @Rule(categories = {RUNELEGEND, RuleCategory.FEATURE})
    public static boolean alwaysSweepAttack = false;
}
