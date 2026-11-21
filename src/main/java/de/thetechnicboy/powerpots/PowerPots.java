package de.thetechnicboy.powerpots;

import com.mojang.logging.LogUtils;
import de.thetechnicboy.powerpots.datagen.DataGenerators;
import de.thetechnicboy.powerpots.pot.PowerPotBlock;
import de.thetechnicboy.powerpots.pot.PowerPotRenderer;
import de.thetechnicboy.powerpots.pot.PowerPotTile;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import net.neoforged.neoforge.registries.*;
import org.slf4j.Logger;

@Mod(PowerPots.MODID)
public class PowerPots {
    public static final String MODID = "powerpots";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation genRL(String key) {
        ResourceLocation rl = ResourceLocation.tryBuild(MODID, key);
        if (rl == null) {
            throw new IllegalArgumentException("Invalid ResourceLocation key: " + key);
        }
        return rl;
    }



    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MODID);
    public static final DeferredHolder<Block, PowerPotBlock> POWER_POT_MK1 = BLOCKS.register("power_pot_mk1", () -> new PowerPotBlock(PotTier.MK1));
    public static final DeferredHolder<Block, PowerPotBlock> POWER_POT_MK2 = BLOCKS.register("power_pot_mk2", () -> new PowerPotBlock(PotTier.MK2));
    public static final DeferredHolder<Block, PowerPotBlock> POWER_POT_MK3 = BLOCKS.register("power_pot_mk3", () -> new PowerPotBlock(PotTier.MK3));
    public static final DeferredHolder<Block, PowerPotBlock> POWER_POT_MK4 = BLOCKS.register("power_pot_mk4", () -> new PowerPotBlock(PotTier.MK4));


    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PowerPotTile.MK1Tile>> POWER_POT_MK1_BE = BLOCK_ENTITIES.register("power_pot_tile_mk1", () -> BlockEntityType.Builder.of(PowerPotTile.MK1Tile::new, POWER_POT_MK1.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PowerPotTile.MK2Tile>> POWER_POT_MK2_BE = BLOCK_ENTITIES.register("power_pot_tile_mk2", () -> BlockEntityType.Builder.of(PowerPotTile.MK2Tile::new, POWER_POT_MK2.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PowerPotTile.MK3Tile>> POWER_POT_MK3_BE = BLOCK_ENTITIES.register("power_pot_tile_mk3", () -> BlockEntityType.Builder.of(PowerPotTile.MK3Tile::new, POWER_POT_MK3.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PowerPotTile.MK4Tile>> POWER_POT_MK4_BE = BLOCK_ENTITIES.register("power_pot_tile_mk4", () -> BlockEntityType.Builder.of(PowerPotTile.MK4Tile::new, POWER_POT_MK4.get()).build(null));


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);
    public static final DeferredHolder<Item, Item> POWER_POT_MK1_ITEM = ITEMS.register("power_pot_mk1", () -> new BlockItem(POWER_POT_MK1.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> POWER_POT_MK2_ITEM = ITEMS.register("power_pot_mk2", () -> new BlockItem(POWER_POT_MK2.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> POWER_POT_MK3_ITEM = ITEMS.register("power_pot_mk3", () -> new BlockItem(POWER_POT_MK3.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> POWER_POT_MK4_ITEM = ITEMS.register("power_pot_mk4", () -> new BlockItem(POWER_POT_MK4.get(), new Item.Properties()));


    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> POWER_POTS_TAB = CREATIVE_TABS.register("powerpots_tab", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MODID))
                    .icon(() -> POWER_POT_MK4_ITEM.get().getDefaultInstance())
                    .displayItems((params, output) -> {
                        output.accept(POWER_POT_MK1_ITEM.get());
                        output.accept(POWER_POT_MK2_ITEM.get());
                        output.accept(POWER_POT_MK3_ITEM.get());
                        output.accept(POWER_POT_MK4_ITEM.get());
                    })
                    .build()
    );


    public PowerPots(IEventBus modEventBus, ModContainer modContainer) {

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(DataGenerators::gatherData);

        modEventBus.addListener(PowerPots::register);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("HELLO FROM CLIENT SETUP");

        event.enqueueWork(() -> BlockEntityRenderers.register(PowerPots.POWER_POT_MK1_BE.get(), PowerPotRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(PowerPots.POWER_POT_MK2_BE.get(), PowerPotRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(PowerPots.POWER_POT_MK3_BE.get(), PowerPotRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(PowerPots.POWER_POT_MK4_BE.get(), PowerPotRenderer::new));
    }


    public static void register(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                POWER_POT_MK1_BE.get(),
                ( be, side) ->  be.getEnergyStorage()
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                POWER_POT_MK1_BE.get(),
                ( be, side) -> {
                    if (side == Direction.DOWN) {
                        return new SidedInvWrapper(be, Direction.DOWN);
                    }
                    return null;
                }
        );

        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                POWER_POT_MK2_BE.get(),
                ( be, side) ->  be.getEnergyStorage()
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                POWER_POT_MK2_BE.get(),
                ( be, side) -> {
                    if (side == Direction.DOWN) {
                        return new SidedInvWrapper(be, Direction.DOWN);
                    }
                    return null;
                }
        );

        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                POWER_POT_MK3_BE.get(),
                ( be, side) ->  be.getEnergyStorage()
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                POWER_POT_MK3_BE.get(),
                ( be, side) -> {
                    if (side == Direction.DOWN) {
                        return new SidedInvWrapper(be, Direction.DOWN);
                    }
                    return null;
                }
        );

        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                POWER_POT_MK4_BE.get(),
                ( be, side) ->  be.getEnergyStorage()
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                POWER_POT_MK4_BE.get(),
                ( be, side) -> {
                    if (side == Direction.DOWN) {
                        return new SidedInvWrapper(be, Direction.DOWN);
                    }
                    return null;
                }
        );
    }

}
