package top.ilov.mcmods.cakedelight.blocks.cakes;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import top.ilov.mcmods.cakedelight.blocks.BlocksRegistry;
import top.ilov.mcmods.cakedelight.blocks.CakePortalBase;

public class NetherCakeBlock extends CakePortalBase {

    public NetherCakeBlock(Settings settings) {
        super(settings);
    }

    public static final IntProperty BITES = Properties.BITES;

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);

        if (world instanceof ServerWorld && !player.isSpectator() && itemStack.isEmpty() && !player.hasVehicle()) {

            if (player.getWorld().getDimensionEntry() != DimensionTypes.THE_NETHER) {

                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

                player.getWorld().getProfiler().push("portal");
                player.requestTeleportAndDismount(player.getX(), player.getY(), player.getZ());
                //player.moveToWorld(((ServerWorld) world).getServer().getWorld(World.NETHER));

                player.getWorld().getProfiler().pop();

                if (state.get(BITES) == 0) {
                    player.setStackInHand(Hand.MAIN_HAND, new ItemStack(BlocksRegistry.overworld_cake));
                }

            } else {
                player.sendMessage(Text.translatable("msg.cakedelight.cannot_eat_nether_cake"),true);
                return ActionResult.FAIL;
            }

        }

        if (itemStack.getItem() == Items.OBSIDIAN && state.get(BITES) > 0 && !world.isClient) {

            world.setBlockState(pos, state.with(BITES, state.get(BITES) - 1));
            itemStack.decrement(1);

        }

        if (world.isClient && itemStack.isEmpty()) {

            return ActionResult.CONSUME;

        }

        return tryEat(world, pos, state, player);

    }
}
