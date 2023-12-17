package top.ilov.mcmods.cakedelight.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import top.ilov.mcmods.cakedelight.CakeItemGroup;
import top.ilov.mcmods.cakedelight.items.materials.CakeDelightMaterials;

import static top.ilov.mcmods.cakedelight.CakeDelightMod.MOD_ID;

public class ItemsRegistry {

    public static final Item ekac_slice = registerItem("ekac_slice", new Item(new FabricItemSettings().food(CDFoodComponents.ekac_slice).group(CakeItemGroup.itemgroup)));
    public static final Item small_ekac = registerItem("small_ekac", new Item(new FabricItemSettings().food(CDFoodComponents.small_ekac).group(CakeItemGroup.itemgroup)));

    public static final Item ekac_hat = registerItem("ekac_hat", new ArmorItem(CakeDelightMaterials.EKAC, EquipmentSlot.HEAD,
            new FabricItemSettings().group(CakeItemGroup.itemgroup)));
    public static final Item cake_base = registerItem("cake_base", new Item(new FabricItemSettings().maxCount(64).group(CakeItemGroup.itemgroup)));

    public static final Item unfired_porcelain_bowl = registerItem("unfired_porcelain_bowl", new Item(new FabricItemSettings().maxCount(64).group(CakeItemGroup.itemgroup)));
    public static final Item porcelain_bowl = registerItem("porcelain_bowl", new Item(new FabricItemSettings().maxCount(64).group(CakeItemGroup.itemgroup)));

    public static final Item cream = registerItem("cream", new Item(new FabricItemSettings().food(CDFoodComponents.cream).group(CakeItemGroup.itemgroup)));
    public static final Item eggs_with_tomato = registerItem("eggs_with_tomato", new Item(new FabricItemSettings().food(CDFoodComponents.eggs_with_tomato).group(CakeItemGroup.itemgroup)));
    public static final Item tomato_egg_noodle_soup = registerItem("tomato_egg_noodle_soup",
            new Item(new FabricItemSettings().food(CDFoodComponents.tomato_egg_noodle_soup).group(CakeItemGroup.itemgroup)));
    public static final Item cream_of_mushroom_soup = registerItem("cream_of_mushroom_soup",
            new Item(new FabricItemSettings().food(CDFoodComponents.cream_of_mushroom_soup).group(CakeItemGroup.itemgroup)));

    public static final Item tomatoes_with_tomatoes = registerItem("tomatoes_with_tomatoes",
            new Item(new FabricItemSettings().food(CDFoodComponents.tomatoes_with_tomatoes).group(CakeItemGroup.itemgroup)));
    public static final Item stewed_beef_with_tomato = registerItem("stewed_beef_with_tomato",
            new Item(new FabricItemSettings().food(CDFoodComponents.stewed_beef_with_tomato).group(CakeItemGroup.itemgroup)));

    protected static Item registerItem(String name, Item item) {

        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), item);

    }

    public static void registerItems() {}

}
