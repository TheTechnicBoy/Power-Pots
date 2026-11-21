package de.thetechnicboy.powerpots.datagen;

import de.thetechnicboy.powerpots.PowerPots;
import mekanism.common.registries.MekanismItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries){
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PowerPots.POWER_POT_MK1.get())
                .pattern("ECE")
                .pattern("CPC")
                .pattern("ECE")
                .define('P', BuiltInRegistries.ITEM.get(ResourceLocation.parse("botanypots:terracotta_hopper_botany_pot")))
                .define('C', MekanismItems.BASIC_CONTROL_CIRCUIT)
                .define('E', MekanismItems.ENERGY_TABLET)
                .unlockedBy("has_well", has(Items.HOPPER))
                .showNotification(false)
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PowerPots.POWER_POT_MK2.get())
                .pattern("ECE")
                .pattern("CPC")
                .pattern("ECE")
                .define('P', PowerPots.POWER_POT_MK1.get())
                .define('C', MekanismItems.ADVANCED_CONTROL_CIRCUIT)
                .define('E', MekanismItems.ENERGY_TABLET)
                .unlockedBy("has_well", has(Items.HOPPER))
                .showNotification(false)
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PowerPots.POWER_POT_MK3.get())
                .pattern("ECE")
                .pattern("CPC")
                .pattern("ECE")
                .define('P', PowerPots.POWER_POT_MK2.get())
                .define('C', MekanismItems.ELITE_CONTROL_CIRCUIT)
                .define('E', MekanismItems.ENERGY_TABLET)
                .unlockedBy("has_well", has(Items.HOPPER))
                .showNotification(false)
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PowerPots.POWER_POT_MK4.get())
                .pattern("ECE")
                .pattern("CPC")
                .pattern("ECE")
                .define('P', PowerPots.POWER_POT_MK3.get())
                .define('C', MekanismItems.ULTIMATE_CONTROL_CIRCUIT)
                .define('E', MekanismItems.ENERGY_TABLET)
                .unlockedBy("has_well", has(Items.HOPPER))
                .showNotification(false)
                .save(output);
    }
}
