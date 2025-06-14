package top.ilov.mcmods.cakedelight.items;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import top.ilov.mcmods.cakedelight.items.materials.CakeDelightMaterials;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import java.util.function.Function;

import static top.ilov.mcmods.cakedelight.CakeDelightMod.MOD_ID;

public class ItemsRegistry {

    public static final Item ekac_hat = registerItem("ekac_hat", key ->
            new Item(new Item.Settings().armor(CakeDelightMaterials.EKAC, EquipmentType.HELMET)
                    .registryKey(key)));

    public static final Item porcelain_bowl = registerItem("porcelain_bowl", key ->
            new Item(new Item.Settings().maxCount(64).registryKey(key)));

    public static final Item unfired_porcelain_bowl = registerItem("unfired_porcelain_bowl", key ->
            new Item(new Item.Settings().maxCount(64).registryKey(key)));

    public static final Item cake_base = registerItem("cake_base", key ->
            new Item(new Item.Settings().maxCount(64).registryKey(key)));

    public static final Item small_ekac = registerItem("small_ekac", key ->
            new ConsumableItem(baseFoodItem(CDFoodComponents.small_ekac, key)));

    public static final Item cream = registerItem("cream", key ->
            new ConsumableItem(baseFoodItem(CDFoodComponents.cream, key).recipeRemainder(porcelain_bowl).maxCount(16)));

    public static final Item ekac_slice = registerItem("ekac_slice", key ->
            new ConsumableItem(baseFoodItem(CDFoodComponents.ekac_slice, key)));

    public static final Item eggs_with_tomato = registerItem("eggs_with_tomato", key ->
            new ConsumableItem(bowlFoodItem(CDFoodComponents.eggs_with_tomato, key)));

    public static final Item tomato_egg_noodle_soup = registerItem("tomato_egg_noodle_soup", key ->
            new ConsumableItem(bowlFoodItem(CDFoodComponents.tomato_egg_noodle_soup, key)));

    public static final Item cream_of_mushroom_soup = registerItem("cream_of_mushroom_soup", key ->
            new ConsumableItem(bowlFoodItem(CDFoodComponents.cream_of_mushroom_soup, key)));

    public static final Item tomatoes_with_tomatoes = registerItem("tomatoes_with_tomatoes", key ->
            new ConsumableItem(bowlFoodItem(CDFoodComponents.tomatoes_with_tomatoes, key)));

    public static final Item stewed_beef_with_tomato = registerItem("stewed_beef_with_tomato", key ->
            new ConsumableItem(bowlFoodItem(CDFoodComponents.stewed_beef_with_tomato, key)));

    protected static Item registerItem(String name, Function<RegistryKey<Item>, Item> itemFactory) {
        Identifier id = Identifier.of(MOD_ID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
        Item item = itemFactory.apply(key);
        return Registry.register(Registries.ITEM, key, item);
    }

    public static Item.Settings baseFoodItem(FoodComponent food, RegistryKey<Item> key) {
        return new Item.Settings().food(food).registryKey(key);
    }

    public static Item.Settings bowlFoodItem(FoodComponent food, RegistryKey<Item> key) {
        return new Item.Settings().food(food).recipeRemainder(Items.BOWL).maxCount(16).registryKey(key);
    }

    public static void registerItems() {}

}
