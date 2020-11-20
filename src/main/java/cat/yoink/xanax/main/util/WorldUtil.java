package cat.yoink.xanax.main.util;

import cat.yoink.xanax.main.MinecraftInstance;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public final class WorldUtil implements MinecraftInstance
{
    public static void placeBlock(final BlockPos pos)
    {
        for (final EnumFacing enumFacing : EnumFacing.values())
        {
            if (!mc.world.getBlockState(pos.offset(enumFacing)).getBlock().equals(Blocks.AIR) && !isIntercepted(pos))
            {
                final Vec3d vec = new Vec3d(pos.getX() + 0.5D + (double) enumFacing.getXOffset() * 0.5D, pos.getY() + 0.5D + (double) enumFacing.getYOffset() * 0.5D, pos.getZ() + 0.5D + (double) enumFacing.getZOffset() * 0.5D);

                final float[] old = new float[]{mc.player.rotationYaw, mc.player.rotationPitch};

                mc.player.connection.sendPacket(new CPacketPlayer.Rotation((float) Math.toDegrees(Math.atan2((vec.z - mc.player.posZ), (vec.x - mc.player.posX))) - 90.0F, (float) (-Math.toDegrees(Math.atan2((vec.y - (mc.player.posY + (double) mc.player.getEyeHeight())), (Math.sqrt((vec.x - mc.player.posX) * (vec.x - mc.player.posX) + (vec.z - mc.player.posZ) * (vec.z - mc.player.posZ)))))), mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                mc.playerController.processRightClickBlock(mc.player, mc.world, pos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d(pos), EnumHand.MAIN_HAND);
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(old[0], old[1], mc.player.onGround));

                return;
            }
        }
    }

    public static boolean isIntercepted(final BlockPos pos)
    {
        for (final Entity entity : mc.world.loadedEntityList)
        {
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) return true;
        }

        return false;
    }

    public static boolean isInterceptedByOther(final BlockPos pos)
    {
        for (final Entity entity : mc.world.loadedEntityList)
        {
            if (entity.equals(mc.player)) continue;
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) return true;
        }

        return false;
    }

    public static boolean isInHole(final Entity entity)
    {
        return isHole(new BlockPos(entity.posX, entity.posY, entity.posZ));
    }

    public static boolean isObsidianHole(final BlockPos blockPos)
    {
        if (!mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 3, 0)).getBlock().equals(Blocks.AIR))
            return false;

        for (final BlockPos blockPos2 : new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()})
        {
            final IBlockState iBlockState = mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && iBlockState.getBlock() == Blocks.OBSIDIAN) continue;
            return false;
        }
        return true;
    }

    public static boolean isBedrockHole(final BlockPos blockPos)
    {
        if (!mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR))
            return false;

        for (final BlockPos blockPos2 : new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()})
        {
            final IBlockState iBlockState = mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && iBlockState.getBlock() == Blocks.BEDROCK) continue;
            return false;
        }
        return true;
    }

    public static boolean isHole(final BlockPos blockPos)
    {
        if (!mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR))
            return false;

        for (final BlockPos blockPos2 : new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()})
        {
            final IBlockState iBlockState = mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && (iBlockState.getBlock() == Blocks.BEDROCK || iBlockState.getBlock() == Blocks.OBSIDIAN))
                continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleHoleX(final BlockPos blockPos)
    {
        if (!mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(blockPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(blockPos.add(1, 2, 0)).getBlock().equals(Blocks.AIR))
            return false;

        for (final BlockPos blockPos2 : new BlockPos[]{blockPos.add(2, 0, 0), blockPos.add(1, 0, 1), blockPos.add(1, 0, -1), blockPos.add(-1, 0, 0), blockPos.add(0, 0, 1), blockPos.add(0, 0, -1), blockPos.add(0, -1, 0), blockPos.add(1, -1, 0)})
        {
            final IBlockState iBlockState = mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && (iBlockState.getBlock() == Blocks.BEDROCK || iBlockState.getBlock() == Blocks.OBSIDIAN))
                continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleHoleZ(final BlockPos blockPos)
    {
        if (!mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(blockPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(blockPos.add(0, 2, 1)).getBlock().equals(Blocks.AIR))
            return false;

        for (final BlockPos blockPos2 : new BlockPos[]{blockPos.add(0, 0, 2), blockPos.add(1, 0, 1), blockPos.add(-1, 0, 1), blockPos.add(0, 0, -1), blockPos.add(1, 0, 0), blockPos.add(-1, 0, 0), blockPos.add(0, -1, 0), blockPos.add(0, -1, 1)})
        {
            final IBlockState iBlockState = mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && (iBlockState.getBlock() == Blocks.BEDROCK || iBlockState.getBlock() == Blocks.OBSIDIAN))
                continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleBedrockHoleX(final BlockPos blockPos)
    {
        if (!mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(blockPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(blockPos.add(1, 2, 0)).getBlock().equals(Blocks.AIR))
            return false;

        for (final BlockPos blockPos2 : new BlockPos[]{blockPos.add(2, 0, 0), blockPos.add(1, 0, 1), blockPos.add(1, 0, -1), blockPos.add(-1, 0, 0), blockPos.add(0, 0, 1), blockPos.add(0, 0, -1), blockPos.add(0, -1, 0), blockPos.add(1, -1, 0)})
        {
            final IBlockState iBlockState = mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && (iBlockState.getBlock() == Blocks.BEDROCK)) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleBedrockHoleZ(final BlockPos blockPos)
    {
        if (!mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(blockPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(blockPos.add(0, 2, 1)).getBlock().equals(Blocks.AIR))
            return false;

        for (final BlockPos blockPos2 : new BlockPos[]{blockPos.add(0, 0, 2), blockPos.add(1, 0, 1), blockPos.add(-1, 0, 1), blockPos.add(0, 0, -1), blockPos.add(1, 0, 0), blockPos.add(-1, 0, 0), blockPos.add(0, -1, 0), blockPos.add(0, -1, 1)})
        {
            final IBlockState iBlockState = mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && (iBlockState.getBlock() == Blocks.BEDROCK)) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleObsidianHoleX(final BlockPos blockPos)
    {
        if (!mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(blockPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(blockPos.add(1, 2, 0)).getBlock().equals(Blocks.AIR))
            return false;

        for (final BlockPos blockPos2 : new BlockPos[]{blockPos.add(2, 0, 0), blockPos.add(1, 0, 1), blockPos.add(1, 0, -1), blockPos.add(-1, 0, 0), blockPos.add(0, 0, 1), blockPos.add(0, 0, -1), blockPos.add(0, -1, 0), blockPos.add(1, -1, 0)})
        {
            final IBlockState iBlockState = mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && (iBlockState.getBlock() == Blocks.OBSIDIAN)) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleObsidianHoleZ(final BlockPos blockPos)
    {
        if (!mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(blockPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(blockPos.add(0, 2, 1)).getBlock().equals(Blocks.AIR))
            return false;

        for (final BlockPos blockPos2 : new BlockPos[]{blockPos.add(0, 0, 2), blockPos.add(1, 0, 1), blockPos.add(-1, 0, 1), blockPos.add(0, 0, -1), blockPos.add(1, 0, 0), blockPos.add(-1, 0, 0), blockPos.add(0, -1, 0), blockPos.add(0, -1, 1)})
        {
            final IBlockState iBlockState = mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && (iBlockState.getBlock() == Blocks.OBSIDIAN)) continue;
            return false;
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    public static boolean isBreakable(final BlockPos pos)
    {
        return mc.world.getBlockState(pos).getBlock().getBlockHardness(mc.world.getBlockState(pos), mc.world, pos) != -1;
    }
}
