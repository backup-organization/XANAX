package cat.yoink.xanax.main.module.modules.combat;

import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.BooleanSetting;
import cat.yoink.xanax.main.setting.EnumSetting;
import cat.yoink.xanax.main.setting.NumberSetting;
import cat.yoink.xanax.main.util.ChatUtil;
import cat.yoink.xanax.main.util.InventoryUtil;
import cat.yoink.xanax.main.util.WorldUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Arrays;
import java.util.List;

public final class Surround extends Module
{
    private final NumberSetting blocksPerTick = addSetting(new NumberSetting("BlocksPerTick", 1, 1, 10, 1));
    private final BooleanSetting obsidian = addSetting(new BooleanSetting("Obsidian", true));
    private final EnumSetting disable = addSetting(new EnumSetting("Disable", "WhenDone", "WhenDone", "OnLeave", "Off"));
    private final BooleanSetting enderChest = addSetting(new BooleanSetting("EnderChest", false));
    private final BooleanSetting center = addSetting(new BooleanSetting("Center", false));
    private final BooleanSetting announce = addSetting(new BooleanSetting("Announce", false));
    private final List<Vec3d> positions = Arrays.asList(new Vec3d(1, -1, 0), new Vec3d(-1, -1, 0), new Vec3d(0, -1, 1), new Vec3d(0, -1, -1), new Vec3d(1, 0, 0), new Vec3d(-1, 0, 0), new Vec3d(0, 0, 1), new Vec3d(0, 0, -1));
    private boolean finished;
    private Vec3d playerPos;

    public Surround()
    {
        super("Surround", Category.COMBAT);
    }

    @Override
    protected void onEnable()
    {
        finished = false;

        if (center.getValue())
        {
            BlockPos centerPos = mc.player.getPosition();
            playerPos = mc.player.getPositionVector();
            double y = centerPos.getY();
            double x = centerPos.getX();
            double z = centerPos.getZ();

            final Vec3d plusPlus = new Vec3d(x + 0.5, y, z + 0.5);
            final Vec3d plusMinus = new Vec3d(x + 0.5, y, z - 0.5);
            final Vec3d minusMinus = new Vec3d(x - 0.5, y, z - 0.5);
            final Vec3d minusPlus = new Vec3d(x - 0.5, y, z + 0.5);

            if (getDst(plusPlus) < getDst(plusMinus) && getDst(plusPlus) < getDst(minusMinus) && getDst(plusPlus) < getDst(minusPlus))
            {
                x = centerPos.getX() + 0.5;
                z = centerPos.getZ() + 0.5;
                centerPlayer(x, y, z);
            }
            if (getDst(plusMinus) < getDst(plusPlus) && getDst(plusMinus) < getDst(minusMinus) && getDst(plusMinus) < getDst(minusPlus))
            {
                x = centerPos.getX() + 0.5;
                z = centerPos.getZ() - 0.5;
                centerPlayer(x, y, z);
            }
            if (getDst(minusMinus) < getDst(plusPlus) && getDst(minusMinus) < getDst(plusMinus) && getDst(minusMinus) < getDst(minusPlus))
            {
                x = centerPos.getX() - 0.5;
                z = centerPos.getZ() - 0.5;
                centerPlayer(x, y, z);
            }
            if (getDst(minusPlus) < getDst(plusPlus) && getDst(minusPlus) < getDst(plusMinus) && getDst(minusPlus) < getDst(minusMinus))
            {
                x = centerPos.getX() - 0.5;
                z = centerPos.getZ() + 0.5;
                centerPlayer(x, y, z);
            }
        }

        if (announce.getValue()) ChatUtil.sendPrivateMessage("Enabled Surround");
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event)
    {
        if (nullCheck()) return;

        if (finished && (disable.getValue().equalsIgnoreCase("WhenDone") || (disable.getValue().equalsIgnoreCase("OnLeave") && !mc.player.onGround)))
        {
            disable();
        }

        int blocksPlaced = 0;

        for (Vec3d position : positions)
        {
            BlockPos pos = new BlockPos(position.add(mc.player.getPositionVector()));

            if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR))
            {
                int oldSlot = mc.player.inventory.currentItem;
                int newSlot = getSlot();

                if (newSlot == -1)
                {
                    disable();
                    return;
                }

                mc.player.inventory.currentItem = newSlot;

                WorldUtil.placeBlock(pos);

                mc.player.inventory.currentItem = oldSlot;

                blocksPlaced++;

                if (blocksPlaced == blocksPerTick.getValue()) return;
            }

            if (WorldUtil.isIntercepted(pos)) blocksPlaced++;
        }
        if (blocksPlaced == 0) finished = true;
    }

    @Override
    protected void onDisable()
    {
        if (announce.getValue()) ChatUtil.sendPrivateMessage("Disabled Surround");
    }

    private int getSlot()
    {
        int slot = -1;

        if (enderChest.getValue())
        {
            int enderChestSlot = InventoryUtil.getHotbarSlot(Blocks.ENDER_CHEST);
            if (enderChestSlot != -1) slot = enderChestSlot;
        }
        if (obsidian.getValue())
        {
            int obsidianSlot = InventoryUtil.getHotbarSlot(Blocks.OBSIDIAN);
            if (obsidianSlot != -1) slot = obsidianSlot;
        }

        return slot;
    }

    private void centerPlayer(double x, double y, double z)
    {
        mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, true));
        mc.player.setPosition(x, y, z);
    }

    double getDst(Vec3d vec)
    {
        return playerPos.distanceTo(vec);
    }
}
