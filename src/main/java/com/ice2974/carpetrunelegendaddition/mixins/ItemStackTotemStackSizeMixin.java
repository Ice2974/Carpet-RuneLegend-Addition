package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.settings.CarpetRuneLegendSettings;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;

/**
 * 可堆叠不死图腾：规则开启时，不死图腾的最大堆叠数为 64；规则关闭或物品不是
 * 不死图腾时保持原版行为。
 *
 * <p>26.3 的 {@link ItemStack} 自身没有声明 {@code getMaxStackSize()}，该方法来自
 * {@link net.minecraft.world.item.ItemInstance} 的默认实现（读取
 * {@code DataComponents.MAX_STACK_SIZE}）。这里向 {@code ItemStack} 合并一个同名具体方法，
 * 覆盖接口默认实现；物品栏合并、{@code /give}、掉落物合并等路径都会经过该虚方法。
 *
 * <p>回退逻辑直接读取 {@code MAX_STACK_SIZE} 组件，避免再次调用
 * {@code getMaxStackSize()} 造成递归。
 */
@Mixin(ItemStack.class)
abstract class ItemStackTotemStackSizeMixin {
    public int getMaxStackSize() {
        ItemStack self = (ItemStack) (Object) this;
        if (CarpetRuneLegendSettings.stackableTotemOfUndying
                && self.is(Items.TOTEM_OF_UNDYING)) {
            return 64;
        }
        return self.getOrDefault(DataComponents.MAX_STACK_SIZE, 1);
    }
}
