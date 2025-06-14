package top.ilov.mcmods.cakedelight.utils;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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

    public static boolean hasExistingTorch(ServerWorld world, BlockPos center) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 2; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos check = center.add(dx, dy, dz);
                    if (world.getBlockState(check).isOf(Blocks.TORCH)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void checkPlatformAndPlaceCake(ServerWorld world, BlockPos spawnPos, Direction facing) {
        BlockPos cakePos = spawnPos.offset(facing);
        BlockPos groundCenter = cakePos.down();

        if (hasExistingOverworldCake(world, cakePos, 3)) {
            return;
        }

        boolean needPlatform = false;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos check = groundCenter.add(dx, 0, dz);
                if (world.isAir(check)) {
                    needPlatform = true;
                    break;
                }
            }
            if (needPlatform) break;
        }

        if (needPlatform) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos pos = groundCenter.add(dx, 0, dz);
                    world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
                }
            }
        }

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = 1; dy <= 3; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos pos = groundCenter.add(dx, dy, dz);
                    if (!world.isAir(pos)) {
                        world.breakBlock(pos, false);
                    }
                }
            }
        }

        if (world.isAir(cakePos)) {
            world.setBlockState(cakePos, BlocksRegistry.overworld_cake.getDefaultState());
        }

        if (!hasExistingTorch(world, cakePos)) {
            BlockPos torchPos = findTorchPosition(world, cakePos);
            if (torchPos != null) {
                world.setBlockState(torchPos, Blocks.TORCH.getDefaultState());
            }
        }

    }

    @Nullable
    private static BlockPos findTorchPosition(ServerWorld world, BlockPos facing) {
        for (Direction dir : Direction.Type.HORIZONTAL) {
            BlockPos candidate = facing.offset(dir);
            BlockPos below = candidate.down();
            if (world.getBlockState(candidate).isAir() && world.getBlockState(below).isSolidBlock(world, below)) {
                return candidate;
            }
        }
        return null;
    }

}
