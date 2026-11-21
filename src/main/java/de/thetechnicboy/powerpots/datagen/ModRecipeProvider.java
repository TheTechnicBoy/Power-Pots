
package de.thetechnicboy.powerpots.datagen;

import de.thetechnicboy.powerpots.PowerPots;
import mekanism.common.registries.MekanismItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    private static final ResourceLocation BOTANY_POT = ResourceLocation.parse(
            "botanypots:terracotta_hopper_botany_pot"
    );

    public ModRecipeProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries
    ) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {

        RecipeOutput mekanismOutput = output.withConditions(
                new ModLoadedCondition("mekanism")
        );

        RecipeOutput vanillaOutput = output.withConditions(
                new NotCondition(new ModLoadedCondition("mekanism"))
        );

        addMekanismRecipes(mekanismOutput);
        addVanillaRecipes(vanillaOutput);
    }

    private void addMekanismRecipes(RecipeOutput output) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PowerPots.POWER_POT_MK1.get())
                .pattern("ECE")
                .pattern("CPC")
                .pattern("ECE")
                .define('P', BuiltInRegistries.ITEM.get(BOTANY_POT))
                .define('C', MekanismItems.BASIC_CONTROL_CIRCUIT)
                .define('E', MekanismItems.ENERGY_TABLET)
                .unlockedBy("has_hopper", has(Items.HOPPER))
                .showNotification(false)
                .save(output, PowerPots.genRL("power_pot_mk1_mekanism"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PowerPots.POWER_POT_MK2.get())
                .pattern("ECE")
                .pattern("CPC")
                .pattern("ECE")
                .define('P', PowerPots.POWER_POT_MK1.get())
                .define('C', MekanismItems.ADVANCED_CONTROL_CIRCUIT)
                .define('E', MekanismItems.ENERGY_TABLET)
                .unlockedBy("has_mk1", has(PowerPots.POWER_POT_MK1.get()))
                .showNotification(false)
                .save(output, PowerPots.genRL("power_pot_mk2_mekanism"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PowerPots.POWER_POT_MK3.get())
                .pattern("ECE")
                .pattern("CPC")
                .pattern("ECE")
                .define('P', PowerPots.POWER_POT_MK2.get())
                .define('C', MekanismItems.ELITE_CONTROL_CIRCUIT)
                .define('E', MekanismItems.ENERGY_TABLET)
                .unlockedBy("has_mk2", has(PowerPots.POWER_POT_MK2.get()))
                .showNotification(false)
                .save(output, PowerPots.genRL("power_pot_mk3_mekanism"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PowerPots.POWER_POT_MK4.get())
                .pattern("ECE")
                .pattern("CPC")
                .pattern("ECE")
                .define('P', PowerPots.POWER_POT_MK3.get())
                .define('C', MekanismItems.ULTIMATE_CONTROL_CIRCUIT)
                .define('E', MekanismItems.ENERGY_TABLET)
                .unlockedBy("has_mk3", has(PowerPots.POWER_POT_MK3.get()))
                .showNotification(false)
                .save(output, PowerPots.genRL("power_pot_mk4_mekanism"));
    }

    private void addVanillaRecipes(RecipeOutput output) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PowerPots.POWER_POT_MK1.get())
                .pattern("CRC")
                .pattern("RPR")
                .pattern("CRC")
                .define('P', BuiltInRegistries.ITEM.get(BOTANY_POT))
                .define('C', Items.COPPER_INGOT)
                .define('R', Items.REDSTONE)
                .unlockedBy("has_hopper", has(Items.HOPPER))
                .showNotification(false)
                .save(output, PowerPots.genRL("power_pot_mk1_vanilla"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PowerPots.POWER_POT_MK2.get())
                .pattern("GQG")
                .pattern("QPQ")
                .pattern("GQG")
                .define('P', PowerPots.POWER_POT_MK1.get())
                .define('G', Items.GOLD_INGOT)
                .define('Q', Items.QUARTZ)
                .unlockedBy("has_mk1", has(PowerPots.POWER_POT_MK1.get()))
                .showNotification(false)
                .save(output, PowerPots.genRL("power_pot_mk2_vanilla"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PowerPots.POWER_POT_MK3.get())
                .pattern("DED")
                .pattern("EPE")
                .pattern("DED")
                .define('P', PowerPots.POWER_POT_MK2.get())
                .define('D', Items.DIAMOND)
                .define('E', Items.EMERALD)
                .unlockedBy("has_mk2", has(PowerPots.POWER_POT_MK2.get()))
                .showNotification(false)
                .save(output, PowerPots.genRL("power_pot_mk3_vanilla"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PowerPots.POWER_POT_MK4.get())
                .pattern("NEN")
                .pattern("EPE")
                .pattern("NEN")
                .define('P', PowerPots.POWER_POT_MK3.get())
                .define('N', Items.NETHERITE_INGOT)
                .define('E', Items.ECHO_SHARD)
                .unlockedBy("has_mk3", has(PowerPots.POWER_POT_MK3.get()))
                .showNotification(false)
                .save(output, PowerPots.genRL("power_pot_mk4_vanilla"));
    }
}
