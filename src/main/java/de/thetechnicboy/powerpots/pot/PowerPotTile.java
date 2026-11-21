package de.thetechnicboy.powerpots.pot;

import de.thetechnicboy.powerpots.PotTier;
import de.thetechnicboy.powerpots.PowerPots;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PowerPotTile {

    public static class MK1Tile extends PowerPotBlockEntity {
        public MK1Tile(BlockPos pos, BlockState state) {
            super(() -> PowerPots.POWER_POT_MK1_BE.get(), pos, state, PotTier.MK1);
        }

        @Override
        public BlockEntityType<?> getType() {
            return PowerPots.POWER_POT_MK1_BE.get();
        }
    }

    public static class MK2Tile extends PowerPotBlockEntity {
        public MK2Tile(BlockPos pos, BlockState state) {
            super(() -> PowerPots.POWER_POT_MK2_BE.get(), pos, state, PotTier.MK2);
        }

        @Override
        public BlockEntityType<?> getType() {
            return PowerPots.POWER_POT_MK2_BE.get();
        }
    }

    public static class MK3Tile extends PowerPotBlockEntity {
        public MK3Tile(BlockPos pos, BlockState state) {
            super(() -> PowerPots.POWER_POT_MK3_BE.get(), pos, state, PotTier.MK3);
        }

        @Override
        public BlockEntityType<?> getType() {
            return PowerPots.POWER_POT_MK3_BE.get();
        }
    }

    public static class MK4Tile extends PowerPotBlockEntity {
        public MK4Tile(BlockPos pos, BlockState state) {
            super(() -> PowerPots.POWER_POT_MK4_BE.get(), pos, state, PotTier.MK4);
        }

        @Override
        public BlockEntityType<?> getType() {
            return PowerPots.POWER_POT_MK4_BE.get();
        }
    }
}
