# Dream-Mission System Plugin

This is a configurable mission system for Minecraft, allowing players to complete various tasks and receive rewards.

## Plugin made for [DreamCode](https://www.youtube.com/@Keymilo)

## Features
- **Custom Missions**: Define missions such as running a distance, catching fish, or writing messages.
- **Progress Tracking**: Players can see their progress and time remaining until mission reset.
- **Configurable Resets**: Missions reset after a configurable time set in the config.
- **Custom Commands on Completion**: Execute a specific command when a player completes a mission.
- **Manual Reset Command**: Admins can manually reset missions using a dedicated command.
- **Reward System**: Players receive in-game currency (`100 coins` by default) upon completion.
- **Flexible Filtering**: Supports filtering by specific items (e.g., only COD and SALMON for fishing missions).
- **Custom gui**: Custom GUI for missions with full configuration and customization options.
- **Mission Types**:
    - `RUNNING_DISTANCE` – Run a specified distance.
    - `CAUGHT_ITEMS` – Catch specific items (e.g., fish).
    - `WRITTEN_MESSAGE` – Send a specified number of messages.
    - `KILLED_MOBS` – Kill a specified number of mobs.
    - `BLOCKS_PLACED` – Place a specified number of blocks.
    - `MINED_BLOCKS` – Mine a specified number of blocks.
    - `EATEN_FOOD` – Eat a specified number of food items.

## Watch demo video

https://github.com/user-attachments/assets/6f29c0fd-037f-4850-83f5-c50131f7f061



## Example Configuration
```yaml
missions:
  1:
    id: 1
    name: Eat 10 apples
    type: EATEN_ITEMS
    goal-amount: 10
    goal-command: REWARD
    reset-time: PT24H
    filter-mode: SPECIFIC
    filter-materials:
      - APPLE
    icon:
      icon:
        legacy: true
        v: 3955
        type: APPLE
        meta:
          ==: ItemMeta
          meta-type: UNSPECIFIC
          display-name: '{"text":"","extra":["&7Eat 10 apples"]}'
          lore:
            - '""'
            - '{"text":"","extra":["&8» &7Status: {status}"]}'
            - '{"text":"","extra":["&8» &7Progress: &e{percentage}%"]}'
            - '{"text":"","extra":["&8» &7Mission resets in: &e{time}"]}'
            - '{"text":"","extra":["&8» &7Reward: &e100 coins"]}'
      slot: 16
```
------

Working with template:
-----
Take a loot at [Dream-Otchlan](https://github.com/DreamPoland/dream-otchlan), example project based on platform.

**Required:**
-----
- Spigot-api 1.8.8 with NMS support.
- JDK 21, target 1.8 for compatibility.
------

**Libraries**: <33
- [Dream-Platform](https://github.com/DreamPoland/dream-platform) by [DreamCode](https://github.com/DreamPoland)
- [Dream-Menu](https://github.com/DreamPoland/dream-menu) by [DreamCode](https://github.com/DreamPoland)
- [Dream-Command](https://github.com/DreamPoland/dream-command) by [DreamCode](https://github.com/DreamPoland)
- [Dream-Notice](https://github.com/DreamPoland/dream-notice) by [DreamCode](https://github.com/DreamPoland)
- [Dream-Utilities](https://github.com/DreamPoland/dream-utilities) by [DreamCode](https://github.com/DreamPoland)
- [Okaeri-Configs](https://github.com/OkaeriPoland/okaeri-configs) by [Okaeri](https://github.com/OkaeriPoland)
- [Okaeri-Persistence](https://github.com/OkaeriPoland/okaeri-persistence) by [Okaeri](https://github.com/OkaeriPoland)
- [Okaeri-Injector](https://github.com/OkaeriPoland/okaeri-injector) by [Okaeri](https://github.com/OkaeriPoland)
- [Okaeri-Placeholders](https://github.com/OkaeriPoland/okaeri-placeholders) by [Okaeri](https://github.com/OkaeriPoland)
- [Okaeri-Tasker](https://github.com/OkaeriPoland/okaeri-tasker) by [Okaeri](https://github.com/OkaeriPoland)
- [XSeries](https://github.com/CryptoMorin/XSeries) by [CryptoMorin](https://github.com/CryptoMorin)
- and [Lombok](https://github.com/projectlombok/lombok) for clean dev by [ProjectLombok](https://github.com/projectlombok)
