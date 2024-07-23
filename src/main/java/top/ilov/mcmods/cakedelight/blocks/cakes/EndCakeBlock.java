package top.ilov.mcmods.cakedelight.blocks.cakes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.jetbrains.annotations.Nullable;
import top.ilov.mcmods.cakedelight.blocks.BlocksRegistry;
import top.ilov.mcmods.cakedelight.blocks.CakePortalBase;

import java.util.List;

public class EndCakeBlock extends CakePortalBase {

    public EndCakeBlock(Settings settings) {
        super(settings);
    }

    public static final IntProperty BITES = Properties.BITES;

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (world instanceof ServerWorld && !player.isSpectator() && itemStack.isEmpty()) {

            if (player.getWorld().getDimensionKey() != DimensionTypes.THE_END) {

                RegistryKey<World> registryKey = world.getRegistryKey() == World.END ? World.OVERWORLD : World.END;
                ServerWorld serverWorld = ((ServerWorld)world).getServer().getWorld(registryKey);

                BlockPos spawnPos = ServerWorld.END_SPAWN_POS;

                if (serverWorld == null) {
                    return ActionResult.FAIL;
                }
                tryEat(world, pos, state, player);

                PlayerEntity teleportedPlayer = (PlayerEntity) player.moveToWorld(serverWorld);
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

        if (itemStack.getItem() == Items.ENDER_EYE && state.get(BITES) > 0 && !world.isClient) {

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

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {

        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.cakedelight.end_cake"));
        } else {
            tooltip.add(Text.translatable("tooltip.cakedelight.shift"));
        }

    }
}
