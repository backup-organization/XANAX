package cat.yoink.xanax.main.module.modules.world;

import cat.yoink.xanax.main.event.events.DamageBlockEvent;
import cat.yoink.xanax.main.event.events.Render3DEvent;
import cat.yoink.xanax.main.event.events.TickEvent;
import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.BooleanSetting;
import cat.yoink.xanax.main.setting.NumberSetting;
import cat.yoink.xanax.main.util.RenderUtil;
import cat.yoink.xanax.main.util.WorldUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;

public final class PacketMine extends Module
{
    private final NumberSetting red = addSetting(new NumberSetting("Red", 200, 0, 255, 1));
    private final BooleanSetting render = addSetting(new BooleanSetting("Render", true));
    private final NumberSetting green = addSetting(new NumberSetting("Green", 10, 0, 255, 1));
    private final BooleanSetting box = addSetting(new BooleanSetting("Box", true));
    private final NumberSetting blue = addSetting(new NumberSetting("Blue", 10, 0, 255, 1));
    private final BooleanSetting outline = addSetting(new BooleanSetting("Outline", true));
    private final NumberSetting alpha = addSetting(new NumberSetting("Alpha", 100, 0, 255, 1));
    private final BooleanSetting change = addSetting(new BooleanSetting("Change", false));
    private final BooleanSetting noBreak = addSetting(new BooleanSetting("NoBreak", false));
    private BlockPos breakBlock;
    private int miningTicks;

    public PacketMine()
    {
        super("PacketMine", Category.WORLD);
    }

    @Listener
    public void leftClickBlockEvent(DamageBlockEvent event)
    {
        if (WorldUtil.isBreakable(event.getPos()))
        {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getFace()));
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getFace()));

            if (render.getValue()) breakBlock = event.getPos();
            if (noBreak.getValue()) event.setCancelled(true);
        }
    }

    @Listener
    public void tickEvent(TickEvent event)
    {
        if (breakBlock != null && mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE) miningTicks++;
    }

    @Listener
    public void render(Render3DEvent event)
    {
        if (breakBlock != null && mc.world.getBlockState(breakBlock).getBlock() == Blocks.AIR)
        {
            breakBlock = null;
            miningTicks = 0;
        }
        else if (breakBlock != null)
        {
            Color c;
            if (change.getValue())
            {
                if (miningTicks < 50) c = new Color(200, 10, 10, 150);
                else c = new Color(10, 200, 10, 150);
            }
            else c = new Color((int) red.getValue() / 255f, (int) green.getValue() / 255f, (int) blue.getValue() / 255f, (int) alpha.getValue() / 255f);

            RenderUtil.drawBox(breakBlock, c, box.getValue(), outline.getValue());
        }
    }
}
