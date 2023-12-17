package top.ilov.mcmods.cakedelight.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import top.ilov.mcmods.cakedelight.CakeItemGroup;

import java.util.function.ToIntFunction;

import static top.ilov.mcmods.cakedelight.CakeDelightMod.MOD_ID;

public class BlocksRegistry {

    public static final CakeBlock ekac = registerCakeBlock("ekac", new EkacBlock(FabricBlockSettings.of(Material.CAKE).hardness(0.5F)
            .sounds(BlockSoundGroup.WOOL)), CakeItemGroup.itemgroup);

    public static final Block candle_ekac = registerBlock("candle_ekac", new CandleEkacBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block white_candle_ekac = registerBlock("white_candle_ekac", new CandleEkacBlock(Blocks.WHITE_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block orange_candle_ekac = registerBlock("orange_candle_ekac", new CandleEkacBlock(Blocks.ORANGE_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block magenta_candle_ekac = registerBlock("magenta_candle_ekac", new CandleEkacBlock(Blocks.MAGENTA_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block light_blue_candle_ekac = registerBlock("light_blue_candle_ekac", new CandleEkacBlock(Blocks.LIGHT_BLUE_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block yellow_candle_ekac = registerBlock("yellow_candle_ekac", new CandleEkacBlock(Blocks.YELLOW_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block lime_candle_ekac = registerBlock("lime_candle_ekac", new CandleEkacBlock(Blocks.LIME_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block pink_candle_ekac = registerBlock("pink_candle_ekac", new CandleEkacBlock(Blocks.PINK_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block gray_candle_ekac = registerBlock("gray_candle_ekac", new CandleEkacBlock(Blocks.GRAY_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block light_gray_candle_ekac = registerBlock("light_gray_candle_ekac", new CandleEkacBlock(Blocks.LIGHT_GRAY_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block cyan_candle_ekac = registerBlock("cyan_candle_ekac", new CandleEkacBlock(Blocks.CYAN_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block purple_candle_ekac = registerBlock("purple_candle_ekac", new CandleEkacBlock(Blocks.PURPLE_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block blue_candle_ekac = registerBlock("blue_candle_ekac", new CandleEkacBlock(Blocks.BLUE_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block brown_candle_ekac = registerBlock("brown_candle_ekac", new CandleEkacBlock(Blocks.BROWN_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block green_candle_ekac = registerBlock("green_candle_ekac", new CandleEkacBlock(Blocks.GREEN_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block red_candle_ekac = registerBlock("red_candle_ekac", new CandleEkacBlock(Blocks.RED_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));
    public static final Block black_candle_ekac = registerBlock("black_candle_ekac", new CandleEkacBlock(Blocks.BLACK_CANDLE, AbstractBlock.Settings.copy(ekac)
            .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))));

    protected static Block registerBlock(String name, Block block) {

        registerBlockItem(name, block);
        return Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), block);

    }

    protected static Item registerBlockItem(String name, Block block) {

        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, new FabricItemSettings()));

    }

    protected static CakeBlock registerCakeBlock(String name, CakeBlock block, ItemGroup group) {

        registerCakeBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), block);

    }

    protected static Item registerCakeBlockItem(String name, CakeBlock block, ItemGroup group) {

        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(group)));

    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(Properties.LIT) ? litLevel : 0;
    }

    public static void registerBlocks() {}

}
