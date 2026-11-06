package com.ice2974.carpetrunelegendaddition.settings;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.Validator;
import net.minecraft.commands.CommandSourceStack;

/**
 * {@code customBlockingKnockbackStrength} 规则的取值定义、解析与校验。
 *
 * <p>取值允许 {@code vanilla}（遵循原版行为，不击退）或非负浮点数（原版击退强度参数，
 * 数值越大击退越远）。非法取值不会写入规则字段。
 */
public final class CustomBlockingKnockbackStrength {
    /** 遵循原版行为（不击退）。 */
    public static final String VANILLA = "vanilla";

    private CustomBlockingKnockbackStrength() {
    }

    /** 是否为 vanilla（大小写不敏感；null 或空白串按 vanilla 处理）。 */
    public static boolean isVanilla(String value) {
        return value == null || value.isBlank() || VANILLA.equalsIgnoreCase(value);
    }

    /**
     * 解析击退强度。
     *
     * @return 非负浮点强度；vanilla、非法值或非有限值返回 {@link Double#NaN}
     */
    public static double strengthOf(String value) {
        if (isVanilla(value)) {
            return Double.NaN;
        }
        try {
            double strength = Double.parseDouble(value);
            return Double.isFinite(strength) && strength >= 0.0D ? strength : Double.NaN;
        } catch (NumberFormatException ignored) {
            return Double.NaN;
        }
    }

    /** Carpet 原生校验器：只接受 {@code vanilla} 或非负浮点数。 */
    public static final class StrengthValidator extends Validator<String> {
        @Override
        public String validate(CommandSourceStack source, CarpetRule<String> rule, String value, String userInput) {
            if (isVanilla(value)) {
                return VANILLA;
            }
            return Double.isNaN(strengthOf(value)) ? null : value;
        }

        @Override
        public String description() {
            return "Must be 'vanilla' or a non-negative number";
        }
    }
}
