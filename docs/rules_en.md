## Rules

### alwaysSweepAttack

Always triggers a sweep attack when an attack with a sword in the main hand succeeds, ignoring attack cooldown, critical-hit checks, sprinting, grounded state, and movement speed limits.

- Type: `boolean`
- Default: `false`
- Suggested values: `false`, `true`
- Categories: `RL`, `FEATURE`

### convenientTotemOfUndying

Totems of Undying can trigger their protection from any slot of a player's inventory, excluding items stored inside shulker boxes or ender chests.

- Type: `boolean`
- Default: `false`
- Suggested values: `false`, `true`
- Categories: `RL`, `FEATURE`

### customBlockingKnockbackStrength

Custom knockback strength applied to the attacker after an item blocks a melee attack.

> `vanilla`: follows vanilla behavior and applies no knockback

- Type: `string`
- Default: `vanilla`
- Suggested values: `vanilla`, `0.5`
- Categories: `RL`, `FEATURE`

### deflectableWitherSkulls

Players can left-click wither skulls of the selected colors to deflect them in the direction they are aiming.

- Type: `enum`
- Default: `off` (vanilla behavior)
- Allowed values: `off`, `all` (both colors), `black` (black only), `blue` (blue only)
- Categories: `RL`, `FEATURE`, `CLIENT`

### enhancedTotemOfUndying

Totems of Undying immediately restore an additional 10 health when triggered.

- Type: `boolean`
- Default: `false`
- Suggested values: `false`, `true`
- Categories: `RL`, `FEATURE`

### evokerNeverSummonVex

Evokers will no longer attempt to summon vexes.

- Type: `boolean`
- Default: `false`
- Suggested values: `false`, `true`
- Categories: `RL`, `FEATURE`

### stackableTotemOfUndying

Allows totems of undying to stack up to 64.

- Type: `boolean`
- Default: `false`
- Suggested values: `false`, `true`
- Categories: `RL`, `FEATURE`

### undeadNotImmuneToPoison

Undead mobs are no longer immune to poison effects and will take normal poison damage.

- Type: `boolean`
- Default: `false`
- Suggested values: `false`, `true`
- Categories: `RL`, `FEATURE`

### witchNeverUseHarmingPotion

Witches will no longer attempt to throw instant damage potions.

- Type: `boolean`
- Default: `false`
- Suggested values: `false`, `true`
- Categories: `RL`, `FEATURE`
