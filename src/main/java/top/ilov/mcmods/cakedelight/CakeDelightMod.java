package top.ilov.mcmods.cakedelight;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ilov.mcmods.cakedelight.blocks.BlocksRegistry;
import top.ilov.mcmods.cakedelight.blocks.ExperimentalBlocksRegistry;
import top.ilov.mcmods.cakedelight.items.ItemsRegistry;
import top.ilov.mcmods.cakedelight.sounds.SoundsRegistry;

import java.util.Map;

import static top.ilov.mcmods.cakedelight.ItemGroup.ITEM_GROUP;

public class CakeDelightMod implements ModInitializer {

	public static final String MOD_ID = "cakedelight";

    public static final Logger LOGGER = LoggerFactory.getLogger("Cake Delight");

	public static CakeConfig CONFIG = new CakeConfig();

	@Override
	public void onInitialize() {

		ItemsRegistry.registerItems();
		BlocksRegistry.registerBlocks();
		SoundsRegistry.registerSounds();

		CONFIG = CakeConfig.loadConfig();

		if (CONFIG.isEnable_experimental_contents()) {
			ExperimentalBlocksRegistry.registerExperimentalBlocks();
		}

		if (!FabricLoader.getInstance().isModLoaded("farmersdelight")) {

			LOGGER.error("There's no Farmer's Delight mod found.");

		} else {

			LOGGER.info("Hello Cake!");

		}

		Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
				.displayName(Text.translatable("cakedelight.name"))
				.icon(() -> new ItemStack(BlocksRegistry.end_cake))
				.entries((context, entries) -> {
					entries.add(BlocksRegistry.overworld_cake);
					entries.add(BlocksRegistry.nether_cake);
					entries.add(BlocksRegistry.end_cake);
					entries.add(BlocksRegistry.ekac);
					entries.add(ItemsRegistry.ekac_slice);
					entries.add(ItemsRegistry.small_ekac);
					entries.add(ItemsRegistry.ekac_hat);
					entries.add(ItemsRegistry.cake_base);
					entries.add(ItemsRegistry.unfired_porcelain_bowl);
					entries.add(ItemsRegistry.porcelain_bowl);
					entries.add(ItemsRegistry.cream);
					entries.add(ItemsRegistry.eggs_with_tomato);
					entries.add(ItemsRegistry.tomato_egg_noodle_soup);
					entries.add(ItemsRegistry.cream_of_mushroom_soup);
					entries.add(ItemsRegistry.tomatoes_with_tomatoes);
					entries.add(ItemsRegistry.stewed_beef_with_tomato);
					if (CONFIG.isEnable_experimental_contents()) {

					}
				})
				.build()
		);

		ItemTooltipCallback.EVENT.register((stack, context, type, tooltip) -> {

			if (!CONFIG.isEnable_tooltips_for_displaying_item()) {
				return;
			}

			Map<Item, String> tooltipMap = Map.of(
					BlocksRegistry.ekac.asItem(), "tooltip.cakedelight.ekac",
					BlocksRegistry.end_cake.asItem(), "tooltip.cakedelight.end_cake",
					BlocksRegistry.nether_cake.asItem(), "tooltip.cakedelight.nether_cake"
			);

			String translationKey = tooltipMap.get(stack.getItem());

			if (translationKey != null) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Text.translatable(translationKey));
				} else {
					tooltip.add(Text.translatable("tooltip.cakedelight.shift"));
				}
			}
		});

	}
}