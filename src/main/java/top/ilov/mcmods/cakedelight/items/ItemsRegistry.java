package top.ilov.mcmods.cakedelight.items;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import top.ilov.mcmods.cakedelight.items.materials.CakeDelightMaterials;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import static top.ilov.mcmods.cakedelight.CakeDelightMod.MOD_ID;

public class ItemsRegistry {

    public static final Item ekac_hat = registerItem("ekac_hat", new ArmorItem(CakeDelightMaterials.EKAC, ArmorItem.Type.HELMET,
            new Item.Settings()));

    public static final Item porcelain_bowl = registerItem("porcelain_bowl",
            new Item(new Item.Settings().maxCount(64)));
    public static final Item unfired_porcelain_bowl = registerItem("unfired_porcelain_bowl",
            new Item(new Item.Settings().maxCount(64)));
    public static final Item cake_base = registerItem("cake_base",
            new Item(new Item.Settings().maxCount(64)));

    public static final Item small_ekac = registerItem("small_ekac",
            new ConsumableItem(baseFoodItem(CDFoodComponents.small_ekac)));
    public static final Item cream = registerItem("cream",
            new ConsumableItem(baseFoodItem(CDFoodComponents.cream).recipeRemainder(porcelain_bowl).maxCount(16)));
    public static final Item ekac_slice = registerItem("ekac_slice",
            new ConsumableItem(baseFoodItem(CDFoodComponents.ekac_slice)));

    public static final Item eggs_with_tomato = registerItem("eggs_with_tomato",
            new ConsumableItem(bowlFoodItem(CDFoodComponents.eggs_with_tomato)));
    public static final Item tomato_egg_noodle_soup = registerItem("tomato_egg_noodle_soup",
            new ConsumableItem(bowlFoodItem(CDFoodComponents.tomato_egg_noodle_soup)));
    public static final Item cream_of_mushroom_soup = registerItem("cream_of_mushroom_soup",
            new ConsumableItem(bowlFoodItem(CDFoodComponents.cream_of_mushroom_soup)));

    public static final Item tomatoes_with_tomatoes = registerItem("tomatoes_with_tomatoes",
            new ConsumableItem(bowlFoodItem(CDFoodComponents.tomatoes_with_tomatoes)));
    public static final Item stewed_beef_with_tomato = registerItem("stewed_beef_with_tomato",
            new ConsumableItem(bowlFoodItem(CDFoodComponents.stewed_beef_with_tomato)));

    protected static Item registerItem(String name, Item item) {

        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name), item);

    }

    public static Item.Settings bowlFoodItem(FoodComponent food) {
        return new Item.Settings().food(food).recipeRemainder(Items.BOWL).maxCount(16);
    }

    public static Item.Settings baseFoodItem(FoodComponent food) {
        return new Item.Settings().food(food);
    }

    public static void registerItems() {}

}
