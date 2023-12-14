package top.ilov.mcmods.cakedelight.integration.clothconfig;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import top.ilov.mcmods.cakedelight.CakeConfig;
import top.ilov.mcmods.cakedelight.CakeDelightMod;

public class ClothConfig {

    public static Screen genConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setTitle(Text.translatable("config.cakedelight.title"))
                .setParentScreen(parent)
                .setSavingRunnable(() -> CakeConfig.write(CakeDelightMod.CONFIG));

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.cakedelight.general"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.cakedelight.enable_eating_ekac_sound"), CakeDelightMod.CONFIG.isEnableEkacSound())
                .setDefaultValue(true)
                .setTooltip(Text.translatable("config.cakedelight.enable_eating_ekac_sound.tooltip"))
                .setSaveConsumer(newValue -> CakeDelightMod.CONFIG.setEnableEkacSound(newValue))
                .build());

        return builder.build();
    }

}
