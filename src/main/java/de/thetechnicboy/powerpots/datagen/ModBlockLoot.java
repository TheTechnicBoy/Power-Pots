package de.thetechnicboy.powerpots.datagen;

import de.thetechnicboy.powerpots.PowerPots;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLoot extends BlockLootSubProvider {

    public ModBlockLoot(HolderLookup.Provider  provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {

        dropSelf(PowerPots.POWER_POT_MK1.get());
        dropSelf(PowerPots.POWER_POT_MK2.get());
        dropSelf(PowerPots.POWER_POT_MK3.get());
        dropSelf(PowerPots.POWER_POT_MK4.get());

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return PowerPots.BLOCKS.getEntries().stream()
                .map(holder -> (Block) holder.get())
                .toList();
    }
}
