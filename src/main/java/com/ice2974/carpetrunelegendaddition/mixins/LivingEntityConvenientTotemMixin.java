package com.ice2974.carpetrunelegendaddition.mixins;

import com.ice2974.carpetrunelegendaddition.settings.CarpetRuneLegendSettings;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 便捷不死图腾：规则开启时，玩家的不死图腾即使不在主手或副手，只要位于背包的任意顶层栏位，
 * 也能触发原版的死亡保护。
 *
 * <p>26.3 的 {@code LivingEntity#checkTotemDeathProtection} 依次检查主手与副手：
 * 通过 {@code getItemInHand(hand)} 取出物品，若带有
 * {@code DataComponents.DEATH_PROTECTION} 组件，则复制该物品、消耗 1 个，并在随后
 * 处理统计、进度、振动、生命值设为 1 点、死亡保护效果与实体事件 35。这里重定向该方法内
 * 唯一的 {@code getItemInHand} 调用：主手没有死亡保护物品时，先保持原版“副手优先”，
 * 再按槽位顺序查找玩家 {@link Inventory} 顶层栏位中带死亡保护组件的不死图腾，并把真实
 * 物品栈交给原版。后续复制、消耗与效果完全沿用原版逻辑，因此与
 * {@code stackableTotemOfUndying} 等规则兼容，堆叠图腾也只消耗 1 个。
 *
 * <p>只遍历 {@code Inventory} 的顶层槽位，不读取潜影盒等物品的 {@code CONTAINER}
 * 组件，也不访问末影箱容器，因此背包内潜影盒与末影箱中的物品不会触发。规则关闭、
 * 非玩家实体、主手或副手已有死亡保护物品时不做任何干预，保持原版行为。
 */
@Mixin(LivingEntity.class)
abstract class LivingEntityConvenientTotemMixin {
    @Redirect(
            method = "checkTotemDeathProtection(Lnet/minecraft/world/damagesource/DamageSource;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack carpetRuneLegendAddition$useInventoryTotem(LivingEntity entity, InteractionHand hand) {
        ItemStack held = entity.getItemInHand(hand);
        if (!CarpetRuneLegendSettings.convenientTotemOfUndying
                || hand != InteractionHand.MAIN_HAND
                || !(entity instanceof Player player)) {
            return held;
        }
        // 保持原版优先级：主手或副手已有死亡保护物品时，交由原版逻辑处理。
        if (held.has(DataComponents.DEATH_PROTECTION)
                || player.getOffhandItem().has(DataComponents.DEATH_PROTECTION)) {
            return held;
        }
        Inventory inventory = player.getInventory();
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            if (slot == Inventory.SLOT_OFFHAND) {
                // 副手由原版下一轮循环处理。
                continue;
            }
            ItemStack stack = inventory.getItem(slot);
            if (stack.is(Items.TOTEM_OF_UNDYING) && stack.has(DataComponents.DEATH_PROTECTION)) {
                // 返回真实槽位物品栈；原版会复制后消耗 1 个并完成其余死亡保护逻辑。
                return stack;
            }
        }
        return held;
    }
}
