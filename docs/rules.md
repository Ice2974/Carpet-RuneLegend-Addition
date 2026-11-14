## 规则

### 始终触发横扫（alwaysSweepAttack）

主手持有剑时，攻击成功必定触发横扫攻击，无视攻击冷却、暴击判定、疾跑状态、着地状态与移动速度限制。

- 类型: `boolean`
- 默认值: `false`
- 可选值: `false`, `true`
- 分类：`RL`, `FEATURE`

### 便捷不死图腾（convenientTotemOfUndying）

不死图腾在玩家背包的任意位置均可触发保护效果，潜影盒与末影箱中的物品除外。

- 类型: `boolean`
- 默认值: `false`
- 可选值: `false`, `true`
- 分类：`RL`, `FEATURE`

### 自定义格挡击退强度（customBlockingKnockbackStrength）

自定义物品格挡近战攻击后对攻击者的击退强度。

> `vanilla`: 遵循原版行为，不产生击退

- 类型: `string`
- 默认值: `vanilla`
- 建议值: `vanilla`, `0.5`
- 分类：`RL`, `FEATURE`

### 可反弹凋灵头（deflectableWitherSkulls）

玩家可以左键将指定颜色的凋灵头沿瞄准方向打回去。

- 类型: `string`
- 默认值: `false`（保持原版行为）
- 可选值: `false`、`true`（两种颜色）、`black`（仅黑色）、`blue`（仅蓝色）

若世界的 `carpet.conf` 仍使用旧值 `off` 或 `all`，请分别改为 `false` 或 `true`。
- 分类：`RL`, `FEATURE`, `CLIENT`

### 强化不死图腾（enhancedTotemOfUndying）

不死图腾触发时立刻额外恢复 10 点生命值。

- 类型: `boolean`
- 默认值: `false`
- 可选值: `false`, `true`
- 分类：`RL`, `FEATURE`

### 唤魔者永不召唤恼鬼（evokerNeverSummonVex）

唤魔者不会再尝试召唤恼鬼。

- 类型: `boolean`
- 默认值: `false`
- 可选值: `false`, `true`
- 分类：`RL`, `FEATURE`

### 可堆叠不死图腾（stackableTotemOfUndying）

允许不死图腾物品堆叠到64个。

- 类型: `boolean`
- 默认值: `false`
- 可选值: `false`, `true`
- 分类：`RL`, `FEATURE`

### 亡灵生物不免疫中毒（undeadNotImmuneToPoison）

亡灵生物不再免疫中毒效果，会正常受到中毒伤害。

- 类型: `boolean`
- 默认值: `false`
- 可选值: `false`, `true`
- 分类：`RL`, `FEATURE`

### 女巫永不使用瞬伤药水（witchNeverUseHarmingPotion）

女巫不会再尝试投掷瞬间伤害药水。

- 类型: `boolean`
- 默认值: `false`
- 可选值: `false`, `true`
- 分类：`RL`, `FEATURE`
