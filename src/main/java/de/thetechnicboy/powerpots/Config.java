package de.thetechnicboy.powerpots;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class Config {

    public static final ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec COMMON_CONFIG;

    public static final PotMkConfig POT_MK1 = new PotMkConfig("mk1", .2, 100000, 500, 16);
    public static final PotMkConfig POT_MK2 = new PotMkConfig("mk2", .4, 200000, 1000, 32);
    public static final PotMkConfig POT_MK3 = new PotMkConfig("mk3", .6, 300000, 1500, 48);
    public static final PotMkConfig POT_MK4 = new PotMkConfig("mk4", .8, 400000, 2000, 64);

    static {
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static class PotMkConfig {
        public final ModConfigSpec.DoubleValue speedModifier;
        public final ModConfigSpec.IntValue maxEnergy;
        public final ModConfigSpec.IntValue perTickEnergy;
        public final ModConfigSpec.IntValue itemsPerOutput;

        public PotMkConfig(String tier, double speed, int energy, int cost, int itemsOut) {
            COMMON_BUILDER.push("tier-" + tier);

            this.speedModifier = COMMON_BUILDER
                    .comment(
                            "Modifies the processing time of taking to process a resource: 0 being no speed modifier and 1 being twice as fast"
                    )
                    .defineInRange("speedModifier", speed, 0.0, 3.0);

            this.maxEnergy = COMMON_BUILDER
                    .comment("How much energy the pot will hold")
                    .defineInRange("maxEnergy", energy, 0, Integer.MAX_VALUE);

            this.perTickEnergy = COMMON_BUILDER
                    .comment("Energy consumed per tick")
                    .defineInRange("perTickEnergy", cost, 0, Integer.MAX_VALUE);

            this.itemsPerOutput = COMMON_BUILDER
                    .comment("Amount of items outputted per operation")
                    .defineInRange("itemsPerOutput", itemsOut, 1, 64 * 12);

            COMMON_BUILDER.pop();
        }
    }
}
