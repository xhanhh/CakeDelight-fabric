package top.ilov.mcmods.cakedelight;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static top.ilov.mcmods.cakedelight.CakeDelightMod.MOD_ID;

public class ItemGroup {

    protected static final RegistryKey<net.minecraft.item.ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "group"));

}
