package top.ilov.mcmods.cakedelight.blocks;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import top.ilov.mcmods.cakedelight.blocks.cakes.*;

import java.util.function.Function;
import java.util.function.ToIntFunction;

import static top.ilov.mcmods.cakedelight.CakeDelightMod.MOD_ID;

public class BlocksRegistry {

    public static final CakeBlock ekac = registerCakeBlock("ekac", key ->
            new EkacBlock(AbstractBlock.Settings.create().hardness(0.5F).sounds(BlockSoundGroup.WOOL)
                    .registryKey(key)));

    public static final Block candle_ekac = registerBlock("candle_ekac", key ->
            new CandleEkacBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(ekac)
                    .luminance(BlocksRegistry.createLightLevelFromLitBlockState(3))
                    .registryKey(key)));

    public static final CakeBlock end_cake = registerCakeBlock("end_cake", key ->
            new EndCakeBlock(AbstractBlock.Settings.create().hardness(0.5F).sounds(BlockSoundGroup.WOOL)
                    .registryKey(key)));

    public static final CakeBlock overworld_cake = registerCakeBlock("overworld_cake", key ->
            new OverworldCakeBlock(AbstractBlock.Settings.create().hardness(0.5F).sounds(BlockSoundGroup.WOOL)
                    .registryKey(key)));

    public static final CakeBlock nether_cake = registerCakeBlock("nether_cake", key ->
            new NetherCakeBlock(AbstractBlock.Settings.create().hardness(0.5F).sounds(BlockSoundGroup.WOOL)
                    .registryKey(key)));

    public static final Block white_candle_ekac = registerCandle("white_candle_ekac", Blocks.WHITE_CANDLE);
    public static final Block orange_candle_ekac = registerCandle("orange_candle_ekac", Blocks.ORANGE_CANDLE);
    public static final Block magenta_candle_ekac = registerCandle("magenta_candle_ekac", Blocks.MAGENTA_CANDLE);
    public static final Block light_blue_candle_ekac = registerCandle("light_blue_candle_ekac", Blocks.LIGHT_BLUE_CANDLE);
    public static final Block yellow_candle_ekac = registerCandle("yellow_candle_ekac", Blocks.YELLOW_CANDLE);
    public static final Block lime_candle_ekac = registerCandle("lime_candle_ekac", Blocks.LIME_CANDLE);
    public static final Block pink_candle_ekac = registerCandle("pink_candle_ekac", Blocks.PINK_CANDLE);
    public static final Block gray_candle_ekac = registerCandle("gray_candle_ekac", Blocks.GRAY_CANDLE);
    public static final Block light_gray_candle_ekac = registerCandle("light_gray_candle_ekac", Blocks.LIGHT_GRAY_CANDLE);
    public static final Block cyan_candle_ekac = registerCandle("cyan_candle_ekac", Blocks.CYAN_CANDLE);
    public static final Block purple_candle_ekac = registerCandle("purple_candle_ekac", Blocks.PURPLE_CANDLE);
    public static final Block blue_candle_ekac = registerCandle("blue_candle_ekac", Blocks.BLUE_CANDLE);
    public static final Block brown_candle_ekac = registerCandle("brown_candle_ekac", Blocks.BROWN_CANDLE);
    public static final Block green_candle_ekac = registerCandle("green_candle_ekac", Blocks.GREEN_CANDLE);
    public static final Block red_candle_ekac = registerCandle("red_candle_ekac", Blocks.RED_CANDLE);
    public static final Block black_candle_ekac = registerCandle("black_candle_ekac", Blocks.BLACK_CANDLE);

    protected static Block registerBlock(String name, Function<RegistryKey<Block>, Block> blockFactory) {
        Identifier id = Identifier.of(MOD_ID, name);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);
        Block block = blockFactory.apply(key);
        Registry.register(Registries.BLOCK, key, block);

        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);
        Item.Settings itemSettings = new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey();
        Registry.register(Registries.ITEM, itemKey, new BlockItem(block, itemSettings));
        return block;
    }

    protected static CakeBlock registerCakeBlock(String name, Function<RegistryKey<Block>, CakeBlock> blockFactory) {
        Identifier id = Identifier.of(MOD_ID, name);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);
        CakeBlock block = blockFactory.apply(key);
        Registry.register(Registries.BLOCK, key, block);

        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);
        Item.Settings itemSettings = new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey();
        Registry.register(Registries.ITEM, itemKey, new BlockItem(block, itemSettings));
        return block;
    }

    private static Block registerCandle(String name, Block baseCandle) {
        return registerBlock(name, key -> new CandleEkacBlock(baseCandle, AbstractBlock.Settings.copy(ekac)
                .luminance(createLightLevelFromLitBlockState(3)).registryKey(key)));
    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(Properties.LIT) ? litLevel : 0;
    }

    public static void registerBlocks() {}

}
