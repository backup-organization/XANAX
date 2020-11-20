package cat.yoink.xanax.main.module.modules.movement;

import cat.yoink.xanax.main.event.events.TickEvent;
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
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public final class Burrow extends Module
{
    private final EnumSetting mode = addSetting(new EnumSetting("LagBackMode", "Jump", "Jump", "TP", "Packet"));
    private final NumberSetting height = addSetting(new NumberSetting("Height", 1.2, 1, 1.3, 0.01));
    private final BooleanSetting announce = addSetting(new BooleanSetting("Announce", false));
    private BlockPos originalPos;

    public Burrow()
    {
        super("Burrow", Category.MOVEMENT);
    }

    @Override
    protected void onEnable()
    {
        originalPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);

        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).getBlock().equals(Blocks.OBSIDIAN) || WorldUtil.isInterceptedByOther(originalPos) || InventoryUtil.getHotbarSlot(Blocks.OBSIDIAN) == -1)
        {
            disable();
            return;
        }

        if (announce.getValue()) ChatUtil.sendPrivateMessage("Enabled Burrow");

        mc.player.jump();
    }

    @Listener
    public void onTickClientTick(final TickEvent event)
    {
        if (mc.player.posY > originalPos.getY() + height.getValue())
        {
            final int oldSlot = mc.player.inventory.currentItem;

            mc.player.inventory.currentItem = InventoryUtil.getHotbarSlot(Blocks.OBSIDIAN);

            WorldUtil.placeBlock(originalPos);

            mc.player.inventory.currentItem = oldSlot;

            if (mode.is("Jump")) mc.player.jump();
            else if (mode.is("TP")) mc.player.posY = originalPos.getY();
            else if (mode.is("Packet"))
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, originalPos.getY(), mc.player.posZ, true));

            disable();
        }
    }
}
