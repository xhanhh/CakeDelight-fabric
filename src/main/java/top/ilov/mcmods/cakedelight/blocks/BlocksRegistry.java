package top.ilov.mcmods.cakedelight.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.CakeBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static top.ilov.mcmods.cakedelight.CakeDelightMod.MOD_ID;

public class BlocksRegistry {

    public static final CakeBlock ekac = registerCakeBlock("ekac", new EkacBlock(FabricBlockSettings.create().hardness(0.5F)
            .sounds(BlockSoundGroup.WOOL)));

    protected static Block registerBlock(String name, Block block) {

        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MOD_ID, name), block);

    }

    protected static Item registerBlockItem(String name, Block block) {

        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, new FabricItemSettings()));

    }

    protected static CakeBlock registerCakeBlock(String name, CakeBlock block) {

        registerCakeBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MOD_ID, name), block);

    }

    protected static Item registerCakeBlockItem(String name, CakeBlock block) {

        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, new FabricItemSettings()));

    }

    public static void registerBlocks() {}

}
