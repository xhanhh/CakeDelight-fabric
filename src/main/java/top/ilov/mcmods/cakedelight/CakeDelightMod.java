package top.ilov.mcmods.cakedelight;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ilov.mcmods.cakedelight.blocks.BlocksRegistry;
import top.ilov.mcmods.cakedelight.items.ItemsRegistry;
import top.ilov.mcmods.cakedelight.sounds.SoundsRegistry;

public class CakeDelightMod implements ModInitializer {

	public static final String MOD_ID = "cakedelight";

    public static final Logger LOGGER = LoggerFactory.getLogger("Cake Delight");

	public static CakeConfig CONFIG = new CakeConfig();

	@Override
	public void onInitialize() {

		BlocksRegistry.registerBlocks();
		ItemsRegistry.registerItems();
		SoundsRegistry.registerSounds();

		CONFIG = CakeConfig.loadConfig();

		if (!FabricLoader.getInstance().isModLoaded("farmersdelight")) {

			LOGGER.error("There's no Farmer's Delight mod found.");

		} else {

			LOGGER.info("Loading CakeDelight mod.");

		}

	}
}