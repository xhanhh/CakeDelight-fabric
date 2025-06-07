package top.ilov.mcmods.cakedelight.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import top.ilov.mcmods.cakedelight.blocks.BlocksRegistry;

public class NetherTeleportHelper {

    @Nullable
    public static BlockPos findSafeNetherSpawn(ServerWorld nether, PlayerEntity player) {
        BlockPos center = new BlockPos(0, 70, 0);
        int radius = 16;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos pos = center.add(dx, 0, dz);
                for (int dy = 64; dy < 120; dy++) {
                    BlockPos candidate = new BlockPos(pos.getX(), dy, pos.getZ());
                    if (isSafeSpawn(nether, candidate, player)) {
                        return candidate;
                    }
                }
            }
        }

        return null;
    }

    public static boolean isSafeSpawn(ServerWorld world, BlockPos pos, PlayerEntity player) {
        return world.getBlockState(pos).isAir()
                && world.getBlockState(pos.up()).isAir()
                && world.getBlockState(pos.down()).isSolidBlock(world, pos.down());
    }

    public static boolean hasExistingOverworldCake(ServerWorld world, BlockPos center, int radius) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -1; dy <= 2; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos check = center.add(dx, dy, dz);
                    if (world.getBlockState(check).isOf(BlocksRegistry.overworld_cake)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
