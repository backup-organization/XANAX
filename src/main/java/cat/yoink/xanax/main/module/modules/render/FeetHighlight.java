package cat.yoink.xanax.main.module.modules.render;

import cat.yoink.xanax.main.event.events.Render3DEvent;
import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.BooleanSetting;
import cat.yoink.xanax.main.setting.EnumSetting;
import cat.yoink.xanax.main.setting.NumberSetting;
import cat.yoink.xanax.main.util.RenderUtil;
import cat.yoink.xanax.main.util.WorldUtil;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;

public final class FeetHighlight extends Module
{
    private final NumberSetting red = addSetting(new NumberSetting("Red", 50, 0, 255, 1));
    private final EnumSetting mode = addSetting(new EnumSetting("Mode", "OutHole", "OutHole", "InHole", "Always"));
    private final NumberSetting green = addSetting(new NumberSetting("Green", 50, 0, 255, 1));
    private final BooleanSetting self = addSetting(new BooleanSetting("Self", false));
    private final NumberSetting blue = addSetting(new NumberSetting("Blue", 50, 0, 255, 1));
    private final BooleanSetting box = addSetting(new BooleanSetting("Box", true));
    private final NumberSetting alpha = addSetting(new NumberSetting("Alpha", 100, 0, 255, 1));
    private final BooleanSetting outline = addSetting(new BooleanSetting("Outline", true));

    public FeetHighlight()
    {
        super("FeetESP", Category.RENDER);
    }

    @Listener
    public void render(Render3DEvent event)
    {
        mc.world.playerEntities.forEach(player -> {
            if (!self.getValue() && player.equals(mc.player)) return;

            if (mode.is("InHole"))
            {
                if (WorldUtil.isInHole(player)) RenderUtil.drawBox(new BlockPos(player.posX, player.posY, player.posZ), new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue(), (int) alpha.getValue()), box.getValue(), outline.getValue());
            }
            else if (mode.is("OutHole"))
            {
                if (!WorldUtil.isInHole(player)) RenderUtil.drawBox(new BlockPos(player.posX, player.posY, player.posZ), new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue(), (int) alpha.getValue()), box.getValue(), outline.getValue());
            }
            else
            {
                RenderUtil.drawBox(new BlockPos(player.posX, player.posY, player.posZ), new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue(), (int) alpha.getValue()), box.getValue(), outline.getValue());
            }
        });
    }
}
