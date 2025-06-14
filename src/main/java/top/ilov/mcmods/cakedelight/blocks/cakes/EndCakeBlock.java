package top.ilov.mcmods.cakedelight.blocks.cakes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.EndPlatformFeature;
import top.ilov.mcmods.cakedelight.blocks.BlocksRegistry;
import top.ilov.mcmods.cakedelight.blocks.CakePortalBase;

import java.util.function.Consumer;

public class EndCakeBlock extends CakePortalBase implements TooltipAppender {

    public EndCakeBlock(Settings settings) {
        super(settings);
    }

    public static final IntProperty BITES = Properties.BITES;

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);

        if (world instanceof ServerWorld && !player.isSpectator() && itemStack.isEmpty()) {

            if (player.getWorld().getRegistryKey() != World.END) {

                ServerWorld serverWorld = ((ServerWorld)world).getServer().getWorld(World.END);

                BlockPos spawnPos = ServerWorld.END_SPAWN_POS;

                if (serverWorld == null) {
                    return ActionResult.FAIL;
                }

                Vec3d vec3d = spawnPos.toBottomCenterPos();
                EndPlatformFeature.generate(serverWorld, BlockPos.ofFloored(vec3d).down(), true);
                float f = Direction.WEST.getPositiveHorizontalDegrees();

                if (player instanceof ServerPlayerEntity) {
                    vec3d = vec3d.subtract(0.0F, 1.0F, 0.0F);
                }

                tryEat(world, pos, state, player);

                PlayerEntity teleportedPlayer = (PlayerEntity) player.teleportTo(
                        new TeleportTarget(serverWorld, vec3d, player.getVelocity(), f, player.getPitch(),
                                TeleportTarget.SEND_TRAVEL_THROUGH_PORTAL_PACKET.then(TeleportTarget.ADD_PORTAL_CHUNK_TICKET)));

                if (teleportedPlayer != null) {
                    teleportedPlayer.refreshPositionAfterTeleport(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                }

                BlockPos cakePos = spawnPos.add(player.getHorizontalFacing().getVector());
                cakePos = new BlockPos(cakePos.getX(), cakePos.getY(), cakePos.getZ());

                while (serverWorld.isAir(cakePos.down())) {
                    cakePos = cakePos.down();
                }

                if (serverWorld.isAir(cakePos)) {
                    serverWorld.setBlockState(cakePos, BlocksRegistry.overworld_cake.getDefaultState());
                }

                return ActionResult.SUCCESS;
            } else {
                player.sendMessage(Text.translatable("msg.cakedelight.cannot_eat_end_cake"),true);
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
        if (stack.getItem() == Items.ENDER_EYE && state.get(BITES) > 0 && !world.isClient) {

            world.setBlockState(pos, state.with(BITES, state.get(BITES) - 1));
            stack.decrement(1);
            return ActionResult.SUCCESS;

        }
        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {

        if (Screen.hasShiftDown()) {
            textConsumer.accept(Text.translatable("tooltip.cakedelight.end_cake"));
        } else {
            textConsumer.accept(Text.translatable("tooltip.cakedelight.shift"));
        }

    }
}
