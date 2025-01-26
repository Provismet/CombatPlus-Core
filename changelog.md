Minor update to add the groundwork for a shield-related features.


## Additions
- Added new class for easily executing callbacks from an item.
- Added new interface, BlockingItem.
  - Items with the ability to block attacks should implement this interface.
  - It contains methods that allow the item to configure the nature of its blocking.
    - Cooldown duration when disabled.
    - How many ticks the item must be used for until it is actively blocking attacks.
    - Callbacks
      - Damaging the shield.
      - Post-block actions.
- Added enchantment callback for post-block actions.
- Added enchantment context (for conditions) for post-block actions.
  - In this context, the target is the owner of the enchanted item, the one who blocked the attack.
- Added debug shield item and debug blocking enchantment for developers.
- **[LilyLib]** Enchantment descriptions now also generate a `.description` key as well as the existing `.desc` key.