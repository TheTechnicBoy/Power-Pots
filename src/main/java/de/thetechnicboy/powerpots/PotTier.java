package de.thetechnicboy.powerpots;

import net.minecraft.world.level.material.MapColor;

public enum PotTier {
    MK1(Config.POT_MK1, MapColor.COLOR_GREEN),
    MK2(Config.POT_MK2, MapColor.COLOR_YELLOW),
    MK3(Config.POT_MK3, MapColor.COLOR_LIGHT_BLUE),
    MK4(Config.POT_MK4, MapColor.COLOR_PURPLE);

    public final Config.PotMkConfig config;
    public final MapColor color;
    PotTier(Config.PotMkConfig configProvider, MapColor color) {
        this.config = configProvider;
        this.color = color;
    }
}
