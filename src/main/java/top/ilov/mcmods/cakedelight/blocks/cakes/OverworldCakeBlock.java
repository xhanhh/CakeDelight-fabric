package top.ilov.mcmods.cakedelight.blocks.cakes;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
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

public class OverworldCakeBlock extends CakePortalBase {
    public OverworldCakeBlock(Settings settings) {
        super(settings);
    }

    public static final IntProperty BITES = Properties.BITES;

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (world instanceof ServerWorld && !player.isSpectator() && itemStack.isEmpty()) {

            if (player.getWorld().getDimensionKey() != DimensionTypes.OVERWORLD) {

                RegistryKey<World> registryKey = World.OVERWORLD;
                ServerWorld serverWorld = ((ServerWorld)world).getServer().getWorld(registryKey);

                if (serverWorld == null) {
                    return ActionResult.FAIL;
                }

                BlockPos blockPos = serverWorld.getSpawnPos();
                Vec3d targetPos = blockPos.toCenterPos();
                float yaw = player.getYaw();
                float pitch = player.getPitch();

                tryEat(world, pos, state, player);

                if (player instanceof ServerPlayerEntity serverPlayer) {
                    serverPlayer.teleport(serverWorld, targetPos.x, targetPos.y, targetPos.z, yaw, pitch);
                }

                PlayerEntity teleportedPlayer = (PlayerEntity) player.moveToWorld(serverWorld);
                if (teleportedPlayer != null) {
                    teleportedPlayer.refreshPositionAfterTeleport(serverWorld.getSpawnPos().getX() + 1,
                            serverWorld.getSpawnPos().getY(), serverWorld.getSpawnPos().getZ());
                }

                return ActionResult.SUCCESS;

            } else {
                player.sendMessage(Text.translatable("msg.cakedelight.cannot_eat_overworld_cake"),true);
            }

        }

        if (itemStack.getItem() == Items.MILK_BUCKET && state.get(BITES) > 0 && !world.isClient) {

            world.setBlockState(pos, state.with(BITES, state.get(BITES) - 1));
            itemStack.decrement(1);
            player.setStackInHand(hand, new ItemStack(Items.BUCKET));

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
