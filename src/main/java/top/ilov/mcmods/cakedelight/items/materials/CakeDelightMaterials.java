package top.ilov.mcmods.cakedelight.items.materials;

import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import top.ilov.mcmods.cakedelight.CakeDelightMod;

import java.util.Map;

import static net.minecraft.item.equipment.EquipmentAssetKeys.register;

public interface CakeDelightMaterials {

    RegistryKey<EquipmentAsset> EKAC_KEY = register("ekac");

    TagKey<Item> EKAC_TAG = of("ekac");

    ArmorMaterial EKAC = new ArmorMaterial(10, createDefenseMap(1, 1, 1, 2,
            1), 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, EKAC_TAG, EKAC_KEY);

    private static Map<EquipmentType, Integer> createDefenseMap(int bootsDefense, int leggingsDefense, int chestplateDefense,
                                                                int helmetDefense, int bodyDefense) {
        return Maps.newEnumMap(Map.of(EquipmentType.BOOTS, bootsDefense, EquipmentType.LEGGINGS,
                leggingsDefense, EquipmentType.CHESTPLATE, chestplateDefense, EquipmentType.HELMET,
                helmetDefense, EquipmentType.BODY, bodyDefense));
    }

    private static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(CakeDelightMod.MOD_ID, id));
    }

}
