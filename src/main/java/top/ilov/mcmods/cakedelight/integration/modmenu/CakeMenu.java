package top.ilov.mcmods.cakedelight.integration.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;
import top.ilov.mcmods.cakedelight.integration.clothconfig.ClothConfig;

public class CakeMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {

        if (FabricLoader.getInstance().isModLoaded("cloth-config2")) {
            return ClothConfig::genConfigScreen;
        } else {
            return parent -> null;
        }
    }

}
