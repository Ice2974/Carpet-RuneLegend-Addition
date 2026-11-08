## Rules

### alwaysSweepAttack

Always triggers a sweep attack when an attack with a sword in the main hand succeeds, ignoring attack cooldown, critical-hit checks, sprinting, grounded state, and movement speed limits.

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

### evokerNeverSummonVex

Evokers never attempt to summon vexes: they never start the summoning spell (no cast warm-up or prepare sound); other spells such as evoker fangs are unaffected.

- Type: `boolean`
- Default: `false`
- Suggested values: `false`, `true`
- Categories: `RL`, `FEATURE`

### witchNeverUseHarmingPotion

Witches never attempt to use potions of harming: when vanilla would choose a harming potion they skip the throw entirely; other potion choices are unaffected.

- Type: `boolean`
- Default: `false`
- Suggested values: `false`, `true`
- Categories: `RL`, `FEATURE`
