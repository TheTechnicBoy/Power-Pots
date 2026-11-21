package de.thetechnicboy.powerpots.pot;

import de.thetechnicboy.powerpots.PotTier;
import de.thetechnicboy.powerpots.PowerPots;
import net.darkhax.botanypots.common.impl.block.BotanyPotBlock;
import net.darkhax.botanypots.common.impl.block.PotType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class PowerPotBlock extends BotanyPotBlock {
    private static final VoxelShape NORTH = Stream.of(
            Block.box(3, 0, 3, 13, 1, 13),
            Block.box(2, 1, 2, 14, 7, 3),
            Block.box(2, 1, 13, 14, 7, 14),
            Block.box(13, 1, 3, 14, 7, 13),
            Block.box(2, 1, 3, 3, 7, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape EAST = Stream.of(
            Block.box(3, 0, 3, 13, 1, 13),  //UNTEN
            Block.box(13, 1, 2, 14, 7, 14), //LINKS
            Block.box(2, 1, 2, 3, 7, 14),
            Block.box(3, 1, 13, 13, 7, 14),
            Block.box(3, 1, 2, 13, 7, 3)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SOUTH = Stream.of(
            Block.box(3, 0, 3, 13, 1, 13),
            Block.box(2, 1, 13, 14, 7, 14),
            Block.box(2, 1, 2, 14, 7, 3),
            Block.box(2, 1, 3, 3, 7, 13),
            Block.box(13, 1, 3, 14, 7, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape WEST = Stream.of(
            Block.box(3, 0, 3, 13, 1, 13),
            Block.box(2, 1, 2, 3, 7, 14),
            Block.box(13, 1, 2, 14, 7, 14),
            Block.box(3, 1, 13, 13, 7, 14),
            Block.box(3, 1, 2, 13, 7, 3)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();



    public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final Property<Integer> LEVEL = BlockStateProperties.LEVEL;

    private final PotTier tier;

    public PowerPotBlock(PotTier tier) {
        super(tier.color, PotType.HOPPER);
        this.tier = tier;

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(WATERLOGGED, false)
                .setValue(LEVEL, 0)
                .setValue(HORIZONTAL_FACING, Direction.NORTH)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        // unbedingt LEVEL mit aufnehmen – sonst crasht die Library
        builder.add(WATERLOGGED, LEVEL, HORIZONTAL_FACING);
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        final BlockPos placedPos = context.getClickedPos();
        final FluidState fluidState = context.getLevel().getFluidState(placedPos);

        return this.defaultBlockState()
                .setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getCounterClockWise())
                .setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER)
                .setValue(LEVEL, 0);
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Direction value = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        if (value == Direction.NORTH) return NORTH;
        if (value == Direction.EAST) return EAST;
        if (value == Direction.SOUTH) return SOUTH;
        return WEST;
    }




    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return switch (tier) {
            case MK1 -> new PowerPotTile.MK1Tile(pos, state);
            case MK2 -> new PowerPotTile.MK2Tile(pos, state);
            case MK3 -> new PowerPotTile.MK3Tile(pos, state);
            case MK4 -> new PowerPotTile.MK4Tile(pos, state);
        };
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // Wenn Client -> kein Tick nötig (Render läuft separat)
        if (level.isClientSide) {
            return null;
        }

        // Wähle richtigen BlockEntityType basierend auf dem Tier
        return switch (tier) {
            case MK1 -> createTickerHelper(type, PowerPots.POWER_POT_MK1_BE.get(), PowerPotTile.MK1Tile::tick);
            case MK2 -> createTickerHelper(type, PowerPots.POWER_POT_MK2_BE.get(), PowerPotTile.MK2Tile::tick);
            case MK3 -> createTickerHelper(type, PowerPots.POWER_POT_MK3_BE.get(), PowerPotTile.MK3Tile::tick);
            case MK4 -> createTickerHelper(type, PowerPots.POWER_POT_MK4_BE.get(), PowerPotTile.MK4Tile::tick);
        };
    }


}
