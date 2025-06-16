package top.ilov.mcmods.cakedelight.blocks.cakes;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.EndPlatformFeature;
import top.ilov.mcmods.cakedelight.blocks.CakePortalBase;
import top.ilov.mcmods.cakedelight.utils.NetherTeleportHelper;

public class NetherCakeBlock extends CakePortalBase {

    public NetherCakeBlock(Settings settings) {
        super(settings);
    }

    public static final IntProperty BITES = Properties.BITES;

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);

        if (world instanceof ServerWorld && !player.isSpectator() && itemStack.isEmpty() && !player.hasVehicle()) {

            if (player.getWorld().getRegistryKey() != World.NETHER) {

                ServerWorld nether = world.getServer().getWorld(World.NETHER);
                if (nether == null) {
                    return ActionResult.FAIL;
                }

                BlockPos spawnPos = NetherTeleportHelper.findSafeNetherSpawn(nether, player);
                if (spawnPos == null) {
                    spawnPos = new BlockPos(0, 70, 0);
                    EndPlatformFeature.generate(nether, spawnPos.down(), true);
                }

                Vec3d targetPos = spawnPos.toCenterPos();
                float yaw = player.getYaw();
                float pitch = player.getPitch();

                TeleportTarget target = new TeleportTarget(nether, targetPos, player.getVelocity(), yaw, pitch,
                        TeleportTarget.SEND_TRAVEL_THROUGH_PORTAL_PACKET.then(TeleportTarget.ADD_PORTAL_CHUNK_TICKET)
                );

                PlayerEntity teleportedPlayer = (PlayerEntity) player.teleportTo(target);
                if (teleportedPlayer != null) {
                    teleportedPlayer.refreshPositionAfterTeleport(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                }

                NetherTeleportHelper.checkPlatformAndPlaceCake(nether, spawnPos, player.getHorizontalFacing());

                return tryEat(world, pos, state, player);

            } else {
                player.sendMessage(Text.translatable("msg.cakedelight.cannot_eat_nether_cake"),true);
            }

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

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
                                             PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.getItem() == Items.OBSIDIAN && state.get(BITES) > 0 && !world.isClient) {

            world.setBlockState(pos, state.with(BITES, state.get(BITES) - 1));
            stack.decrement(1);

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
    }

}
