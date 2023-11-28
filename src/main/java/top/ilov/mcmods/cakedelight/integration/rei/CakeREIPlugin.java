package top.ilov.mcmods.cakedelight.integration.rei;

import me.shedaniel.rei.api.client.entry.filtering.base.BasicFilteringRule;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.common.util.EntryStacks;
import top.ilov.mcmods.cakedelight.blocks.BlocksRegistry;

public class CakeREIPlugin implements REIClientPlugin {

    @Override
    public void registerBasicEntryFiltering(BasicFilteringRule<?> rule) {

        rule.hide(EntryStacks.of(BlocksRegistry.candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.black_candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.blue_candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.brown_candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.green_candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.red_candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.white_candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.orange_candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.magenta_candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.light_blue_candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.yellow_candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.lime_candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.pink_candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.gray_candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.light_gray_candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.cyan_candle_ekac));
        rule.hide(EntryStacks.of(BlocksRegistry.purple_candle_ekac));

    }

}
