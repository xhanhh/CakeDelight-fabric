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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import top.ilov.mcmods.cakedelight.blocks.CakePortalBase;
import top.ilov.mcmods.cakedelight.utils.NetherTeleportHelper;

public class NetherCakeBlock extends CakePortalBase {

    public NetherCakeBlock(Settings settings) {
        super(settings);
    }

    public static final IntProperty BITES = Properties.BITES;

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (world instanceof ServerWorld && !player.isSpectator() && itemStack.isEmpty() && player.canUsePortals() && !player.hasVehicle()) {

            if (player.getWorld().getDimensionKey() != DimensionTypes.THE_NETHER) {

                ServerWorld nether = ((ServerWorld) world).getServer().getWorld(World.NETHER);
                if (nether == null) {
                    return ActionResult.FAIL;
                }

                BlockPos spawnPos  = NetherTeleportHelper.findSafeNetherSpawn(nether, player);
                if (spawnPos  == null) {
                    spawnPos = new BlockPos(0, 70, 0);
                    NetherTeleportHelper.checkPlatformAndPlaceCake(nether, spawnPos, player.getHorizontalFacing());
                }

                Vec3d targetPos = spawnPos.toCenterPos();
                float yaw = player.getYaw();
                float pitch = player.getPitch();

                if (player instanceof ServerPlayerEntity serverPlayer) {
                    serverPlayer.teleport(nether, targetPos.x, targetPos.y, targetPos.z, yaw, pitch);
                }

                NetherTeleportHelper.checkPlatformAndPlaceCake(nether, spawnPos, player.getHorizontalFacing());

                return tryEat(world, pos, state, player);

            } else {
                player.sendMessage(Text.translatable("msg.cakedelight.cannot_eat_nether_cake"),true);

            }

        }

        if (itemStack.getItem() == Items.OBSIDIAN && state.get(BITES) > 0 && !world.isClient) {

            world.setBlockState(pos, state.with(BITES, state.get(BITES) - 1));
            itemStack.decrement(1);

        }

        if (world.isClient && itemStack.isEmpty()) {
            if (tryEat(world, pos, state, player).isAccepted()) {
                return ActionResult.SUCCESS;
            }

            if (itemStack.isEmpty()) {
                return ActionResult.CONSUME;
            }
        }

        return ActionResult.PASS;

    }
}
