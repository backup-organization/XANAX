package cat.yoink.xanax.main.module.modules.render;

import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.NumberSetting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class HitMarkers extends Module
{
    private final NumberSetting height = addSetting(new NumberSetting("Height", 20, 4, 40, 2));
    private final NumberSetting width = addSetting(new NumberSetting("Width", 20, 4, 40, 2));
    private final NumberSetting time = addSetting(new NumberSetting("Time", 25, 1, 50, 1));
    private final ResourceLocation image = new ResourceLocation("hitmarker.png");
    private int renderTicks;

    public HitMarkers()
    {
        super("HitMarkers", Category.RENDER);
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event)
    {
        if (nullCheck() || !event.getEntity().equals(mc.player)) return;

        renderTicks = 0;
    }

    @SubscribeEvent
    public void onTickClientTick(TickEvent.ClientTickEvent event)
    {
        if (nullCheck()) return;

        renderTicks++;
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event)
    {
        if (nullCheck() || !event.getType().equals(RenderGameOverlayEvent.ElementType.TEXT)) return;

        if (renderTicks < time.getValue())
        {
            ScaledResolution resolution = new ScaledResolution(mc);

            GlStateManager.enableBlend();
            mc.getTextureManager().bindTexture(image);
            Gui.drawModalRectWithCustomSizedTexture(resolution.getScaledWidth() / 2 - (int) width.getValue() / 2, resolution.getScaledHeight() / 2 - (int) height.getValue() / 2, 0, 0, (int) width.getValue(), (int) height.getValue(), (int) width.getValue(), (int) height.getValue());
            mc.getTextureManager().deleteTexture(image);
            GlStateManager.disableBlend();
        }
    }


}
