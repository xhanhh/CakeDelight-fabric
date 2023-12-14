package top.ilov.mcmods.cakedelight.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import top.ilov.mcmods.cakedelight.CakeDelightMod;
import top.ilov.mcmods.cakedelight.sounds.SoundsRegistry;

import java.util.List;
import java.util.Random;

public class EkacBlock extends CakeBlock {

    public static final IntProperty BITES = Properties.BITES;

    public EkacBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(BITES, 0));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        Block block;
        Item item = itemStack.getItem();
        if (itemStack.isIn(ItemTags.CANDLES) && state.get(BITES) == 0 && (block = Block.getBlockFromItem(item)) instanceof CandleBlock) {
            if (!player.isCreative()) {
                itemStack.decrement(1);
            }
            world.playSound(null, pos, SoundEvents.BLOCK_CAKE_ADD_CANDLE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.setBlockState(pos, CandleEkacBlock.getCandleCakeFromCandle(block));
            world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            player.incrementStat(Stats.USED.getOrCreateStat(item));
            return ActionResult.SUCCESS;
        }

        if (world.isClient) {
            if (tryEat(world, pos, state, player).isAccepted()) {
                return ActionResult.SUCCESS;
            }

            if (itemStack.isEmpty()) {
                return ActionResult.CONSUME;
            }
        }

        return tryEat(world, pos, state, player);
    }

    protected static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        }
        player.incrementStat(Stats.EAT_CAKE_SLICE);
        player.getHungerManager().add(2, 0.1f);

        Random random = new Random();

        if (CakeDelightMod.CONFIG.isEnableEkacSound() | FabricLoader.getInstance().isModLoaded("cakechomps")) {
            player.playSound(SoundsRegistry.eat_ekac, 0.5f + 0.4f * (float) random.nextInt(2),
                    (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }

        int i = state.get(BITES);
        world.emitGameEvent(player, GameEvent.EAT, pos);
        if (i < 6) {
            world.setBlockState(pos, state.with(BITES, i + 1), Block.NOTIFY_ALL);
        } else {
            world.removeBlock(pos, false);
            world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
        }
        return ActionResult.SUCCESS;
    }
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {

        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.cakedelight.ekac"));
        } else {
            tooltip.add(Text.translatable("tooltip.cakedelight.shift"));
        }

    }

}
