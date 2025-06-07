package top.ilov.mcmods.cakedelight.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.CakeBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import top.ilov.mcmods.cakedelight.blocks.cakes.NetherCakeBlock;

import static top.ilov.mcmods.cakedelight.CakeDelightMod.MOD_ID;

public class ExperimentalBlocksRegistry {



    protected static CakeBlock registerCakeBlock(String name, CakeBlock block) {

        registerCakeBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, name), block);

    }

    protected static Item registerCakeBlockItem(String name, CakeBlock block) {

        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name), new BlockItem(block, new Item.Settings()));

    }

    public static void registerExperimentalBlocks() {}

}
