package top.ilov.mcmods.cakedelight.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

import java.util.Map;

public class CandleEkacBlock extends AbstractCandleBlock {

    public static final BooleanProperty LIT = AbstractCandleBlock.LIT;

    protected static final VoxelShape CAKE_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 8.0, 15.0);
    protected static final VoxelShape CANDLE_SHAPE = Block.createCuboidShape(7.0, 8.0, 7.0, 9.0, 14.0, 9.0);
    protected static final VoxelShape SHAPE = VoxelShapes.union(CAKE_SHAPE, CANDLE_SHAPE);

    private static final Iterable<Vec3d> PARTICLE_OFFSETS = ImmutableList.of(new Vec3d(0.5, 1.0, 0.5));
    private static final Map<Block, CandleEkacBlock> CANDLES_TO_CANDLE_CAKES = Maps.newHashMap();

    protected CandleEkacBlock(Block candle, AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false));
        CANDLES_TO_CANDLE_CAKES.put(candle, this);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.FLINT_AND_STEEL) || itemStack.isOf(Items.FIRE_CHARGE)) {

                this.playUseSound(world, pos);
                world.setBlockState(pos, state.with(Properties.LIT, true));
                world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);

                return ActionResult.success(world.isClient);

        }
        if (!(CandleEkacBlock.isHittingCandle(hit) && player.getStackInHand(hand).isEmpty() && state.get(LIT).booleanValue())) {
            ActionResult actionResult = EkacBlock.tryEat(world, pos, BlocksRegistry.ekac.getDefaultState(), player);
            if (actionResult.isAccepted()) {
                CandleEkacBlock.dropStacks(state, world, pos);
            }
            return actionResult;
        }
        CandleEkacBlock.extinguish(player, state, world, pos);
        return ActionResult.success(world.isClient);
    }

    private void playUseSound(World world, BlockPos pos) {
        Random random = world.getRandom();
        world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected Iterable<Vec3d> getParticleOffsets(BlockState state) {
        return PARTICLE_OFFSETS;
    }
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(BlocksRegistry.ekac);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    private static boolean isHittingCandle(BlockHitResult hitResult) {
        return hitResult.getPos().y - (double)hitResult.getBlockPos().getY() > 0.5;
    }

    public static BlockState getCandleCakeFromCandle(Block candle) {
        return CANDLES_TO_CANDLE_CAKES.get(candle).getDefaultState();
    }

}
