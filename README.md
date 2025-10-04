CustomApples

A customizable apple plugin for **PaperMC 1.21** that allows players to consume special apples with unique potion effects, cooldowns, and visual effects. Configurable through YAML files with support for admin commands and dynamic messaging.

> **Why this plugin?** CustomApples lets you create unique apple items with tailored effects, rarities, and cooldowns, enhancing gameplay with rewarding mechanics. Perfect for survival or RPG servers looking to add exciting consumables!

## ✨ Features

- **Customizable Apples**: Define apples in `apples.yml` with specific potion effects, cooldowns, rarities, and broadcast messages.
- **Potion Effects**: Apply multiple potion effects (e.g., Speed, Strength) when an apple is consumed.
- **Cooldown System**: Prevent spamming with configurable per-apple cooldowns, managed via a thread-safe system.
- **Visual Effects**: Trigger a visual lightning effect on consumption (no damage to players or environment).
- **Broadcast Messages**: Announce apple consumption server-wide (toggleable per apple).
- **Admin Commands**: Includes commands to give apples to players and reload configurations.
- **Configurable Messages**: Customize all plugin messages in `messages.yml` with support for placeholders.
- **Extensible**: Modular code structure with managers and listeners for easy customization.

## 📦 Installation

1. **Download**: Grab the latest JAR from Releases or build from source (see below).
2. **Server Setup**: Ensure you're running **PaperMC 1.21** (or compatible Spigot fork).
3. **Place the JAR**: Drop `CustomApples-1.0.0.jar` into your `plugins/` folder.
4. **Restart**: Start/restart your server. The plugin will auto-generate `apples.yml` and `messages.yml`.
5. **Configure**: Edit the generated YAML files in `plugins/CustomApples/` to customize apples and messages.

> **Dependencies**: None required. Uses Midnight framework (bundled).

## ⚙️ Configuration

Edit `plugins/CustomApples/apples.yml` to define custom apples. Here's a sample:

```yaml
apples:
  strength_apple:
    item:
      type: APPLE
      name: "&cStrength Apple"
    effects:
      - type: INCREASE_DAMAGE
        level: 2
        duration-seconds: 30
    broadcast: true
    cooldown-seconds: 60
    rarity: RARE
```

## 📜 Commands

| Command | Permission | Description |
| --- | --- | --- |
| `/customapples reload` | `customapples.admin.reload` | Reloads the plugin's configurations. |
| `/customapples give <player> <apple> [amount]` | `customapples.admin.give` | Gives a specified apple to a player. |
| `/customapples info` | `customapples.admin.info` | Displays plugin name, version, and author. |

## 🔧 Building from Source

1. Clone the repository: `git clone https://github.com/imsnaily/apples-minecraft.git`
2. Navigate to the project: `cd customapples`
3. Build with Gradle: `./gradle build`
4. Find the JAR in `build/libs/CustomApples-1.0.0.jar`

## 📝 Notes

- **Version**: Ensure compatibility with PaperMC 1.21. Check for updates if using newer versions.
- **Customization**: Modify `apples.yml` to add new apple types with unique effects and properties.
- **Support**: Report issues or suggest features at GitHub Issues.
