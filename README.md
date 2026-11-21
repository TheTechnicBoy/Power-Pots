# Power Pots

Power Pots is a small Minecraft mod that adds **powered versions of Botany Pots**.
These powered pots use energy (FE/RF) to grow crops faster and to yield more output.

This is a port / fork of [FTB Power Pots](https://www.curseforge.com/minecraft/mc-mods/ftb-power-pots) by [FTB](https://www.curseforge.com/members/ftb/projects).

## Features

* Adds **4 tiers** of Power Pots (MK1 to MK4). Each tier grows faster, holds more energy and gives more output per harvest, but also uses more energy.
* Fully compatible with **Botany Pots** crops, soils and recipes. Anything that grows in a Botany Pot also grows in a Power Pot.
* Accepts energy (FE/RF) from any energy source, such as cables or generators from other mods.
* Items can be taken out with hoppers or pipes, just like with a Botany Hopper Pot.
* All values are configurable per tier.
* Own creative tab.

## Tiers

The default values are listed below. You can change all of them in the config.

| Tier | Speed bonus | Energy capacity | Energy per tick | Output multiplier |
|---|---|---|---|---|
| MK1 | +20 % | 100,000 FE | 500 FE | x16 |
| MK2 | +40 % | 200,000 FE | 1,000 FE | x32 |
| MK3 | +60 % | 300,000 FE | 1,500 FE | x48 |
| MK4 | +80 % | 400,000 FE | 2,000 FE | x64 |

The pot only uses energy while a crop is planted on a soil. The output multiplier means that each harvest rolls the crop's drops that many times.

## Crafting

Each pot is an upgrade of the one before it. MK1 is crafted from a **Terracotta Hopper Botany Pot**.

* **Without Mekanism:** the recipes use vanilla items (copper, redstone, gold, quartz, diamonds, emeralds, netherite and echo shards).
* **With Mekanism:** the recipes use Mekanism energy tablets and control circuits instead.

## Requirements

* Minecraft 1.21.1 & NeoForge
* [Botany Pots](https://modrinth.com/mod/botany-pots)
* [Bookshelf](https://modrinth.com/mod/bookshelf-lib)
* [Mekanism](https://modrinth.com/mod/mekanism) (optional, only for the alternative recipes)

## Configuration

The config file is `powerpots-common.toml` in the `config` folder. Each tier (`tier-mk1` to `tier-mk4`) has these options:

| Option | Description |
|---|---|
| `speedModifier` | Growth speed bonus. `0` is no bonus, `1` is twice as fast. |
| `maxEnergy` | How much energy the pot can hold. |
| `perTickEnergy` | Energy used per tick. |
| `itemsPerOutput` | How many times the drops are rolled per harvest. |

## License

All Rights Reserved.

## Credits

* Port / fork of [FTB Power Pots](https://www.curseforge.com/minecraft/mc-mods/ftb-power-pots) by [FTB](https://www.curseforge.com/members/ftb/projects)
* Author of this port: TheTechnicBoy