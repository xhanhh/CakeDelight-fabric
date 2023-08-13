package top.ilov.mcmods.cakedelight;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ilov.mcmods.cakedelight.blocks.BlocksRegistry;
import top.ilov.mcmods.cakedelight.items.ItemsRegistry;

import static top.ilov.mcmods.cakedelight.ItemGroup.ITEM_GROUP;

public class CakeDelightMod implements ModInitializer {

	public static final String MOD_ID = "cakedelight";

    public static final Logger LOGGER = LoggerFactory.getLogger("Cake Delight");

	@Override
	public void onInitialize() {

		ItemsRegistry.registerItems();
		BlocksRegistry.registerBlocks();

		if (!FabricLoader.getInstance().isModLoaded("farmersdelight")) {

			LOGGER.error("There's no Farmer's Delight mod founded.");

		} else {

			LOGGER.info("Loading CakeDelight mod.");

		}

		Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
				.displayName(Text.translatable("cakedelight.name"))
				.icon(() -> new ItemStack(BlocksRegistry.ekac))
				.entries((context, entries) -> {
					entries.add(BlocksRegistry.ekac);
					entries.add(ItemsRegistry.ekac_hat);
					entries.add(ItemsRegistry.ekac_slice);
					entries.add(ItemsRegistry.small_ekac);
					entries.add(ItemsRegistry.unfired_porcelain_bowl);
					entries.add(ItemsRegistry.porcelain_bowl);
					entries.add(ItemsRegistry.cream);
					entries.add(ItemsRegistry.eggs_with_tomato);
					entries.add(ItemsRegistry.tomato_egg_noodle_soup);
					entries.add(ItemsRegistry.cream_of_mushroom_soup);
				})
				.build()
		);

	}
}