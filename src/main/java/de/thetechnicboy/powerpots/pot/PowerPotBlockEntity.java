package de.thetechnicboy.powerpots.pot;

import de.thetechnicboy.powerpots.PotTier;
import net.darkhax.bookshelf.common.api.service.Services;
import net.darkhax.botanypots.common.impl.Helpers;
import net.darkhax.botanypots.common.impl.block.entity.BotanyPotBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PowerPotBlockEntity extends BotanyPotBlockEntity {

    protected final PotTier tier;
    protected final PowerPotEnergyStorage energyStorage;

    public PowerPotBlockEntity(Supplier<? extends BlockEntityType<?>> type, BlockPos pos, BlockState state, PotTier tier) {
        super((Supplier) type, pos, state);
        this.tier = tier;
        this.energyStorage = new PowerPotEnergyStorage(tier.config.maxEnergy.get(), tier.config.maxEnergy.get(), tier.config.maxEnergy.get());
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Energy", energyStorage.getEnergyStored());
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        energyStorage.setEnergy(tag.getInt("Energy"));
    }

    public IEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PowerPotBlockEntity pot) {
        if (pot.isRemoved() || level.isClientSide()) return;

        if(pot.getOrInvalidateCrop() == null || pot.getOrInvalidateSoil() == null) return;

        int cost = pot.tier.config.perTickEnergy.get();
        if (pot.energyStorage.getEnergyStored() < cost) return;

        pot.energyStorage.extractEnergy(cost, false);

        float beforeGrowth = pot.growthTime();
        int required = pot.getRequiredGrowthTicks();
        float threshold = 10f;

        double speed = pot.tier.config.speedModifier.get();
        float ticksToApply = (float) (1f + speed);

        for (int i = 0; i < Math.floor(ticksToApply); i++)
            tickOrHarvest(level, pos, state, pot, required, threshold);

        if (Math.random() < (ticksToApply % 1f))
            tickOrHarvest(level, pos, state, pot, required, threshold);

        if (pot.growthTime() != beforeGrowth) {
            pot.setChanged();
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
        }

    }

    private static void tickOrHarvest(Level level, BlockPos pos, BlockState state, PowerPotBlockEntity pot, float required, float threshold) {
        if (required - pot.growthTime() > threshold)
            BotanyPotBlockEntity.tickPot(level, pos, state, pot);
        else
            pot.growthTime.tickUp(level);

        if (pot.growthTime() >= required && pot.canHarvest())
            harvest(pot, level);
    }

    public static void harvest(PowerPotBlockEntity pot, Level level) {
        if (level instanceof ServerLevel serverLevel) {
            final int baseRolls = Helpers.getLootRolls(
                    pot.getRecipeContext(),
                    level,
                    pot.getOrInvalidateCrop(),
                    pot.getOrInvalidateSoil()
            );
            final int rolls = baseRolls * pot.tier.config.itemsPerOutput.get();

            for (int roll = 0; roll < rolls; roll++) {
                pot.getOrInvalidateCrop().onHarvest(pot.getRecipeContext(), level, stack ->
                        Services.GAMEPLAY.addItem(stack, pot.getItems(), BotanyPotBlockEntity.STORAGE_SLOTS)
                );
            }
        }
        pot.growthTime.reset();
    }
}