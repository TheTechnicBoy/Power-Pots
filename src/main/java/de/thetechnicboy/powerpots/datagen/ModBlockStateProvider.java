package de.thetechnicboy.powerpots.datagen;

import de.thetechnicboy.powerpots.PowerPots;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PowerPots.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlock(PowerPots.POWER_POT_MK1.get(), potModel("t1"));
        horizontalBlock(PowerPots.POWER_POT_MK2.get(), potModel("t2"));
        horizontalBlock(PowerPots.POWER_POT_MK3.get(), potModel("t3"));
        horizontalBlock(PowerPots.POWER_POT_MK4.get(), potModel("t4"));

        simpleBlockItem(PowerPots.POWER_POT_MK1.get(), potModel("t1"));
        simpleBlockItem(PowerPots.POWER_POT_MK2.get(), potModel("t2"));
        simpleBlockItem(PowerPots.POWER_POT_MK3.get(), potModel("t3"));
        simpleBlockItem(PowerPots.POWER_POT_MK4.get(), potModel("t4"));
    }

    private ModelFile potModel(String textureName) {
        return models().withExistingParent(textureName, modLoc("block/power-pot"))
                .texture("base", modLoc("block/" + textureName))
                .texture("particle", modLoc("block/" + textureName));
    }
}
