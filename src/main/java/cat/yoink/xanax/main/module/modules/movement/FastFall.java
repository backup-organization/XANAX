package cat.yoink.xanax.main.module.modules.movement;

import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.BooleanSetting;
import cat.yoink.xanax.main.setting.NumberSetting;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class FastFall extends Module
{
    private final BooleanSetting holeOnly = addSetting(new BooleanSetting("HoleOnly", true));
    private final NumberSetting speed = addSetting(new NumberSetting("Speed", 1, 0.01, 1, 0.01));
    private final BooleanSetting noLiquid = addSetting(new BooleanSetting("NoLiquid", true));

    public FastFall()
    {
        super("FastFall", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onTickClientTick(TickEvent.ClientTickEvent event)
    {
        if (nullCheck() || !mc.player.onGround || noLiquid.getValue() && (mc.player.isInLava() || mc.player.isInWater()) || holeOnly.getValue() && !fallingIntoHole())
            return;

        mc.player.motionY -= speed.getValue();
    }

    private boolean fallingIntoHole()
    {
        final Vec3d vec = interpolateEntity(mc.player, mc.getRenderPartialTicks());

        final BlockPos pos = new BlockPos(vec.x, vec.y - 1, vec.z);

        BlockPos[] posList = {pos.north(), pos.south(), pos.east(), pos.west(), pos.down()};

        int blocks = 0;

        for (BlockPos blockPos : posList)
        {
            Block block = mc.world.getBlockState(blockPos).getBlock();

            if (block == Blocks.OBSIDIAN || block == Blocks.BEDROCK) ++blocks;
        }

        return blocks == 5;
    }

    private Vec3d interpolateEntity(EntityPlayerSP entity, float time)
    {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time,
                entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time,
                entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
    }
}