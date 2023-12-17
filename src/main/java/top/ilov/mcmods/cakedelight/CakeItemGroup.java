package top.ilov.mcmods.cakedelight;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import top.ilov.mcmods.cakedelight.blocks.BlocksRegistry;

import static top.ilov.mcmods.cakedelight.CakeDelightMod.MOD_ID;

public class CakeItemGroup {

    public static final ItemGroup itemgroup = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "group"), () -> new ItemStack(BlocksRegistry.ekac));

}
