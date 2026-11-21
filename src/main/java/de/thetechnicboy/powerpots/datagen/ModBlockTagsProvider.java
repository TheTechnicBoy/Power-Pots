package de.thetechnicboy.powerpots.datagen;

import de.thetechnicboy.powerpots.PowerPots;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public static final TagKey<Block> POWER_POTS = BlockTags.create(PowerPots.genRL("power_pots"));

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PowerPots.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(POWER_POTS).add(PowerPots.POWER_POT_MK1.get());
        tag(POWER_POTS).add(PowerPots.POWER_POT_MK2.get());
        tag(POWER_POTS).add(PowerPots.POWER_POT_MK3.get());
        tag(POWER_POTS).add(PowerPots.POWER_POT_MK4.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(POWER_POTS);
    }
}
