## 规则

### 始终触发横扫（alwaysSweepAttack）

主手持有剑时，攻击成功必定触发横扫攻击，无视攻击冷却、暴击判定、疾跑状态、着地状态与移动速度限制。

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

### 唤魔者永不召唤恼鬼（evokerNeverSummonVex）

唤魔者永不尝试召唤恼鬼，不会进入召唤施法前摇或播放准备音效；尖牙等其他法术不受影响。

- 类型: `boolean`
- 默认值: `false`
- 可选值: `false`, `true`
- 分类：`RL`, `FEATURE`

### 女巫永不使用瞬伤药水（witchNeverUseHarmingPotion）

女巫不再尝试投掷瞬伤药水：原版会选择瞬伤药水的场合不再投掷药水，治疗、再生、缓慢、中毒、虚弱等其他药水选择不受影响。

- 类型: `boolean`
- 默认值: `false`
- 可选值: `false`, `true`
- 分类：`RL`, `FEATURE`
